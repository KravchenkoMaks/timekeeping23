<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="util/header.jsp"%>

<div id="login">
    <form action="${pageContext.request.contextPath}/login" method="post">
        <table id="tableForm">
            <caption><h2>Log In</h2></caption>
            <tr>
                <td>Email</td>
                <td>
                    <label for="email">
                        <input type="email" name="email" id="email" value="${param.email}" required>
                    </label>
                </td>
            </tr>
            <tr>
                <td>Password</td>
                <td>
                    <label for="password">
                        <input type="password" name="password" id="password" required>
                    </label>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <button type="submit">Send</button>
                </td>
            </tr>
        </table>
    </form>
</div>

<div id="errorLogin">
    <c:if test="${param.error != null}">
        <span>Email or password is not correct</span>
    </c:if>
</div>

<%@include file="util/footer.jsp"%>
