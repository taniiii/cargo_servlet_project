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

</div>
<!--transportation список - copy of List of orders (можно вынести в отдельный фрагмент)-->
<div class="container my-2" id="user-transportations-list">
    <h1><fmt:message key="orders.list" bundle="${bundle}"/></h1>

    <table border="1" class="table table-striped table-responsive-md">
        <thead>
        <tr>
            <th>
                <a href="${pageContext.request.contextPath}/user/orders?p=1&s=5&sortBy=id&sortDirection=${reverseSortDirection}">
                    <fmt:message key="receipt" bundle="${bundle}"/>
                </a>
            </th>
            <th><fmt:message key="order.address" bundle="${bundle}"/></th>
            <th><fmt:message key="order.comment" bundle="${bundle}"/></th>
            <th><fmt:message key="order.price" bundle="${bundle}"/></th>
            <th>
                <a href="${pageContext.request.contextPath}/user/orders?p=1&s=5&sortBy=delivery_at&sortDirection=${reverseSortDirection}">
                    <fmt:message key="order.delivery" bundle="${bundle}"/>
                </a>
            </th>
            <th>
                <a href="${pageContext.request.contextPath}/user/orders?p=1&s=5&sortBy=status_id&sortDirection=${reverseSortDirection}">
                    <fmt:message key="order.status" bundle="${bundle}"/>
                </a>
            </th>
            <th><fmt:message key="status.edit" bundle="${bundle}"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userOrders.items}" var="order">
        <tr>
            <td>${order.id}</td>
            <td>${order.getTariff().getAddress().name()}</td>
            <td>${order.getComment()}</td>
            <td>${order.getTariff().getPrice()} <fmt:message key="uah" bundle="${bundle}"/></td>
            <td>${order.getDeliveryDate()}</td>
            <td>${order.getOrderStatus().name()}</td>
            <div>
            <c:choose>
                <c:when test="${order.getOrderStatus().name()  == 'WAITING_FOR_PAYMENT'}">
                    <p><td >
                    <a href="${pageContext.request.contextPath}/user/transp?id=${order.getId()}" class="btn btn-dark">
                        <fmt:message key="pay.order" bundle="${bundle}"/></a></td></p>
                </c:when>
                <c:otherwise>
                    <p><td></td></p>
                </c:otherwise>
            </c:choose>
            </div>
        </tr>
        </c:forEach>
        </tbody>
    </table>

    <div>
        <div class="row col-sm-20">
            <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
                ${currentPage}
            </div>
            <div class="col-sm-1">
                <c:if test="${!userOrders.firstPage}">
                    <a href="${pageContext.request.contextPath}/user/orders?p=${userOrders.pageNo-1}&s=${userOrders.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <fmt:message key="previous" bundle="${bundle}"/>
                    </a>
                </c:if>
            </div>

            <div class="col-sm-1">
                <c:if test="${!userOrders.lastPage}">
                    <a href="${pageContext.request.contextPath}/user/orders?p=${userOrders.pageNo+1}&s=${userOrders.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <span><fmt:message key="next" bundle="${bundle}"/></span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>



</div>
<div class="container mt-5">
    <a class="btn btn-info btn" href="${pageContext.request.contextPath}/user/showNewTransportationForm">
        <fmt:message key="company.new.order" bundle="${bundle}"/></a>
</div>

<%@include file="jspf/footer.jsp"%>
</body>
</html>