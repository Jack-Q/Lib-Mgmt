<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div>
    <c:choose>
        <c:when test="${BookCopies != null && BookCopies.size() > 0}">

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Status</th>
                        <th>Login Date</th>
                        <th>Note</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="BookCopy" items="${BookCopies}">
                        <tr>
                            <td>${BookCopy.id}</td>
                            <td><c:out value="${BookCopy.status}"/></td>
                            <td><fmt:formatDate value="${BookCopy.dateOfRecord}" pattern="yyyy-MM-dd"/></td>
                            <td><c:out value="${BookCopy.note}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="alert alert-inverse text-center">
                Sorry, no copies of this book available currently.
            </div>
        </c:otherwise>
    </c:choose>
</div>