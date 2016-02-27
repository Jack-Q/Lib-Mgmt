<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="Loan Status">
    <jsp:body>
        <div class="container page-lend">
            <h1>
                <c:out value="${ ManageView ? CurrentUser.name : 'My' }"/> Loan Status
            </h1>
            <c:choose>
                <c:when test="${ManageView}">
                    <a href="<spring:url value="/user/manage" />"> &#8810; Return to Manage page </a>
                </c:when>
                <c:otherwise>

                    <a href="<spring:url value="/" />"> &#8810; Return to Index page </a>
                </c:otherwise>
            </c:choose>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>

            <div>

                <!-- Nav tabs -->
                <ul class="nav nav-pills">
                    <li role="presentation" class="active"><a href="#reading" aria-controls="home" role="tab"
                                                              data-toggle="tab"> ${ ManageView? "" : "I\'m " }
                        Reading</a></li>
                    <li role="presentation"><a href="#read" aria-controls="profile" role="tab"
                                               data-toggle="tab">${ ManageView? "" : "I\'ve " }
                        Read</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div class="tab-pane active fade in" id="reading">
                        <c:choose>
                            <c:when test="${CurrentLoanList.size() > 0 }">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Book Name</th>
                                            <th>Date of borrowing</th>
                                            <th>Deadline of returning</th>
                                            <th>Note</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${CurrentLoanList}" var="CurrentLoan">
                                            <tr>
                                                <td>${CurrentLoan.id}</td>
                                                <td>
                                                    <a href="<spring:url value="/book/detail/${CurrentLoan.bookCopy.book.id}"/>">
                                                        <c:out value="${CurrentLoan.bookCopy.book.bookName}"/>
                                                    </a>
                                                </td>
                                                <td>
                                                    <fmt:formatDate value="${CurrentLoan.dateOfBorrowing}"
                                                                    pattern="yyyy-MM-dd hh:mm:ss"/>
                                                </td>
                                                <td>
                                                    <c:if test="${CurrentLoan.deadlineOfReturning.before(DateTimeNow) }">
                                                        <span class="center-block label label-danger">FINED: will be fined for
                                                            <fmt:formatNumber type="currency"
                                                                              pattern="$0.00"
                                                                              value="${CurrentLoan.calcLoanFinedAmount(null)}"/>
                                                        </span>
                                                    </c:if>
                                                    <fmt:formatDate value="${CurrentLoan.deadlineOfReturning}"
                                                                    pattern="yyyy-MM-dd hh:mm:ss"/>
                                                </td>
                                                <td>
                                                    <c:out value="${CurrentLoan.note}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="well well-lg">
                                    No book in this list ~
                                    <c:if test="${!ManageView}">
                                        <br>
                                        Visit home page to search for more books
                                        <a class="btn btn-primary btn-raised" href="<spring:url value="/"/>">Home
                                            Page</a>
                                    </c:if>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="tab-pane fade" id="read">
                        <c:choose>
                            <c:when test="${FinishedLoanList.size() > 0 }">
                                <div class="table-responsive">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Book Name</th>
                                            <th>Finished Date</th>
                                            <th>Note</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${FinishedLoanList}" var="FinishedLoan">
                                            <tr>
                                                <td>${FinishedLoan.id}</td>
                                                <td>
                                                    <a href="<spring:url value="/book/detail/${FinishedLoan.bookCopy.book.id}"/>">
                                                        <c:out value="${FinishedLoan.bookCopy.book.bookName}"/>
                                                    </a>
                                                </td>
                                                <td>
                                                    <c:out value="${FinishedLoan.dateOfReturning}"/>
                                                </td>
                                                <td>
                                                    <c:if test="${FinishedLoan.finedAmount > 0}">
                                                        <span class="label label-danger">Fined: <fmt:formatNumber
                                                                value="${FinishedLoan.finedAmount}"
                                                                pattern="$0.00"/></span>
                                                    </c:if>
                                                    <c:out value="${FinishedLoan.note}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="well well-lg">
                                    <c:choose>
                                        <c:when test="${ManageView}">
                                            No book in this list ~
                                        </c:when>
                                        <c:otherwise>

                                            It seems that you've not finished reading any books ~

                                            <br>
                                            Visit home page to search for more books
                                            <a class="btn btn-primary btn-raised" href="<spring:url value="/"/>">Home
                                                Page</a>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            </div>

        </div>
    </jsp:body>
</layout:basic>