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
    <title><c:out value='${pageTitle}${empty pageTitle ? "" : " - "}'/> Lib-Mgmt</title>
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
            <a class="navbar-brand" href="#">Lib-Mgmt</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="<spring:url value="/" />">Home</a></li>
                <li><a href="<spring:url value="/user/index"/>">User</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle"
                       data-toggle="dropdown" role="button"
                       aria-haspopup="true"
                       aria-expanded="false">Book list <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li class="dropdown-header">Public List</li>
                        <li><a href="#">Recent Update</a></li>
                        <li><a href="#">Most Popular</a></li>
                        <li><a href="#">Recommendation</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="dropdown-header">Your Flavor</li>
                        <li><a href="#">Your Flavor</a></li>
                        <li><a href="#">Pre-ordered</a></li>
                        <li><a href="#">Borrowed</a></li>
                        <li><a href="#">History</a></li>
                    </ul>
                </li>
                <jsp:invoke fragment="pageHeader"/>
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <layout:login>
                    <jsp:attribute name="whenLogin">
                        <li>
                            <a href="<spring:url value="/user/index" />"
                               title="manage user profile">Welcome <c:out value="${sessionScope.Auth.name}"/>! </a>
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

        2016 &copy; Lib-Mgmt | <a href="<spring:url value="/home/about" />">About</a>

    </div>
</div>
<script>
    $.material.init();
</script>

</body>
</html>