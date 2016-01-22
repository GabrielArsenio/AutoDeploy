package com.deploy.sistema.controller;

import com.deploy.sistema.util.LocalShell;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gabriel
 */
public class PuxarServlet extends HttpServlet {

    private final String stringMestre = "source /etc/bash.bashrc;cd /home/projetos/#NOME_PROJETO#;git #COMANDO_GIT# #REPOSITORIO_GIT#;ant -f \"#PASTA_PROJETO#\" -Dj2ee.server.home=$CATALINA_HOME -Dnb.internal.action.name=rebuild -DforceRedeploy=false \"-Dbrowser.context=#PASTA_PROJETO#\" clean dist;cp #PASTA_PROJETO#/dist/*.war /opt/tomcat8/webapps/";
    private final String pastaProjeto = "/home/projetos/#NOME_PROJETO#";
    private String stringMestreImpl;
    private String pastaProjetoImpl;
    private LocalShell shell;
    private String nome;
    private String git;
    private String token;

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
            token = request.getParameter("token");            
            String gitArray[] = git.split("/");        
            nome = gitArray[gitArray.length - 1].replaceAll(".git", "");            
            
            stringMestreImpl = stringMestre.replaceAll("#REPOSITORIO_GIT#", git);
            pastaProjetoImpl = pastaProjeto.replaceAll("#NOME_PROJETO#", nome);

            if (new File(pastaProjetoImpl).exists()) {
                stringMestreImpl = stringMestreImpl.replaceAll("#NOME_PROJETO#", nome);
                stringMestreImpl = stringMestreImpl.replaceAll("#COMANDO_GIT#", "pull");
            } else {
                stringMestreImpl = stringMestreImpl.replaceAll("#NOME_PROJETO#", "");
                stringMestreImpl = stringMestreImpl.replaceAll("#COMANDO_GIT#", "clone");
            }

            stringMestreImpl = stringMestreImpl.replaceAll("#PASTA_PROJETO#", pastaProjetoImpl);
            /* Fim Montagem Strings */

            /* Valida Token */
            Scanner scan = new Scanner(new FileReader("/home/token.txt"));
            if (token.equals(scan.next())) {
                shell = new LocalShell();
                shell.executeCommand(stringMestreImpl);
                stringMestreImpl = stringMestreImpl + "<br>" + shell.getSaida();
            } else {
                stringMestreImpl = "Token Inválido.";
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Deploy - Resultado</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(request.getParameter("git"));
            out.println("<br>");
            out.println(request.getParameter("nome"));
            out.println("<br>");
            out.println(stringMestreImpl);
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
