<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="${UserPageTitle}">
    <jsp:body>
        <div class="container page-lend">
            <h1>
                <c:out value="${UserPageTitle}"/>
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
                <div class="row select-user">
                    <div class="col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8">
                        <div class="form-group label-floating">

                            <label class="control-label" for="user-search">Search for user</label>

                            <div class="input-group">
                                <input type="text" id="user-search" class="form-control">
                              <span class="input-group-btn">
                                <button type="button" class="btn btn-fab btn-fab-mini">
                                    <i class="material-icons">search</i>
                                </button>
                              </span>
                            </div>
                        </div>
                        <div class="row select-option">

                            <div class="col-sm-6 togglebutton">
                                <label>
                                    <input type="checkbox" id="user-search-id"> search by ID
                                </label>
                            </div>
                            <div class="col-sm-6 togglebutton">
                                <label>
                                    <input type="checkbox" id="user-search-name" checked> search by Name
                                </label>
                            </div>
                        </div>
                        <div class="select-candidate">
                            <span data-id="1">Admin (12)</span>
                            <span data-id="2">Label (3)</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>