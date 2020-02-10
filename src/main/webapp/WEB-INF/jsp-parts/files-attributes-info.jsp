<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>

<div class="files-info-details-box">

    <li class="files-attributes-title">Информация о файле</li>


    <c:if test="${!(not empty requestScope.openedFile) && !(not empty requestScope.filesNum) }">
        <div class="no-files">
            <span>Выберите файл для просмотра информации о нем</span>
        </div>
    </c:if>

    <c:if test="${(not empty requestScope.filesNum)}">
    <div class="file-attributes">
        <div class="file-attribute">
            <div class="fa-number">1. Колисчество выделенных файлов: </div>
            <div class="fa-data">
                <div class="fa-files-number">${requestScope.filesNum}</div>
            </div>
        </div>
        <div class="file-attribute">
            <div class="fa-name">2. Размер файлов: </div>
            <div class="fa-data">
                <div class="fa-file-size">${requestScope.filesSize} kilobytes or
                    <fmt:formatNumber type="number" maxFractionDigits="2" value="${requestScope.filesSize/1024}"/> megabytes</div>
            </div>
        </div>
    </div>
    </c:if>


    <c:if test="${(not empty requestScope.openedFile)}">
        <div class="file-attributes">
            <div class="file-attribute">
                <div class="fa-name">1. Название файла: </div>
                <div class="fa-data">
                    <div class="fa-file-name">${requestScope.openedFile.name}</div>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">2. Размер файла: </div>
                <div class="fa-data">
                    <div class="fa-file-size">${requestScope.fileSize} kilobytes or
                        <fmt:formatNumber type="number" maxFractionDigits="2" value="${requestScope.fileSize/1024}"/> megabytes</div>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">3. Видимость файла: </div>
                <div class="fa-data">
                    <c:if test="${requestScope.openedFile.flagHidden}">
                        <div class="fa-file-flagHidden">Файл скрыт</div>
                    </c:if>
                    <c:if test="${!requestScope.openedFile.flagHidden}">
                        <div class="fa-file-flagHidden">Файл не скрыт</div>
                    </c:if>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">4. Удален ли файл: </div>
                <div class="fa-data">
                    <c:if test="${requestScope.openedFile.flagDeleted}">
                        <div class="fa-file-flagHidden">Файл удален</div>
                    </c:if>
                    <c:if test="${!requestScope.openedFile.flagDeleted}">
                        <div class="fa-file-flagHidden">Файл не удален</div>
                    </c:if>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">5. Тип файла: </div>
                <div class="fa-data">
                    <c:choose>
                        <c:when test="${requestScope.openedFile.id_FileType == 1}"><div class="fa-file-fileType">.pdf формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 2}"><div class="fa-file-fileType">.doc формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 3}"><div class="fa-file-fileType">.docx формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 4}"><div class="fa-file-fileType">.xls формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 5}"><div class="fa-file-fileType">.xlsx формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 6}"><div class="fa-file-fileType">.rar формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 7}"><div class="fa-file-fileType">.zip формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 8}"><div class="fa-file-fileType">.txt формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 9}"><div class="fa-file-fileType">.ppt формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 10}"><div class="fa-file-fileType">.pptx формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 11}"><div class="fa-file-fileType">.jpg формат</div></c:when>
                        <c:when test="${requestScope.openedFile.id_FileType == 12}"><div class="fa-file-fileType">.png формат</div></c:when>
                        <c:otherwise><div class="fa-file-fileType">Неизвестный формат файла</div></c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">6. Папка родитель: </div>
                <div class="fa-data">
                    <div class="fa-file-folder">${requestScope.parentFolderName}</div>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">7. Кто создал: </div>
                <div class="fa-data">
                    <div class="fa-file-userCreated">${requestScope.openedFile.userCreated.lastName} ${openedFile.userCreated.firstName.charAt(0).toString()}.
                        <c:if test="${not empty openedFile.userCreated.middleName}">${openedFile.userCreated.middleName.charAt(0).toString()}.</c:if></div>
                </div>
            </div>
            <c:if test="${(not empty requestScope.openedFile.userDeleted)}">
            <div class="file-attribute">
                <div class="fa-name">8. Кто удалил: </div>
                <div class="fa-data">
                    <div class="fa-file-userDeleted">${requestScope.openedFile.userDeleted.lastName} ${openedFile.userDeleted.firstName.charAt(0).toString()}.
                        <c:if test="${not empty openedFile.userDeleted.middleName}">${openedFile.userDeleted.middleName.charAt(0).toString()}.</c:if></div>
                </div>
            </div>
            <div class="file-attribute">
                <div class="fa-name">9. Дата Создания: </div>
                <div class="fa-data">
                    <div class="fa-file-creationDate"><span><fmt:formatDate value="${requestScope.openedFile.creationDate}" pattern="dd.MM.yy HH:mm:ss"/></span></div>
                </div>
            </div>
            </c:if>

            <c:if test="${!(not empty requestScope.openedFile.userDeleted)}">
            <div class="file-attribute">
                <div class="fa-name">8. Дата Создания: </div>
                <div class="fa-data">
                    <div class="fa-file-creationDate"><span><fmt:formatDate value="${requestScope.openedFile.creationDate}" pattern="dd.MM.yy HH:mm:ss"/></span></div>
                </div>
            </div>
            </c:if>

        </div>
    </c:if>

</div>