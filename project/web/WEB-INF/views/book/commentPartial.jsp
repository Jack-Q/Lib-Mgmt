<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<div>

    <c:choose>
        <c:when test="${BookComments != null && BookComments.size() > 0}">

            <c:forEach var="Comment" items="${BookComments}">
                <div class="comment well-lg row">
                    <div class="col-xs-2 col-md-1">
                        <div>
                                ${Comment.stars}
                        </div>
                        <div class="badge">
                            STARS
                        </div>
                    </div>
                    <div class="col-xs-10 col-md-11">

                        <div>
                            <c:out value="${Comment.content}"/>
                        </div>
                        <div>
                            By <c:out value="${Comment.user.name}"/> @ <fmt:formatDate
                                value="${Comment.dateOfComment}"/>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="alert alert-inverse text-center">
                No Comments to this book ~
            </div>
        </c:otherwise>
    </c:choose>
    <layout:role>
        <jsp:attribute name="student">
            <c:choose>
                <c:when test="${true}">
                    Add new comment
                </c:when>

            </c:choose>
        </jsp:attribute>
    </layout:role>
</div>