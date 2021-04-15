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
import javax.servlet.http.HttpSession;
import thliem.daos.QuestionDAO;
import thliem.dtos.CreateQuestionErrDTO;
import thliem.dtos.UserDTO;

/**
 *
 * @author LiemNguyen
 */
public class CreateQuestionController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CreateQuestionController.class);

    final String CREATE_QUESTION = "CreateQuestionPageController";
    final String LOGIN_PAGE = "login.jsp";
    CreateQuestionErrDTO errDTO = new CreateQuestionErrDTO();
    QuestionDAO dao = new QuestionDAO();

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
        String url = CREATE_QUESTION;

        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("LOGIN_USER") != null) {
                if (((UserDTO) session.getAttribute("LOGIN_USER")).getRoleID().equals("AD")) {
                    String subjectID = request.getParameter("subjID");
                    String questionContent = request.getParameter("questionContent");
                    String ans1 = request.getParameter("ans1");
                    String ans2 = request.getParameter("ans2");
                    if(ans2==null){
                        ans2 = "";
                    }
                    String ans3 = request.getParameter("ans3");
                    if(ans3==null){
                        ans3 = "";
                    }
                    String ans4 = request.getParameter("ans4");
                    if(ans4==null){
                        ans4 = "";
                    }
                    String correctAns = request.getParameter("correctAns");

                    //check question info 
                    if (checkQuestionInfo(subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns)) {
                        //then
                        if (correctAns.equalsIgnoreCase("ans1")) {
                            correctAns = ans1;
                        }
                        else if (correctAns.equalsIgnoreCase("ans2")) {
                            correctAns = ans2;
                        }
                        else if (correctAns.equalsIgnoreCase("ans3")) {
                            correctAns = ans3;
                        }
                        else if (correctAns.equalsIgnoreCase("ans4")) {
                            correctAns = ans4;
                        }

                        boolean success = dao.addNewQuestion(subjectID, questionContent, ans1, ans2, ans3, ans4, correctAns);
                        if (!success) {
                            request.setAttribute("FAILED_MESSAGE", "Failed to add this question,Please try again");
                        }
                        else {
                            request.setAttribute("SUCCESS_MESSAGE", "Successfully added new question");
                        }
                    }
                    else {
                        request.setAttribute("ERROR_DTO", errDTO);
                        request.setAttribute("subjectID", subjectID);
                        request.setAttribute("questionContent", questionContent);
                        request.setAttribute("ans1", ans1);
                        request.setAttribute("ans2", ans2);
                        request.setAttribute("ans3", ans3);
                        request.setAttribute("ans4", ans4);
                        request.setAttribute("correctAns", correctAns);
                    }
                }
            }
            else {
                url = LOGIN_PAGE;
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private boolean checkQuestionInfo(String subjectID, String questionContent, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        boolean valid = true;
        try {
            //question content is unique
            if (!subjectID.trim().isEmpty()) {
                if (!questionContent.trim().isEmpty()) {
                    //answer content is also unique
                    if (checkDupAnswer(ans1, ans2, ans3, ans4)) {
                        if (dao.checkQuestionExist(questionContent, 1, 0)) {
                            if (!ans1.trim().isEmpty()) {
                                switch (correctAns) {
                                    case ("ans2"): {
                                        if (ans2.trim().isEmpty()) {
                                            valid = false;
                                            errDTO.setCorrectAnsErr("Can't choose this answer");
                                        }
                                    }
                                    case ("ans3"): {
                                        if (ans3.trim().isEmpty()) {
                                            valid = false;
                                            errDTO.setCorrectAnsErr("Can't choose this answer");
                                        }
                                    }
                                    case ("ans4"): {
                                        if (ans4.trim().isEmpty()) {
                                            valid = false;
                                            errDTO.setCorrectAnsErr("Can't choose this answer");
                                        }
                                    }
                                }
                            }
                            else {
                                valid = false;
                            }
                        }
                        else {
                            errDTO.setQuestionContentErr("Duplicated question detected");
                        }
                    }
                    else {
                        errDTO.setDupAnswerErr("Duplicated answer detected");
                    }
                }
                else {
                    valid = false;
                }
            }
            else {
                valid = false;
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return valid;
    }

     private boolean checkDupAnswer(String ans1, String ans2, String ans3, String ans4) {
        boolean valid = true;

        if (!ans1.equalsIgnoreCase(ans2) || !ans1.equalsIgnoreCase(ans3) || !ans1.equalsIgnoreCase(ans4)) {
            if (!ans2.trim().isEmpty()) {
                if (ans2.equalsIgnoreCase(ans3) || ans2.equalsIgnoreCase(ans4)) {
                    valid = false;
                }
            }
            if (!ans3.isEmpty() && !ans4.isEmpty()) {
                if (ans3.equalsIgnoreCase(ans4)) {
                    valid = false;
                }
            }
        }
        else {
            valid = false;
        }
        return valid;
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
