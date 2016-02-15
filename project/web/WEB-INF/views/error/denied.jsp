<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="Authentication Denied">
    <div class="container">
        <h1>
            Authentication Denied
        </h1>
        <div>
            <p>
                Authentication failed, access denied.
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