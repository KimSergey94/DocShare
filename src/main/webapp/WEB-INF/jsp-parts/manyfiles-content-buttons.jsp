<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>


<c:if test="${(sessionScope.user.id_Role == 2 || sessionScope.user.id_Role == 3 || requestScope.isUserFileUploader == true)}">
    <c:if test="${(requestScope.isTrash != 'trash')}">
        <%--<div id="content-button-download-files" class="content-button-download-files">
            <input type="hidden" id="download-files-id" >
            <div class="download-file-icon"></div>
            <li>Скачать файлы</li>
        </div>--%>
        <c:if test="${(requestScope.allFilesVisible == false)}">
            <div id="content-button-unhide-files" class="content-button-unhide-files">
                <input type="hidden" id="unhide-files-id" >
                <div class="unhide-icon"></div>
                <li>Сделать файлы видимыми</li>
            </div>
        </c:if>
        <c:if test="${(requestScope.allFilesVisible == true)}">
            <div id="content-button-hide-files" class="content-button-hide-files">
                <input type="hidden" id="hide-files-id" >
                <div class="hide-icon"></div>
                <li>Сделать файлы невидимыми</li>
            </div>
        </c:if>

        <div id="content-button-delete-files" class="content-button-delete-files">
            <input type="hidden" id="delete-files-id" >
            <div class="delete-icon"></div>
            <li>Удалить файлы</li>
        </div>
        <div id="content-button-share-files" class="content-button-share-files">
            <input type="hidden" id="share-files-id" >
            <div class="share-icon"></div>
            <li>Поделиться файлами</li>
        </div>
    </c:if>

    <c:if test="${(requestScope.isTrash == 'trash')}">
        <div id="content-button-restore-files" class="content-button-restore-files">
            <input type="hidden" id="restore-files-id">
            <div class="restore-icon"></div>
            <li>Восстановить файлы</li>
        </div>
        <div id="content-button-erase-files" class="content-button-erase-files">
            <input type="hidden" id="erase-files-id">
            <div class="erase-icon"></div>
            <li>Удалить файлы навсегда</li>
        </div>
    </c:if>

</c:if>
