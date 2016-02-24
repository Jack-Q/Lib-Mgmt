<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="Lent books">
    <jsp:body>
        <div class="container page-lend">
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
                        <span class="num" id="num-current-holding">3</span>
                        books
                    </span>
                    <span class="label label-info">
                        Renting
                        <span class="num" id="num-current-renting">0</span>
                        books
                    </span>
                    <span class="label label-info">
                        Leaving
                        <span class="num" id="num-current-leaving">7</span>
                        books
                    </span>
                </div>
                <div class="limit-tip well well-lg">
                    Sorry, the reader has arrived the limit of books to borrow.
                    Please let him return some books first.
                </div>
                <div class="lend-action row">
                    <div class="col-sm-4 col-lg-3">

                        <button data-toggle="tooltip"
                                onclick="$('.select-book').toggleClass('on')"
                                class="btn btn-raised btn-lg btn-primary"
                                title="search for a book and lend it to current user">Lend a book
                        </button>


                    </div>
                    <div class="col-sm-4 col-lg-3">
                        <a class="btn btn-raised btn-primary"
                           href="<spring:url value="/loan/return/${CurrentUser.id}"/> ">Return books</a>
                    </div>
                </div>
                <div class="row select-book well well-lg">
                    <div class="col-sm-offset-1 col-sm-10">
                        <div class="form-group label-floating">

                            <label class="control-label" for="book-search">Search for a book</label>

                            <div class="input-group">
                                <input type="text" id="book-search" class="form-control">
                              <span class="input-group-btn">
                                <button type="button" class="btn btn-fab btn-fab-mini">
                                    <i class="material-icons">search</i>
                                </button>
                              </span>
                            </div>
                        </div>
                        <div class="row select-option">
                            <div class="col-sm-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="book-search-code"> by Code
                                    </label>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" id="book-search-name" checked> by Name
                                    </label>
                                </div>
                            </div>
                            <div class="col-sm-4">
                                <div class="checkbox">

                                    <label>
                                        <input type="checkbox" id="book-search-author" checked> by Author
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div id="book-candidate" class="select-candidate">
                            <span>Java Web Application, Somebody, 2015 [TP312JA0002]</span>
                            <span>Java Web Application, Somebody, 2015 [TP312JA0002]</span>
                            <span>Java Web Application, Somebody, 2015 [TP312JA0002]</span>
                            <span>Java Web Application, Somebody, 2015 [TP312JA0002]</span>
                        </div>
                    </div>
                </div>
                <div class="select-book-placer"></div>
                <div class="lend-list table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Book Name</th>
                            <th>Book Code</th>
                            <th>Loan Period</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>Java Programming Language</td>
                            <td>TP312JA-0001</td>
                            <td>60</td>
                        </tr>
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
                <a href="<spring:url value="/loan/lend" />" data-toggle="tooltip"
                   class="btn btn-raised btn-lg btn-primary"
                   title="refresh current page and restart the process of lending books">Restart</a>
                <p class="badge">
                    * Select finish to return to index page or select restart to start a new process of
                    lending books
                </p>
            </div>
        </div>
    </jsp:body>
</layout:basic>