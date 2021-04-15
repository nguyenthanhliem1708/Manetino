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
import thliem.dtos.UpdateQuestionErrDTO;

/**
 *
 * @author LiemNguyen
 */
public class UpdateQuestionController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UpdateQuestionController.class);
    final String MANAGE = "ManagePageController";
    UpdateQuestionErrDTO errDTO;
    QuestionDAO dao = new QuestionDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        errDTO = new UpdateQuestionErrDTO();
        String url = MANAGE;
        try {
            String questionIdStr = request.getParameter("questionId");

            String subjectIDStr = request.getParameter("subjectId");

            String questionContent = request.getParameter("questionContent");
            String ans1 = request.getParameter("ans1");
            String ans2 = request.getParameter("ans2");
            String ans3 = request.getParameter("ans3");
            String ans4 = request.getParameter("ans4");
            String correctAns = null;
            String ansCheckbox = request.getParameter("ansCheckbox");

            correctAns = ansCheckbox;

            int subjectId = Integer.parseInt(subjectIDStr);
            int questionId = Integer.parseInt(questionIdStr);
            if (checkQuestionInfo(questionId, subjectIDStr, questionContent, ans1, ans2, ans3, ans4, correctAns)) {
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
                boolean success = dao.updateQuestion(questionId, subjectId, questionContent, ans1, ans2, ans3, ans4, correctAns);
                if (!success) {
                    request.setAttribute("FAILED_MESSAGE", "Failed to UPDATE this question,Please try again");
                }
                else {
                    request.setAttribute("SUCCESS_MESSAGE", "Successfully UPDATE question");
                }
            }
            else {
                request.setAttribute("ERROR_DTO", errDTO);
            }

            //do sth here
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private boolean checkQuestionInfo(int questionId, String subjectID, String questionContent, String ans1, String ans2, String ans3, String ans4, String correctAns) {
        boolean valid = true;
        try {
            //answer content is also unique
            if (!subjectID.trim().isEmpty()) {
                if (!questionContent.trim().isEmpty()) {
                    //question content is unique,
                    if (checkDupAnswer(ans1, ans2, ans3, ans4)) {
                        if (dao.checkQuestionExist(questionContent, 0, questionId)) {
                            if (!ans1.trim().isEmpty()) {
                                if (correctAns.equals("ans2")) {
                                    if (ans2.trim().isEmpty()) {
                                        valid = false;
                                        errDTO.setCorrectAnsErr("Can't choose this answer");
                                    }
                                }
                                else if (correctAns.equals("ans3")) {
                                    if (ans3.trim().isEmpty()) {
                                        valid = false;
                                        errDTO.setCorrectAnsErr("Can't choose this answer");
                                    }
                                }
                                else if (correctAns.equals("ans4")) {
                                    if (ans4.trim().isEmpty()) {
                                        valid = false;
                                        errDTO.setCorrectAnsErr("Can't choose this answer");
                                    }
                                }
                            }
                            else {
                                valid = false;
                            }
                        }
                        else {
                            errDTO.setDupQuestionErr("Duplicated question detected");
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
