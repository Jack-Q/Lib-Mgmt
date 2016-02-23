<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag description="represent a block which is defferent login user with different role" %>
<%@ attribute name="admin" fragment="true" %>
<%@ attribute name="librarian" fragment="true" %>
<%@ attribute name="student" fragment="true" %>
<%@ attribute name="guest" fragment="true" %>
<%@ attribute name="otherwise" fragment="true" %>

<c:choose>
    <c:when test="${admin!=null && sessionScope['Auth'].authorized && sessionScope['Auth'].admin}">
        <jsp:invoke fragment="admin"/>
    </c:when>
    <c:when test="${librarian!=null && sessionScope['Auth'].authorized && sessionScope['Auth'].librarian}">
        <jsp:invoke fragment="librarian"/>
    </c:when>
    <c:when test="${student!=null && sessionScope['Auth'].authorized && sessionScope['Auth'].student}">
        <jsp:invoke fragment="student"/>
    </c:when>
    <c:when test="${guest!=null && sessionScope['Auth'].authorized && sessionScope['Auth'].guest}">
        <jsp:invoke fragment="guest"/>
    </c:when>
    <c:otherwise>
        <jsp:invoke fragment="otherwise"/>
    </c:otherwise>
</c:choose>
