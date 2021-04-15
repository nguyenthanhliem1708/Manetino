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
import javax.servlet.http.HttpSession;
import thliem.daos.QuestionDAO;
import thliem.daos.SubjectDAO;
import thliem.dtos.QuestionDTO;
import thliem.dtos.SubjectDTO;
import thliem.dtos.UserDTO;

/**
 *
 * @author LiemNguyen
 */
public class ManagePageController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(ManagePageController.class);

    final String MANAGE_PAGE = "manage.jsp";
    final String LOGIN_PAGE = "login.jsp";

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
        String url = MANAGE_PAGE;
        try {
            HttpSession ss = request.getSession();
            if (ss.getAttribute("LOGIN_USER") != null) {
                if (((UserDTO) ss.getAttribute("LOGIN_USER")).getRoleID().equals("AD")) {

                    QuestionDAO quesDAO = new QuestionDAO();
                    SubjectDAO subjDAO = new SubjectDAO();

                    String indexStr = request.getParameter("index");
                    int index;
                    if(indexStr == null){
                        index = 1;
                    }else{
                        index = Integer.parseInt(indexStr);
                    }
                    List<QuestionDTO> quesList = quesDAO.getAllQuestionList(index);
                    List<SubjectDTO> subjList = subjDAO.loadSubjectList(0,0);
                    int numberOfQ = quesDAO.getPageNumber();
                    int pageNumber = numberOfQ / 6;
                    if (numberOfQ % 6 != 0) {
                        pageNumber++;
                    }
                    request.setAttribute("index", index);
                    request.setAttribute("pageNumber", pageNumber);
                    request.setAttribute("QUESTION_LIST", quesList);
                    request.setAttribute("SUBJECT_LIST", subjList);

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
