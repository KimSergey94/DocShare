<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>

<c:if test="${(requestScope.isTrash != 'trash')}">
    <c:if test="${(sessionScope.current_page.isDepartment() && sessionScope.current_page.getId() == sessionScope.user.department.getId_Department())}">
        <div id="content-button-upload-file" class="content-button-upload-file">
            <input type="hidden" id="department-folder-id" value="${requestScope.openedFolder.id_Folder}">
            <div class="upload-files-icon"></div>
            <li>Загрузить файлы</li>
        </div>
    </c:if>


<c:if test="${(sessionScope.user.id_Role == 2 || sessionScope.user.id_Role == 3 || requestScope.openedFolder.userCreated.id_User == sessionScope.user.id_User)}">

    <c:if test="${(!sessionScope.current_page.isDepartment())}">
        <div id="content-button-upload-file" class="content-button-upload-file">
            <input type="hidden" id="folder-id" value="${requestScope.openedFolder.id_Folder}">
            <div class="upload-files-icon"></div>
            <li>Загрузить файлы</li>
        </div>
    </c:if>




    <c:if test="${(requestScope.openedFolder.flagHidden == true)}">
        <div id="content-button-unhide-folder" class="content-button-unhide-folder">
            <input type="hidden" id="unhide-folder-id" value="${requestScope.openedFolder.id_Folder}">
            <div class="unhide-icon"></div>
            <li>Сделать папку видимой</li>
        </div>
    </c:if>
    <c:if test="${(requestScope.openedFolder.flagHidden == false)}">
        <div id="content-button-hide-folder" class="content-button-hide-folder">
            <input type="hidden" id="hide-folder-id" value="${requestScope.openedFolder.id_Folder}">
            <div class="hide-icon"></div>
            <li>Скрыть папку</li>
        </div>
    </c:if>

    <c:if test="${(requestScope.openedFolder.flagDeleted == false)}">
        <div id="content-button-delete-folder" class="content-button-delete-folder">
            <input type="hidden" id="delete-folder-id" value="${requestScope.openedFolder.id_Folder}">
            <div class="delete-icon"></div>
            <li>Удалить папку</li>
        </div>
    </c:if>

    <div id="content-button-share-folder" class="content-button-share-folder">
        <input type="hidden" id="share-folder-id" value="${requestScope.openedFolder.id_Folder}">
        <div class="share-icon"></div>
        <li>Поделиться папкой</li>
    </div>

</c:if>
</c:if>

<c:if test="${(requestScope.isTrash == 'trash')}">
    <div id="content-button-restore-folder" class="content-button-restore-folder">
        <input type="hidden" id="restore-folder-id" value="${requestScope.openedFolder.id_Folder}">
        <div class="restore-icon"></div>
        <li>Восстановить папку</li>
    </div>
    <div id="content-button-erase-folder" class="content-button-erase-folder">
        <input type="hidden" id="erase-folder-id" value="${requestScope.openedFolder.id_Folder}">
        <div class="erase-icon"></div>
        <li>Удалить папку навсегда</li>
    </div>
</c:if>
