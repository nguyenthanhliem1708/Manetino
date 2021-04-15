/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thliem.filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import thliem.dtos.UserDTO;

/**
 *
 * @author LiemNguyen
 */
public class AuthenFilter implements Filter {

    private final static Logger LOG = Logger.getLogger(AuthenFilter.class);
    private final List<String> USER;
    private final List<String> ADMIN;
    private final String LOGIN = "LoginPageController";
    private final String AD = "AD";
    private final String US = "ST";
    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthenFilter() {
        //  --------  User Role --------- //
        USER = new ArrayList<>();
        USER.add("index.jsp");
        USER.add("login.jsp");
        USER.add("nav-bar.jsp");
        USER.add("quiz.jsp");
        USER.add("quiz-result.jsp");
        
        
        USER.add("CompleteQuizController");
        USER.add("ToTestController");
        USER.add("HomePageController");
        //Attempt the quiz controller
        USER.add("TakeTestController");
        //  --------  Admin Role --------- //
        ADMIN = new ArrayList<>();
        /**
         * Questions Action
         */
        //Create Question
        ADMIN.add("CreateQuestionController");
        ADMIN.add("CreateQuestionPageController");
        //Update Question
        ADMIN.add("UpdateQuestionController");
        //Delete Question
        ADMIN.add("DeleteQuestionController");
        //Search Question
        ADMIN.add("SearchQuestionController");
        ADMIN.add("ResultPageController");
        //Dispatcher
        ADMIN.add("UpdateDeleteDispatcherController");

        /**
         * Subjects Action
         */
        //Create Subject
        
        ADMIN.add("CreateSubjectController");
        ADMIN.add("UpdateSubjectController");

        //HOME PAGE
        ADMIN.add("SubjectPageController");
        ADMIN.add("HomePageController");
        ADMIN.add("ManagePageController");
        ADMIN.add("create-question.jsp");
        ADMIN.add("index.jsp");
        ADMIN.add("login.jsp");
        ADMIN.add("nav-bar.jsp");
        ADMIN.add("search-result.jsp");
        ADMIN.add("subject.jsp");
        ADMIN.add("manage.jsp");
        

    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoBeforeProcessing");
        }      
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenFilter:DoAfterProcessing");
        }
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("AuthenFilter:doFilter()");
        }

        doBeforeProcessing(request, response);

        Throwable problem = null;
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;
            String uri = req.getRequestURI();
            if (uri.contains(".jpg") || uri.contains(".png") || uri.contains(".gif")) {
                chain.doFilter(request, response);
            }
            else {
                if (uri.contains("CreateAccountController") || uri.contains("CreateAccountPageController")
                        || uri.contains("LoginController") || uri.contains("LoginPageController")
                        || uri.contains("LogoutController")) {
                    chain.doFilter(request, response);
                    return ;
                }
                int index = uri.lastIndexOf("/");
                String resource = uri.substring(index + 1);
                HttpSession session = req.getSession();
                if (session == null || session.getAttribute("LOGIN_USER") == null) {
                    res.sendRedirect(LOGIN);
                }
                else {
                    UserDTO user = (UserDTO) session.getAttribute("LOGIN_USER");
                    String roleID = user.getRoleID();
                    if (roleID.equals(AD) && ADMIN.contains(resource)) {
                        chain.doFilter(request, response);
                    }
                    else if (US.equals(roleID) && USER.contains(resource)) {
                        chain.doFilter(request, response);
                    }
                    else {
                        res.sendRedirect(LOGIN);
                    }
                }
            }
        }
        catch (IOException | ServletException e) {
            System.out.println(e);
            LOG.error(e);
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex) {
            }
        }
        else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        }
        catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
