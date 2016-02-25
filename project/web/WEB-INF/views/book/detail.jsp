<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<layout:basic pageTitle="Detail: ${CurrentBook.bookName}">
    <jsp:body>
        <div class="container">
            <h1>
                <c:out value="${CurrentBook.bookName}"/>
            </h1>
            <div>
                <c:if test="${CurrentBook.author != null && CurrentBook.author.length() > 0 }">
                    By <span class="book-author"><c:out value="${CurrentBook.author}"/></span>
                </c:if>
                <c:if test="${CurrentBook.publisher != null && CurrentBook.publisher.length() > 0 }">
                    <span class="book-publisher"><c:out value="${CurrentBook.publisher}"/></span>
                </c:if>
                <c:if test="${CurrentBook.yearOfPublish > 1900 }">
                    <span class="book-year-of-publish"><c:out value="${CurrentBook.yearOfPublish}"/></span>
                </c:if>
            </div>
            <div>
                <h4>Meta Data</h4>
                <dl class="dl-horizontal">
                    <dt>Book Code</dt>
                    <dd><c:out value="${CurrentBook.bookCode}"/></dd>
                    <dt>ISBN</dt>
                    <dd><c:out value="${CurrentBook.isbn}"/></dd>
                    <dt>Book Note</dt>
                    <dd><c:out value="${CurrentBook.bookNote}"/></dd>
                </dl>
            </div>
            <div class="book-copies">
                <h4>Book Copies</h4>
                <jsp:include page="/book/copiesPartial/${CurrentBook.id}"/>
            </div>
            <div class="book-detail">
                <div class="book-description">
                    <c:out value="${CurrentBook.description}"/>
                </div>
            </div>
            <div class="book-comments">
                <h4>Comments</h4>
                <jsp:include page="/book/commentPartial/${CurrentBook.id}"/>
            </div>
        </div>
    </jsp:body>
</layout:basic>