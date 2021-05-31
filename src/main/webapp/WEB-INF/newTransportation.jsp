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

  <h2><fmt:message key="company.new.order" bundle="${bundle}"/></h2>

  <form action="${pageContext.request.contextPath}/user/saveTransportation" method="post">
    <div class="form-group row"> <!-- из раздела Layout - Horizontal form -->
      <label class="col-sm-2 col-form-label"><fmt:message key="choose.destination" bundle="${bundle}"/></label>
        <select name="address">
        <c:forEach items="${addresses}" var="address">
        <option value="${address.name()}">${address.name()}</option>
        </c:forEach>
      </select>
    </div>

    <div class="form-group row"> <!-- из раздела Layout - Horizontal form -->
      <label class="col-sm-2 col-form-label"><fmt:message key="choose.size" bundle="${bundle}"/></label>
        <select name="size">
          <c:forEach items="${sizes}" var="size">
            <option value="${size.name()}">${size.getSize()}</option>
          </c:forEach>
        </select>
    </div>
    <div class="form-group row"> <!-- из раздела Layout - Horizontal form -->
      <label class="col-sm-2 col-form-label"><fmt:message key="choose.weight" bundle="${bundle}"/></label>
        <select  name="weight">
          <c:forEach items="${weights}" var="weight">
            <option value="${weight.name()}">${weight.getWeight()}</option>
          </c:forEach>
        </select>
    </div>
    <div class="form-group">
      <input type="text" class="form-control" placeholder="<fmt:message key="order.comment" bundle="${bundle}"/>" name="comment"/>
    </div>

    <button type="submit" class="btn btn-success"><fmt:message key="save.new.order" bundle="${bundle}"/></button>
  </form>
</div>


<%@include file="jspf/footer.jsp"%>
</body>
</html>
