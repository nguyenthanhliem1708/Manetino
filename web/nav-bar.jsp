<%-- 
    Document   : nav-bar
    Created on : Jan 18, 2021, 1:28:20 AM
    Author     : LiemNguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://fonts.googleapis.com/css?family=Raleway:400,500,500i,700,800i" rel="stylesheet">
</head>

<nav class="navbar navbar-expand-sm   navbar-light bg-light">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item">
                <a class="nav-link" href="HomePageController">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="Quiz">Quiz</a>
            </li>

            <c:choose>
                <c:when test="${sessionScope.LOGIN_USER != null}">

                    <c:if test="${sessionScope.LOGIN_USER.roleID eq 'ST'}">
                        <li class="nav-item">
                            <a class="nav-link" href="HistoryPageController">History</a>
                        </li>
                    </c:if>

                    <c:if test="${sessionScope.LOGIN_USER.roleID eq 'AD'}">
                        <li class="nav-item">
                            <a class="nav-link" href="ManagePageController?index=1">Manage</a>
                        </li>
                    </c:if>

                    <li class="nav-item dropdown dmenu">
                        <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                            ${sessionScope.LOGIN_USER.name}
                        </a>
                        <div class="dropdown-menu sm-menu">
                            <a class="dropdown-item" href="LogoutController">Logout</a>
                        </div>
                    </li>

                </c:when>

                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>


        <div class="social-part">
            <i class="fa fa-graduation-cap" aria-hidden="true"></i>
            

        </div>
    </div>
</nav>

<style>
    .social-part .fa{
        padding-right:20px;
    }
    ul li a{
        margin-right: 20px;
    }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('.navbar-light .dmenu').hover(function () {
            $(this).find('.sm-menu').first().stop(true, true).slideDown(150);
        }, function () {
            $(this).find('.sm-menu').first().stop(true, true).slideUp(105);
        });
    });
</script>

