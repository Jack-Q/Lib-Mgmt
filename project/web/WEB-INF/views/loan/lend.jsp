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
                        <span class="num" id="num-current-holding">${BookHoldingNum}</span>
                        books
                    </span>
                    <span class="label label-info">
                        Renting
                        <span class="num" id="num-current-renting"></span>
                        books
                    </span>
                    <span class="label label-info">
                        Leaving
                        <span class="num" id="num-current-leaving"></span>
                        books
                    </span>
                </div>
                <div class="limit-tip well well-lg">
                    Sorry, the reader has arrived the limit of books to borrow.
                    Please let him return some books first.
                </div>
                <div class="lend-action row">
                    <div class="col-sm-4 col-lg-3">
                        <button data-toggle="tooltip" id="book-lend-toggle"
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
                                <button type="button" id="book-search-btn" class="btn btn-fab btn-fab-mini">
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
                        <tbody id="lend-table-body">

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
        <script>
            // search/loanAjax?q=123&byCode=1&byName=0&byAuthor=1&userId=1
            $(function () {
                var currentUserId = ${CurrentUser.id};
                var currentBookLimit = ${BookLeavingNum};
                var currentLending = 0;

                var loanBookData = [];
                var currentListData = [];
                var candidates = $(".select-candidate");
                var bookSearch = $("#book-search");

                // Update Lend Status
                var updateLendStatus = function () {
                    $("#num-current-leaving").text(currentBookLimit - currentLending);
                    $("#num-current-renting").text(currentLending);
                    // Lend table
                    $(".lend-list").css({"display": currentLending > 0 ? "block" : "none"});

                    // Button
                    $("#book-lend-toggle").attr("disabled", currentBookLimit - currentLending == 0);

                    $('.select-book').removeClass('on');

                    // Limit Tip
                    (currentBookLimit - currentLending > 0) ?
                            $(".limit-tip").hide() : $(".limit-tip").show();
                };
                updateLendStatus(); // Setup init state;

                // Book Lent Toggle
                $("#book-lend-toggle").click(function () {
                    if (currentBookLimit - currentLending == 0) {
                        return;
                    }

                    $('.select-book').toggleClass('on');
                    candidates.empty();
                    // Call change to trigger Material Float style detector
                    bookSearch.val("").change();
                });

                // Book Lent box
                var updateCandidates = function (newCandidates) {

                    candidates.fadeOut(100, function () {
                        candidates.empty();
                        currentListData = newCandidates;
                        newCandidates.forEach(function (candidate) {
                            candidates.append("<span data-id='" + candidate.id + "'>"
                                    + candidate.bookname + "," + candidate.author + ","
                                    + candidate.year + "[" + candidate.bookcode + "][" + candidate.isbn + "]</span>");
                        });
                        if (newCandidates.length == 0) {
                            candidates.append("<div class='alert alert-inverse  '>no book satisfying current creteria can be lend to current reader</div>");
                        }
                    }).fadeIn(100);
                };
                var retrieveCandidates = function () {
                    var query = bookSearch.val();
                    var isByName = $("#book-search-name").prop("checked");
                    var isByCode = $("#book-search-code").prop("checked");
                    var isByAuthor = $("#book-search-author").prop("checked");
                    if (!isByCode && !isByName && !isByAuthor) {
                        $("#book-search-name").prop("checked", true);
                        isByName = true;
                    }
                    if (query.length == 0) {
                        bookSearch.focus();
                        return;
                    }
                    $.get("<spring:url value="/search/loanAjax" />", {
                        q: query,
                        byCode: isByCode,
                        byAuthor: isByAuthor,
                        byName: isByName,
                        userId: currentUserId
                    }, function (res) {
                        if (typeof res === 'string') {
                            try {
                                res = JSON.parse(res);
                            } catch (e) {
                                res = {success: false};
                            }
                        }

                        if (res.success && res.data) {
                            updateCandidates(res.data);
                        }

                    });
                };
                $("#book-search-btn").click(retrieveCandidates);
                bookSearch.bind("keydown", function (e) {
                    if (e.keyCode == 13 /* Key Code for Enter*/) {
                        retrieveCandidates();
                    }
                });
                candidates.click(function (e) {
                    var id = parseInt($(e.target).data("id"));
                    if (!id) {
                        return;
                    }
                    var book;
                    currentListData.forEach(function (b) {
                        if (b.id === id) {
                            book = b;
                        }
                    });
                    if (!book) {
                        return;
                    }
                    selectBook(book);

                });

                // Select book
                var selectBook = function (book) {
                    $.post("<spring:url value="/loan/lendAjax" />", {
                        userId: currentUserId,
                        bookId: book.id
                    }, function (res) {
                        if (typeof res === 'string') {
                            try {
                                res = JSON.parse(res);
                            } catch (e) {
                                res = {success: false};
                            }
                        }
                        if (res.success) {
                            currentLending++;
                            loanBookData.push(book);
                            var $row = $("<tr>");
                            $row.append($("<td>").text(book.id));
                            $row.append($("<td>").text(book.bookname));
                            $row.append($("<td>").text(book.bookcode));
                            $row.append($("<td>").text(res.period));
                            $("#lend-table-body").append($row);
                            updateLendStatus();
                        }
                    }).fail(function () {

                    });
                };


            });
        </script>
    </jsp:body>
</layout:basic>