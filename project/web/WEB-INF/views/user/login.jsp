<%--
  Created by IntelliJ IDEA.
  User: Jack
  Date: 2/4/2016
  Time: 22:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>

<layout:basic pageTitle="User Login">
    <jsp:attribute name="pageHeader">
        <li class="dropdown">
            <a class="dropdown-toggle" href="#" data-toggle="dropdown">User Login<i class="caret"></i></a>
            <ul class="dropdown-menu">
                <li><a href="#">Login</a></li>
            </ul>
        </li>
    </jsp:attribute>
    <jsp:attribute name="pageFooter">
        <div>
            Need Help for login? Refer to Help Center.
        </div>
    </jsp:attribute>
    <jsp:body>
        <div>
            <spring:form action="/user/login" method="post">

            </spring:form>
        </div>
    </jsp:body>
</layout:basic>