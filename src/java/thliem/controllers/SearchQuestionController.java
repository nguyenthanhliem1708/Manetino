/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import thliem.daos.QuestionDAO;
import thliem.daos.SubjectDAO;
import thliem.dtos.QuestionDTO;
import thliem.dtos.SubjectDTO;

/**
 *
 * @author LiemNguyen
 */
public class SearchQuestionController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SearchQuestionController.class);
    final String RESULT_PAGE = "ResultPageController";

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
        String url = RESULT_PAGE;
        List<QuestionDTO> list = null;
        
        try {
            String indexStr = request.getParameter("index");
            int index, pageNumber = 0;
            if (indexStr == null) {
                index = 1;
            }
            else {
                index = Integer.parseInt(indexStr);
            }
            String questionContent = request.getParameter("searchContent");
            String subjectIdStr = request.getParameter("searchSubjectId");
            String selectedStatusStr = request.getParameter("searchStatus");
            String category = request.getParameter("searchCategory");

            QuestionDAO dao = new QuestionDAO();
            SubjectDAO subjDAO = new SubjectDAO();
            if (category.equals("content")) {
                pageNumber = dao.getSearchResultPageNumber(0, category, 0, true);
                list = dao.searchByContent(questionContent, index);
            }
            else if (category.equals("subject")) {
                int subjectId = Integer.parseInt(subjectIdStr);
                pageNumber = dao.getSearchResultPageNumber(1, questionContent, subjectId, true);
                list = dao.searchBySubject(subjectId, index);
            }
            else if (category.equals("status")) {
                boolean status = Boolean.parseBoolean(selectedStatusStr);
                pageNumber = dao.getSearchResultPageNumber(2, questionContent, 0, status);
                list = dao.searchByStatus(status, index);
            }
            List<SubjectDTO> subjList = subjDAO.loadSubjectList(0, 0);
            int numberOfQ = dao.getPageNumber();
            pageNumber = numberOfQ / 6;
            if (numberOfQ % 6 != 0) {
                pageNumber++;
            }
            request.setAttribute("index", index);
            request.setAttribute("pageNumber", pageNumber);
            request.setAttribute("SEARCH_CONTENT", questionContent);
            request.setAttribute("SUBJECT_SELECTED", subjectIdStr);
            request.setAttribute("STATUS_SELECTED", subjectIdStr);
            request.setAttribute("CATEGORY_SELECTED", category);
            request.setAttribute("QUESTION_LIST", list);
            request.setAttribute("SUBJECT_LIST", subjList);

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
