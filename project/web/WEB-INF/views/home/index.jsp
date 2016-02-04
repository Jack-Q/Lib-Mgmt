<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2/3/2016
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <title>Library Management</title>
  <link rel="stylesheet" href="<spring:url value="/style/index.css"/>">
</head>
<body>
<h1>
  Library Management
</h1>
<div>
  <p>
    Index Page of Lib-Mgmt
  </p>
    <c:if test="${(indexMessageId != null)}">
        <spring:message code="${fn:escapeXml(indexMessageId)}" />
    </c:if>
    <div>
        <a href="<c:url value="/add"/>" >Add User</a>
    </div>
    <%--@elvariable id="userList" type="java.util.List<cn.edu.xjtu.se.jackq.libmgmt.entity.User>"--%>
    <c:forEach items="${userList}" var="user">
        <div>
            <c:out value="${user.name}"/> - <c:out value="${user.passwordHash}"/>
        </div>
    </c:forEach>
</div>
</body>
</html>
