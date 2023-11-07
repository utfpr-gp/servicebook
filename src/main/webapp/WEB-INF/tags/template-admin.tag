<%@tag description="Template inicial" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" %>

<%@attribute name="title" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>${title}</title>
    <base href="${pageContext.request.contextPath}/">
    <link rel="shortcut icon" href="assets/resources/images/favicon.ico" type="image/x-icon">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="assets/libraries/materialize/css/materialize.min.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="assets/resources/styles/tags/template-admin-tag.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="assets/resources/styles/styles.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<main>
    <nav class="nav-main" role="navigation">
        <div class="nav-wrapper container">
            <a id="logo-container" href="" class="brand-logo">
                <svg class="logo-icon left" style="width: 50px;height:50px" viewBox="0 0 24 24">
                    <path class="primary-color-icon"
                          d="M21.71 20.29L20.29 21.71A1 1 0 0 1 18.88 21.71L7 9.85A3.81 3.81 0 0 1 6 10A4 4 0 0 1 2.22 4.7L4.76 7.24L5.29 6.71L6.71 5.29L7.24 4.76L4.7 2.22A4 4 0 0 1 10 6A3.81 3.81 0 0 1 9.85 7L21.71 18.88A1 1 0 0 1 21.71 20.29M2.29 18.88A1 1 0 0 0 2.29 20.29L3.71 21.71A1 1 0 0 0 5.12 21.71L10.59 16.25L7.76 13.42M20 2L16 4V6L13.83 8.17L15.83 10.17L18 8H20L22 4Z"/>
                </svg>
                <h4 class="left logo-text">ServiceBook</h4>
            </a>

            <%-- Anônimo --%>
            <sec:authorize access="!isAuthenticated()">
                <ul class="right hide-on-med-and-down">
                    <li><a href="login">ENTRAR</a></li>
                </ul>
            </sec:authorize>

            <%-- Usuário logado --%>
            <sec:authorize access="isAuthenticated()">
                <ul class="right hide-on-med-and-down">
                    <li><a href="a/dashboard">Dashboard</a></li>
                    <li><a href="logout">SAIR</a></li>
                </ul>
                <div id="nav-mobile" class="sidenav">
                    <t:side-panel-admin userInfo="${userInfo}"></t:side-panel-admin>
                </div>
            </sec:authorize>
            <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <!-- Painel lateral -->
            <div class="col m4 l3 hide-on-med-and-down">
                <t:side-panel-admin userInfo="${userInfo}"></t:side-panel-admin>
            </div>

            <!-- Painel com as solicitações de serviços -->
            <div class="col s12 l9">
                <jsp:doBody/>
            </div>
        </div>
    </div>
</main>

<footer class="page-footer">
    <div class="footer-copyright">
        <div class="container">
            <p class="center">© Copyright 2021 - ServiceBook - Todos os direitos</p>
        </div>
    </div>
</footer>

<script
        src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
        crossorigin="anonymous"></script>
<script src="assets/libraries/materialize/js/materialize.min.js"></script>
<script src="assets/resources/scripts/init.js"></script>
<script src="assets/resources/scripts/main.js"></script>
</body>
</html>
