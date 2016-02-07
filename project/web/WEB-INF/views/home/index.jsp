<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2/3/2016
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="Welcome">
    <div class="index-banner">
        Welcome to Lib-Mgmt
    </div>
    <div class="index-section">
        <c:if test="${(indexMessageId != null)}">
            <spring:message code="${fn:escapeXml(indexMessageId)}" />
        </c:if>
        <div>
            <a href="<spring:url value="/add"/>" >Add User</a>
        </div>
            <%--@elvariable id="userList" type="java.util.List<cn.edu.xjtu.se.jackq.libmgmt.entity.User>"--%>
        <c:forEach items="${userList}" var="user">
            <div>
                <c:out value="${user.name}"/> - <c:out value="${user.passwordHash}"/>
            </div>
        </c:forEach>
    </div>
</layout:basic>