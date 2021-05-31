<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<html>
<%@include file="jspf/header.jsp"%>

<body style="'background: url(/static/images/ship5.jpg) no-repeat center center fixed;'">
<%@include file="jspf/navbar.jsp"%>

<div class="container mt-5">
    <!--        форма редактирования профиля пользователя-->
    <h3><fmt:message key="edit.details" bundle="${bundle}"/></h3><br/>

    <form action="${pageContext.request.contextPath}/user/profile" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="new.password" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="New password or confirm old one here"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="new.email" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="${sessionScope.user.getEmail()}"/>
            </div>
        </div>
        <button type="submit" class="btn btn-success"><fmt:message key="save" bundle="${bundle}"/></button>
    </form>
</div>

<%@include file="jspf/footer.jsp"%>
</body>
</html>
