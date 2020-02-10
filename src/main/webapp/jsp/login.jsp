<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty sessionScope.sessionLocale}">
        <fmt:setLocale value="${sessionLocale}"/>
    </c:when>
    <c:otherwise>
        <fmt:setLocale value="${initParam['sessionLocale']}"/>
    </c:otherwise>
</c:choose>
<fmt:setBundle basename="locale" var="lang"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.maskedinput.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/aes.js"></script>
    <title><fmt:message key = "key.signIn" bundle="${lang}"/></title>
</head>
<body class="login-body">
<div class="lf-container">
    <div class="login-box">
        <div class="login-box-logo"></div>
        <p class="error-msg">
            <c:if test = "${error == 1000}">
                <fmt:message key = "key.incorrectLogin" bundle = "${lang}"/>
            </c:if>
            <c:if test = "${error == 1001}">
                <fmt:message key = "key.incorrectPassword" bundle = "${lang}"/>
            </c:if>
            <c:if test = "${error == 1002}">
                Пользователь заблокирован до <fmt:formatDate value="${blockedDate}" pattern="dd/MM/yyyy"/>
            </c:if>
        </p>
        <div id="login-form" class="login-form">
            <form class="log-form" method="POST" autocomplete="off" action="${pageContext.request.contextPath}/login">
                <input id="login" class="input-data input-login" name = "login" type="text" minlength="5" maxlength="24" placeholder="<fmt:message key = "key.login" bundle="${lang}"/>"  required />
                <input id="password" class = "input-data input-password" name = "password" type="password" minlength="5"  placeholder="<fmt:message key = "key.password" bundle="${lang}"/>" required />
                <input id="signin" class="input-button" type="submit" name="submit"  value="<fmt:message key = "key.signIn" bundle="${lang}"/>">
            </form>
        </div>
        <div class="login-box-pass-forgot">
            <a  class="pass-forgot-link" href="#"><span><fmt:message key = "key.forgotPassword" bundle="${lang}"/></span></a>
        </div>
    </div>
    <div class="login-box mt5"></div>
    <div class="login-box mt10"></div>
</div>
</body>
</html>