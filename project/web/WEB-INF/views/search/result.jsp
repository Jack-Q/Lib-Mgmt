<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<body class="index-body result-body">

<div class="search-result">
    <div class="result-header">

        <div class="index-search-wrapper">
            <div class="index-search-box">
                <div class="form-group label-floating">
                    <label class="control-label" for="input-search">Type something here to search for a
                        book &hellip; &hellip;</label>
                    <input class="form-control" id="input-search" type="text" value="${QueryString}">
                </div>

                <div class="icon-search" id="icon-search">
                    <i class="material-icons">&#xE8B6;</i>
                </div>
            </div>
            <div>

            </div>
        </div>
        <div class="index-user">
            <layout:login>
                <jsp:attribute name="whenLogin">
                    <div class="user-info">

                        <div class="user-name">
                            <i class="material-icons">&#xE851;</i>
                            <div class="current-user">
                                <c:out value="${sessionScope.Auth.userName}"/>
                            </div>
                        </div>
                        <div class="section-actions">
                            <a href="<spring:url value="/" />">Home</a>
                            <a href="<spring:url value="/user/index" />">Profile</a>
                        </div>
                    </div>
                </jsp:attribute>
                <jsp:attribute name="notLogin">
                    <div class="user-section">
                        <i class="material-icons">&#xE851;</i>
                        <div class="section-actions">
                            <a href="<spring:url value="/user/login"/>">Login</a>
                            <a href="<spring:url value="/user/register"/>">Register</a>
                        </div>
                    </div>
                </jsp:attribute>
            </layout:login>
        </div>


    </div>
    <div class="result-content ${ResultList.size() > 0?"scroll":"" } ">
        <c:choose>
            <c:when test="${ResultList.size() > 0}">
                <c:forEach var="SearchItem" items="${ResultList}">
                    <div class="result-item">
                        <a class="center-block" href="<spring:url value="/book/detail/${SearchItem.id}"/> ">
                            <div class="name"><c:out value="${SearchItem.bookName}"/></div>
                            <div class="meta"><c:out value="${SearchItem.author}"/> &centerdot; <c:out
                                    value="${SearchItem.publisher}"/> &centerdot; ${SearchItem.yearOfPublish} </div>
                            <div class="detail"><c:out
                                    value="${SearchItem.description.length() > 80 ? SearchItem.description.substring(0, 80) : SearchItem.description} ..."/></div>
                            <div class="additional-info">
                                <div><i class="material-icons"></i><c:out value="${SearchItem.bookCopies.size()}"/>
                                    copies
                                </div>
                                <div><i class="material-icons"></i><c:out value="${SearchItem.bookCode}"/></div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="null-result-wrapper">
                    <div class="null-result">
                        Oops! No Result found ~

                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="index-footer-wrapper">

        <div class="index-footer">
            Lib-Mgmt, 2016 &copy; Thinker &amp; Performer | <a href="<spring:url value="/home/about" />">About</a>
        </div>
    </div>
</div>
<script src="<spring:url value="/script/lib/jquery.jInvertScroll.js" />"></script>
<script>
    $(function () {
        $.material.init();
        <c:if test="${ResultList.size() > 0}">

        var elem = $.jInvertScroll(['.result-content']);
        $(window).resize(function () {
            elem.reinitialize();
        });
        </c:if>
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
