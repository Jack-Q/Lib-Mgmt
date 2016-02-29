<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="Change Password">
    <jsp:body>
        <div class="container">
            <h1>
                Change Password
            </h1>
            <a href="<spring:url value="/user/index" />"> &#8810; Return to Profile page </a>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <form:form action="${ pageContext.request.contextPath }/user/changePassword"
                               modelAttribute="UserChangePasswrd" method="post"
                               cssClass="form-horizontal">
                        <%-- This form use standard HTML5 tags to utilize the new features in HTML 5 --%>
                        <%-- As a result, I need to manually bind data to related input element --%>
                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-current-password">Current Password</label>
                            <div class="col-xs-9">
                                <input type="password" name="currentPassword" id="form-current-password"
                                       placeholder="0123456789"
                                       value="<c:out value="${UserChangePassword.currentPassword}"/>"
                                       required class="form-control" title="Current password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-new-password">New Password</label>
                            <div class="col-xs-9">
                                <input type="password" name="newPassword"
                                       value="<c:out value="${UserChangePassword.newPassword}"/>"
                                       onchange="$('#form-new-password-repeat').attr('pattern', this.value.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\\$&'))"
                                       placeholder="%$^&^%%^$@@%!@%$#$#^&" id="form-new-password" required
                                       class="form-control" title="New Password"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-new-password-repeat">New Password
                                Repeat</label>
                            <div class="col-xs-9">
                                <input type="password" id="form-new-password-repeat"
                                       placeholder="%$^&^%%^$@@%!@%$#$#^&"
                                       value="<c:out value="${UserChangePassword.newPassword}"/>"
                                       required class="form-control" title="Repeat your new password again"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Change Password" type="submit"/>
                            <input class="btn btn-raised" value="Reset" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>