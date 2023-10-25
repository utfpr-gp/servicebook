<%@tag description="Template inicial" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="title" %>
<%@ attribute name="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="currenturl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="right">
    <ul class="right nav-btn">
        <li class="hide-on-med-and-down">
            <a class='dropdown-trigger btn' href='#' data-target='dropdown-cliente'>SOU PROFISSIONAL<i
                    class="tiny material-icons right">arrow_drop_down</i></a>
            <ul id='dropdown-cliente' class='dropdown-content'>
                <li><a href="cliente">Sou cliente</a></li>
            </ul>
        </li>

        <c:if test="${empty eventsse}">
            <li id="li-notifications" class="hide-on-med-and-down">
                <a onclick="M.toast({html: 'Não há notificação!'})" class='dropdown-trigger eventSize' href='#'
                   data-target='dropdown3'><i class="material-icons">notifications</i></a>
                <ul id='dropdown3' class='dropdown-content'>
                </ul>
            </li>
        </c:if>

        <c:if test="${not empty eventsse}">
            <li id="li-notifications" class="hide-on-med-and-down">
                <a class='dropdown-trigger eventSize' href='#' data-target='dropdown4'><i class="material-icons">notifications</i></a>
                <ul id='dropdown4' class='dropdown-content'>
                    <c:forEach items="${eventsse}" var="e">
                        <li>
                            <div class="card">
                                <div class="card-content">
                                    <h5><c:out value="${e.message}"> </c:out></h5>
                                    <p><c:out value="${e.fromName}"> </c:out> para o serviço <c:out
                                            value="${e.descriptionServ}"> </c:out></p>
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

        <li>
            <a class='dropdown-trigger circle truncate' href='#' data-target='dropdown-perfil'>
                <img src="${userInfo.profilePicture}" class="responsive-img" style="width: 50px; padding: 15px 5px 0px 5px">
            </a>
            <ul id='dropdown-perfil' class='dropdown-content dropdown-content-width'>
                <li style="background-color: #f0f0f0">
                    <div class="center-align">
                        <img src="${userInfo.profilePicture}" class="responsive-img" style="width: 50px; padding: 15px 5px 0px 5px">
                    </div>
                    <span class="center upper-case disabled">${userInfo.name}</span>
                </li>
                <li class="divider" tabindex="-1"></li>
                <li><a href="/servicebook/minha-conta/profissional/meus-anuncios">Meus Anúncios</a></li>
                <li><a href="/servicebook/minha-conta/profissional">Minha Conta</a></li>
                <li><a href="/servicebook/minha-conta/perfil">Meu Perfil</a></li>
                <li class="divider" tabindex="-1"></li>
                <li><a href="logout">Sair</a></li>
            </ul>
        </li>
    </ul>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        let elems = document.querySelectorAll('.dropdown-trigger');
        console.log((elems))
        let t = M.Dropdown.init(elems, {
            coverTrigger: false, constrainWidth: false
        });
    });
</script>