<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<%--<%@include file="navbar.jsp"%>--%>
<%--<fmt:setBundle basename="localization" var="bundle"/>--%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1">
<%--    <script src="${pageContext.request.contextPath}/static/css/style.css"></script>--%>
    <link rel="stylesheet" href="/static/css/style.css">
<%--    <script src="${pageContext.request.contextPath}/static/css/style.css"></script>--%>
<%--    <link rel="stylesheet" href="<c:url value="/static/css/style.css"/> "/>--%>
<%--    <style><%@include file="/static/css/style.css"%></style>--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous"/>

    <title>Trustworthy cargo shipping</title>
    <%--    <fmt:setLocale value="${locale.getLanguage()}"/>--%>
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="localization" var="bundle"/>

</head>

<body>
<%@include file="jspf/navbar.jsp" %>
<img src="<c:url value="/static/img/auto.jpg"/>"/>
<%--<img src="${pageContext.request.contextPath}/static/img/auto.jpg"/>--%>

<div class="container mt-5">

    <c:if test="${sessionScope.authenticated == null}">
        <fmt:message key="company.greeting" bundle="${bundle}"/>, Guest!
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <fmt:message key="company.greeting" bundle="${bundle}"/>, ${sessionScope.user.getUsername()}!
</c:if>
</div>

<%@include file="jspf/footer.jsp"%>

</body>
</html>



