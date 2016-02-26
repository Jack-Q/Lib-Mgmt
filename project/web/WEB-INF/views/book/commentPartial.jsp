<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <c:choose>
        <c:when test="${sessionScope.Auth.authorized}">
            <div class="well well-lg">
                <form class="form-horizontal" action="<spring:url value="/book/comment/${CurrentBook.id}"/>"
                      method="post">
                    <div>
                        <label class="center-block">
                            <textarea name="content" placeholder="Say something?" class="form-control"></textarea>
                        </label>
                    </div>
                    <input class="btn btn-raised btn-primary" type="submit" value="Submit">
                    <input class="btn btn-raised btn-default" type="reset" value="Clear">
                    <span class="text-info">(You are logged in as <c:out value="${sessionScope.Auth.userName}"/>)</span>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <div class="well well-lg">
                Please login before leaving comment
            </div>
        </c:otherwise>
    </c:choose>

</div>