<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div id="department-folder-create-overlay" class="modal fade">
    <div class="department-folder-create-box">
        <div class="close-overlay"></div>
        <h4 class="folder-create-box-title">Введите название папки</h4>
        <p id="department-folder-create-error-msg" class="error-msg"></p>
        <div class="folder_data">
            <input class="data" type="hidden" id="department-id" value="${sessionScope.current_page.getId()}"/>
            <input class="data" type="text" id="department-folder-name" />
            <div>
                <label for="department_folder_flagHidden" style="color: black">Сделать папку скрытой?</label>
                <input class="department_folder_flagHidden" type="checkbox" id="department_folder_flagHidden"/>
            </div>


        </div>
        <input id="overlay-create-department-folder" class="overlay-submit" type="button" value="Создать папку">
    </div>
</div>