/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import thliem.daos.QuestionDAO;

/**
 *
 * @author LiemNguyen
 */
public class DeleteQuestionController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DeleteQuestionController.class);

    final String MANAGE = "ManagePageController";

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
        String url = MANAGE;
        try {
            String action = request.getParameter("btnAction");

            int questionId = Integer.parseInt(request.getParameter("questionId"));
            String questionContent = request.getParameter("questionContent");
            String subject = request.getParameter("subjectName");

            QuestionDAO dao = new QuestionDAO();

            if (action.equals("Delete")) {
                if (dao.deleteQuestion(questionId)) {
                    request.setAttribute("SUCESS_MESSAGE", "Successfully DEACTIVATE question : \n ID : " + Integer.toString(questionId) + "\nContent : " + questionContent + " Subject : " + subject);
                }
                else {
                    request.setAttribute("FAILED_MESSAGE", "Failed to DEACTIVATE question : \n ID : " + Integer.toString(questionId) + "\nContent : " + questionContent + " Subject : " + subject);
                }
            }else{
                 if (dao.restoreQuestion(questionId)) {
                    request.setAttribute("SUCESS_MESSAGE", "Successfully ACTIVATE question : \n ID : " + Integer.toString(questionId) + "\nContent : " + questionContent + " Subject : " + subject);
                }
                else {
                    request.setAttribute("FAILED_MESSAGE", "Failed to ACTIVATE question : \n ID : " + Integer.toString(questionId) + "\nContent : " + questionContent + " Subject : " + subject);
                }
            }
        }
        catch (NumberFormatException e) {
            LOG.error(e);
        }
        finally {
            request.getRequestDispatcher(url).forward(request, response);
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
