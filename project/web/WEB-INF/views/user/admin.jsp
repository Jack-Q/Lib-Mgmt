<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<c:set var="CurrentUser" value="${applicationScope}" scope="page"/>

<layout:basic pageTitle="Manage Librarian Account">
    <jsp:body>
        <div class="container">
            <h1>
                Manage Librarian Account
            </h1>
            <p>
                To add new librarian, register a new account and then grant
                <span class="label label-info">LIBRARIAN</span> role to his account
                at <a href="<spring:url value="/user/manage" />" class="btn btn-xs btn-raised">User Manage</a> page.
            </p>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>
            <div class="row">
                <div class="col-xs-12">
                    <c:choose>
                        <c:when test="${LibrarianList.size() == 0}">
                            <%--Empty List--%>
                            No User found ~
                        </c:when>
                        <c:otherwise>
                            <%--Nonempty List--%>
                            <%--Extra bottom padding to contain the drop down menu--%>
                            <div class="table-responsive" style="padding-bottom: 100px;">
                                <table class="table  table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>User Name</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Date of Birth</th>
                                        <th>Phone Number</th>
                                        <th>
                                            <div class="sr-only">Operations</div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${LibrarianList}" var="Librarian">
                                        <tr>
                                            <td><c:out value="${Librarian.id}"/></td>
                                            <td><c:out value="${Librarian.userName}"/></td>
                                            <td><c:out value="${Librarian.name}"/></td>
                                            <td><c:out value="${Librarian.email}"/></td>
                                            <td><fmt:formatDate value="${Librarian.dateOfBirth}" type="Date"
                                                                pattern="yyyy-MM-dd"/></td>
                                            <td><c:out value="${Librarian.phoneNumber}"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-primary btn-raised btn-sm dropdown-toggle"
                                                            data-toggle="dropdown">Actions <i class="caret"></i>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a href="<spring:url value="/user/resetPassword/${Librarian.id}"/>">
                                                                Reset Password
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="<spring:url value="/user/edit/${Librarian.id}"/>">
                                                                Edit Profile
                                                            </a>
                                                        </li>
                                                        <li class="divider"></li>
                                                        <li>
                                                            <a href="<spring:url value="/user/deselectLibrarian/${Librarian.id}"/>"
                                                               class=" alert-link">
                                                                Mark as Guest
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>

                    </c:choose>

                </div>
            </div>
        </div>
    </jsp:body>
</layout:basic>