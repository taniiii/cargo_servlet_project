<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<html>
<%@include file="jspf/header.jsp" %>

<body style="'background: url(/static/images/ship5.jpg) no-repeat center center fixed;'">
<%@include file="jspf/navbar.jsp" %>

<div>
    <c:if test="${requestScope.msgUpdate != null}">
        <div class="alert alert-warning"><fmt:message key="bad.update" bundle="${bundle}"/></div>
    </c:if>
</div>

<div class="container">
    <h2><fmt:message key="user.edit" bundle="${bundle}"/></h2>

    <form action="${pageContext.request.contextPath}/admin/user/edit" method="POST">
        <div> <!-- из раздела Layout - Horizontal form -->
            <label class="col-sm-2 col-form-label"><fmt:message key="change.name" bundle="${bundle}"/></label>
            <input type="text" name="updateName" value="${updatedUser.getUsername()}"/>
        </div>
        <label class="col-sm-2 col-form-label"><fmt:message key="change.role" bundle="${bundle}"/></label>
        <c:forEach items="${roles}" var="role">
          <tr>
              <div>
                    <label><input type="radio" name="updateRole" value="${role.name()}"/>${role.name()}</label>
              </div>
          </tr>
        </c:forEach>

        <button type="submit" class="btn btn-success"><fmt:message key="save" bundle="${bundle}"/></button>
    </form>
    <hr>

</div>


<%@include file="jspf/footer.jsp"%>
</body>
</html>