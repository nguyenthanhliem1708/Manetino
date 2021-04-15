<%-- 
    Document   : quiz
    Created on : Feb 7, 2021, 12:13:58 AM
    Author     : LiemNguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quiz Page</title>
    </head>

    <body style="width: 600px;margin: auto;">
        <header
            <c:set var="quiz" value="${sessionScope.QUIZ}"/>
            <div style="text-align: center">
                <h1>${requestScope.SUBJECT_NAME}</h1>
                <h2>Student : ${sessionScope.LOGIN_USER.name}</h2>
            </div>
            <div>
                Remaining time : <span id="timer"/>
            </div>
        </header>

        <ul id="pagination"></ul>

        <c:set var="question" value="${quiz.list}"/>

        <form action="CompleteQuizController" method="POST" id="submit">
            <div class="btnFinish">
                <input type="submit" value="Finish">
            </div>
            <input type="hidden" value="${requestScope.SUBJECT_NAME}" name="subjectName">
            <c:forEach var="ques" items="${question}" varStatus="counter">
                <div class="contentPage">
                    <div>
                        ${counter.count} .  ${ques.questionContent}
                    </div>
                    <input type="hidden" value="${ques.questionId}" name="questionId${counter.count}">

                    <div>                  
                        <input type="radio" name="answer${counter.count}" value="${ques.ans1}"> 
                        A . ${ques.ans1}
                    </div>
                    <div>                  
                        <input type="radio" name="answer${counter.count}" value="${ques.ans2}"> 
                        B . ${ques.ans2}
                    </div>
                    <div>                  
                        <input type="radio" name="answer${counter.count}" value="${ques.ans3}"> 
                        C . ${ques.ans3}
                    </div>
                    <div>                  
                        <input type="radio" name="answer${counter.count}" value="${ques.ans4}"> 
                        D . ${ques.ans4}
                    </div>
                </div>
            </c:forEach>
        </form>
    </body>

    <script>
        function cal(timer) {
            var minutes = parseInt(timer / 60, 10);
            var seconds = parseInt(timer % 60, 10);

            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            document.querySelector('#timer').textContent = minutes + ":" + seconds;

        }

        function startTimer(duration) {
            var timer = duration;
            cal(timer);
            var intervalCount = setInterval(function () {
                cal(timer);

                if (--timer < 0) {
                    document.getElementById('submit').submit();
                    clearInterval(intervalCount);
                }

            }, 1000);
        }

        window.onload = function () {
            var endTime = ${sessionScope.QUIZ.endTime.time};
            var curTime = new Date().getTime();
            var diff = Math.round((endTime - curTime) / 1000);
            var fiveMinutes = diff;
            startTimer(fiveMinutes);
        };

    </script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.4/css/bootstrap.min.css" />
    <script src="https://code.jquery.com/jquery-3.2.1.js" ></script>

    <script src="http://1892.yn.lt/blogger/JQuery/Pagging/js/jquery.twbsPagination.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            var pageSize = 1;
            showPage = function (page) {
                $(".contentPage").hide();
                $(".contentPage").each(function (n) {
                    if (n >= pageSize * (page - 1) && n < pageSize * page)
                        $(this).show();
                });
            };
            showPage(1);

            var totalRows = ${sessionScope.NUMBER_OF_PAGE};
            var btnPage = 5;
            var iTotalPages = Math.ceil(totalRows / pageSize);

            var obj = $('#pagination').twbsPagination({
                totalPages: iTotalPages,
                visiblePages: btnPage,
                onPageClick: function (event, page) {
                    console.info(page);
                    showPage(page);
                }
            });
            console.info(obj.data());
        });
    </script>
    <style>
        #pagination {
            display: flex;
            display: -webkit-flex;
            flex-wrap: wrap;
            -webkit-flex-wrap: wrap;
            justify-content: center;
            -webkit-justify-content: center;
        }
    </style>


</html>
