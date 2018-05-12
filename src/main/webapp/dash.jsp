<%--
  Created by IntelliJ IDEA.
  User: vvn
  Date: 08.05.2018
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <meta name="description" content="Dashboard">
    <link rel="stylesheet" href="style.css">
    <link rel="shortcut icon" href="<c:url value="/images/favicon.ico"/>">
    <link rel=”icon” type=”image/x-icon” href="<c:url value="/images/favicon.ico"/>"/>
</head>
<body>
<header>
    <div id="logo">StudentsProject</div>
    <nav>
        <ul>

            <c:if test="${userType.toString()=='Administrator'}">
            <li><a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=1">Subjects</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=2">Users</a>
                </c:if>
                <c:if test="${userType.toString()=='Teacher'}">
            <li><a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=1">Subjects</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=2">Users</a>
                </c:if>
                <c:if test="${userType.toString()=='Student'}">
            <li><a href="${pageContext.request.contextPath}/dashboard?">Home</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=1">Grades</a>
                </c:if>
        </ul>
    </nav>
</header>
<section>
    <strong>Hello, ${login} (${userType})</strong><span> <a href="<c:url value="/auth?logout=1"/>">logout</a></span>
</section>
<section id="pageContent">
    <main role="main">
        <article>
            <c:if test="${param.page==1}">
            <table class="zui-table">
                <thead>
                <tr>
                    <c:if test="${userType.toString()=='Administrator'}">
                    <th>Subject</th>
                    <th>Teacher</th>
                    </c:if>
                    <c:if test="${userType.toString()=='Teacher'}">
                        <th>Subject</th>
                    </c:if>
                    <c:if test="${userType.toString()=='Student'}">
                        <th>Subject</th>
                        <th>Teacher</th>
                        <th>Grade</th>
                    </c:if>
                </tr>
                </thead>
                <tbody>
                <c:if test="${userType.toString()=='Administrator'}">
                <c:forEach var="c" items="${content}">
                <tr>
                    <td>${c.name}</td>
                    <td>${c.teacher}</td>
                </tr>
                </c:forEach>
                </c:if>
                <c:if test="${userType.toString()=='Teacher'}">
                    <c:forEach var="c" items="${content}">
                        <tr>
                            <td>${c.name}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${userType.toString()=='Student'}">
                    <c:forEach var="c" items="${content}">
                        <tr>
                            <td>${c.name}</td>
                            <td>${c.teacher}</td>
                            <td>${c.grade}</td>
                        </tr>
                    </c:forEach>
                </c:if>
                </tbody>
            </table>
            </c:if>
            <c:if test="${param.page==2}">
                usersList
            </c:if>
        </article>
    </main>
    <aside>
        <div></div>
    </aside>
</section>
<footer>
    <p>ru.innopolis.vasiliev <a href="https://github.com/soqman/studentsProj">Project page on github.</a></p>
</footer>
</body>
</html>
