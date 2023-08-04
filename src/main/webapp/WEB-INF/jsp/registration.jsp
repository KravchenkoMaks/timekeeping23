<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="util/header.jsp" %>

<div id="registration">
    <form action="${pageContext.request.contextPath}/registration" method="post" enctype="multipart/form-data">
        <input type="hidden" name="role" value="NEW">
        <table id="tableForm">
            <caption><h2>Registration</h2></caption>
            <tr>
                <td>FirstName:</td>
                <td>
                    <label for="firstName">
                        <input type="text" name="firstName" id="firstName" required>
                    </label>
                </td>
                <c:if test="${not empty requestScope.errors}">
                    <td>
                        <div class="error">
                            <c:forEach var="error" items="${requestScope.errors}">
                                <c:if test="${error.code eq 'invalid.firstName'}">
                                    <span>${error.massage}</span>
                                </c:if>
                            </c:forEach>
                        </div>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>LastName:</td>
                <td>
                    <label for="lastName">
                        <input type="text" name="lastName" id="lastName" required>
                    </label>
                </td>
                <c:if test="${not empty requestScope.errors}">
                    <td>
                        <div class="error">
                            <c:forEach var="error" items="${requestScope.errors}">
                                <c:if test="${error.code eq 'invalid.lastName'}">
                                    <span>${error.massage}</span>
                                </c:if>
                            </c:forEach>
                        </div>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>Email:</td>
                <td>
                    <label for="email">
                        <input type="text" name="email" id="email" required>
                    </label>
                </td>
                <c:if test="${not empty requestScope.errors}">
                    <td>
                        <div class="error">
                            <c:forEach var="error" items="${requestScope.errors}">
                                <c:if test="${error.code eq 'invalid.email'}">
                                    <span>${error.massage}</span>
                                </c:if>
                            </c:forEach>
                        </div>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>Password:</td>
                <td>
                    <label for="password">
                        <input type="password" name="password" id="password" required>
                    </label>
                </td>
                <c:if test="${not empty requestScope.errors}">
                    <td>
                        <div class="error">
                            <c:forEach var="error" items="${requestScope.errors}">
                                <c:if test="${error.code eq 'invalid.password'}">
                                    <span>${error.massage}</span>
                                </c:if>
                            </c:forEach>
                        </div>
                    </td>
                </c:if>
            </tr>
            <tr>
                <td>Image:</td>
                <td>
                    <label for="image">
                        <input type="file" name="image" id="image">
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

<%@include file="util/footer.jsp" %>
