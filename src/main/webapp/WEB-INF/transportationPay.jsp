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

    <form action="${pageContext.request.contextPath}/user/paymentComplete" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">
                <fmt:message key="option.payment" bundle="${bundle}"/></label>
            <div>
                <label><input type="checkbox" name="paymentOption"/>
                    <fmt:message key="payment.credit.card" bundle="${bundle}"/></label><br/>
                <label><input type="checkbox" name="paymentOption"/>PayPal</label><br/>
            </div>

            <button type="submit" class="btn btn-success">
                <fmt:message key="payment.complete" bundle="${bundle}"/></button>
        </div>
    </form>

</div>

<%@include file="jspf/footer.jsp"%>
</body>
</html>