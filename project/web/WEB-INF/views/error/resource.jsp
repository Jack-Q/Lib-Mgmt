<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="Resource Access Denied">
    <div class="container">
        <h1>
            Resource Access Denied
        </h1>
        <div>
            <p>
                Static Resource Access Limited in same origin.
            </p>
            <p>

                Return to
                <a href="<spring:url value="/"/>" class="btn btn-raised btn-primary" title="Home page">
                    home page
                </a>
            </p>
        </div>
    </div>
    <div class="index-section">

    </div>
</layout:basic>