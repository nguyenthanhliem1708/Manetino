<%-- 
    Document   : subject
    Created on : Jan 30, 2021, 3:12:17 AM
    Author     : LiemNguyen
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Subject Page</title>
    </head>
    <body>
        <nav>
            <!--Navigation bar-->
            <div id="nav-placeholder">
            </div>

            <script src="//code.jquery.com/jquery.min.js"></script>
            <script>
                $.get("nav-bar.jsp", function (data) {
                    $("#nav-placeholder").replaceWith(data);
                });
            </script>
            <!--end of nav-bar-->
        </nav>

        <!--UPDATE Err Messages--> 

        <c:if test="${not empty requestScope.UPDATE_MESS}">
            <script>alert('${requestScope.UPDATE_SUCCESS_MESS}');</script>
        </c:if>
        <c:if test="${not empty requestScope.DUPLICATED_SUBJECT_NAME_ERR}">
            <script>alert('${requestScope.DUPLICATED_SUBJECT_NAME_ERR}');</script>
        </c:if>

        <c:if test="${not empty requestScope.INVALID_UPDATE_SUBJECT_NAME_ERR}">
            <script>alert('${requestScope.INVALID_UPDATE_SUBJECT_NAME_ERR}');</script>
        </c:if>

        <!--CREATE Err Messages-->   
        <c:if test="${not empty requestScope.CREATE_MESS}">
            <script>alert('${requestScope.CREATE_SUCCESS_MESS}');</script>
        </c:if>
        <c:if test="${not empty requestScope.DUPLICATED_SUBJECT_NAME_ERR}">
            <script>alert('${requestScope.DUPLICATED_SUBJECT_NAME_ERR}');</script>
        </c:if>
        <c:if test="${not empty requestScope.INVALID_CREATE_SUBJECT_NAME_ERR}">
            <script>alert('${requestScope.INVALID_CREATE_SUBJECT_NAME_ERR}');</script>
        </c:if>
        <c:if test="${not empty requestScope.NUMBERIC_ERROR}">
            <script>alert('${requestScope.NUMBERIC_ERROR}');</script>
        </c:if>
        <div class="create-form">
            <form action="CreateSubjectController">
                <label class="create-label">New Subject : </label>
                <input type="text" name="createSubjectName" required value="${requestScope.createName}" placeholder="subject name here">
                <input type="number" name="createSubjectQuantity" required min="0" title="Must be a number > 0" value="${requestScope.createQuantity}" placeholder="number of question in quizes">
                <input type="number" name="createSubjectTime" required min="0" title="Must be a number > 0" value="${requestScope.createTime}" placeholder="number of time in quizes">
                <button type="submit" name="btnAction" value="CreateSubject">Create</button><br>
            </form>
        </div>

        <c:set var="subjectList" value="${requestScope.SUBJECT_LIST}"/>



        <c:forEach var="s" items="${subjectList}">
            <div id="subjectInfo">
                <form action="UpdateDeleteDispatcherController">
                    <br><br>
                    <label id="subjectId">ID : ${s.subjectId}</label><br>


                    <input type="hidden" name="updateSubjectId" value="${s.subjectId}">
                    <c:if test="${s.status == true}">
                        <span class="active">Active</span><br>
                    </c:if>
                    <c:if test="${s.status == false}">
                        <span class="inactive">Inactive</span><br>
                    </c:if>
                    <label>Name</label><br>
                    <input type="text" name="updateSubjectName" required value="${s.subjectName}"> 
                    <br>
                    <label>Number of questions</label><br>
                    <input type="number" name="updateSubjectQuantity" required min="0" title="Must be a number > 0" value="${s.quantityLimit}" placeholder="number of question in quizes">
                    <br>
                    <label>Time Limit</label><br>
                    <input type="number" name="updateSubjectTime" required min="0" title="Must be a number > 0" value="${s.timeLimit}" placeholder="number of minutes">
                    
                    <button type="submit" name="btnAction" value="UpdateSubject">Update</button><br>

                </form>
            </div>
        </c:forEach>

        <div class="paging">
            <c:forEach begin="1" end="${pageNumber}" var="i">
                <a href="SubjectPageController?index=${i}" id="${i}" >${i}</a>
            </c:forEach>
        </div>


    </body>
</html>


<style>
    .active{
        color: green;
    }
    .inactive{
        color:red;
    }
    .paging{
        text-align: center;
    }
    .create-form{
        text-align: center;
    }
    .create-label{
        color: red;
    }
</style>
<script>
    document.getElementById('${index}').style.color = "red";
</script>