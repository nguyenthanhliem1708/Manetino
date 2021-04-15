/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thliem.daos.QuizDAO;
import thliem.dtos.HistoryDTO;
import thliem.dtos.QuestionDTO;
import thliem.dtos.QuizDTO;
import thliem.dtos.UserDTO;

/**
 *
 * @author LiemNguyen
 */
public class CompleteQuizController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CompleteQuizController.class);
    private final String QUIZ_RESULT_PAGE = "ToResultPageController";

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
        String url = QUIZ_RESULT_PAGE;
        try {
            HttpSession ss = request.getSession();
            String subjectName = request.getParameter("subjectName");
            int numbOfPage = (int) ss.getAttribute("NUMBER_OF_PAGE");

            UserDTO user = (UserDTO) ss.getAttribute("LOGIN_USER");

            Map<Integer, String> ansList = new HashMap<>();

            for (int i = 1; i <= numbOfPage; i++) {
                String idStr = request.getParameter("questionId" + i);
                int questionId = Integer.parseInt(idStr);
                String ans = request.getParameter("answer" + i);
                if (ans == null) {
                    ans = "";
                }
                ansList.put(questionId, ans);
            }
            QuizDTO quiz = (QuizDTO) ss.getAttribute("QUIZ");
            List<QuestionDTO> qList = quiz.getList();
            int correctCount = 0;
            float mark = 10;
            float point = 10 / Float.parseFloat(Integer.toString(numbOfPage));
            for (Map.Entry<Integer, String> stdAns : ansList.entrySet()) {
                for (QuestionDTO ques : qList) {
                    System.out.println(stdAns.getKey() + " : " + ques.getQuestionId());
                    if (stdAns.getKey() == ques.getQuestionId()) {
                        if (stdAns.getValue().equalsIgnoreCase(ques.getAnswerCorrect())) {
                            correctCount++;
                        }
                        else {
                            mark -= point;
                        }
                    }
                }
            }
            QuizDAO dao = new QuizDAO();
            HistoryDTO dto = new HistoryDTO(-1, user.getEmail(), quiz.getSubjectId(), numbOfPage, correctCount, mark, java.sql.Date.valueOf(LocalDate.now()));

            int quizId = dao.createQuiz(dto);
            for (Map.Entry<Integer, String> stdAns : ansList.entrySet()) {
                dao.createDetail(stdAns.getKey(), stdAns.getValue(), quizId);
            }
            dto.setQuizId(quizId);
            request.setAttribute("RESULT", dto);
            request.setAttribute("SUBJECT_NAME", subjectName);
            ss.removeAttribute("QUIZ");

        }
        catch (Exception e) {
            System.out.println(e);
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
