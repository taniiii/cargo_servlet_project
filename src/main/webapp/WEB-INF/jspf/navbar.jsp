
<!--  навбар из раздела components-navbar-->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Trustworthy cargo shipping</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>  <!-- описание кнопки меню navbar-->
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent"> <!--описание меню navbar-->
            <ul class="navbar-nav mr-auto">
                <li class="nav-item"><!-- переделать на тарифы-->
                    <a class="nav-link" href="${pageContext.request.contextPath}/tariffs?p=1&s=5&sortDirection=${reverseSortDirection}">
                        <fmt:message key="company.tariffs" bundle="${bundle}"/></a>
                </li>
                <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'USER'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/showNewTransportationForm">
                            <fmt:message key="company.new.order" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/admin/orders?p=1&s=5&sortBy=created_at&sortDirection=asc">
                            <fmt:message key="orders.list" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'USER'}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/orders?p=1&s=5&sortBy=created_at&sortDirection=asc">
                            <fmt:message key="company.user.transportations" bundle="${bundle}"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.authenticated == true}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/user/profile"><fmt:message key="company.user.profile" bundle="${bundle}"/></a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.authenticated == true && sessionScope.role == 'ADMIN'}">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/admin/userlist?p=1&s=10&sortBy=id&sortDirection=asc">
                        <fmt:message key="company.userlist" bundle="${bundle}"/></a>
                </li>
                </c:if>
            </ul>
            <div class="navbar-text mr-3"><a href="${pageContext.request.contextPath}/?locale=ua"><fmt:message key="lanquage.ua" bundle="${bundle}"/></a> <a href="${pageContext.request.contextPath}/?locale=en"><fmt:message key="language.eng" bundle="${bundle}"/></a></div>
            <c:if test="${sessionScope.authenticated == null && sessionScope.role == null}">
            <div class="navbar-text mr-3">Guest</div></c:if>
            <c:if test="${sessionScope.authenticated == true}">
            <div class="navbar-text mr-3">${sessionScope.user.getUsername()}</div>
            </c:if>
                <c:if test="${sessionScope.authenticated == true}">
                <div>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="btn btn-light"><fmt:message key="company.sign.out" bundle="${bundle}"/></button>
                </form>
                </div>
                </c:if>
                <c:if test="${sessionScope.authenticated == null}">
                <div>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/login" role="button"><fmt:message key="company.log.in" bundle="${bundle}"/></a>
                </div>
                </c:if>
        </div>
    </nav>



