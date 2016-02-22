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

<layout:login>
    <jsp:attribute name="whenLogin">

        <layout:basic pageTitle="Welcome">
            <div class="container">

                <div class="index-banner">
                    Welcome to Lib-Mgmt
                </div>
                <div class="index-section">

                        <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
                    <c:if test="${(indexMessageId != null)}">
                        <div class="alert-info alert alert-dismissible">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
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
                                <c:out value="${user.userName}"/>
                            </dt>
                            <dd>
                                <c:out value="${user.passwordHash}"/>
                            </dd>
                        </c:forEach>
                    </dl>

                </div>
            </div>
        </layout:basic>
    </jsp:attribute>
    <jsp:attribute name="notLogin">

<!DOCTYPE html>
<html lang="en-US">
<head>
    <title>Lib-Mgmt</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Stylesheets -->
    <link href="<spring:url value="/style/bootstrap.css" />" rel="stylesheet">
    <link href="<spring:url value="/style/bootstrap-material-design.css" />" rel="stylesheet">
    <link href="<spring:url value="/style/ripples.css" />" rel="stylesheet"/>
    <link href="<spring:url value="/style/index.css"/>" rel="stylesheet"/>
    <!-- Scripts -->
    <script src="<spring:url value="/script/lib/jquery.js"/>"></script>
    <script src="<spring:url value="/script/lib/bootstrap.js"/>"></script>
    <script src="<spring:url value="/script/lib/ripples.js"/>"></script>
    <script src="<spring:url value="/script/lib/material.js"/>"></script>
    <script src="<spring:url value="/script/index.js"/>"></script>
</head>
<body class="index-body">

<div class="index">
    <div class="index-search-wrapper">
        <div class="index-search-box">
            <div class="form-group label-floating">
                <label class="control-label" for="input-search">Type something here to search for a
                    book &hellip; &hellip;</label>
                <input class="form-control" id="input-search" type="text">
            </div>
        </div>
    </div>
    <div class="index-footer-wrapper">

        <div class="index-footer">
            <div class="user-section">
                <i class="material-icons">&#xE851;</i>
                <div class="section-actions">
                    <a class="btn btn-raised btn-primary" href="<spring:url value="/user/login"/>">Login</a>
                    <a class="btn btn-raised btn-primary" href="<spring:url value="/user/register"/>">Register</a>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $.material.init();
        $('[data-toggle="tooltip"]').tooltip()
    })
</script>

</body>
</html>
    </jsp:attribute>
</layout:login>
