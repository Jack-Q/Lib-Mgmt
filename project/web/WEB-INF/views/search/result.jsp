<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Book Search Result Page!
<br>
Searching for <c:out value="${QueryString}"/>
<c:forEach items="${ResultList}" var="item">
    <div>${item.bookName} / ${item.bookCode} / ${item.bookNote}</div>
</c:forEach>