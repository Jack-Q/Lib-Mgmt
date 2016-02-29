<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:login>
    <jsp:attribute name="whenLogin">

        <layout:basic pageTitle="Welcome">
            <div class="container">

                <h1 class="index-banner">
                    Welcome to Lib-Mgmt
                </h1>
                <div class="index-section">

                        <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
                    <c:if test="${(indexMessageId != null)}">
                        <div class="alert-info alert alert-dismissible">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                        </div>
                    </c:if>
                        <%-- Search box --%>
                    <h6>Search</h6>
                    <div class="index index-login">
                        <div class="index-search-wrapper">
                            <div class="index-search-box">
                                <div class="form-group label-floating">
                                    <label class="control-label" for="input-search">Type something here to search for a
                                        book &hellip; &hellip;</label>
                                    <input class="form-control" id="input-search" type="text">
                                </div>

                                <div class="icon-search" id="icon-search">
                                    <i class="material-icons">&#xE8B6;</i>
                                </div>
                            </div>
                        </div>

                    </div>
                    <hr>
                    <layout:role>
                        <jsp:attribute name="admin">
                            <h6>Operations</h6>
                            <div class="row">
                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage Book records and information. Add, update, remove, modify
                                            book data in database.
                                        </p>
                                        <a href="<spring:url value="/book/manage" />"
                                           class="btn btn-default btn-raised">
                                            Book Management
                                        </a>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage user information for those user with STUDENT role and GUEST role.
                                            You can modify their information and reset their password here.
                                            You can also grant librarian role to some user to select them as librarian
                                            here.
                                        </p>
                                        <a href="<spring:url value="/user/manage" />"
                                           class="btn btn-lg btn-default btn-raised">
                                            User Management
                                        </a>
                                    </div>
                                </div>
                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage librarian information here.
                                            You can also reset their password or revoke their librarian role.
                                        </p>
                                        <a href="<spring:url value="/user/admin" />" class="btn btn-default btn-raised">
                                            Librarian Management
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </jsp:attribute>
                        <jsp:attribute name="librarian">
                            <h6>Lend & Return</h6>
                            <div class="index-lend-return">
                                <a data-toggle="tooltip" href="<spring:url value="/loan/lend" />"
                                   title="Click to lend books to reader">
                                    Lend
                                </a>
                                &nbsp;
                                <a data-toggle="tooltip" href="<spring:url value="/loan/return" />"
                                   title="Click to accept books returned from reader">
                                    Return
                                </a>
                            </div>
                            <hr>
                            <h6>Operations</h6>
                            <div class="row">
                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage Book records and information. Add, update, remove, modify
                                            book data in database.
                                        </p>
                                        <a href="<spring:url value="/book/manage" />"
                                           class="btn btn-default btn-raised">
                                            Book Management
                                        </a>
                                    </div>
                                </div>

                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage user information for those user with STUDENT role and GUEST role.
                                            You can modify their information and reset their password here.
                                        </p>
                                        <a href="<spring:url value="/user/manage" />"
                                           class="btn btn-lg btn-default btn-raised">
                                            User Management
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </jsp:attribute>
                        <jsp:attribute name="student">
                            <h6>Operations</h6>
                            <div class="row">
                                <div class="col-sm-6 col-lg-4">
                                    <div class="well well-lg">
                                        <p>
                                            Manage my loan status and records.
                                        </p>
                                        <a href="<spring:url value="/loan/status" />"
                                           class="btn btn-default btn-raised">
                                            Loan Management
                                        </a>
                                    </div>
                                </div>

                            </div>
                        </jsp:attribute>
                        <jsp:attribute name="guest">
                            <h6>Operations</h6>
                            <div class="well">
                                Sorry, as a guest currently, you can only search for information about books.
                                Please contact librarian for supports.
                            </div>
                        </jsp:attribute>
                    </layout:role>

                </div>
            </div>
            <script>
                $(function () {
                    var search = $('#input-search');
                    var searchButton = $('#icon-search');
                    var doSearch = function () {
                        var searchValue = search.val();
                        if (searchValue == "") {
                            return false;
                        }
                        window.location = "<spring:url value="/search" />?q=" + encodeURIComponent(searchValue);
                        return true;
                    };
                    search.bind('focus blur change', function () {
                        if ($(this).is(':focus') || $(this).val().length > 0) {
                            searchButton.addClass('on')
                        } else {
                            searchButton.removeClass('on')
                        }
                    });
                    if (search.val().length > 0) {
                        searchButton.addClass('on');
                    }
                    search.bind('keydown', function (e) {
                        if (e.keyCode == 13 /*Code for enter kry*/) {
                            doSearch();
                        }
                    });
                    searchButton.bind('click', function () {
                        if (!doSearch()) {
                            search.focus();
                        }
                    });
                });
            </script>
        </layout:basic>
    </jsp:attribute>
    <jsp:attribute name="notLogin">

<!DOCTYPE html>
<html lang="en-US">
<head>
    <title>Lib-Mgmt</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Stylesheets -->
    <link href="<spring:url value="/style/bootstrap.css" />" rel="stylesheet">
    <link href="<spring:url value="/style/bootstrap-material-design.css" />" rel="stylesheet">
    <link href="<spring:url value="/style/ripples.css" />" rel="stylesheet"/>
    <link href="<spring:url value="/style/index.css"/>" rel="stylesheet"/>
    <!-- Scripts -->
    <script src="<spring:url value="/script/lib/jquery.js"/>"></script>
    <script src="<spring:url value="/script/lib/bootstrap.js"/>"></script>
    <script src="<spring:url value="/script/lib/ripples.js"/>"></script>
    <script src="<spring:url value="/script/lib/material.js"/>"></script>
    <script src="<spring:url value="/script/index.js"/>"></script>
</head>
<body class="index-body">

<div class="index">
    <div class="index-search-wrapper">
        <div class="index-search-box">
            <div class="form-group label-floating">
                <label class="control-label" for="input-search">Type something here to search for a
                    book &hellip; &hellip;</label>
                <input class="form-control" id="input-search" type="text">
            </div>

            <div class="icon-search" id="icon-search">
                <i class="material-icons">&#xE8B6;</i>
            </div>
        </div>
    </div>
    <div class="index-footer-wrapper">

        <div class="index-footer">
            <div class="logo-section">
                <div class="text-logo">
                    Lib-Mgmt
                </div>
            </div>
            <div class="user-section">
                <i class="material-icons">&#xE851;</i>
                <div class="section-actions">
                    <a href="<spring:url value="/user/login"/>">Login</a>
                    <a href="<spring:url value="/user/register"/>">Register</a>

                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $.material.init();
        $('[data-toggle="tooltip"]').tooltip();
        var search = $('#input-search');
        var searchButton = $('#icon-search');
        var doSearch = function () {
            var searchValue = search.val();
            if (searchValue == "") {
                return false;
            }
            window.location = "<spring:url value="/search" />?q=" + encodeURIComponent(searchValue);
            return true;
        };
        search.bind('focus blur change', function () {
            if ($(this).is(':focus') || $(this).val().length > 0) {
                searchButton.addClass('on')
            } else {
                searchButton.removeClass('on')
            }
        });
        if (search.val().length > 0) {
            searchButton.addClass('on');
        }
        search.bind('keydown', function (e) {
            if (e.keyCode == 13 /*Code for enter kry*/) {
                doSearch();
            }
        });
        searchButton.bind('click', function () {
            if (!doSearch()) {
                search.focus();
            }
        });
    })
</script>

</body>
</html>
    </jsp:attribute>
</layout:login>
