<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
    <link href="css/welcome.css" rel="stylesheet" media="all">
</head>

<body>
<div id="block1"></div>
<div id="block2">
    <h2><%= "Welcome to application" %></h2>
    <h1>TimeKeeping</h1>
</div>

<div id="block3">
    <a href="${pageContext.request.contextPath}/login">
        <button type="button">Log In</button>
    </a>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Sing In</button>
    </a>
</div>

<div id="block4">
    <img src="image/general/logoBig.png" alt="logo" width="376" height="376">
</div>

</body>
</html>