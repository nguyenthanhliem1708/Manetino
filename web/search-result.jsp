<%-- 
    Document   : search-result
    Created on : Jan 31, 2021, 1:42:11 AM
    Author     : LiemNguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Result</title>
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    </head>

    <body>

        <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>
        <c:set var="index" value="${requestScope.index}"/>
        <c:set var="pageNumber" value="${requestScope.pageNumber}"/>


        <!-- Navigations -->
        <div class="nav">
            <a href="HomePageController">Home </a>   
            <a href="CreateQuestionPageController">Create Question</a>
            <a href="SubjectPage?index=1">Subject</a>
        </div>


        <!--Search Form-->
        <div class="container">
            <div class="row">
                <div class="span3">
                    <form method="POST" action="SearchQuestionController" class="form-inline" >
                        <div id="contentTxt" <c:if test="${requestScope.CATEGORY_SELECTED  ne 'content'}">hidden</c:if>>
                                <input name="searchContent" class="span4" type="text"  placeholder="Question content" >
                            </div>

                            <div  id="subjectCmb" <c:if test="${requestScope.CATEGORY_SELECTED  ne 'subject'}">hidden</c:if>>
                                <select name="searchSubjectId" class="span4">
                                <c:forEach var="subj" items="${subjectList}">
                                    <c:set var="subjID" value="${subj.subjectId}"/>
                                    <option value="${subjID}" <c:if test="${subjID eq requestScope.SUBJECT_SELECTED}">selected</c:if>>${subj.subjectName}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div id="statusCmb"<c:if test="${requestScope.CATEGORY_SELECTED  ne 'status'}">hidden</c:if>>
                                <select name="searchStatus" class="span4">
                                    <option value="true">Active</option>
                                    <option value="false">Inactive</option>
                                </select>
                            </div>

                            <select name="searchCategory" class="span3">
                                <option value="content" onclick="toQuestionContent();" <c:if test="${CATEGORY_SELECTED eq 'content'}">selected</c:if>>Question Content</option>
                            <option value="status" onclick="toStatus();" <c:if test="${CATEGORY_SELECTED eq 'status'}">selected</c:if>>Status</option>
                            <option value="subject"onclick="toSubject();" <c:if test="${CATEGORY_SELECTED eq 'subject'}">selected</c:if>>Subject</option>
                            </select>
                            <button type="submit" name="btnAction" value="Search" class="btn btn-primary"> <i class="icon-search icon-white"></i></button>
                        </form>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                function toQuestionContent() {
                    document.getElementById("contentTxt").style.display = "inline";
                    document.getElementById("subjectCmb").style.display = "none";
                    document.getElementById("statusCmb").style.display = "none";

                }
                function toSubject() {
                    document.getElementById("contentTxt").style.display = "none";
                    document.getElementById("subjectCmb").style.display = "inline";
                    document.getElementById("statusCmb").style.display = "none";
                }
                function toStatus() {
                    document.getElementById("contentTxt").style.display = "none";
                    document.getElementById("subjectCmb").style.display = "none";
                    document.getElementById("statusCmb").style.display = "inline";
                }
            </script>


            <!-- Question Display-->
            <div id="questions" class="q-div">
            <c:forEach var="subject" items="${subjectList}">
                <div id="subject_space">

                    <c:if test="${requestScope.CATEGORY_SELECTED eq 'subject'}">
                        <c:out value="${subject.subjectName}"/>
                    </c:if>

                    <c:if test="${requestScope.CATEGORY_SELECTED eq 'content'}">
                        <c:out value="${subject.subjectName}"/>
                    </c:if>


                    <c:forEach var="ques" items="${requestScope.QUESTION_LIST}">
                        <c:set var="subjID" value="${subject.subjectId}"/>
                        <c:if test="${subject.subjectId eq ques.subjectId}">
                            <form action="UpdateDeleteDispatcherController" method="POST">
                                <div id="questionInfo" class="q-info">

                                    <input type="hidden" name="questionId" value="${ques.questionId}"/>
                                    <input type="hidden" name="subjectName" value="${subject.subjectName}"/>
                                    <input type="hidden" name="subjectId" value="${subjID}"/>
                                    <br>
                                    <label id="questionId">ID : ${ques.questionId}</label>
                                    <label id="createDate">Created on  : ${ques.createDate}</label>
                                    <c:if test="${ques.status == true}">
                                        <span class="active">Active</span><br>
                                    </c:if>
                                    <c:if test="${ques.status == false}">
                                        <span class="inactive">Inactive</span><br>
                                    </c:if>
                                    <textarea name="questionContent" wrap="true" >${ques.questionContent}</textarea><br> 

                                    <c:set var="ans1" value="${ques.ans1}"/>
                                    <input type="radio" name="ansCheckbox" value="ans1"<c:if test="${ans1 eq ques.answerCorrect}">checked</c:if> ><input type="text" id="ans1" name="ans1" value="${ans1}"><br> 

                                    <c:set var="ans2" value="${ques.ans2}"/>
                                    <input type="radio" name="ansCheckbox" value="ans2"<c:if test="${ans2 eq ques.answerCorrect}">checked</c:if> ><input type="text" id="ans2" name="ans2" value="${ans2}"><br> 

                                    <c:set var="ans3" value="${ques.ans3}"/>
                                    <input type="radio" name="ansCheckbox" value="ans3"<c:if test="${ans3 eq ques.answerCorrect}">checked</c:if> ><input type="text" id="ans3" name="ans3" value="${ans3}"><br> 

                                    <c:set var="ans4" value="${ques.ans4}"/>
                                    <input type="radio" name="ansCheckbox" value="ans4"<c:if test="${ans4 eq ques.answerCorrect}">checked</c:if> ><input type="text" id="ans4" name="ans4" value="${ans4}"><br> 

                                        <button type="submit" name="btnAction" value="Update" onclick="return confirm('Do you wish to continue UPDATE question : ${ques.questionContent}');">Update</button>

                                    <c:if test="${ques.status == true}">
                                        <button type="submit" name="btnAction" value="Delete" onclick="return confirm('Do you wish to continue DELETE question : ${ques.questionContent}');">Delete</button><br> 
                                    </c:if>
                                    <c:if test="${ques.status == false}">
                                        <button type="submit" name="btnAction" value="Restore" onclick="return confirm('Do you wish to continue Restore question : ${ques.questionContent}');">Restore</button><br> 
                                    </c:if>

                                </div>
                            </form>
                        </c:if>
                    </c:forEach>
                </div>
            </c:forEach>
        </div>

        <div class="paging">
            <c:forEach begin="1" end="${pageNumber}" var="i"> 
                <c:if test="${pageNumber > 1}">
                    <c:url var="link" value="SearchQuestionController">
                        <c:param name="index" value="${i}"/>
                        <c:param name="pageNumber" value="${pageNumber}"/>
                        <c:param name="searchContent" value="${param.searchContent}"/>
                        <c:param name="searchSubject" value="${param.searchSubject}"/>
                        <c:param name="searchStatus" value="${param.searchStatus}"/>
                        <c:param name="searchCategory" value="${param.searchCategory}"/>
                    </c:url>
                    <a href="${link}" id="${i}" <c:if test="${pageNumber eq i}">disabled</c:if>>${i}</a>
                </c:if>
            </c:forEach>
        </div>
    </body>
</html>

<script>
    document.getElementById('${index}').style.color = "red";
</script>
<style>

    .q-info{      
        float: left;
        width: 50%;
        text-align: center;
        padding-bottom: 10%;

    }
    .active{
        color: green;
    }
    .inactive{
        color:red;
    }
    .paging{
        text-align: center;
        float: none;
        padding-bottom: 10px;
    }
    .nav{
        text-align: center;


        padding: 2% 2% 2% 2%;
        font-size: 200%;
        background-color: lightgrey;
    }
    .nav a{
        color: black;
        font-family: sans-serif;
        text-decoration: none;
        margin-right: 5%;
    }
    .nav a:hover{
        color: blue;
    }
</style>