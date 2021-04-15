/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import thliem.daos.QuizDAO;
import thliem.dtos.QuestionDTO;
import thliem.dtos.QuizDTO;

/**
 *
 * @author LiemNguyen
 */
public class TakeTestController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(TakeTestController.class);
    private static final String QUIZ_PAGE = "quiz.jsp";
    private static final String HOME_PAGE = "HomePageController";

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
        String url = HOME_PAGE;

        try {
            HttpSession session = request.getSession();

            session.removeAttribute("QUIZ_ERROR");
            String subjectName = request.getParameter("subjectName");
            String subjectIdStr = request.getParameter("subjectId");
            int subjectId = Integer.parseInt(subjectIdStr);
            if (session.getAttribute("QUIZ") == null || subjectId == ((QuizDTO)(session.getAttribute("QUIZ"))).getSubjectId()) {
                String subjectQuantityStr = request.getParameter("quantityLimit");
                int quantityLimit = Integer.parseInt(subjectQuantityStr);
                String subjectTimeStr = request.getParameter("timeLimit");
                int timeLimit = Integer.parseInt(subjectTimeStr);
                QuizDAO dao = new QuizDAO();
                List<QuestionDTO> list = dao.getRandomQuestion(quantityLimit, subjectId);
                
                if (list != null) {
                    QuizDTO quiz = new QuizDTO();
                    Calendar time = Calendar.getInstance();
                    quiz.setStartTime(time.getTime());
                    time.add(Calendar.MINUTE, timeLimit);
                    quiz.setEndTime(time.getTime());
                    quiz.setList(list);
                    quiz.setSubjectId(subjectId);
                    session.setAttribute("QUIZ", quiz);
                    session.setAttribute("NUMBER_OF_PAGE", quantityLimit);
                    request.setAttribute("SUBJECT_ID", subjectId);
                    request.setAttribute("SUBJECT_NAME", subjectName);
                    url = QUIZ_PAGE;
                    
                }
            }
            else {
                session.setAttribute("QUIZ_ERROR", "Sorry,seem like  you're having another quiz going on. Please finish it to continue !");
            }
            
        }
        catch (Exception e) {
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
