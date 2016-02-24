<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="Loan Status">
    <jsp:body>
        <div class="container page-lend">
            <h1>
                My Loan Status
            </h1>
            <a href="<spring:url value="/" />"> &#8810; Return to Index page </a>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>

            <div>

                <!-- Nav tabs -->
                <ul class="nav nav-pills">
                    <li role="presentation" class="active"><a href="#reading" aria-controls="home" role="tab"
                                                              data-toggle="tab">I'm Reading</a></li>
                    <li role="presentation"><a href="#read" aria-controls="profile" role="tab" data-toggle="tab">I've
                        Read</a>
                    </li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div class="tab-pane active fade in" id="reading">
                        List to show books I'm reading
                    </div>
                    <div class="tab-pane fade" id="read">
                        List to show the books I've read
                    </div>
                </div>

            </div>

        </div>
    </jsp:body>
</layout:basic>