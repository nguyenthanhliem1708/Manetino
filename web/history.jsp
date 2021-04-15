<%-- 
    Document   : history
    Created on : Feb 7, 2021, 5:54:47 AM
    Author     : LiemNguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
    </head>
    <body>
        <div>
            <nav>
                <!--Navigation bar-->
                <div id="nav-placeholder">
                    <script src="//code.jquery.com/jquery.min.js"></script>
                    <script>
                        $.get("nav-bar.jsp", function (data) {
                            $("#nav-placeholder").replaceWith(data);
                        });
                    </script>
            </nav>
            <!--end of nav-bar--> 
        </div>
        
    </body>
</html>
