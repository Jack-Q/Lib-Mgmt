<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<layout:basic pageTitle="Update Book Information">
    <jsp:body>
        <div class="container">
            <h1>
                Update Book Information
            </h1>
            <a href="<spring:url value="/book/manage" />"> &#8810; Return to Manage page </a>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <form:form action="${ pageContext.request.contextPath }/book/edit/${CurrentBook.id}"
                               modelAttribute="BookEdit" method="post"
                               cssClass="form-horizontal">
                        <%-- This form use standard HTML5 tags to utilize the new features in HTML 5 --%>
                        <%-- As a result, I need to manually bind data to related input element --%>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-bookName">Book Name</label>
                            <div class="col-xs-9">
                                <input type="text" id="form-bookName" name="bookName"
                                       value="<c:out value="${BookEdit.bookName}"/>" required class="form-control"
                                       title="Book Name" placeholder=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-author">Author</label>
                            <div class="col-xs-9">
                                <input type="text" id="form-author" name="author"
                                       value="<c:out value="${BookEdit.author}"/>" required class="form-control"
                                       title="Author" placeholder=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-bookCode">Book Code</label>
                            <div class="col-xs-9">
                                <input type="text" value="<c:out value="${BookEdit.bookCode}"/>"
                                       placeholder="TP312JA.1698" id="form-bookCode"
                                       name="bookCode" required class="form-control" title="Phone Number"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-isbn">ISBN</label>
                            <div class="col-xs-9">
                                <input type="text" pattern="\d{10}(\d{3})?"
                                       value="<c:out value="${BookEdit.isbn}"/>"
                                       placeholder="9787302384496/7302384496" id="form-isbn"
                                       name="isbn" required class="form-control" title="ISBN"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-publisher">Publisher</label>
                            <div class="col-xs-9">
                                <input type="text" id="form-publisher" name="publisher"
                                       value="<c:out value="${BookEdit.publisher}"/>" required class="form-control"
                                       title="Publisher" placeholder="Higher Education Press"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-yearOfPublish">Year of Publish</label>
                            <div class="col-xs-9">
                                <input type="number" min="1960" step="1" max="2020"
                                       value="<c:out value="${BookEdit.yearOfPublish}"/>"
                                       placeholder="2016" id="form-yearOfPublish"
                                       name="yearOfPublish" required class="form-control" title="Year of Publish"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-bookNote">Book Note</label>
                            <div class="col-xs-9">
                                <input type="text" id="form-bookNote" name="bookNote"
                                       value="<c:out value="${BookEdit.bookNote}"/>" class="form-control"
                                       title="Book Note" placeholder=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-xs-3" for="form-description">Description</label>
                            <div class="col-xs-9">
                                <input type="text" id="form-description" name="description"
                                       value="<c:out value="${BookEdit.description}"/>" class="form-control"
                                       title="Description" placeholder="some brief introduction of the book"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Update Book Information" type="submit"/>
                            <input class="btn btn-raised" value="Reset" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>