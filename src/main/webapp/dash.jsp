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
</head>
<body>
<header>
    <div id="logo">StudentsProject</div>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">Home</a>
            <li><a href="${pageContext.request.contextPath}/subjects">Subjects</a>
            <li><a href="${pageContext.request.contextPath}/users">Users</a>
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
            <h2>Stet facilis ius te</h2>
            <p>Lorem ipsum dolor sit amet, nonumes voluptatum mel ea, cu case ceteros cum. Novum commodo malorum vix ut. Dolores consequuntur in ius, sale electram dissentiunt quo te. Cu duo omnes invidunt, eos eu mucius fabellas. Stet facilis ius te, quando voluptatibus eos in. Ad vix mundi alterum, integre urbanitas intellegam vix in.</p>
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
