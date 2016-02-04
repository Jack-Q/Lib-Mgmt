<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Library Management</title>
    <link rel="stylesheet" href="<spring:url value="/style/index.css"/>">
</head>
<body>
<h1>
    Add New User
</h1>
<div>
    <p>
        Fill in user info
    </p>
    <form:form target="_self" method="post" commandName="User" action="/add" >
        <label for="form-name">Name</label>
        <form:input path="name" id="form-name" />
        <br />
        <label for="form-pass">Value</label>
        <form:input path="passwordHash" id="form-pass" />

        <br />
        <input type="submit" value="Submit" />
        <input type="reset" value="Reset">
    </form:form>
</div>
</body>
</html>
