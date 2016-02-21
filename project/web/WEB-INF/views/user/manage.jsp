<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<layout:basic pageTitle="User Profile">
    <jsp:body>
        <div class="container">
            <h1>
                User Management
            </h1>
            <h2>
                Manage User Accounts
            </h2>
            <div class="row">
                <div class="col-sm-6 col-lg-4">
                    <c:choose>
                        <c:when test="${ReaderList.size() == 0}">
                            <%--Empty List--%>
                            No User found ~
                        </c:when>
                        <c:otherwise>
                            <%--Nonempty List--%>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>User Name</th>
                                    <th>Roles</th>
                                    <th>Name</th>
                                    <th>Email</th>
                                    <th>Date of Birth</th>
                                    <th>Phone Number</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${ReaderList}" var="Reader">
                                    <tr>
                                        <td><c:out value="${Reader.id}"/></td>
                                        <td><c:out value="${Reader.userName}"/></td>
                                        <td><c:out value="${Reader.roles}"/></td>
                                        <td><c:out value="${Reader.name}"/></td>
                                        <td><c:out value="${Reader.email}"/></td>
                                        <td><c:out value="${Reader.dateOfBirth}"/></td>
                                        <td><c:out value="${Reader.phoneNumber}"/></td>
                                    </tr>

                                </c:forEach>
                                </tbody>
                            </table>


                        </c:otherwise>
                    </c:choose>


                    <div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:basic>