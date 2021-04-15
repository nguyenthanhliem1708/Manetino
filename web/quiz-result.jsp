<%-- 
    Document   : quiz-result
    Created on : Feb 7, 2021, 5:37:08 AM
    Author     : LiemNguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Result</title>
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

        <c:set var="result" value="${requestScope.RESULT}"/>
        <div style="text-align: center">
            <h1>${sessionScope.LOGIN_USER.name}</h1>
        </div>

        <div>
            <label>Subject : ${requestScope.SUBJECT_NAME}</label><br>

            <label>Email : ${result.email}</label><br>
            <label>Result : ${result.totalCorrect} / ${result.totalQuestion}</label><br>
            <label>Mark : ${result.mark} </label><br>
            <label>Submit on : ${result.createDate}</label>
        </div>
    </body>
</html>
