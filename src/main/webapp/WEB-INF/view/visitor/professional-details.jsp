<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<head>
    <meta property="og:title" content="${professional.description}"/>
    <meta property="og:type" content="website"/>
    <meta property="og:description" content="SERVICEBOOK - O MELHOR PROFISSIONAL QUE VOCÊ PRECISA ESTÁ AQUI!"/>
    <meta property="og:image" content="${professional.profilePicture}"/>
    <meta property="og:site_name" content="Servicebook"/>

    <meta name="twitter:card" content="photo">
    <meta name="twitter:url" content="$(location).attr('href')">
    <meta name="twitter:title" content="${professional.description}">
    <meta name="twitter:description" content="SERVICEBOOK - O MELHOR PROFISSIONAL QUE VOCÊ PRECISA ESTÁ AQUI!">
    <meta name="twitter:image "content="${professional.profilePicture}">
</head>
<t:template title="Servicebook - Início">
    <jsp:body>
        <div id="" class="blue lighten-1">
            <div class="section no-pad-bot">
                <div class="container">
                    <div class="row center-align">
                        <c:choose>
                            <c:when test="${professional.profilePicture != null}">
                                <img src="${professional.profilePicture}" class="avatar" alt="Foto de perfil">
                            </c:when>
                            <c:otherwise>
                                <img src="assets/resources/images/no-photo.png" class="avatar" alt="Sem foto de perfil">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <sec:authorize access="isAuthenticated()">
            <div class="col s12 center">
                <c:if test="${!isFollow}">
                    <form method="post" id="follow-form">
                        <input type="hidden" name="professional" value="${professional.id}"/>
                        <input type="hidden" name="client" value="${client.id}"/>
                        <button alt="seguir" type="button"
                                class="waves-effect waves-light btn" id="follow-button">Seguir
                        </button>
                    </form>
                </c:if>
                <c:if test="${isFollow}">
                    <button type="button" data-professional="${professional.id}"
                            class="waves-effect waves-light btn"
                            id="unfollow-button">Deixar de Seguir
                    </button>
                </c:if>
            </div>
        </sec:authorize>

        <div class="tertiary-background-color white-text center-align">
            <h5 class="upper-case mb-1 mt-1">${professional.name}</h5>
        </div>

        <div class="container">
            <div class="section">
                <div class="row">
                    <c:forEach var="expertise" items="${professionalExpertises}">
                        <div class="col expertise-label">${expertise.name}</div>
                    </c:forEach>
                </div>

                <div class="row center-align">
                    <h5>Descrição geral do profissional</h5>
                    <p class="contact-item center dark-color-text">${professional.description}</p>
                </div>

                <div class="row center-align">
                    <a href="https://web.whatsapp.com/send?phone=55${professional.getOnlyNumbersFromPhone()}" target="_blank">
                        <div class="row waves-effect waves-light btn-large green accent-3 white-text">
                            <div class="col s2">
                                <img src="assets/resources/images/whatsapp-logo.png" class="whatsapp-icon mt-1" alt="">
                            </div>
                            <div class="col s10"><strong>Chamar no whatsapp</strong></div>
                        </div>
                    </a>
                </div>

                <sec:authorize access="!isAuthenticated()">
                    <div class="divider"></div>

                    <div class="row center-align">
                        <h4 class="center-align">ACESSE O <span class="facebook-color-text">SERVICEBOOK</span></h4>
                        PARA TER MAIORES INFORMAÇÕES ANTES DE ENTRAR EM CONTATO COM O PROFISSIONAL OU EMPRESA
                        </br>
                        <p class="primary-color-text">
                            HISTÓRICO DE SERVIÇOS REALIZADOS E AVALIAÇÕES COM COMENTÁRIOS FEITAS POR
                            CLIENTES REAIS
                        </p>
                        OU
                        </br>
                        PRINCIPALMENTE PARA SIMPLIFICAR A TAREFA DE ENCONTRAR O MELHOR PROFISSIONAL OU EMPRESA PARA REALIZAR O SEU SERVICO.
                        </br>
                        DE FORMA GRATUITA!
                        </br>
                        DESCREVA O SERVIÇO A SER REALIZADO E RECEBA AS INFORMAÇÕES DE CONTATO APENAS DE PROFISSIONAIS OU EMPRESAS COM
                        </br>
                        <p class="primary-color-text">
                            INTERESSE NO SERVIÇO, DISPONÍVEL PARA A DATA ESPECIFICADA E COM EXPERIÊNCIA NO TIPO DE
                            SERVIÇO QUE VOCÊ PRECISA!
                        </p>

                        <a class="waves-effect waves-light btn" href="login">Entrar</a>
                    </div>

                    <div class="divider"></div>

                    <div class="row center-align">
                        <p>VOCÊ AINDA NÃO TEM UMA CONTA?</p>
                        <a class="waves-effect waves-light btn" href="cadastrar-se">Cadastrar-se</a>
                    </div>
                </sec:authorize>
            </div>
        </div>

    </jsp:body>
</t:template>
<script type="text/javascript" src="https://platform-api.sharethis.com/js/sharethis.js#property=64931a737674a9001261149d&product=sticky-share-buttons&source=platform" async="async"></script>
<script src="assets/resources/scripts/follow-professional.js"></script>
