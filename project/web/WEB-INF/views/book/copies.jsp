<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<layout:basic pageTitle="Manage Copies">
    <jsp:body>
        <div class="container">
            <h1>
                Manage Copies
            </h1>

            <h3>
                <c:out value="${CurrentBook.bookName}"/>
            </h3>
            <div>
                <c:if test="${CurrentBook.author != null && CurrentBook.author.length() > 0 }">
                    By <span class="book-author"><c:out value="${CurrentBook.author}"/></span>,
                </c:if>
                <c:if test="${CurrentBook.publisher != null && CurrentBook.publisher.length() > 0 }">
                    <span class="book-publisher"><c:out value="${CurrentBook.publisher}"/></span>,
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

                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>

            <form class="row" action="<spring:url value="/book/addCopies" />" method="POST" target="_self">
                <input type="hidden" name="bookId" value="${CurrentBook.id}">
                <div class=" col-sm-2" style="margin-top: -18px;">
                    <input class="form-control" type="number" min="1" max="16" step="1" name="numOfNewCopies"
                           placeholder="1~16" required>
                </div>
                <div>
                    <input class="btn btn-raised btn-primary" type="submit" value="Add new copies">
                </div>
            </form>

            <jsp:include page="/book/copiesPartial/${CurrentBook.id}"/>

        </div>
    </jsp:body>
</layout:basic>