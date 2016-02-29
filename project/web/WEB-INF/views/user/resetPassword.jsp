<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="Change Password">
    <jsp:body>
        <div class="container">
            <h1>
                Reset Password
            </h1>
            <a href="<spring:url value="/user/manage" />"> &#8810; Return to Manage page </a>
            <p>
                After resetting the password for current user, the user cannot login with existing username password
                pair.

            </p>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                        <%--@elvariable id="errorMessageId" type="java.lang.String"--%>
                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <h4>Account Information</h4>
                    <dl class="dl-horizontal">
                        <dt>User name</dt>
                        <dd><c:out value="${CurrentUser.userName}"/></dd>
                        <dt>Name</dt>
                        <dd><c:out value="${CurrentUser.name}"/></dd>
                        <dt>Email</dt>
                        <dd><c:out value="${CurrentUser.email}"/></dd>
                        <dt>Phone Number</dt>
                        <dd><c:out value="${CurrentUser.phoneNumber}"/></dd>
                        <dt>Date of Birth</dt>
                        <dd><fmt:formatDate value="${CurrentUser.dateOfBirth}" type="Date" pattern="yyyy-MM-dd"/></dd>
                    </dl>

                            <form:form
                                    action="${ pageContext.request.contextPath }/user/resetPassword/${CurrentUser.id}"
                                    modelAttribute="UserChangePasswrd"
                                    method="post"
                                    cssClass="form-horizontal">
                        <%-- This form use standard HTML5 tags to utilize the new features in HTML 5 --%>
                        <%-- As a result, I need to manually bind data to related input element --%>
                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-password">New Password</label>
                            <div class="col-xs-9">
                                <input type="text" name="password" id="form-password"
                                       value="<c:out value="${UserResetPassword.password}"/>"
                                       required class="form-control" title="Current password"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Reset Password" type="submit"/>
                            <input class="btn btn-raised" value="Clear" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>