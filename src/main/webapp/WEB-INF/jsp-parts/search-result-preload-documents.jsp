<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="language"
       value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>


<c:if test="${!(not empty requestScope.folders)}">
    <div class="empty-box">
        <div class="empty-box-icon"></div>
        <div class="empty-box-icon-text"><span>Поиск по папкам не дал результатов</span></div>
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
