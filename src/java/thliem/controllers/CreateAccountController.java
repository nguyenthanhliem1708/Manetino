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
import thliem.dtos.CreateAccountErrDTO;
import thliem.dtos.UserDTO;
import thliem.utils.SHAUtil;

/**
 *
 * @author LiemNguyen
 */
public class CreateAccountController extends HttpServlet {

    final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CreateAccountController.class);
    final String REGISTER_PAGE = "register.jsp";
    final String INDEX_PAGE = "index.jsp";
    final String LOGIN_PAGE = "login.jsp";

    final private String EMAIL_REGEX = "^[A-Za-z]([A-Za-z0-9._]{5,15})+@fpt.edu.vn$";
    final private int MIN_PASSWORD_LENGTH = 6;
    final private int MAX_PASSWORD_LENGTH = 16;
    final private int MAX_NAME_LENGTH = 50;

    CreateAccountErrDTO errDTO = null;

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

        HttpSession session = request.getSession();

        String url = REGISTER_PAGE;
        try {
            if (((UserDTO) session.getAttribute("LOGIN_USER")) == null) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (checkAccountInfo(name, email, password)) {
                    UserDAO dao = new UserDAO();

                    if (dao.createNewAccount(name, email, SHAUtil.encryptPassword(password))) {
                        url = LOGIN_PAGE;

                        session.setAttribute("LOGIN_MESSAGE", "Please login with your new account to continue");
                    }
                }
                else {
                    request.setAttribute("REGISTER_ERROR", errDTO);
                }
            }
            else {
                url = INDEX_PAGE;
            }
        }
        catch (Exception e) {
            System.out.println(e);
            LOG.error(e);
        }
        finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    private boolean checkAccountInfo(String name, String email, String password) {
        boolean valid = true;
        String nameErr = null, emailErr = null, passwordErr = null;
        if (name.length() > MAX_NAME_LENGTH || name.trim().isEmpty() || !name.matches("[a-zA-Z ]{0,50}")) {
            valid = false;
            nameErr = "Invalid name inputted";
        }

        if (!email.matches(EMAIL_REGEX)) {
            valid = false;
            emailErr = "Invalid email inputted";
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            valid = false;
            passwordErr = "Invalid password inputted";
        }

        if (!valid) {
            errDTO = new CreateAccountErrDTO(nameErr, emailErr, passwordErr);
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
