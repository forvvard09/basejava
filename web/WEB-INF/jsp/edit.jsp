<%@ page import="main.java.app.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="main.java.app.model.Resume" scope="request"/>
    <title>Редактирование резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"></jsp:include>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.contacts.get(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <input type="text" name="section" size="30" value="1"></br>
        <input type="text" name="section" size="30" value="2"></br>
        <input type="text" name="section" size="30" value="3"></br>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>