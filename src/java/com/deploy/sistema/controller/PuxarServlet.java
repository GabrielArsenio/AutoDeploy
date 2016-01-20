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

    private String cdPastaProjeto = "cd /home/projetos/#NOME_PROJETO#";
    private String gitPull = "git pull #REPOSITORIO_GIT#";
    private String gitClone = "git clone #REPOSITORIO_GIT#";
    private String antCleanDist = "ant -f \"/home/projetos/#NOME_PROJETO#\" -Dj2ee.server.home=$CATALINA_HOME -Dnb.internal.action.name=rebuild -DforceRedeploy=false \"-Dbrowser.context=/home/projetos/#NOME_PROJETO#\" clean dist";
    private String cpPastaWebapps = "cp /home/projetos/#NOME_PROJETO#/dist/#WAR_PROJETO# /opt/tomcat8/webapps/";
    private String war = "";
    private String pastaProjetos = "/home/projetos/";

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
            cdPastaProjeto = cdPastaProjeto.replaceAll("#NOME_PROJETO#", request.getParameter("nome"));
            gitPull = gitPull.replaceAll("#REPOSITORIO_GIT#", request.getParameter("git"));
            gitClone = gitClone.replaceAll("#REPOSITORIO_GIT#", request.getParameter("git"));
            antCleanDist = antCleanDist.replaceAll("#NOME_PROJETO#", request.getParameter("nome"));
            cpPastaWebapps = cpPastaWebapps.replaceAll("#NOME_PROJETO#", request.getParameter("nome"));
            pastaProjetos = pastaProjetos.replaceAll("#NOME_PROJETO#", request.getParameter("nome"));

            LocalShell shell = new LocalShell();
            if (new File(pastaProjetos + request.getParameter("nome")).exists()) {
                shell.executeCommand(cdPastaProjeto);
                shell.executeCommand(gitPull);
            } else {
                shell.executeCommand("cd " + pastaProjetos);
                shell.executeCommand(gitClone);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PuxarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println(request.getParameter("git"));
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
