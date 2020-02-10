<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<div class="content-buttons" id="content-buttons">
    <jsp:include page="/loadfoldersmenu" />
</div>

<div class="content" id="content">
    <div class="files-view">
        <div class="files-info-box">

                <div class="files-box-header" id="files-box-header">
                    <li class="files-box-header-name">${requestScope.headerName}</li>
                    <div class="files-box-header-func">
                        <li class="files-box-header-view">
                            <div class="files-view-btn">
                                <div class="files-view-name">Вид файлов</div>
                                <div class="files-view-icon"></div>
                                <div class="files-view-options-box">
                                    <ul>
                                        <input type="hidden" class="current-view-id" id="current-view-id" <c:if test="${not empty sessionScope.filesView}">value="${sessionScope.filesView}</c:if>">
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


                    <%--<li class="files-box-header-file-info"> Информация о файле</li>--%>

                </div>


            <div class="file-info-box" id="file-info-box">
                <div class="file-info" id="file-info">
                    <c:if test="${!(not empty requestScope.files)}">
                        <div class="no-files">
                        </div>
                    </c:if>

                    <c:forEach var="file" items="${requestScope.files}">
                        <div class="file">
                            <input type="hidden" class="file-id" value="${file.id_File}">
                            <div class="file-icon-div">
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
                            <div class="file-name"><span>${file.name}</span></div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div class="files-attributes-info" id="files-attributes-info">
            <div class="files-info-details-box">

                <li class="files-attributes-title">Информация о файле</li>


                <c:if test="${!(not empty requestScope.openedFile)}">
                    <div class="no-files">
                        <span>Выберите файл для просмотра информации о нем</span>
                    </div>
                </c:if>
            </div>

        </div>

    </div>
</div>



