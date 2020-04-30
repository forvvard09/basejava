<%@ page import="main.java.app.model.ContactType" %>
<%@ page import="main.java.app.model.ListSection" %>
<%@ page import="main.java.app.model.OrganizationSection" %>
<%@ page import="main.java.app.model.SectionType" %>
<%@ page import="main.java.app.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
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
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.sections.get(type)}"/>
            <jsp:useBean id="section" type="main.java.app.model.AbstractSection"/>
            <h2>${type.title}</h2>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE'}">
                    <input type="text" name="${type.name()}" value="${section}">
                </c:when>
                <c:when test="${type == 'PERSONAL'}">
                    <input type="text" name="${type.name()}" value="${section}">
                </c:when>

                <c:when test="${type == 'ACHIEVEMENT' || type == 'QUALIFICATIONS'}">
                        <textarea name="${type.name()}" cols="75"
                                  rows="5"><%=String.join(System.lineSeparator(), ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type == 'EXPERIENCE' || type == 'EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getListOrganizations()%>"
                               varStatus="counter">
                        <span class="${type}">
                        <dl>
                            <dt>
                                Название учреждения:
                            </dt>
                            <dd>
                                <input type="text" name="${type}" size="100" value="${org.homePage.name}">
                            </dd>
                        </dl>
                        <dl>
                            <dt>
                                Сайт учреждения:
                            </dt>
                            <dd>
                                <input type="text" name="${type}url" size="50" value="${org.homePage.url}">
                            </dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="pos" items="${org.listPosition}">
                                <jsp:useBean id="pos" type="main.java.app.model.Organization.Position"/>
                                <dl>
                                    <dt>
                                        Начальная дата:
                                    <dd>
                                        <input type="text" name="${type}${counter.index}startDate" size="10"
                                               value="<%=DateUtil.format(pos.getStartData())%>" placeholder="MM/yyyy">
                                    </dd>
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        Конечная дата:
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size="10"
                                               value="<%=DateUtil.format(pos.getFinishData())%>" placeholder="MM/yyyy">
                                    </dd>
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        Должность:
                                    <dd>
                                        <input type="text" name="${type}${counter.index}title" size="75"
                                               value="${pos.title}">
                                    </dd>
                                    </dt>
                                </dl>
                                <dl>
                                    <dt>
                                        Описание:
                                    <dd>
                                        <textarea name="${type}${counter.index}description" rows="2"
                                                  cols="75">${pos.description}</textarea>
                                    </dd>
                                    </dt>
                                </dl>
                            </c:forEach>
                        </div>
                            </span>
                    </c:forEach>
                    <dl>
                        <dt>
                        <dd>
                        <dd>
                            <p style="margin-left: 200px">
                                <button type="button" class="addOrg ${type}">Добавить организацию</button>
                            </p>
                        </dd>
                        </dd>
                        </dt>
                    </dl>
                </c:when>
            </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragment/footer.jsp"></jsp:include>
</body>
</html>

<script>
    $(document).ready(function () {
        let sizeObj = $('input[name="OBJECTIVE"]').val().length < 10 ? 70 : $('input[name="OBJECTIVE"]').val().length;
        $('input[name="OBJECTIVE"]').width(sizeObj * 7);

        let sizePers = $('input[name="PERSONAL"]').val().length < 10 ? 70 : $('input[name="PERSONAL"]').val().length;
        $('input[name="PERSONAL"]').width(sizePers * 7);
    });
</script>

<script>
    $(document).ready(function () {
        let sizeObj = $('input[name="OBJECTIVE"]').val().length < 10 ? 70 : $('input[name="OBJECTIVE"]').val().length;
        $('input[name="OBJECTIVE"]').width(sizeObj * 7);

        let sizePers = $('input[name="PERSONAL"]').val().length < 10 ? 70 : $('input[name="PERSONAL"]').val().length;
        $('input[name="PERSONAL"]').width(sizePers * 7);
    });
</script>

<script>
    $(document).ready(function () {
        if ($("span.education").length === 1) {
            $(".addOrg.education").remove();
        }

        if ($("span.experience").length === 1) {
            $(".addOrg.experience").remove();
        }

        if ($("span.education").length > 1) {
            $("span.education:first").hide();
        }

        if ($("span.experience").length > 1) {
            $("span.experience:first").hide();
        }
    });
</script>

<script>
    $(document).ready(function () {
        $(".addOrg").click(function () {
            console.log($(this).attr('class'));
            let str = ($(this).attr('class')).toLowerCase();
            if ((str.indexOf("education") + 1) > 0) {
                $("span.education:first").show();
                scroll(0, $("span.education:first").offset().top - 200);
                $(this).remove();
            }

            if ((str.indexOf("experience") + 1) > 0) {
                $("span.experience:first").show();
                scroll(0, $("span.experience:first").offset().top - 200);
                $(this).remove();
            }
        });
    });
</script>

<script>
    $(document).ready(function () {
        $(".addOrg").click(function () {
            console.log($(this).attr('class'));
            let str = ($(this).attr('class')).toLowerCase();
            if ((str.indexOf("education") + 1) > 0) {
                $("span.education:first").show();
                scroll(0, $("span.education:first").offset().top - 200);
                $(this).remove();
            }

            if ((str.indexOf("experience") + 1) > 0) {
                $("span.experience:first").show();
                scroll(0, $("span.experience:first").offset().top - 200);
                $(this).remove();
            }
        });
    });
</script>

