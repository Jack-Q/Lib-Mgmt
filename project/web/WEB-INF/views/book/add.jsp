<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="Add book">
    <jsp:body>

        <div class="container">
            <h1>
                Add Book
            </h1>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                        <%--@elvariable id="errorMessageId" type="java.lang.String"--%>
                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <form:form action="${ pageContext.request.contextPath }/book/add" modelAttribute="BookAdd"
                               method="post"
                               cssClass="form-horizontal">
                        <%-- This form use standard HTML5 tags to utilize the new features in HTML 5 --%>
                        <%-- As a result, I need to manually bind data to related input element --%>
                        <fieldset>
                            <legend>Book Metadata</legend>

                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-book-name">Book Name</label>
                                <div class="col-xs-9">
                                    <input type="text" name="bookName" id="form-book-name"
                                           placeholder="Java Web Application"
                                           value="<c:out value="${BookAdd.bookName}"/>"
                                           required class="form-control" title="Book Name"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-book-code">Book Code</label>
                                <div class="col-xs-9">
                                    <input type="text" name="bookCode" id="form-book-code"
                                           placeholder="TP312JA0001"
                                           value="<c:out value="${BookAdd.bookCode}"/>"
                                           required class="form-control" title="Book Code"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-isbn">ISBN</label>
                                <div class="col-xs-9">
                                    <input type="text" name="isbn" id="form-isbn"
                                           placeholder="9787000000000 / 7115000000"
                                           pattern="\d{10}(\d{3})?"
                                           value="<c:out value="${BookAdd.isbn}"/>"
                                           required class="form-control" title="ISBN"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-note">Book Note</label>
                                <div class="col-xs-9">
                                    <input type="text"
                                           value="<c:out value="${BookAdd.bookNote}"/>"
                                           placeholder="" id="form-note"
                                           name="bookNote" class="form-control" title="Book Note"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Detailed Description</legend>

                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-author">Author</label>
                                <div class="col-xs-9">
                                    <input type="text" id="form-author" name="author"
                                           value="<c:out value="${BookAdd.author}"/>" class="form-control"
                                           title="Author" placeholder="Peter"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-publisher">Publisher</label>
                                <div class="col-xs-9">
                                    <input type="text"
                                           value="<c:out value="${BookAdd.publisher}"/>"
                                           placeholder="Xi'an Jiaotong University Press" id="form-publisher"
                                           name="publisher" class="form-control" title="Publisher"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-year-of-publish">Year of Publish</label>
                                <div class="col-xs-9">
                                    <input type="number"
                                           min="1960"
                                           step="1"
                                           max="2020"
                                           value="<c:out value="${BookAdd.yearOfPublish != 0?BookAdd.yearOfPublish :2016}" default="2016"/>"
                                           placeholder="2016" id="form-year-of-publish"
                                           name="yearOfPublish" class="form-control" title="Year of Publish"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-description">Description</label>
                                <div class="col-xs-9">
                                    <textarea
                                            placeholder="" id="form-description"
                                            name="description" class="form-control" title="Description"><c:out
                                            value="${BookAdd.description}"/></textarea>
                                </div>
                            </div>

                        </fieldset>
                        <fieldset>

                            <legend>Book Copies</legend>

                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-count">Book Copies</label>
                                <div class="col-xs-9">
                                    <input type="number" id="form-count" name="count" required
                                           min="0" max="100" step="1"
                                           value="<c:out value="${BookAdd.count}"/>" class="form-control"
                                           title="count" placeholder="1"/>
                                </div>
                            </div>
                        </fieldset>
                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Add" type="submit"/>
                            <input class="btn btn-raised" value="Reset" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>