<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>


<c:if test="${!(not empty requestScope.files)}">
    <div class="document-not-selected">
        <div class="document-not-selected-icon"></div>
        <div class="document-not-selected-text">Поиск по файлам не дал результатов</div>
    </div>
</c:if>

<c:if test="${(not empty requestScope.files)}">
    <div class="files-view">
        <div class="files-info-box">

            <div class="files-box-header" id="files-box-header">
                <li class="files-box-header-name">${requestScope.headerName} </li>
                <div class="files-box-header-func">
                    <li class="files-box-header-view">
                        <div class="files-view-btn">
                            <div class="files-view-name">Вид файлов</div>
                            <div class="files-view-icon"></div>
                            <div class="files-view-options-box">
                                <ul>
                                    <input type="hidden" class="files-view-id" id="current-view-id" <c:if test="${not empty sessionScope.filesView}">value="${sessionScope.filesView}</c:if>">
                                    <li class="file-view-option" id="tiles-view" value="1"><fmt:message key = "key.tilesview" bundle="${lang}"/></li>
                                    <li class="file-view-option" id="details-view" value="2"><fmt:message key = "key.detailsview" bundle="${lang}"/></li>
                                    <li class="file-view-option" id="list-view" value="3"><fmt:message key = "key.listview" bundle="${lang}"/></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                    <li class="files-box-header-sort">
                        <div class="files-sort">
                            <div class="files-sort-name">Сортировать файлы</div>
                            <div class="documents-sort-icon"></div>
                            <div class="files-sort-options-box">
                                <ul>
                                    <input type="hidden" class="current-sort-id" id="current-sort-id" >
                                    <input type="hidden" class="sort-order-type" id="sort-order-type" >
                                    <li class="file-sort-option" id="sort-by-date" value="1"><fmt:message key = "key.byDate" bundle="${lang}"/></li>
                                    <li class="file-sort-option" id="sort-by-name" value="2"><fmt:message key = "key.byName" bundle="${lang}"/></li>
                                </ul>
                            </div>
                        </div>
                    </li>
                </div>


            </div>

            <div class="attachment-functional-buttons"></div>

            <div class="file-info-box" id="file-info-box">
                <div class="file-info-details" id="file-info-details">
                    <c:if test="${!(not empty requestScope.files)}">
                        <div class="no-files">
                        </div>
                    </c:if>
                    <c:if test="${not empty requestScope.files}">
                        <div class="file-details-head">
                            <input type="hidden" class="file-id-details" >
                            <div class="file-icon-div-details">
                                <span class="icons-details"></span>
                            </div>
                            <div class="file-name-details"><span>Название файла</span></div>
                            <div class="file-type-details">
                                <span class="file-type-span-details">Тип файла</span>
                            </div>
                            <div class="file-date-details"><span>Дата создания</span></div>
                        </div>
                    </c:if>


                    <c:forEach var="file" items="${requestScope.files}">
                        <div class="file-details">
                            <input type="hidden" class="file-id-details" value="${file.id_File}">
                            <div class="file-icon-div-details">
                                <c:if test="${file.id_FileType == 1}">
                                    <span class="pdf-icon"></span>
                                </c:if>
                                <c:if test="${file.id_FileType == 2}"><span class="doc-icon"></span></c:if><c:if test="${file.id_FileType == 3}"><span class="docx-icon"></span></c:if>
                                <c:if test="${file.id_FileType == 4}"><span class="xls-icon"></span></c:if><c:if test="${file.id_FileType == 5}"><span class="xlsx-icon"></span></c:if>
                                <c:if test="${file.id_FileType == 6}"><span class="rar-icon"></span></c:if><c:if test="${file.id_FileType == 7}"><span class="zip-icon"></span></c:if>
                                <c:if test="${file.id_FileType == 8}"><span class="txt-icon"></span></c:if><c:if test="${file.id_FileType == 9}"><span class="ppt-icon"></span></c:if>
                                <c:if test="${file.id_FileType == 10}"><span class="pptx-icon"></span></c:if><c:if test="${file.id_FileType == 11}"><span class="jpg-icon"></span></c:if>
                                <c:if test="${file.id_FileType == 12}"><span class="png-icon"></span></c:if><c:if test="${file.id_FileType == 13}"><span class="unknown-file-icon"></span></c:if>
                            </div>
                            <div class="file-name-details"><span>${file.name}</span></div>
                            <div class="file-type-details">
                                <span class="file-type-span-details">
                                    <c:if test="${file.id_FileType == 1}">.df</c:if>
                                    <c:if test="${file.id_FileType == 2}">.doc</span></c:if><c:if test="${file.id_FileType == 3}">.docx</span></c:if>
                                <c:if test="${file.id_FileType == 4}">.xls</span></c:if><c:if test="${file.id_FileType == 5}">.xlsx</c:if>
                                <c:if test="${file.id_FileType == 6}">.rar</c:if><c:if test="${file.id_FileType == 7}">.zip</c:if>
                                <c:if test="${file.id_FileType == 8}">.txt</c:if><c:if test="${file.id_FileType == 9}">.ppt</c:if>
                                <c:if test="${file.id_FileType == 10}">.pptx</c:if><c:if test="${file.id_FileType == 11}">.jpg</c:if>
                                <c:if test="${file.id_FileType == 12}">.png</span></c:if><c:if test="${file.id_FileType == 13}">Неизвестный формат</c:if>
                                </span>
                            </div>
                            <div class="file-date-details"><span><fmt:formatDate value="${file.creationDate}" pattern="dd.MM.yy HH:mm:ss"/></span></div>
                        </div>
                    </c:forEach>
                </div>
            </div>


        </div>

        <div class="files-attributes-info" id="files-attributes-info">
            <div class="files-info-details-box">

                <li class="files-attributes-title">Информация о файле</li>


                <c:if test="${!(not empty requestScope.openedFile) && !(not empty requestScope.filesNum) }">
                    <div class="no-files">
                        <span>Выберите файл для просмотра информации о нем</span>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</c:if>



