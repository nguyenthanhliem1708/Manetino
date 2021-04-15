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
import thliem.daos.UserDAO;
import thliem.dtos.UserDTO;
import thliem.utils.SHAUtil;

/**
 *
 * @author LiemNguyen
 */
public class LoginController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(LoginController.class);
    final private String LOGIN_PAGE = "LoginPageController";
    final private String INDEX_PAGE = "HomePageController";

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
        String url = LOGIN_PAGE;
        String loginErr = null;
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("LOGIN_USER") == null) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                UserDAO dao = new UserDAO();
               
                UserDTO dto = dao.checkLogin(email, SHAUtil.encryptPassword(password));
                if (dto != null) {
                    if (!dto.getStatus().equals("Deactived")) {
                        HttpSession ss = request.getSession();
                        ss.setAttribute("LOGIN_USER",dto);
                        url = INDEX_PAGE;
                    }
                    else {
                        loginErr = "This account is inactive, please contact for futher support";
                    }
                }
                else {
                    loginErr = "Incorrect username or password, Please try again";
                }
                if (loginErr != null) {
                    session.setAttribute("LOGIN_MESSAGE", loginErr);
                }
            }
        }
        catch (Exception e) {
            LOG.error(e);
        }
        finally {
            response.sendRedirect(url);
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
