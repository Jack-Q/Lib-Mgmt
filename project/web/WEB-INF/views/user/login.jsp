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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="User Login">
    <jsp:body>
        <div class="container">
            <h1>Login</h1>
            <h2>Login Lib-Mgmt to manage your account.</h2>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <form:form action="${ pageContext.request.contextPath }/user/login?returnTo=${returnTo}"
                               modelAttribute="UserLogin" method="post">
                        <div class="form-group">
                            <label class="control-label" for="form-username">User Name</label>
                            <form:input path="userName" id="form-username" cssClass="form-control" title="User name"/>
                        </div>
                        <div class="form-group">
                            <label class="control-label" for="form-password">Password</label>
                            <form:password path="password" id="form-password" cssClass="form-control" title="Password"/>
                        </div>
                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Login" type="submit"/>
                            <input class="btn btn-raised" value="Reset" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>

    </jsp:body>
</layout:basic>