<%@ page import="main.java.app.model.ListSection" %>
<%@ page import="main.java.app.model.OrganizationSection" %>
<%@ page import="main.java.app.model.TextSection" %>
<%@ page import="main.java.app.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="main.java.app.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragment/header.jsp"></jsp:include>
<section>
    <h2>${resume.fullName}&nbsp;&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a>
    </h2>
    </br>
    <c:if test="${resume.contacts.size() > 0}">
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<main.java.app.model.ContactType, java.lang.String>"/>
                <dfn><%=contactEntry.getKey().getTitle()%>
                </dfn><%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
                <br/>
            </c:forEach>
        </p>
        <hr>
    </c:if>

    <c:if test="${resume.sections.size() > 0}">
        <p>
        <table cellpadding="2">
            <c:forEach var="sectionEntry" items="${resume.sections}">
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<main.java.app.model.SectionType, main.java.app.model.AbstractSection>"/>
                <c:set var="type" value="${sectionEntry.key}"/>
                <c:set var="section" value="${sectionEntry.value}"/>

                <jsp:useBean id="section" type="main.java.app.model.AbstractSection"/>

                <tr>
                    <td><h3><a name="${type.name()}">${type.title}</a>
                        <c:if test="${type == 'OBJECTIVE'}">
                            &nbsp;&nbsp;&nbsp;&nbsp;<%=((TextSection) section).getDescription()%>
                        </c:if>
                    </h3></td>
                </tr>

                <c:if test="${type != 'OBJECTIVE'}">
                    <c:choose>
                        <c:when test="${type == 'PERSONAL'}">
                            <tr>
                                <td><%=((TextSection) section).getDescription()%>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                            <tr>
                                <td>
                                    <ul>
                                        <c:forEach var="itemListSection"
                                                   items="<%=((ListSection) section).getItems()%>">
                                            <li>${itemListSection}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:when>
                        <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                            <table>
                                <tr>
                                    <c:forEach var="org"
                                               items="<%=((OrganizationSection) section).getListOrganizations()%>">

                                    <c:if test="${empty org.homePage.url}">
                                        <td>
                                            <h4>${org.homePage.name}</h4>
                                        </td>
                                    </c:if>
                                    <c:if test="${not empty org.homePage.url}">
                                        <td>
                                            <a href="${org.homePage.url}"><h4>${org.homePage.url}</h4></a>
                                        </td>
                                    </c:if>
                                </tr>
                                <c:forEach var="position" items="${org.listPosition}">
                                    <jsp:useBean id="position" type="main.java.app.model.Organization.Position"/>
                                    <tr>
                                        <td>
                                            <%=HtmlUtil.formatDates(position)%>
                                        </td>

                                        <td>
                                            <c:if test="${not empty position.title}">
                                                <b>${position.title}</b>
                                                <br>
                                            </c:if>
                                                ${position.description}
                                        </td>
                                    </tr>
                                </c:forEach>
                                </c:forEach>
                            </table>
                        </c:when>
                    </c:choose>
                </c:if>
            </c:forEach>
        </table>
        </p>
    </c:if>
    <p style="text-align: center">
        <button onclick="window.history.back()">OK</button>
    </p>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>
