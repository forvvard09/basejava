<%@ page import="main.java.app.model.ContactType" %>
<%@ page import="main.java.app.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {

        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%>
            </a></td>
            <td><%=resume.getContacts().get(ContactType.EMAIL)%>
            </td>
        <tr>
                <%
                }
            %>
    </table>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>
