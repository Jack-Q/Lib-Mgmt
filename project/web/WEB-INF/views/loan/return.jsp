<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="Return books">
    <jsp:body>
        <div class="container page-return">
            <h1>
                Lend books
            </h1>
            <a href="<spring:url value="/" />"> &#8810; Return to Index page </a>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>
            <div class="step step-user">
                <div class="step-title">
                    User
                </div>
                <div class="selected-user">
                    <div class="user-name">
                        <span id="user-name"><c:out value="${CurrentUser.userName}"/></span>
                        (<span id="user-id">${CurrentUser.id}</span>)
                    </div>
                </div>
            </div>
            <div class="step step-book">

                <div class="step-title">
                    Books
                </div>
                <div class="amount-tip">
                    <span class="label label-info">
                        Holding
                        <span class="num" id="num-current-holding">${BookHoldingNum}</span>
                        books
                    </span>
                    <span class="label label-info">
                        Renting
                        <span class="num" id="num-current-renting">0</span>
                        books
                    </span>
                    <span class="label label-info">
                        Leaving
                        <span class="num" id="num-current-leaving">${BookLeavingNum}</span>
                        books
                    </span>
                </div>
                <div class="lend-action row">
                    <div class="col-sm-4 col-lg-3">
                        <a class="btn btn-raised btn-primary"
                           href="<spring:url value="/loan/lend/${CurrentUser.id}"/> ">Lend books</a>
                    </div>
                </div>
                <div class="lend-list table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Book Name</th>
                            <th>Book Code</th>
                            <th>Loan Period</th>
                            <th>Deadline</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="Loan" items="${LoanList}">
                            <tr>
                                <td>${Loan.id}</td>
                                <td><c:out value="${Loan.bookCopy.book.bookName}"/></td>
                                <td><c:out value="${Loan.bookCopy.book.bookCode}"/></td>
                                <td><c:out value="${Loan.loanPeriod}"/></td>
                                <td><c:out value="${Loan.deadlineOfReturning}"/></td>
                                <td>
                                    <button data-loan-id="${Loan.id}" class="btn btn-primary btn-raised">Return</button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="step step-finish">

                <div class="step-title">
                    Next step ...
                </div>
                <a href="<spring:url value="/" />" data-toggle="tooltip"
                   class="btn btn-raised btn-lg btn-primary"
                   title="finish lending books and return to index page ">Finish</a>
                <a href="<spring:url value="/loan/return" />" data-toggle="tooltip"
                   class="btn btn-raised btn-lg btn-primary"
                   title="refresh current page and restart the process of returning books">Restart</a>
                <p class="badge">
                    * Select finish to return to index page or select restart to start a new process of
                    returning books
                </p>
            </div>
        </div>
    </jsp:body>
</layout:basic>