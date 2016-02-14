<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="represent a block which is defferent from logined user to guest user" %>
<%@ attribute name="whenLogin" required="false" fragment="true"
              description="HTMl Content which will be represented when some user has logined" %>
<%@ attribute name="notLogin" required="false" fragment="true"
              description="HTML Content which will be represented when no user has logined" %>


<c:choose>
    <c:when test="${sessionScope['Auth'].authorized}">
        <jsp:invoke fragment="whenLogin"/>
    </c:when>
    <c:otherwise>
        <jsp:invoke fragment="notLogin"/>
    </c:otherwise>
</c:choose>
