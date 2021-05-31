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
    <form action="${pageContext.request.contextPath}/admin/transportationConfirm" method="POST">
        <h4><fmt:message key="change.status" bundle="${bundle}"/></h4>
        <select name="status">
            <c:forEach items="${statuses}" var="status">
                <option value="${status.name()}">${status.name()}</option>
            </c:forEach>
        </select>
<%--        <!--    <input type="hidden" name="transportationId" th:value="${transportation.id}"/>-->--%>
        <button type="submit" class="btn btn-success"><fmt:message key="save" bundle="${bundle}"/></button>
    </form>
</div>

<%@include file="jspf/footer.jsp"%>
</body>
</html>