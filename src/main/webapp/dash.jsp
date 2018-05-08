<%--
  Created by IntelliJ IDEA.
  User: vvn
  Date: 08.05.2018
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <meta name="description" content="Dashboard">
    <link rel="stylesheet" href="style.css">
    <link rel="shortcut icon" href="/images/favicon.ico">
    <link rel=”icon” type=”image/x-icon” href=”favicon.ico” />
</head>
<body>
<header>
    <div id="logo">StudentsProject</div>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=1">Subjects</a>
            <li><a href="${pageContext.request.contextPath}/dashboard?page=2">Users</a>
            <li>
        </ul>
    </nav>
</header>
<section>
    <strong>Hello, <%=request.getSession().getAttribute("login")%> (<%=request.getSession().getAttribute("userType").toString()%>)</strong><span> <a href="/auth?logout=1">logout</a></span>
</section>
<section id="pageContent">
    <main role="main">
        <article>
            <%=request.getAttribute("content")!=null?(String)request.getAttribute("content"):""%>
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
