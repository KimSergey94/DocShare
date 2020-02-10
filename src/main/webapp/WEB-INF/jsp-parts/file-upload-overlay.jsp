<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<c:set var="att_counter" value="0" scope="page" />

<div id="file-upload-overlay" class="modal fade">
    <div class="file-upload-box">
        <div class="close-overlay"></div>
        <h4 class="file-upload-box-title">Выберите файлы для загрузки </h4>
        <h3 class="hidden-file-sugg">Отметьте файлы которые хотите сделать скрытыми </h3>
        <p id="file-upload-error-msg" class="error-msg"></p>

        <form id="fileForm">
            <div class="file_data" id="file_data">
                <div>
                    <li class="files-are-required">Файлы:<p class="required">*</p></li>
                    <label class="file-upload">
                        <input name="files_to_upload" id="files_to_upload" multiple="multiple" onchange="addFiles(this);" type="file">
                        <p class="fa fa-cloud-upload"></p><div class="upload-icon"></div>Прикрепить файлы<p></p>
                    </label>
                </div>
                <div id="attached-files-box" class="attached-files-box">
                    <li></li>
                    <div id="attached-files" class="attached-files">
                    </div>
                </div>
                <div class="attachments-clear-box">
                    <li></li>
                    <div id="attachments-clear" class="attachments-clear"><p>Очистить список</p></div>
                </div>
                <input id="overlay-upload-files" class="overlay-submit" type="button" value="Загрузить файлы">
            </div>
        </form>
    </div>
</div>
