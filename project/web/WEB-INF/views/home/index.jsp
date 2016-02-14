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
    <div class="container">

        <div class="index-banner">
            Welcome to Lib-Mgmt
        </div>
        <div class="index-section">

            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}" /></p>
                </div>
            </c:if>
            <div>
                Current User List
            </div>
            <div>
                <ul>
                    <li>
                        Currently, password hash function is merely pass its literal value
                    </li>

                    <c:if test="${userList.size() == 0}">
                        <li>
                            Create new user by Register page ()
                        </li>
                    </c:if>

                </ul>
            </div>
            <dl>
                <c:forEach items="${userList}" var="user">
                    <dt>
                        <c:out value="${user.name}"/>
                    </dt>
                    <dd>
                        <c:out value="${user.passwordHash}"/>
                    </dd>
                </c:forEach>
            </dl>

        </div>
    </div>
</layout:basic>