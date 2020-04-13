<%@ page import="main.java.app.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"></jsp:include>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <%--<th></th>
            <th></th>--%>
        </tr>
        <c:forEach var="resume" items="${resumes}">
        <jsp:useBean id="resume" type="main.java.app.model.Resume"/>
        <tr>
            <td><a href="resume?uuid=${resume.uuid}">${resume.fullName}
            </a></td>
            <td>${resume.getContacts().get(ContactType.EMAIL)}
            </td>
        <tr>
            </c:forEach>
    </table>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>
