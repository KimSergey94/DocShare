<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div id="folder-create-overlay" class="modal fade">
    <div class="folder-create-box">
        <div class="close-overlay"></div>
        <h4 class="folder-create-box-title">Введите название папки</h4>
        <p id="folder-create-error-msg" class="error-msg"></p>
        <div class="folder_data">
            <input class="data" type="text" id="folder-name"/>
            <div>
                <label for="folder_flagHidden" style="color: black">Сделать папку скрытой?</label>
                <input class="folder_flagHidden" type="checkbox" id="folder_flagHidden"/>
            </div>
                <input id="overlay-create-folder" class="overlay-submit" type="button" value="Создать папку">
        </div>
    </div>
</div>