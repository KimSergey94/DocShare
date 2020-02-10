<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div id="folder-share-overlay" class="modal fade">
    <div class="folder-share-box">
        <div class="close-overlay"></div>
        <h4 class="folder-share-box-title">Выберите пользователей</h4>
        <p id="folder-share-error-msg" class="error-msg"></p>
        <input type="hidden" id="sharing-folder-id" value="${requestScope.folder.id_Folder}"/>
        <div>
            <input class="data" type="text" id="user-to-share-folder-search"/>
            <div id="suggestion-box-folder-share" class="suggestion-box-overlay"></div>
        </div>
        <div id="selected-users-to-share-folder">
            <input type="hidden" id="selected-users-count-to-share-folder" value="0">
            <div id="selected-users-box-to-share-folder" class="selected-users-box-to-share-folder"></div>
        </div>
        <input id="overlay-share-folder-with-users" class="overlay-submit" type="button" value="Поделиться">
    </div>
</div>