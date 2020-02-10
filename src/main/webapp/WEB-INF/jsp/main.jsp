<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="language" value="${not empty sessionScope.sessionLocale ? sessionScope.sessionLocale : initParam['sessionLocale']}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="locale" var="lang"/>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.maskedinput.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/aes.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.serializejson.min.js"></script>
    <title>Мои файлы</title>
</head>
<body onload="countLines();">
<jsp:include page="/WEB-INF/jsp-parts/header.jsp"/>
<div class="main-container">
    <jsp:include page="/WEB-INF/jsp-parts/navigation-bar.jsp"/>
    <jsp:include page="/WEB-INF/jsp-parts/preload-box.jsp" />
    <div class="content-box" id="content-box">

        <div class="content-buttons" id="content-buttons">
            <c:if test="${sessionScope.current_page.isDepartment() == true}">
                <c:if test="${(sessionScope.user.id_Role == 2 || sessionScope.user.id_Role == 3 || sessionScope.user.department.id_Department == sessionScope.current_page.getId())}">
                    <div id="content-button-create-folder" class="content-button-create-folder">
                        <input type="hidden" id="create-folder-id" value="${requestScope.openedFolder.id_Folder}">
                        <div class="content-button-create-icon"></div>
                        <li>Создать папку</li>
                    </div>
                </c:if>
            </c:if>
        </div>

        <div class="content" id="content">
            <div class="document-not-selected">
                <div class="document-not-selected-icon"></div>
                <div class="document-not-selected-text">Выберите папку для просмотра содержимого</div>
            </div>
        </div>

    </div>
</div>
<div id="popUp-overlay" class="modalPop fade">
    <div class="popUp-box">
        <h2 id="popUp-info"></h2>
    </div>
</div>
</body>
</html>