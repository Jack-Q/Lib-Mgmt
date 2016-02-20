<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="CurrentUser" value="${applicationScope}" scope="page"/>

<layout:basic pageTitle="User Profile">
    <jsp:body>
        <div class="container">
            <h1>
                User Profile
            </h1>
            <h2>
                Manage Your Account Data

            </h2>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>
            <div class="row">
                <div class="col-sm-6 col-lg-4">
                    <h4>Account Information</h4>
                    <dl class="dl-horizontal">
                        <dt>User name</dt>
                        <dd><c:out value="${User.userName}"/></dd>
                        <dt>Password</dt>
                        <dd> ****** <a title="Change Password" href="<spring:url value="/user/changePassword" />"
                                       class="btn btn-raised  btn-sm">Change Password</a></dd>
                    </dl>
                    <h4>User Information</h4>
                    <dl class="dl-horizontal">
                        <dt>Name</dt>
                        <dd><c:out value="${User.name}"/></dd>
                        <dt>Email</dt>
                        <dd><c:out value="${User.email}"/></dd>
                        <dt>Phone Number</dt>
                        <dd><c:out value="${User.phoneNumber}"/></dd>
                        <dt>Date of Birth</dt>
                        <dd><fmt:formatDate value="${User.dateOfBirth}" type="Date" pattern="yyyy-MM-dd"/></dd>
                        <dt></dt>
                        <dd>
                            <a class="btn btn-raised btn-sm" href="<spring:url value="/user/information" />"
                               title="Update Data">Update
                                Data</a>
                        </dd>
                    </dl>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:basic>