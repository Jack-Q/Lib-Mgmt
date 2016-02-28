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
                        <span class="num" id="num-current-holding"></span>
                        books
                    </span>
                    <span class="label label-info">
                        Returning
                        <span class="num" id="num-current-returning"></span>
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
                    <table class="table table-hover" style="margin-bottom: 190px;">
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
                            <tr data-loan-row="${Loan.id}">
                                <td>${Loan.id}</td>
                                <td><c:out value="${Loan.bookCopy.book.bookName}"/></td>
                                <td><c:out value="${Loan.bookCopy.book.bookCode}"/></td>
                                <td class="data-period"><c:out value="${Loan.loanPeriod}"/></td>
                                <td class="data-deadline">
                                    <c:if test="${Loan.deadlineOfReturning.before(DateTimeNow)}">
                                        <span class="label label-danger center-block">
                                            PASSED: Fined for
                                            <fmt:formatNumber type="currency"
                                                              pattern="$0.00"
                                                              value="${Loan.calcLoanFinedAmount(null)}"/>
                                        </span>
                                    </c:if>
                                    <c:out value="${Loan.deadlineOfReturning}"/>
                                </td>
                                <td>
                                    <div class="btn-group">
                                        <button data-action="${Loan.deadlineOfReturning.before(DateTimeNow)?"returnFined":"return" }"
                                                data-id="${Loan.id}"
                                                class="load-operation-btn btn btn-primary btn-raised">
                                            <c:if test="${Loan.deadlineOfReturning.before(DateTimeNow)}">Pay & </c:if>
                                            Return
                                        </button>
                                        <button type="button" class="btn btn-raised btn-primary dropdown-toggle"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu">
                                            <li class="dropdown-header">Other Scenarios</li>
                                            <c:if test="${Loan.deadlineOfReturning.before(DateTimeNow)}">
                                                <li>
                                                    <button data-action="return" data-id="${Loan.id}"
                                                            class="load-operation-btn btn btn-block">Return without
                                                        fined.
                                                    </button>
                                                </li>
                                            </c:if>
                                            <li>
                                                <button data-action="broken" data-id="${Loan.id}"
                                                        class="load-operation-btn btn btn-block">Book Broken
                                                </button>
                                            </li>
                                            <li>
                                                <button data-action="lost" data-id="${Loan.id}"
                                                        class="load-operation-btn btn btn-block">Book Lost
                                                </button>
                                            </li>
                                            <li role="separator" class="divider"></li>
                                            <li class="dropdown-header">Options</li>
                                            <li>
                                                <button data-action="extend" data-id="${Loan.id}"
                                                        class="load-operation-btn btn btn-block">Extend Period
                                                </button>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="loan-empty-tip well well-lg">
                    No book is held by current user currently ~
                </div>
            </div>
            <div class="modal fade" id="message-modal">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-body">
                            <p id="message-modal-message">

                            </p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default btn-raised" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary btn-raised" data-action="do">OK</button>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                $(function () {
                    var currentReturning = 0;
                    var currentHolding = ${BookHoldingNum};
                    // Update Lend Status
                    var updateLendStatus = function () {
                        $("#num-current-holding").text(currentHolding);
                        $("#num-current-returning").text(currentReturning);
                        // Lend table
                        $(".lend-list").css({"display": currentHolding > 0 ? "block" : "none"});

                        // Tip
                        $(".loan-empty-tip").css({"display": currentHolding == 0 ? "block" : "none"});
                    };
                    updateLendStatus(); // Setup init state;

                    var operationList = {
                        'lost': {
                            url: "<spring:url value="/loan/returnAjax/" />",
                            message: "Sure to mark this book as lost?",
                            finish: function (id) {
                                $("tr[data-loan-row='" + id + "']").fadeOut();
                                currentHolding--;
                                currentReturning++;
                            }
                        },
                        'extend': {
                            url: "<spring:url value="/loan/returnAjax/" />",
                            message: "Sure to extend the loan period of this book?",
                            finish: function (id, data) {
                                var row = $("tr[data-loan-row='" + id + "']");
                                var newPeriod = data.period || "Refresh to update";
                                var newDeadline = data.deadline || "Refresh to update";
                                row.find(".data-period").text(newPeriod);
                                row.find(".data-deadline").text(newDeadline)
                            }
                        },
                        'broken': {
                            url: "<spring:url value="/loan/returnAjax/" />",
                            message: "Sure to mark this book as broken?",
                            finish: function (id) {
                                $("tr[data-loan-row='" + id + "']").fadeOut();
                                currentHolding--;
                                currentReturning++;
                            }
                        },
                        'return': {
                            url: "<spring:url value="/loan/returnAjax/" />",
                            message: "Sure to return this book?",
                            finish: function (id) {
                                $("tr[data-loan-row='" + id + "']").fadeOut();
                                currentHolding--;
                                currentReturning++;
                            }
                        },
                        'returnFined': {
                            url: "<spring:url value="/loan/returnAjax/" />",
                            message: "Sure to return this book ? (The reader will be fined! )",
                            finish: function (id) {
                                $("tr[data-loan-row='" + id + "']").fadeOut();
                                currentHolding--;
                                currentReturning++;
                            }
                        }
                    };

                    var doModal = function () {
                        var modal = $("#message-modal");
                        var currentCallback;
                        var modalMessage = $("#message-modal-message");
                        modal.find("button").click(function () {
                            var action = this.dataset.action;
                            if (action == "do") {
                                modal.modal('hide');

                                currentCallback && setTimeout(currentCallback, 420);
                            }
                        });
                        return function (callback, message) {
                            modalMessage.text(message);
                            modal.modal('show');
                            currentCallback = callback;
                        }
                    }();

                    var doProgressingPost = function (url, option, callback) {
                        $.post(url, option, function (data) {
                            callback && callback(data);
                        }).fail(function (data) {
                            callback && callback(data);
                        });
                    };

                    $(".load-operation-btn").on('click', function () {
                        var id = parseInt(this.dataset.id);
                        var operation = this.dataset.action;
                        doModal(function () {
                            doProgressingPost(operationList[operation].url, {
                                id: id,
                                action: operation
                            }, function (data) {
                                if (data.success) {
                                    doModal(null, "Operation finished");
                                    operationList[operation].finish(id, data);
                                    updateLendStatus();
                                } else {
                                    doModal(null, "Operation failed");
                                }

                            })
                        }, operationList[operation].message)
                    });
                });
            </script>
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