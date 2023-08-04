<div id="container">
  <div id="containerRow">
    <div id="leftPanel">
      <span>View:</span><br>
      <ul>
        <li><a href="${pageContext.request.contextPath}/users">users</a><br></li>
        <li><a href="${pageContext.request.contextPath}/activities">activities</a><br></li>
      </ul>
      <span>Sort activity:</span><br>
      <ul>
        <li><a href="${pageContext.request.contextPath}/sort?a=1">byName</a><br></li>
        <li><a href="${pageContext.request.contextPath}/sort?a=2">byCategory</a><br></li>
        <li><a href="${pageContext.request.contextPath}/sort?a=3">byUser</a><br></li>
      </ul>
      <span>Filter activity:</span><br>
      <ul>
        <li><a href="${pageContext.request.contextPath}/filter?a=2">byCategory</a><br></li>
      </ul>
    </div>
