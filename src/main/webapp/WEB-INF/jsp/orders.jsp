<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<body style="'background: url(/static/images/ship5.jpg) no-repeat center center fixed;'">
<%@include file="../jspf/navbar.jsp" %>

<div class="row mx-md-n5">
    <div class="col px-md-5 mt-3 ml-5">
        <form method="get" action="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=id&sortDirection=asc">
            <h4><fmt:message key="search.destination" bundle="${bundle}"/></h4>
            <select name="destinationFilter">
                <c:forEach items="${addresses}" var="address">
                    <option value="${address.name()}">${address.name()}</option>
                </c:forEach>
                <option value=""></option>
            </select>
            <input type="hidden" name="p" value="1"/>
            <input type="hidden" name="s" value="5"/>
            <input type="hidden" name="sortBy" value="id"/>
            <input type="hidden" name="sortDirection" value="asc">
            <button type="submit" class="btn btn-secondary col-3"><fmt:message key="search"
                                                                               bundle="${bundle}"/></button>
        </form>
    </div>


    <div class="col px-md-5 mt-3 ml-5">
        <form method="get" action="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=id&sortDirection=asc">
            <input type="text" name="order_date"/>
            <small id="searchDateHelp" class="form-text text-muted"><fmt:message key="search.date.help"
                                                                                 bundle="${bundle}"/></small>

            <input type="hidden" name="p" value="1"/>
            <input type="hidden" name="s" value="5"/>
            <input type="hidden" name="sortBy" value="id"/>
            <input type="hidden" name="sortDirection" value="asc">
            <button type="submit" class="btn btn-secondary col-3"><fmt:message key="search"
                                                                               bundle="${bundle}"/></button>
        </form>
    </div>
</div>

<div class="row mx-md-n5">

    <div class="container my-2" id="transportations-list">
        <h1><fmt:message key="orders.list" bundle="${bundle}"/></h1>
        <table class="table table-hover table-striped table-responsive-md">
            <thead>
            <tr>
                <th scope="col">
                    <a href="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=id&sortDirection=${reverseSortDirection}">
                        <fmt:message key="order.id" bundle="${bundle}"/>
                    </a>
                </th>
                <th><fmt:message key="order.address" bundle="${bundle}"/></th>
                <th scope="col">
                    <a href="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=created_at&sortDirection=${reverseSortDirection}">
                        <fmt:message key="order.creation" bundle="${bundle}"/>
                    </a>
                </th>
                <th><fmt:message key="order.price" bundle="${bundle}"/></th>
                <th><fmt:message key="order.customer" bundle="${bundle}"/></th>
                <th><fmt:message key="order.comment" bundle="${bundle}"/></th>
                <th>
                    <a href="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=status_id&sortDirection=${reverseSortDirection}">
                        <fmt:message key="order.status" bundle="${bundle}"/>
                    </a>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderList.items}" var="order">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.getTariff().getAddress().name()}</td>
                <td>${order.getCreationDate()}</td>
                <td>${order.getTariff().getPrice()} <fmt:message key="uah" bundle="${bundle}"/></td>
                <td>${order.getComment()}</td>
                <td>${order.getCustomerName()}</td>
                <td>${order.getOrderStatus().name()}</td>
                <td><a href="${pageContext.request.contextPath}/admin/transportation?id=${order.id}" class="btn btn-warning">
                    <fmt:message key="status.edit" bundle="${bundle}"/></a></td>
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
                <c:if test="${!orderList.firstPage}">
                    <a href="${pageContext.request.contextPath}/admin/orders?p=${orderList.pageNo-1}&s=${orderList.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <fmt:message key="previous" bundle="${bundle}"/>
                    </a>
                </c:if>
            </div>

            <div class="col-sm-1">
                <c:if test="${!orderList.lastPage}">
                    <a href="${pageContext.request.contextPath}/admin/orders?p=${orderList.pageNo+1}&s=${orderList.pageSize}&sortBy=${sortBy}&sortDirection=${sortDirection}">
                        <span><fmt:message key="next" bundle="${bundle}"/></span>
                    </a>
                </c:if>
            </div>
        </div>
    </div>


</div>

    <%@include file="../jspf/footer.jsp" %>
</body>
</html>
