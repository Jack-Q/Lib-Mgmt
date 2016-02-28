<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="About">
    <div class="container">
        <h1>
            About Lib-Mgmt
        </h1>
        <h4>Introduction</h4>
        <div style="text-indent: 2em;padding: 20px;">
            <p>The Library provides people with a rich, diverse and enduring source of knowledge to inform, inspire and
                engage them and support their intellectual and creative endeavors.</p>
            <p>Welcome you to this vast online treasure trove of materials and services. Whether you wish to explore the
                collections online, seek academic information, register a copyright, access programs or plan a visit, we
                are glad you are here and invite you to return often.</p>
        </div>
        <h4>Programmer &amp; Designer</h4>
        <p>Thinker &amp; Performer</p>
    </div>
    <div class="index-section">
        <jsp:include page="/about"/>
    </div>
</layout:basic>