<%--
  Created by IntelliJ IDEA.
  User: vvn
  Date: 08.05.2018
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in</title>
    <meta name="description" content="Login">
    <link rel="stylesheet" href="style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    <link rel=”icon” type=”image/x-icon” href="${pageContext.request.contextPath}/images/favicon.ico" />
</head>
<body>
<header>
    <div id="logo">StudentsProject</div>
</header>
<section>
    <strong>
        Please, sign in
        <span style="color:red;margin:10pt 0 10pt;"><%=("authErr".equals(request.getParameter("errorMsg"))) ? "Invalid login or password" : ""%></span>
    </strong>
</section>
<section>
    <form action="${pageContext.request.contextPath}/auth" method="post">

        <input id="login" type="text" value="" name="login"><label for="login"> login</label><BR><BR>
        <input id = "password" type=password value="" name="password"><label for="password"> password</label><BR><BR>
        <input id="submit" type="submit" value="OK"><BR><BR>
    </form>
</section>
</body>
</html>
