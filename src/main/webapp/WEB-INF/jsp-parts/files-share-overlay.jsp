<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div id="files-share-overlay" class="modal fade">
    <div class="files-share-box">
        <div class="close-overlay"></div>
        <h4 class="files-share-box-title">Выберите пользователей</h4>
        <p id="files-share-error-msg" class="error-msg"></p>
        <%--<input type="hidden" id="sharing-file-id" value="${requestScope.file.id_File}"/>--%>
        <div>
            <input class="data" type="text" id="user-to-share-files-search"/>
            <div id="suggestion-box-files-share" class="suggestion-box-overlay"></div>
        </div>
        <div id="selected-users-to-share-files">
            <input type="hidden" id="selected-users-to-share-files-count" value="0">
            <div id="selected-users-box-to-share-files" class="selected-users-box-to-share-files"></div>
        </div>
        <input id="overlay-share-files-with-users" class="overlay-submit" type="button" value="Поделиться">
    </div>
</div>