<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>



<c:if test="${(sessionScope.user.id_Role == 2 || sessionScope.user.id_Role == 3 || requestScope.openedFile.userCreated.id_User == sessionScope.user.id_User)}">
    <c:if test="${(requestScope.isTrash != 'trash')}">
        <div id="content-button-download-file" class="content-button-download-file">
            <input type="hidden" id="download-file-id" value="${requestScope.openedFile.id_File}">
            <div class="download-file-icon"></div>
            <li>Скачать файл</li>
        </div>

        <c:if test="${(requestScope.openedFile.flagHidden == true)}">
            <div id="content-button-unhide-file" class="content-button-unhide-file">
                <input type="hidden" id="unhide-file-id" value="${requestScope.openedFile.id_File}">
                <div class="unhide-icon"></div>
                <li>Сделать файл видимым</li>
            </div>
        </c:if>
        <c:if test="${(requestScope.openedFile.flagHidden == false)}">
            <div id="content-button-hide-file" class="content-button-hide-file">
                <input type="hidden" id="hide-file-id" value="${requestScope.openedFile.id_File}">
                <div class="hide-icon"></div>
                <li>Сделать файл невидимым</li>
            </div>
        </c:if>


        <c:if test="${(requestScope.openedFile.flagDeleted == false)}">
            <div id="content-button-delete-file" class="content-button-delete-file">
                <input type="hidden" id="delete-file-id" value="${requestScope.openedFile.id_File}">
                <div class="delete-icon"></div>
                <li>Удалить файл</li>
            </div>
        </c:if>

        <div id="content-button-share-file" class="content-button-share-file">
            <input type="hidden" id="share-file-id" value="${requestScope.openedFile.id_File}">
            <div class="share-icon"></div>
            <li>Поделиться файлом</li>
        </div>
    </c:if>
    <c:if test="${(requestScope.isTrash == 'trash')}">
        <div id="content-button-restore-file" class="content-button-restore-file">
            <input type="hidden" id="restore-file-id" value="${requestScope.openedFile.id_File}">
            <div class="restore-icon"></div>
            <li>Восстановить файл</li>
        </div>
        <div id="content-button-erase-file" class="content-button-erase-file">
            <input type="hidden" id="erase-file-id" value="${requestScope.openedFile.id_File}">
            <div class="erase-icon"></div>
            <li>Удалить файл навсегда</li>
        </div>
    </c:if>










</c:if>

