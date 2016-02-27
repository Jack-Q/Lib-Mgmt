<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<layout:basic pageTitle="Book Management">
    <jsp:body>
        <div class="container">
            <h1>
                Book Management
            </h1>
            <h2>
                Manage book records
            </h2>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>
            <div>
                <a class="btn btn-lg btn-raised btn-primary" href="<spring:url value="/book/add" />">Add new book</a>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <c:choose>
                        <c:when test="${BookList.size() == 0}">
                            <%--Empty List--%>
                            No Books found ~
                        </c:when>
                        <c:otherwise>
                            <%--Nonempty List--%>
                            <%--Extra bottom padding to contain the drop down menu--%>
                            <div class="table-responsive" style="padding-bottom: 150px;">
                                <table class="table  table-hover">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Book Name</th>
                                        <th>Code</th>
                                        <th>Author</th>
                                        <th>ISBN</th>
                                        <th width="180">
                                            <div class="sr-only">Operations</div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${BookList}" var="Book">
                                        <tr>
                                            <td><c:out value="${Book.id}"/></td>
                                            <td><c:out value="${Book.bookName}"/></td>

                                            <td><c:out value="${Book.bookCode}"/></td>
                                            <td><c:out value="${Book.author}"/></td>
                                            <td><c:out value="${Book.isbn}"/></td>
                                            <td>
                                                <div class="btn-group">
                                                    <button class="btn btn-primary btn-raised btn-sm dropdown-toggle"
                                                            data-toggle="dropdown">Actions <i class="caret"></i>
                                                    </button>
                                                    <ul class="dropdown-menu">
                                                        <li>
                                                            <a href="<spring:url value="/book/detail/${Book.id}"/>">
                                                                View Detail
                                                            </a>
                                                        </li>
                                                        <li class="divider"></li>
                                                        <li>
                                                            <a href="<spring:url value="/book/edit/${Book.id}"/>">
                                                                Update information
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="<spring:url value="/book/copies/${Book.id}"/>">
                                                                Manage Copies
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