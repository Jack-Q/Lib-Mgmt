<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="comment-block">

    <c:choose>
        <c:when test="${BookComments != null && BookComments.size() > 0}">

            <c:forEach var="Comment" items="${BookComments}">
                <div class="comment well-lg row comment-item">
                    <div class="col-xs-2 col-md-1 comment-rate">
                        <div class="comment-star">
                            <i class="material-icons">&#xE885;</i>
                             <span class="rate-value" data-value="${Comment.stars}" data-id="${Comment.id}">
                                     ${Comment.stars}
                             </span>
                        </div>
                        <layout:login>
                            <jsp:attribute name="whenLogin">
                            <div class="comment-star-rater" data-id="${Comment.id}">
                                <a class="star-down" href="#" data-action="down" data-id="${Comment.id}">-1</a>
                                <a class="star-up" href="#" data-action="up" data-id="${Comment.id}">+1</a>
                            </div>
                            </jsp:attribute>
                        </layout:login>
                    </div>
                    <div class="col-xs-10 col-md-11 comment-main">

                        <div class="comment-content">
                            <c:out value="${Comment.content}"/>
                        </div>
                        <div class="comment-meta">
                            By <c:out value="${Comment.user.name}"/> @ <fmt:formatDate
                                value="${Comment.dateOfComment}" pattern="yyyy-MM-dd hh:mm:ss"/>
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
    <layout:login>
        <jsp:attribute name="whenLogin">
            <div class="well well-lg comment-edit-area">
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
        </jsp:attribute>
        <jsp:attribute name="notLogin">
            <div class="well well-lg comment-edit-area center-block">
                Please login before leaving comment
            </div>
        </jsp:attribute>
    </layout:login>
    <layout:login>
        <jsp:attribute name="whenLogin">

        <script>
            $(function () {
                $(".comment-star-rater a").click(function (e) {
                    e.preventDefault();
                    var id = this.dataset.id;
                    var action = this.dataset.action;
                    if (action != "down" && action != "up") {
                        return;
                    }
                    $.post("<spring:url value="/book/commentRateAjax" />", {
                        id: id,
                        action: action
                    }, function (res) {
                        if (res.success) {
                            var data = $(".rate-value[data-id=" + id + "]");
                            var curVal = parseInt(data.data("value"));
                            var newVal = curVal + (action == "up" ? 1 : -1);
                            data.text(newVal).data("value", newVal);
                            $(".comment-star-rater[data-id=" + id + "]").empty().text("Thanks");
                        }
                    })

                })
            });
        </script>
        </jsp:attribute>
    </layout:login>
</div>