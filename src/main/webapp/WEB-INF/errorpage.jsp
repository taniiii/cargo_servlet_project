<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
<h2>
    <fmt:message key="error.page" bundle="${bundle}"/><br/>
    <c:if test="${sessionScope.errorMessage != null}">
        <span>${sessionScope.errorMessage}</span>
    </c:if>

</h2>
</body>
</html>