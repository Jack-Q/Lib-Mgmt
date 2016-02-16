<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<layout:basic pageTitle="Book">
    <jsp:body>
        <div class="container">
            <h1>
                Book Version
            </h1>
            <h2>
                Manage User Accounts
            </h2>
            <div class="row">
                <div class="col-sm-6 col-lg-4">
                    <div>
                        <c:out value="${bookCode}"/>

                    </div>
                    <div>
                        <c:out value="${versionCode}"/>
                    </div>
                </div>
            </div>


            <jsp:include page="/book/comment/${bookCode}/${versionCode}"/>
        </div>
    </jsp:body>
</layout:basic>