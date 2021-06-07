<%@ page isErrorPage="true" pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Trustworthy cargo shipping</title>
</head>
<body>
<h2>this is an error page</h2><br/>
<h2>Oooops seems you don't have authority to access requested page</h2><br/><br/>

<a class="nav-link" href="${pageContext.request.contextPath}/">
    <fmt:message key="go.main.page" bundle="${bundle}"/></a>

</body>
</html>
