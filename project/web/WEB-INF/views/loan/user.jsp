<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<layout:basic pageTitle="${UserPageTitle}">
    <jsp:body>
        <div class="container page-lend">
            <h1>
                <c:out value="${UserPageTitle}"/>
            </h1>
            <a href="<spring:url value="/" />"> &#8810; Return to Index page </a>
                <%--@elvariable id="indexMessageId" type="java.lang.String"--%>
            <c:if test="${(indexMessageId != null)}">
                <div class="alert-info alert alert-dismissible">
                    <button type="button" class="close" data-dismiss="alert">×</button>
                    <p><spring:message code="${fn:escapeXml(indexMessageId)}"/></p>
                </div>
            </c:if>
            <div class="step step-user">
                <div class="step-title">
                    User
                </div>
                <div class="row select-user">
                    <div class="col-sm-offset-1 col-sm-10 col-md-offset-2 col-md-8">
                        <div class="form-group label-floating">

                            <label class="control-label" for="user-search">Search for user</label>

                            <div class="input-group">
                                <input type="text" id="user-search" class="form-control">
                              <span class="input-group-btn">
                                <button type="button" id="user-search-btn" class="btn btn-fab btn-fab-mini">
                                    <i class="material-icons">search</i>
                                </button>
                              </span>
                            </div>
                        </div>
                        <div class="row select-option">

                            <div class="col-sm-6 togglebutton">
                                <label>
                                    <input type="checkbox" id="user-search-id"> search by ID
                                </label>
                            </div>
                            <div class="col-sm-6 togglebutton">
                                <label>
                                    <input type="checkbox" id="user-search-name" checked> search by Name
                                </label>
                            </div>
                        </div>
                        <div class="select-candidate">
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <script>
            // Target URL: http://localhost:8080/user/search?q=123&byId=0&byName=1
            $(function () {
                var candidates = $(".select-candidate");
                var userSearch = $("#user-search");
                var updateCandidates = function (newCandidates) {
                    candidates.fadeOut(300, function () {
                        candidates.empty();
                        newCandidates.forEach(function (candidate) {
                            candidates.append("<span data-id='" + candidate.id + "'>" + candidate.name + "(" + candidate.username + ")</span>");
                        });
                        if (newCandidates.length == 0) {
                            candidates.append("<div class='well well-lg'>No matched user found ~</div>")
                        }
                    }).fadeIn(300);
                };
                // Ensure that at least one option is selected
                $("#user-search-name, #user-search-id").bind("change", function (e) {
                    var isByName = $("#user-search-name").prop("checked");
                    var isById = $("#user-search-id").prop("checked");
                    if (!isById && !isByName) {
                        if (e.target.id == "user-search-id") {
                            $("#user-search-name").prop("checked", true);
                        } else {

                            $("#user-search-id").prop("checked", true);
                        }
                    }
                });
                var retrieveCandidates = function () {
                    var query = userSearch.val();
                    var isByName = $("#user-search-name").prop("checked");
                    var isById = $("#user-search-id").prop("checked");
                    if (!isById && !isByName) {
                        $("#user-search-name").prop("checked", true);
                        isByName = true;
                    }
                    if (query.length == 0) {
                        userSearch.focus();
                        return;
                    }
                    $.get("<spring:url value="/user/search" />", {
                        q: query,
                        byId: isById,
                        byName: isByName
                    }, function (res) {
                        if (typeof res === 'string') {
                            try {
                                res = JSON.parse(res);
                            } catch (e) {
                                res = {success: false};
                            }
                        }

                        if (res.success && res.data) {
                            updateCandidates(res.data);
                        }

                    });
                };
                candidates.click(function (e) {
                    var id = $(e.target).data("id");
                    if (!parseInt(id)) {
                        return;
                    }
                    window.location = "<spring:url value="${NextPage}" />" + id;
                });
                $("#user-search-btn").click(retrieveCandidates);
                userSearch.bind("keydown", function (e) {
                    if (e.keyCode == 13 /* Key Code for Enter*/) {
                        retrieveCandidates();
                    }
                })

            });
        </script>
    </jsp:body>
</layout:basic>