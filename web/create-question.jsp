<%-- 
    Document   : create-question
    Created on : Jan 26, 2021, 8:55:58 PM
    Author     : LiemNguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Question</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <!------ Include the above in your HEAD tag ---------->

    </head>
    <body>

        <c:if test="${not empty requestScope.FAILED_MESSAGE}">
            <script>alert('${requestScope.FAILED_MESSAGE}');</script>
        </c:if>
        <c:if test="${not empty requestScope.SUCCESS_MESSAGE}">
            <script>alert('${requestScope.SUCCESS_MESSAGE}');</script>
        </c:if>
        <c:if test="${not empty requestScope.errDTO.DUPLICATE_ANSWER}">
            <script>alert('${requestScope.errDTO.DUPLICATE_ANSWER}');</script>
        </c:if>
        
        
        
        <form action="CreateQuestionController" method="POST">
            <div class="container contact">
                <div class="row">
                    <div class="col-md-3">
                        <div class="contact-info">

                            <img src="https://i.ibb.co/55wLcgx/pngfind-com-q-and-a-png-6087157.png" alt="image"/>
                            <h2>Create Question</h2>
                            <h4>Contribute to the question bank by your new question</h4>
                            <a href="HomePageController">HOME PAGE</a><br>
                            <a href="ManagePageController">MANAGE PAGE</a>
                        </div>
                    </div>
                    <div class="col-md-9">
                        <div class="contact-form">
                            <label class="control-label col-sm-2" for="comment">Subject : </label>
                            <c:set var="list" value="${requestScope.SUBJECT_LIST}"/>
                            <div class="col-sm-10">
                                <select name="subjID">
                                    <c:forEach var="subject" items="${list}">
                                        <option value="${subject.subjectId}" <c:if test="${requestScope.subjectId eq subject.subjectId}">selected</c:if>>${subject.subjectName}</option> 
                                    </c:forEach> 
                                </select>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-sm-2" for="comment">Question</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control" rows="5" id="comment" name="questionContent" required>${requestScope.questionContent}</textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="fname">Answer A :</label>
                                <div class="col-sm-10">          
                                    <input type="text" class="form-control" id="fname" placeholder="Enter First Answer" name="ans1" required value="${requestScope.ans1}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="lname">Answer B :</label>
                                <div class="col-sm-10">         
                                    <input type="text" class="form-control" id="lname" placeholder="Enter Second Answer" name="ans2" value="${requestScope.ans2}">
                                </div>
                            </div> 
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="Iname">Answer C :</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="Iname" placeholder="Enter Third Answer" name="ans3" value="${requestScope.ans3}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="Iname">Answer D :</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="Iname" placeholder="Enter Fourth Answer" name="ans4" value="${requestScope.ans4}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="email">Correct Answer :</label>
                                <div class="col-sm-10">
                                    <select name="correctAns">
                                        <option value="ans1">Answer A</option> 
                                        <option value="ans2">Answer B</option> 
                                        <option value="ans3">Answer C</option> 
                                        <option value="ans4">Answer D</option> 
                                    </select>
                                </div>
                                <span>${requestScope.ERROR_DTO.correctAnsErr}</span>
                            </div>

                            <div class="form-group">        
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" name="btnAction" class="btn btn-default" onclick="return confirm('Do you with to continue... ?');">Create</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>


<style>
    body{
        background-color: #25274d;
    }
    .contact{
        padding: 4%;
        height: 400px;
    }
    .col-md-3{
        background: #ff9b00;
        padding: 4%;
        border-top-left-radius: 0.5rem;
        border-bottom-left-radius: 0.5rem;
    }
    .contact-info{
        margin-top:10%;
    }
    .contact-info img{
        margin-bottom: 15%;
    }
    .contact-info h2{
        margin-bottom: 10%;
    }
    .col-md-9{
        background: #fff;
        padding: 3%;
        border-top-right-radius: 0.5rem;
        border-bottom-right-radius: 0.5rem;
    }
    .contact-form label{
        font-weight:600;
    }
    .contact-form button{
        background: #25274d;
        color: #fff;
        font-weight: 600;
        width: 25%;
    }
    .contact-form button:focus{
        box-shadow:none;
    }
</style>