<%@ tag description="Template inicial" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" %>
<%@ attribute name="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <title>${title}</title>

    <base href="${pageContext.request.contextPath}/">
    <link rel="shortcut icon" href="assets/resources/images/favicon.ico" type="image/x-icon">
    <link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="assets/libraries/materialize/css/materialize.min.css" type="text/css" rel="stylesheet"
          media="screen,projection"/>
    <link href="assets/resources/styles/tags/template-tag.css" type="text/css" rel="stylesheet"
          media="screen,projection"/>
    <link href="assets/resources/styles/styles.css" type="text/css" rel="stylesheet" media="screen,projection"/>
</head>

<body>
<main>
    <c:set var="currenturl" value="${requestScope['javax.servlet.forward.request_uri']}"/>
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
                    <li><a class="btn waves-effect waves-light" href="como-funciona">Como Funciona?</a></li>
                    <li><a href="login">ENTRAR</a></li>
                    <li><a href="cadastrar-se">CADASTRE-SE</a></li>
                </ul>
            </sec:authorize>

            <sec:authorize access="hasRole('COMPANY')">
                <c:if test="${access_type eq 'CLIENT'}">
                    <t:navbar-client userInfo="${userInfo}"></t:navbar-client>
                </c:if>
                <c:if test="${access_type eq 'COMPANY'}">
                    <t:navbar-company userInfo="${userInfo}"></t:navbar-company>
                </c:if>
            </sec:authorize>

            <sec:authorize access="hasRole('USER')">
                <c:if test="${access_type eq 'PROFESSIONAL'}">
                    <t:navbar-professional userInfo="${userInfo}"></t:navbar-professional>
                </c:if>
                <c:if test="${access_type eq 'CLIENT'}">
                    <t:navbar-client userInfo="${userInfo}"></t:navbar-client>
                </c:if>
            </sec:authorize>

            <sec:authorize access="hasRole('ADMIN')">
                <ul class="right hide-on-med-and-down">
                    <li><a href="logout">SAIR</a></li>
                </ul>
            </sec:authorize>

            <!-- sidenav -->
            <!-- usuário logado -->
            <sec:authorize var="isAdmin" access="hasRole('ADMIN')"/>
            <sec:authorize access="isAuthenticated()">
                <c:choose>
                    <c:when test="${isAdmin}">
                        <div id="nav-mobile" class="sidenav">
                            <t:side-panel-admin userInfo="${userInfo}"></t:side-panel-admin>
                        </div>
                    </c:when>
                    <c:when test="${fn:contains(currenturl, '/minha-conta/cliente')}">
                        <div id="nav-mobile" class="sidenav">
                            <t:side-panel userInfo="${userInfo}" followdto="${followdto}"
                                          statisticInfo="${statisticInfo}"></t:side-panel>
                        </div>
                    </c:when>
                    <c:when test="${fn:contains(currenturl, '/minha-conta/profissional')}">
                        <div id="nav-mobile" class="sidenav">
                            <t:side-panel userInfo="${userInfo}" followdto="${followdto}"
                                          statisticInfo="${statisticInfo}"></t:side-panel>
                        </div>
                    </c:when>
                    <c:when test="${fn:contains(currenturl, '/minha-conta/empresa')}">
                        <ul id="nav-mobile" class="sidenav">
                            <li><a class="menu-itens" href="passo-1">ANUNCIAR</a></li>
                            <li><a href="minha-conta/profissional">Sou profissional</a></li>
                            <li><a href="minha-conta">Sou empresa</a></li>
                            <li><a class="menu-itens" href="logout">SAIR</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <ul id="nav-mobile" class="sidenav">
                            <li><a class="menu-itens" href="passo-1">ANUNCIAR</a></li>
                            <li><a href="minha-conta/profissional">Sou profissional</a></li>
                            <li><a href="minha-conta">Sou empresa</a></li>
                            <li><a class="menu-itens" href="logout">SAIR</a></li>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>

            <a href="#" data-target="nav-mobile" class="sidenav-trigger valign-wrapper"><i class="material-icons">menu</i></a>

        </div>
    </nav>
    <jsp:doBody/>
</main>
<footer class="page-footer">

    <div class="footer-copyright">
        <div class="container">
            <p class="center">© Copyright 2022 - ServiceBook - Todos os direitos</p>
        </div>
    </div>
</footer>

<script
        src="https://code.jquery.com/jquery-3.7.0.min.js"
        integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g="
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>

<script src="assets/libraries/materialize/js/materialize.min.js"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script src="assets/resources/scripts/main.js"></script>
<script src="assets/resources/scripts/init.js"></script>
<script src="assets/resources/scripts/sse.js"></script>
<script src="assets/libraries/jquery.mask.js"></script>

<!--script src="assets/resources/scripts/expertise-sidepanel-ajax.js"></script!-->
<script type="text/javascript">
    function rolar(objID) {
        this.location = "#" + objID;
    }

    document.addEventListener('DOMContentLoaded', function () {
        let elems = document.querySelectorAll('.dropdown-trigger');
        console.log((elems))
        let t = M.Dropdown.init(elems, {
            coverTrigger: false, constrainWidth: false, hover: true
        });
    });
</script>

</body>
</html>
