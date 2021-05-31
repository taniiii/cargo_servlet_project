<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<html>
<%@include file="jspf/header.jsp"%>

<body>
<%@include file="jspf/navbar.jsp"%>

<%--Добавить проверку на наличие сообщений об ошибках!!!--%>

<div class="container mt-5">
    <div class="mb-1"><fmt:message key="add.user" bundle="${bundle}"/></div>

    <div>
        <c:if test="${requestScope.msg != null}">
        <div class="alert alert-warning">${requestScope.msg}</div>
        </c:if>
    </div>
    <div>
        <c:if test="${requestScope.msgSuccess != null}">
            <div class="alert alert-success">${requestScope.msgSuccess}</div>
        </c:if>
    </div>

    <form action="${pageContext.request.contextPath}/registration" method="post">
        <div class="form-group row"> <!-- из раздела Layout - Horizontal form -->
            <label class="col-sm-2 col-form-label"><fmt:message key="user.name" bundle="${bundle}"/></label><!-- размеры поля лейбла -->
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="password" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label"><fmt:message key="email" bundle="${bundle}"/></label>
            <div class="col-sm-6">
                <input type="text" name="email" class="form-control" placeholder="some@some.com"/>
            </div>
        </div>
        <button type="submit" class="btn btn-secondary"><fmt:message key="register" bundle="${bundle}"/></button>
    </form>
</div>


<%@include file="jspf/footer.jsp"%>
</body>
</html>





