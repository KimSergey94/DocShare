<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="highest_executed_priority" value="${0}" scope="request"/>


<div class="preload-box">
    <div class="preload-search-box">
        <input type="search" id="search-box" class="search-box" placeholder="Поиск файлов и папок...">
    </div>
    <div class="preload-box-header">
        <c:if test="${sessionScope.current_page.isDepartment() != true}">
            <li class="preload-box-header-name" id="preload-box-header-name" title="${requestScope.headerName}"> ${requestScope.headerName} </li>
        </c:if>
        <c:if test="${sessionScope.current_page.isDepartment()}">
            <li class="preload-box-header-name-department" id="preload-box-header-name-department" title="${requestScope.headerName}" > ${requestScope.headerName}</li>

        </c:if>
        <li class="preload-box-header-sort">
            <div class="documents-sort">
                <div class="documents-sort-name">Сортировка</div>
                <div class="documents-sort-icon"></div>
                <div class="sort-options-box">
                    <ul>
                        <input type="hidden" class="current-sort-id" id="current-sort-id" >
                        <input type="hidden" class="sort-order-type" id="sort-order-type" >
                        <li class="user-sort-option" id="sort-by-date" value="1"><fmt:message key = "key.byDate" bundle="${lang}"/></li>
                        <li class="user-sort-option" id="sort-by-name" value="2">По алфавиту</li>
                    </ul>
                </div>
            </div>
        </li>
    </div>
    <div id="preload-documents" class="preload-documents">
        <c:if test="${!(not empty requestScope.folders)}">
        <div class="empty-box">
            <div class="empty-box-icon"></div>
            <div class="empty-box-icon-text"><span>Папка "${requestScope.headerName}" пуста</span></div>
        </div>
        </c:if>

        <c:forEach var="folder" items="${requestScope.folders}">
            <div class="preloaded-document">
                <input type="hidden" class="folder-id" value="${folder.id_Folder}">
                <div class="folder-icon-div"><span class="folder-icon"></span></div>

                <div class="preloaded-document-info">
                    <div class="preloaded-document-title"><span>${folder.name}</span></div>

                    <div class="preloaded-document-reg-number-date">
                        <div class="preloaded-document-reg-number">
                            <span>${folder.userCreated.lastName} ${folder.userCreated.firstName.charAt(0).toString()}. <c:if test="${not empty folder.userCreated.middleName}">${folder.userCreated.middleName.charAt(0).toString()}.</c:if></span>
                        </div>
                        <div class="preloaded-document-date"><span><fmt:formatDate value="${folder.creationDate}" pattern="dd.MM.yy HH:mm:ss"/></span></div>
                    </div>
                </div>

            </div>
        </c:forEach>
    </div>
</div>