<%@tag description="Template inicial" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" %>
<%@ attribute name="individualInfo" type="br.edu.utfpr.servicebook.util.sidePanel.SidePanelUserDTO"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="currenturl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="right">
    <a class="left menu-link" href="requisicoes?passo=1">ANUNCIAR</a>

    <ul class="right nav-btn hide-on-med-and-down">
        <c:if test="${fn:contains(currenturl, 'cliente')}">
            <li>
                <a class='dropdown-trigger btn' href='#' data-target='dropdown-cliente'>SOU CLIENTE<i class="tiny material-icons right">arrow_drop_down</i></a>
                <ul id='dropdown-cliente' class='dropdown-content'>
                    <li><a href="minha-conta/profissional">Sou profissional</a></li>
                    <!--li><a href="minha-conta/empresa">Sou empresa</a></li-->
                </ul>
            </li>
        </c:if>
        <c:if test="${fn:contains(currenturl, 'profissional')}">
            <li>
                <a class='dropdown-trigger btn' href='#' data-target='dropdown-profissional'>SOU PROFISSIONAL<i class="tiny material-icons right">arrow_drop_down</i></a>
                <ul id='dropdown-profissional' class='dropdown-content'>
                    <li><a href="minha-conta/cliente">Sou Cliente</a></li>
                    <!--li><a href="minha-conta/empresa">Sou empresa</a></li-->
                </ul>
            </li>
        </c:if>

        <c:if test="${fn:contains(currenturl, 'empresa')}">
            <!--li>
                <a class='dropdown-trigger btn' href='#' data-target='dropdown-empresa'>SOU EMPRESA<i class="tiny material-icons right">arrow_drop_down</i></a>
                <ul id='dropdown-empresa' class='dropdown-content'>
                    <li><a href="minha-conta/cliente">Sou Cliente</a></li>
                    <li><a href="minha-conta/profissional">Sou profissional</a></li>
                </ul>
            </li-->
        </c:if>
        <form action="logout" method="post" hidden>
            <button type="submit" hidden id="my-button"></button>
        </form>

        <li>
            <a class='dropdown-trigger btn truncate' href='#' data-target='dropdown-perfil'>${individualInfo.name}<i class="tiny material-icons right">arrow_drop_down</i></a>
            <ul id='dropdown-perfil' class='dropdown-content'>
                <li><a href="/servicebook/minha-conta/empresa">Minha Conta</a></li>
                <li><a href="/servicebook/minha-conta/perfil">Meu perfil</a></li>
                <li class="divider" tabindex="-1"></li>
                <li>
                    <a onclick="document.getElementById('my-button').click()">Sair</a>
                </li>
            </ul>
        </li>

        <c:if test="${empty eventsse}">
            <li id="li-notifications">
                <a onclick="M.toast({html: 'Não há notificação!'})" class='dropdown-trigger btn eventSize' href='#' data-target='dropdown3'><i class="material-icons">notifications</i></a>
                <ul id='dropdown3' class='dropdown-content'>
                </ul>
            </li>
        </c:if>

        <c:if test="${not empty eventsse}">
            <li id="li-notifications">
                <a class='dropdown-trigger btn eventSize' href='#' data-target='dropdown4'><i class="material-icons">notifications</i></a>
                <ul id='dropdown4' class='dropdown-content'>
                    <c:forEach items="${eventsse}" var="e">
                        <li>
                            <div class="card">
                                <div class="card-content">
                                    <h5><c:out value="${e.message}"> </c:out></h5>
                                    <p><c:out value="${e.fromName}"> </c:out> para o serviço <c:out value="${e.descriptionServ}"> </c:out></p>
                                </div>
                                <div class="card-action">
                                    <form action="sse/delete/${e.id}" method="post">
                                        <input type="hidden" name="_method" value="DELETE"/>
                                        <button type="submit" class="waves-effect waves-light btn">ok</button>
                                    </form>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </li>
        </c:if>
    </ul>
</div>