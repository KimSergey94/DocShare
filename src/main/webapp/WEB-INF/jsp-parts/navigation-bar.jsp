<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<c:set var="unread_counter" value="0" scope="page" />
<div class="navigation-bar">
    <div class="navigation-box">
        <div class="create-document">
            <div id="create-document-link" class="create-document-link">
                <div class="create-document-icon"></div>
                <li><fmt:message key = "key.createFolder" bundle="${lang}"/></li>
            </div>

        </div>

        <div class="navigation">

            <div class="my-documents <c:if test="${fn:contains(sessionScope.opened_menu, 1)}">opened</c:if>">
                <input type="hidden" value="1">

                <div>
                    <span class="menu-arrow"></span>
                    <p>Отделы</p>
                    <span id="unread-counter-mydocs"></span>
                </div>

                <ul>
                    <c:forEach var="department" items="${requestScope.departments}">
                        <li class="department-li" <c:if test="${sessionScope.current_page.getId() == department.id_Department && sessionScope.current_page.isDepartment() == true}">class="navigation-element-opened"</c:if>>
                            <span class="department-icon"></span>
                            <a href="${pageContext.request.contextPath}/department?id_Department=${department.id_Department}">
                            <p class="department-p">${ language == "ru" ? department.localeRU : department.localeKZ}</p>  </a>
                        </li>
                    </c:forEach>
                </ul>
            </div>

            <ul><li <c:if test="${sessionScope.current_page.getId() == 2 && sessionScope.current_page.isDepartment() == false}">class="navigation-element-opened"</c:if>><span class="storage-icon"></span><a href="${pageContext.request.contextPath}/my">Моё хранилище</a></li></ul>
            <ul><li <c:if test="${sessionScope.current_page.getId() == 3 && sessionScope.current_page.isDepartment() == false}">class="navigation-element-opened"</c:if>><span class="shared-icon"></span><a href="${pageContext.request.contextPath}/shared">Доступные мне</a></li></ul>
            <ul><li <c:if test="${sessionScope.current_page.getId() == 4 && sessionScope.current_page.isDepartment() == false}">class="navigation-element-opened"</c:if>><span class="trash-icon"></span><a href="${pageContext.request.contextPath}/trash">Корзина</a></li></ul>

        </div>
    </div>
    <div class="requisites">
        <li><a class="itbc-link" href="http://www.itbc.com.kz"><div class="with-love-itbc"></div></a></li>
    </div>
</div>