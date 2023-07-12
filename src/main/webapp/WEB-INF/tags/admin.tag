<%@tag description="Template inicial" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <link href="assets/resources/styles/tags/admin-tag.css" type="text/css" rel="stylesheet" media="screen,projection"/>
    <link href="assets/resources/styles/style.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>
<body>
<ul id="dropdown1" class="dropdown-content">
    <li><a href="a/cidades">CIDADES</a></li>
    <li><a href="a/categorias">CATEGORIAS</a></li>
    <li><a href="a/especialidades">ESPECIALIDADES</a></li>
    <li><a href="a/servicos">SERVIÇOS</a></li>
</ul>
<nav class="nav-main" role="navigation">
    <div class="nav-wrapper container">
        <a id="logo-container" href="#!" class="brand-logo">
            <svg class="logo-icon left" style="width: 50px;height:50px" viewBox="0 0 24 24">
                <path class="primary-color-icon" d="M21.71 20.29L20.29 21.71A1 1 0 0 1 18.88 21.71L7 9.85A3.81 3.81 0 0 1 6 10A4 4 0 0 1 2.22 4.7L4.76 7.24L5.29 6.71L6.71 5.29L7.24 4.76L4.7 2.22A4 4 0 0 1 10 6A3.81 3.81 0 0 1 9.85 7L21.71 18.88A1 1 0 0 1 21.71 20.29M2.29 18.88A1 1 0 0 0 2.29 20.29L3.71 21.71A1 1 0 0 0 5.12 21.71L10.59 16.25L7.76 13.42M20 2L16 4V6L13.83 8.17L15.83 10.17L18 8H20L22 4Z" />
            </svg>
            <h4 class="left logo-text">ServiceBook</h4>
        </a>

        <ul class="right hide-on-med-and-down">
            <li><a href="a/servicos/dashboard" class="menu-itens">DASHBOARD</a></li>
            <li><a class="menu-itens dropdown-trigger" href="#!" data-target="dropdown1">CADASTRAR<i class="material-icons right">arrow_drop_down</i></a></li>
            <li><a class="menu-itens" href="#!">MINHA CONTA</a></li>
            <li><a class="menu-itens" href="#!">SAIR</a></li>
        </ul>

        <ul id="nav-mobile" class="sidenav">
            <li><a class="menu-itens" href="a/cidades">CIDADES</a></li>
            <li><a class="menu-itens" href="a/especialidades">ESPECIALIDADES</a></li>
            <li><a class="menu-itens" href="a/categorias">CATEGORIAS</a></li>
            <li><a class="menu-itens" href="#!">MINHA CONTA</a></li>
            <li><a class="menu-itens" href="#!">SAIR</a></li>

        </ul>
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
    </div>
</nav>
<jsp:doBody/>

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
