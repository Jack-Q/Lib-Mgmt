<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<c:set var="CurrentUser" value="${applicationScope}" scope="page" />

<layout:basic pageTitle="User Profile">
    <jsp:body>
        <div class="container">
            <h1>
                User Profile
            </h1>
            <h2>
                Manage Your Account Data
            </h2>
            <div class="row">
                <div class="col-sm-6 col-lg-4">
                    <div>
                        ${User.name}
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</layout:basic>