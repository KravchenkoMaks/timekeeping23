<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@include file="util/header.jsp" %>
<%@include file="util/left_panel.jsp"%>
    <div id="rightPanel">
      <table class="styled-table" >
        <caption><h2>List users</h2></caption>
        <thead>
        <tr>
          <th>Firstname</th>
          <th>Lastname</th>
          <th>Email</th>
          <th>Role</th>
          <th>Info</th>
          <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <th>Action</th>
          </c:if>
        </tr>
        </thead>
        <c:forEach var="user" items="${sessionScope.users}">
          <tbody>
          <tr>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.role}</td>
            <td><a href="${pageContext.request.contextPath}/account?id=${user.id}">info</a></td>
            <c:if test="${sessionScope.user.role eq 'ADMIN'}">
              <td>
                <a href="${pageContext.request.contextPath}/updateUser?id=${user.id}">update</a>
              </td>
            </c:if>
          </tr>
          </tbody>
        </c:forEach>
      </table>
    </div>
  </div>
</div>

<%@include file="util/footer.jsp" %>
