<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div id="file-share-overlay" class="modal fade">
    <div class="file-share-box">
        <div class="close-overlay"></div>
        <h4 class="file-share-box-title">Выберите пользователей</h4>
        <p id="file-share-error-msg" class="error-msg"></p>
        <input type="hidden" id="sharing-file-id" value="${requestScope.file.id_File}"/>
        <div>
            <input class="data" type="text" id="user-to-share-search"/>
            <div id="suggestion-box-file-share" class="suggestion-box-overlay"></div>
        </div>
        <div id="selected-users">
            <input type="hidden" id="selected-users-count" value="0">
            <div id="selected-users-box" class="selected-users-box"></div>
        </div>
        <input id="overlay-share-with-users" class="overlay-submit" type="button" value="Поделиться">
    </div>
</div>