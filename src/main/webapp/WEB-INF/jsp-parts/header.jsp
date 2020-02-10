<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<header>
    <div class="site-logo"><a href="#"><div class="logo"></div></a></div>
    <div class="header-right-box">
        <div class="files"><a title="Файлы" href="${pageContext.request.contextPath}/files"><div class="files-icon"></div></a></div>
        <div class="chat"><a title="Чат" href="#"><div class="chat-icon"></div></a></div>
        <div class="mailbox"><a title="Электронная почта" href="#"><div class="mailbox-icon"></div></a></div>
        <div class="notes"><a title="Заметки" href="#"><div class="notes-icon"></div></a></div>
        <div class="help"><a title="Помощь" href="#"><div class="help-icon"></div></a></div>
        <div class="user-menu">
            <div class="user-info">
                <div class="user-icon"></div>
                <div class="user-name">${sessionScope.user.lastName.concat(" ").concat(sessionScope.user.firstName).concat(" ").concat(sessionScope.user.middleName)}</div>
                <div class="arrow"></div>
            </div>
            <div class="dropdown-menu">
                <li><fmt:message key = "key.position" bundle="${lang}"/>: <c:if test="${sessionScope.user.position != null}"> ${ language == "ru" ? sessionScope.user.position.localeRU : sessionScope.user.position.localeKZ}</c:if>  </li>
                <li><fmt:message key = "key.department" bundle="${lang}"/>: <c:if test="${sessionScope.user.department != null}"> ${ language == "ru" ? sessionScope.user.department.localeRU : sessionScope.user.department.localeKZ}</c:if></li>
                <li><fmt:message key = "key.phone" bundle="${lang}"/>: ${sessionScope.user.phone}</li>
                <li><fmt:message key = "key.email" bundle="${lang}"/>: ${sessionScope.user.email}</li>
                <div class="dropdown-container">
                    <div class="lang-theme">
                        <div class="language">
                            <div class="lang-kz"><a href="${pageContext.request.contextPath}/switchlang?sl=kz">kz</a></div>
                            <div class="lang-ru"><a href="${pageContext.request.contextPath}/switchlang?sl=ru">ru</a></div>
                            <input type="hidden" id="lang-js" value="${language}">
                        </div>
                        <div class="delimiter">|</div>
                        <div class="theme"><a href="#"><div class="theme-icon"> &nbsp;</div><fmt:message key = "key.theme" bundle="${lang}"/></a></div>
                    </div>
                    <div class="edit-logout">
                        <div class="edit-profile"><a href="${pageContext.request.contextPath}/editprofile?id=${sessionScope.user.id_User}"><fmt:message key = "key.edit" bundle="${lang}"/></a></div>
                        <div class="logout"><a href="${pageContext.request.contextPath}/logout"><fmt:message key = "key.logout" bundle="${lang}"/></a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>