<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<layout:basic pageTitle="User Register">
    <jsp:body>

        <div class="container">
            <h1>
                User Register
            </h1>
            <p>
                Welcome to join us!

            </p>
            <div class="row">
                <div class="col-xs-12 col-sm-8 col-md-6">

                    <c:if test="${(errorMessageId != null)}">
                        <div class="alert alert-dismissible alert-danger">
                            <button type="button" class="close" data-dismiss="alert">×</button>
                            <h4>Error!</h4>
                            <p><spring:message code="${fn:escapeXml(errorMessageId)}"/></p>
                        </div>
                    </c:if>

                    <form:form action="${ pageContext.request.contextPath }/user/register?returnTo=${returnTo}"
                               modelAttribute="userRegister" method="post"
                               cssClass="form-horizontal">
                        <%-- This form use standard HTML5 tags to utilize the new features in HTML 5 --%>
                        <%-- As a result, I need to manually bind data to related input element --%>
                        <fieldset>
                            <legend>Login Information</legend>

                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-username">User Name</label>
                                <div class="col-xs-9">
                                    <input type="text" name="userName" id="form-username"
                                           placeholder="jack_0001 (Case sensitive)"
                                           value="<c:out value="${UserRegister.userName}"/>"
                                           required class="form-control" title="User name"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-password">Password</label>
                                <div class="col-xs-9">
                                    <input type="password" name="password"
                                           value="<c:out value="${UserRegister.password}"/>"
                                           onchange="$('#form-password-repeat').attr('pattern', this.value.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, '\\\$&'))"
                                           placeholder="%$^&^%%^$@@%!@%$#$#^&" id="form-password" required
                                           class="form-control" title="Password"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-password-repeat">Password Repeat</label>
                                <div class="col-xs-9">
                                    <input type="password" id="form-password-repeat"
                                           placeholder="%$^&^%%^$@@%!@%$#$#^&"
                                           value="<c:out value="${UserRegister.password}"/>"
                                           required class="form-control" title="Repeat your password again"/>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Basic Information</legend>

                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-name">Name</label>
                                <div class="col-xs-9">
                                    <input type="text" id="form-name" name="name"
                                           value="<c:out value="${UserRegister.name}"/>" required class="form-control"
                                           title="Name" placeholder="Jack"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-name">Phone Number</label>
                                <div class="col-xs-9">
                                    <input type="text" pattern="(\d{4})?\d{7}|\d{3}?\d{8}"
                                           value="<c:out value="${UserRegister.phoneNumber}"/>"
                                           placeholder="82660000 / 02982660000 / 13800000000" id="form-phone-number"
                                           name="phoneNumber" class="form-control" title="Phone Number"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-name">Email</label>
                                <div class="col-xs-9">
                                    <input type="email" id="form-email" name="email" class="form-control"
                                           value="<c:out value="${UserRegister.email}"/>"
                                           placeholder="someone@example.com" title="Email"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-xs-3" for="form-name">Date of Birth</label>
                                <div class="col-xs-9">
                                    <input type="date" id="form-date-of-birth"
                                           name="dateOfBirth" class="form-control"
                                           value="<c:out value="${UserRegister.dateOfBirth}"/>"
                                           title="Date of birth"/>
                                </div>
                            </div>

                        </fieldset>
                        <div class="form-group">
                            <input class="btn btn-raised btn-primary" value="Register" type="submit"/>
                            <input class="btn btn-raised" value="Reset" type="reset"/>
                        </div>
                    </form:form>
                </div>
            </div>

        </div>
    </jsp:body>
</layout:basic>