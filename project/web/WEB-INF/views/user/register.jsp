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
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
        <h1>
            User Register
        </h1>
        <p>
            Welcome to join us!
        </p>
        <div class="badge">
            Apart from general register process to create new account, you can also create new
            account via third-party service or CAS Service of XJTU. <a href="#">Learn More >></a>
        </div>
        <div>
            <form:form action="/user/register" commandName="UserRegister"  method="post" cssClass="form-horizontal">
                <div class="form-group">
                    <label class="label" for="form-username">User name</label>
                    <form:input path="userName" cssClass="form-control" id="form-username" />
                </div>
                <div class="form-group">
                    <label for="form-password" class="label"></label>
                    <form:input path="password" cssClass="form-control" id="form-password" />
                </div>
                <div class="form-group">
                    <input class="btn btn-raised btn-primary" type="submit" value="Register" />
                </div>
            </form:form>
        </div>
    </jsp:body>
</layout:basic>