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
import thliem.daos.SubjectDAO;
import thliem.dtos.SubjectDTO;

/**
 *
 * @author LiemNguyen
 */
public class HomePageController extends HttpServlet {
    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(HomePageController.class);
    final String HOME_PAGE = "index.jsp";
    
    
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
        String url =HOME_PAGE;
        try {
            //do sth here
            //list all the valid quiz(subject) that can attemp and
            
            SubjectDAO dao = new SubjectDAO();
            List<SubjectDTO> list = dao.loadSubjectList(0,0);
            
            for (int i = 0; i < list.size(); i++) {
                if(dao.getSubjectSize(list.get(i).getSubjectId()) < list.get(i).getQuantityLimit()){
                    list.remove(i);
                    i--;
                }
            }
            //send the requested LIST to index.jsp
            request.setAttribute("LIST", list);
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
