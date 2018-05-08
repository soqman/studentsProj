<%--
  Created by IntelliJ IDEA.
  User: vvn
  Date: 08.05.2018
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in</title>
    <meta name="description" content="Login">
    <link rel="stylesheet" href="style.css">
    <link rel="shortcut icon" href="/images/favicon.ico">
    <link rel=”icon” type=”image/x-icon” href=”favicon.ico” />
</head>
<body>
<header>
    <div id="logo">StudentsProject</div>
</header>
<section>
    <strong>
        Please, sign in
        <p style="color:red;margin:10pt 0 10pt;"><%=("authErr".equals(request.getParameter("errorMsg"))) ? "Invalid login or password" : ""%></p>
    </strong>
</section>
<section>
    <form action="${pageContext.request.contextPath}/auth" method="post">
        <input type="text" value="" name="login"> <span> login</span><BR><BR>
        <input type=password value="" name="password"> <span> password</span><BR><BR>
        <input type="submit" value="OK"><BR><BR>
    </form>
</section>
</body>
</html>
