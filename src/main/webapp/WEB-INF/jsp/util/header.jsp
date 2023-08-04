<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>${requestScope.page}</title>
    <link href="${pageContext.request.contextPath}/css/page.css" rel="stylesheet" media="all" />
    <link href="${pageContext.request.contextPath}/css/tableStyle.css" rel="stylesheet" media="all" />
</head>

<body>
<div id="header"></div>
<div id="logo"></div>
<div id="buttonHome">
    <a href="${pageContext.request.contextPath}/">
        <img src="../../../image/general/logo.png" alt="Home" width="30" height="30">
    </a>
</div>
<div id="buttonLanguage">
    <form action="${pageContext.request.contextPath}/locale" method="post">
        <button type="submit" name="lang" value="en_US">en</button>
        <button type="submit" name="lang" value="uk_UA">ua</button>
    </form>
</div>

<%--<fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null?param.lang:'en_US')}"/>--%>
<%--<fmt:setBundle basename="translations"/>--%>

<div id="buttonSignIn">
    <a href="${pageContext.request.contextPath}/login">
        <button type="button">Log In </button>
    </a>
</div>
<div id="buttonSignUp">
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Sing In </button>
    </a>
</div>
<div id="buttonLogout">
    <c:if test="${not empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/logout" method="post">
            <button type="submit">Log Out</button>
        </form>
    </c:if>
</div>



