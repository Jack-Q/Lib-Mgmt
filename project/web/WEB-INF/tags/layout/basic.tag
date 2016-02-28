<%@ tag description="This tag provides the default layout for Lib-Mgmt" language="java" pageEncoding="utf-8" %>
<%@ attribute name="pageTitle" description="The title of the page" required="false" type="java.lang.String" %>
<%@ attribute name="pageHeader" required="false" fragment="true"
              description="additional header navigation item which should be a list item
               containing archor or dropdowm" %>
<%@ attribute name="pageFooter" required="false" fragment="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>


<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value='${pageTitle}${empty pageTitle ? "" : " - "}'/> Lib-Mgmt</title>
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
<body>
<nav class="main-header navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">Lib-Mgmt</span>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a title="Return to home" data-toggle="tooltip" data-placement="bottom"
                       href="<spring:url value="/" />">Home</a></li>
                <c:if test="${sessionScope.get('Auth').admin}">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">User<span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<spring:url value="/user/admin"/>">Librarian Management</a>
                            </li>
                            <li>
                                <a href="<spring:url value="/user/manage"/>">User Management</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="<spring:url value="/book/manage" />">Book</a>
                    </li>
                </c:if>
                <layout:role>
                    <jsp:attribute name="librarian">
                    <li>
                        <a href="<spring:url value="/user/manage" />">User</a>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Book<span class="caret"></span></a>
                        <ul class="dropdown-menu">

                            <li>
                                <a href="<spring:url value="/book/manage" />">Book Management</a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <a href="<spring:url value="/loan/lend"/>">Lend Books</a>
                            </li>
                            <li>
                                <a href="<spring:url value="/loan/return"/>">Return Books</a>
                            </li>
                        </ul>
                    </li>
                    </jsp:attribute>
                </layout:role>
                <layout:role>
                    <jsp:attribute name="student">
                    <li>
                        <a href="<spring:url value="/loan/status" />">Loan Status</a>
                    </li>
                    </jsp:attribute>
                </layout:role>
                <jsp:invoke fragment="pageHeader"/>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <layout:login>
                    <jsp:attribute name="whenLogin">
                        <li>
                            <a href="<spring:url value="/user/index" />"
                               title="manage user profile">
                                <c:out value="${sessionScope.Auth.name}"/>
                                <c:forEach var="role" items="${sessionScope.Auth.roles}">
                                    <span class="label label-info" style="white-space: normal;"
                                          title="Your Role is <c:out value="${role.name()}"/>" data-toggle="tooltip"
                                          data-placement="bottom">
                                        <c:out value="${role.name()}"/>
                                    </span>
                                </c:forEach>
                            </a>
                        </li>
                        <li>

                            <a href="<spring:url value="/user/logout"/>">Logout</a>
                        </li>
                    </jsp:attribute>
                    <jsp:attribute name="notLogin">
                        <li>
                            <a class="btn btn-raised btn-inverse" href="<spring:url value="/user/login" />">Login</a>
                        </li>
                        <li>
                            <a class="btn btn-raised btn-info" href="<spring:url value="/user/register"/>">Register</a>
                        </li>
                    </jsp:attribute>
                </layout:login>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>


<div class="main-body">
    <jsp:doBody/>
</div>

<div class="main-footer">
    <div class="${empty pageFooter ? "null-sub-footer" : "page-sub-footer"}">
        <jsp:invoke fragment="pageFooter"/>
    </div>
    <div class="page-copyright">

        Lib-Mgmt, 2016 &copy; Thinker &amp; Performer | <a href="<spring:url value="/home/about" />">About</a>

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
