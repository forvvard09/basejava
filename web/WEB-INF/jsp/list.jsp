<%@ page import="main.java.app.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"></jsp:include>

<div class="create-new-resume" style="margin-left: 30px">
    <button class="addNewResume"><img src="img/add.png">Добавить новое резюме</button>
</div>
</br>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Имя</th>
        <th>Email</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="resume" items="${resumes}">
    <jsp:useBean id="resume" type="main.java.app.model.Resume"/>
    <tr>
        <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
            <%--              TO DO: --%>
        <td><%=ContactType.EMAIL.toHtml(resume.getContacts().get(ContactType.EMAIL))%>
        </td>
        <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></td>
        <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png"></a></td>
    <tr>
        </c:forEach>
</table>


</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>

<script>
    $(document).ready(function () {
        $(".addNewResume").click(function () {
            console.log("Добавляем резюме...");
            window.location.href = "resume?action=add";
        });
    });
</script>
