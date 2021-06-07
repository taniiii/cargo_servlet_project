<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page isELIgnored="false" %>

<html>
<%@include file="../jspf/header.jsp" %>

<body style="'background: url(/static/images/ship5.jpg) no-repeat center center fixed;'">
<%@include file="../jspf/navbar.jsp" %>

<!--  задний фон-->

<div class="container mt-5"> <!--  отступы для заголовка из раздела utilities-spacing-->
    <!--  container из раздела layout-grid-->
    <div class="container my-2">
        <h1><fmt:message key="tariffs" bundle="${bundle}"/></h1>
        <table class="table table-bordered table-dark">
            <thead>
            <tr>
                <th scope="col">
                    <a href="${pageContext.request.contextPath}/tariffs/?p=1&s=5&sortDirection=${reverseSortDirection}">
                        <fmt:message key="direction" bundle="${bundle}"/>
                    </a>
<%--                    <a href="${pageContext.request.contextPath}/tariffs/' + ${currentPage} + '?sortField=address&sortDirection=' + ${reverseSortDirection}">--%>
<%--                        <fmt:message key="direction" bundle="${bundle}"/>--%>
<%--                    </a>--%>
                </th>
                <th scope="col"><fmt:message key="package.size" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="package.weight" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="package.tariff" bundle="${bundle}"/></th>
            </tr>
            </thead>
            <tbody>
<%--            <c:forEach items="${categories}" var="category">--%>
<%--                <a href="${pageContext.request.contextPath}/category?catId=${category.id}&p=1&s=6"--%>
<%--                   class="list-group-item">${category.name}</a>--%>
<%--            </c:forEach>--%>
            <c:forEach items="${tariffs.items}" var="tariff">
                <tr>

<%--                    <a href="${pageContext.request.contextPath}/category?catId=${category.id}&p=1&s=6"--%>
<%--                       class="list-group-item">${category.name}</a>--%>
                <td>${tariff.getAddress().name()}</td>
                <td>${tariff.getSize().getSize()}</td>
                <td>${tariff.getWeight().getWeight()}</td>
                <td>${tariff.getPrice()}</td>
            </tr>
            </c:forEach>
            </tbody>
        </table>

<%--        <div class="row">--%>
<%--            <ul class="pager">--%>
<%--                <c:if test="${!tariffs.firstPage}">--%>
<%--                    <li>--%>
<%--                        <a href="${pageContext.request.contextPath}/tariffs?p=${tariffs.pageNo-1}&s=${tariffs.pageSize}">--%>
<%--                            <span aria-hidden="true">&larr;</span>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </c:if>--%>

<%--                <c:if test="${!tariffs.lastPage}">--%>
<%--                    <li>--%>
<%--                        <a href="${pageContext.request.contextPath}/tariffs?p=${tariffs.pageNo+1}&s=${tariffs.pageSize}">--%>
<%--                            <span aria-hidden="true">&rarr;</span>--%>
<%--                        </a>--%>
<%--                    </li>--%>
<%--                </c:if>--%>
<%--            </ul>--%>
<%--        </div>--%>


        <div>
<%--            <c:if test="${totalPages > 1}">--%>
            <div class="row col-sm-20">
                <div class="col-sm-2"><fmt:message key="current.page" bundle="${bundle}"/>
<%--                    ${tariffs.currentSize}--%>
                    ${currentPage}
                </div>
                <div class="col-sm-2">
<%--            <span>${currentPage}--%>
<%--                <c:forEach begin="1" end="${totalPages}" var="i">--%>
<%--                <c:if test="${currentPage != 1}">--%>
<%--                    <a href="${pageContext.request.contextPath}/tariffs?p=${i}&s=${tariffs.pageSize}">[[${i}]]</a>--%>
<%--                </c:if>--%>
<%--&lt;%&ndash;                <option value="${i}">${i}</option>&ndash;%&gt;--%>
<%--                </c:forEach>--%>
<%--            </span>--%>
<%--      <a th:if="${currentPage != i}" th:href="@{'/tariffs/' + ${i}+ '?sortField=' + ${sortField} + '&sortDirection='--%>
<%--            + ${sortDirection}}">[[${i}]]</a>--%>
<%--      <span th:unless="${currentPage != i}">[[${i}]]</span> &nbsp; &nbsp;--%>
<%--            </span>--%>
                </div>
                <div class="col-sm-1">
                    <c:if test="${!tariffs.firstPage}">
                        <a href="${pageContext.request.contextPath}/tariffs?p=${tariffs.pageNo-1}&s=${tariffs.pageSize}&sortDirection=${sortDirection}">
                            <fmt:message key="previous" bundle="${bundle}"/>
                        </a>
                    </c:if>
<%--                    <c:if test="${currentPage < totalPages}">--%>
<%--                        <a href="${pageContext.request.contextPath}/tariffs?p=${tariffs.pageNo+1}&s=${tariffs.pageSize}">--%>
<%--                            <fmt:message key="next" bundle="${bundle}"/>--%>
<%--                        </a>--%>
<%--                    </c:if>--%>
<%--                    <a th:if="${currentPage < totalPages}" th:href="@{'/tariffs/' + ${currentPage + 1} + '?sortField='--%>
<%--                    + ${sortField} + '&sortDirection=' + ${sortDirection}}" th:text="#{next}"></a>--%>
<%--                    <span th:unless="${currentPage < totalPages}" th:text="#{next}"></span>--%>
                </div>

                <div class="col-sm-1">
                    <c:if test="${!tariffs.lastPage}">
                        <a href="${pageContext.request.contextPath}/tariffs?p=${tariffs.pageNo+1}&s=${tariffs.pageSize}&sortDirection=${sortDirection}">
                            <span><fmt:message key="next" bundle="${bundle}"/></span>
                        </a>
                    </c:if>

<%--                    <c:if test="${currentPage < totalPages}">--%>
<%--                        <a href="${pageContext.request.contextPath}/tariffs?p=${totalPages}&s=${tariffs.pageSize}">--%>
<%--                            <fmt:message key="last" bundle="${bundle}"/>--%>
<%--                        </a>--%>
<%--                    </c:if>--%>
<%--                    <a th:if="${currentPage < totalPages}" th:href="@{'/tariffs/' + ${totalPages} + '?sortField='--%>
<%--                    + ${sortField} + '&sortDirection=' + ${sortDirection}}" th:text="#{last}"></a>--%>
<%--                    <span th:unless="${currentPage < totalPages}" th:text="#{last}"></span>--%>
                </div>
            </div>
<%--            </c:if>--%>
        </div>


    </div>

    <div class="container mt-5">
        <a class="btn btn-dark col-3" href="${pageContext.request.contextPath}/user/showNewTransportationForm">
            <fmt:message key="place.order" bundle="${bundle}"/>
        </a>
    </div>
</div>

<%@include file="../jspf/footer.jsp" %>
</body>
</html>
