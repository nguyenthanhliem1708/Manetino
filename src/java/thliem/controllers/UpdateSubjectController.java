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
import thliem.daos.SubjectDAO;

/**
 *
 * @author LiemNguyen
 */
public class UpdateSubjectController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(UpdateSubjectController.class);
    final String SUBJECT_PAGE = "SubjectPageController";

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
        String url = SUBJECT_PAGE;
        try {
            String subjectName = request.getParameter("updateSubjectName");
            String subjectIdStr = request.getParameter("updateSubjectId");
            int subjectId = Integer.parseInt(subjectIdStr);
            String subjectQuantityStr = request.getParameter("updateSubjectQuantity");
            int subjectQuantity = Integer.parseInt(subjectQuantityStr);
            String subjectTimeStr = request.getParameter("updateSubjectTime");
            int subjectTime = Integer.parseInt(subjectTimeStr);
            
            SubjectDAO dao = new SubjectDAO();
            if (!subjectName.trim().isEmpty()) {
                if (dao.checkDupSubject(subjectName,subjectId)) {
                    if(dao.updateSubject(subjectName, subjectId,subjectQuantity,subjectTime)){
                        request.setAttribute("UPDATE_MESS", "Successfully update subject with  ID :  "+subjectIdStr +" Name : " + subjectName);
                    }else{
                         request.setAttribute("UPDATE_MESS", "Failed to update subject with\n ID :  "+subjectIdStr);
                    }
                }
                else {
                    request.setAttribute("DUPLICATED_SUBJECT_NAME_ERR", "Duplicated subject's name");
                }

            }else{
                
                request.setAttribute("INVALID_UPDATE_SUBJECT_NAME_ERR", "Invalid subject name");
            }
        }
         catch(NumberFormatException e){
            request.setAttribute("NUMBERIC_ERROR", "Numberic error, Please check again");
            LOG.error(e);
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
