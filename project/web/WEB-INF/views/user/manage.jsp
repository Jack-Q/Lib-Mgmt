<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="User Management">
    <jsp:body>
        <div class="container">
            <h1>
                User Management
            </h1>
            <h2>
                Manage User Accounts
            </h2>
            <c:if test="${sessionScope.get('Auth').admin}">

                <p>
                    This page only list the user with role of <span class="label label-info">GUEST</span> or <span
                        class="label label-info">STUDENT</span>.
                    To manage account for librarians, please visit <a href="<spring:url value="/user/admin" />"
                                                                      class="btn btn-xs btn-raised">Admin
                    page</a>.
                </p>
            </c:if>
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
                        <c:when test="${ReaderList.size() == 0}">
                            <%--Empty List--%>
                            No User found ~
                        </c:when>
                        <c:otherwise>
                            <%--Nonempty List--%>
                            <%--Extra bottom padding to contain the drop down menu--%>
                            <div class="table-responsive" style="padding-bottom: 150px;">
                                <table class="table  table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>User Name</th>
                                        <th>Role</th>
                                        <th>Name</th>
                                        <th>Email</th>
                                        <th>Date of Birth</th>
                                        <th>Phone Number</th>
                                        <th width="170">
                                            <div class="sr-only">Operations</div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${ReaderList}" var="Reader">
                                        <tr>
                                            <td><c:out value="${Reader.id}"/></td>
                                            <td><c:out value="${Reader.userName}"/></td>
                                            <td data-role-list="${Reader.id}">
                                                <c:forEach var="role" items="${Reader.roles}">
                                                    <span class="label label-info">
                                                        <c:out value="${role.name()}"/>
                                                    </span>
                                                </c:forEach>

                                                <a href="#" title="change user role" data-toggle="tooltip"
                                                   data-placement="bottom" class="btn btn-fab btn-fab-mini"
                                                   data-id="${Reader.id}">
                                                    <i class="material-icons">&#xE8E8;</i>
                                                </a>
                                            </td>
                                            <td><c:out value="${Reader.name}"/></td>
                                            <td><c:out value="${Reader.email}"/></td>
                                            <td><fmt:formatDate value="${Reader.dateOfBirth}" type="Date"
                                                                pattern="yyyy-MM-dd"/></td>
                                            <td><c:out value="${Reader.phoneNumber}"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-primary btn-raised btn-sm dropdown-toggle"
                                                            data-toggle="dropdown">Actions <i class="caret"></i>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a href="<spring:url value="/loan/status/${Reader.id}"/> ">
                                                                Loan Status
                                                            </a>
                                                        </li>
                                                        <li class="divider"></li>
                                                        <li>
                                                            <a href="<spring:url value="/user/resetPassword/${Reader.id}"/>">
                                                                Reset Password
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="<spring:url value="/user/edit/${Reader.id}"/>">
                                                                Edit Profile
                                                            </a>
                                                        </li>

                                                        <c:if test="${sessionScope.get('Auth').admin}">
                                                            <li class="divider"></li>
                                                            <li>
                                                                <a href="<spring:url value="/user/selectLibrarian/${Reader.id}"/>"
                                                                   class=" alert-link">
                                                                    Mark as Librarian
                                                                </a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <%-- Model for choosing user role --%>
                            <div class="modal fade" id="role-selection-dialog">
                                <div class="modal-dialog ">
                                    <div class="modal-content">

                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title" id="exampleModalLabel">Select Role</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div>
                                                <a href="#" class="btn btn-block btn-raised btn-primary"
                                                   data-action="STUDENT">STUDENT</a>
                                                <a href="#" class="btn btn-block btn-raised btn-primary"
                                                   data-action="GUEST">GUEST</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <%--Script for Modal Action--%>
                            <script>
                                $(function () {
                                    var roleList = $("td[data-role-list]");
                                    var roleButton = $("a", roleList);
                                    var roleDialog = $("#role-selection-dialog");
                                    var currentId = 0;
                                    roleButton.click(function () {
                                        currentId = $(this).data("id");
                                        roleDialog.modal();
                                    });

                                    var roleSelection = roleDialog.find("a[data-action]");
                                    roleSelection.click(function () {
                                        if ($(this).attr("disabled") == "disabled") {
                                            return;
                                        }
                                        var role = $(this).data("action");
                                        if (roleList.parent().find("[data-role-list=" + currentId + "] span").text().replace(/\W/g, "") == role) {

                                            roleDialog.modal('hide');
                                            return;
                                        }
                                        roleSelection.attr("disabled", "disabled");
                                        $.post("<spring:url value="/user/changeRoleAjax" />", {
                                            userId: currentId,
                                            roleName: role
                                        }, function (res) {
                                            if (typeof res === 'string') {
                                                try {
                                                    res = JSON.parse(res);
                                                } catch (e) {
                                                    res = {success: false};
                                                }
                                            }

                                            if (res.success) {
                                                roleList.parent().find("[data-role-list=" + currentId + "] span").text(role);
                                            }
                                            roleSelection.removeAttr("disabled");
                                            roleDialog.modal("hide");

                                        }).fail(function () {
                                            roleSelection.removeAttr("disabled");
                                            roleDialog.modal("hide");
                                        });
                                    });
                                });
                            </script>
                        </c:otherwise>

                    </c:choose>

                </div>
            </div>
        </div>
    </jsp:body>
</layout:basic>