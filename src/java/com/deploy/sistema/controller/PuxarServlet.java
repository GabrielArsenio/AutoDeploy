package com.deploy.sistema.controller;

import com.deploy.sistema.util.LocalShell;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel
 */
public class PuxarServlet extends HttpServlet {

    private String stringMestre = "source /etc/bash.bashrc;cd /home/projetos/#NOME_PROJETO#;git #COMANDO_GIT# #REPOSITORIO_GIT#;chmod 777 /home/projetos/#NOME_PROJETO#;ant -f \"#PASTA_PROJETO#\" -Dtomcat.home=$CATALINA_HOME -Dj2ee.server.home=$CATALINA_HOME -Dnb.internal.action.name=rebuild -DforceRedeploy=false \"-Dbrowser.context=#PASTA_PROJETO#\" clean dist;cp #PASTA_PROJETO#/dist/*.war /opt/tomcat8/webapps/";
    private String pastaProjeto = "/home/projetos/#NOME_PROJETO#";
    private String nome;
    private String git;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            /* Montando Strings */
            git = request.getParameter("git");
            nome = request.getParameter("nome");
            stringMestre = stringMestre.replaceAll("#REPOSITORIO_GIT#", git);
            pastaProjeto = pastaProjeto.replaceAll("#NOME_PROJETO#", nome);

            if (new File(pastaProjeto).exists()) {
                stringMestre = stringMestre.replaceAll("#NOME_PROJETO#", nome);
                stringMestre = stringMestre.replaceAll("#COMANDO_GIT#", "pull");
            } else {
                stringMestre = stringMestre.replaceAll("#NOME_PROJETO#", "");
                stringMestre = stringMestre.replaceAll("#COMANDO_GIT#", "clone");
            }
            
            stringMestre = stringMestre.replaceAll("#PASTA_PROJETO#", pastaProjeto);

            LocalShell shell = new LocalShell();
            shell.executeCommand(stringMestre);
            shell.executeCommand("echo catalina $CATALINA_HOME;echo java $JAVA_HOME;echo ant $ANT_HOME;echo path $PATH;");

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PuxarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(request.getParameter("git"));
            out.println(stringMestre);
            out.println(shell.getSaida());
            out.println("<h1>Servlet PuxarServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
