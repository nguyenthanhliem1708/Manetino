<%-- 
    Document   : index
    Created on : Jan 27, 2021, 10:56:42 PM
    Author     : LiemNguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME</title>
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->

    </head>

    <body >
       
        <c:if test="${sessionScope.QUIZ_ERROR != null}"> 
                <script>  
                    alert("${sessionScope.QUIZ_ERROR}");
                </script>
            </c:if>
      
        
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

        <c:set var="subjectList" value="${requestScope.LIST}"/>

        <c:forEach var="sub" items="${subjectList}">
       
                <div class="col-md-2 column productbox">
                    <div class="producttitle">${sub.subjectName}</div>
                    <c:url var="subInfo" value="TakeTestController">
                        <c:param name="subjectName" value="${sub.subjectName}"/>
                        <c:param name="subjectId" value="${sub.subjectId}"/>
                        <c:param name="quantityLimit" value="${sub.quantityLimit}"/>
                        <c:param name="timeLimit" value="${sub.timeLimit}"/>
                    </c:url>
                    <div class="productprice"><div class="pull-right"><a href="${subInfo}" class="btn btn-primary btn-sm" role="button">Attempt</a></div><div class="pricetext">${sub.timeLimit} mins</div></div>
                </div>
           
        </c:forEach>
    </body>
</html>


<style>
    body{
        zoom: 135%; 
    }
    .productbox {
        background-color:#ffffff;
        padding:1% 3% 1% 3%;
        margin-bottom:10px;
        -webkit-box-shadow: 0 8px 6px -6px  #999;
        -moz-box-shadow: 0 8px 6px -6px  #999;
        box-shadow: 0 8px 6px -6px #999;
    }

    .producttitle {
        font-weight:bold;
        padding:5px 0 5px 0;
    }

    .productprice {
        border-top:1px solid #dadada;
        padding-top:5px;
    }

    .pricetext {
        font-weight:bold;
        font-size:1.4em;
    }
</style>