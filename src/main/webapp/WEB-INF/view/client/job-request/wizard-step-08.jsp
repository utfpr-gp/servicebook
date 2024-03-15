<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template title="Etapa 08">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty errors}">
                    <div class="card-panel red">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>

                <div class="section">
                    <div>
                        <h4 class="center"><strong>Estes ${professionalsAmount} profissionais tem a especialidade que procura!</strong></h4>
                        <h5 class="center">Para ter acesso a lista completa de profissionais deste especialidade ou melhor, para receber o contato de apenas profissionais interessados e disponíveis para a data especificada, podendo você verificar a reputação dos mesmos e experiência, solicitar orçamento e por fim, avaliar o serviço prestado.</h5>
                        <div class="container">
                            <div class="section">
                                <div class="row">
                                    <div class="col s12">
                                        <div class="row">
                                            <c:if test="${not empty professionals}">
                                                <c:forEach var="professional" items="${professionals}">
                                                    <div class="col s12 m6">
                                                        <div class="card-panel card-result blue lighten-1 white-text">
                                                            <div class="card-body">
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
                                                                <div class="row center-align">
                                                                    <h5 class="truncate tooltipped" data-position="bottom" data-tooltip="${professional.name}">${professional.name}</h5>
                                                                    <div class="divider"></div>
                                                                </div>
                                                                <div class="row center-align">
                                                                    <c:forEach var="expertise" items="${professional.expertises}">
                                                                        <div class="col expertise-label">${expertise.name}</div>
                                                                    </c:forEach>
                                                                </div>
                                                            </div>
                                                            <div class="row center-align ">
                                                                <a href="profissionais/detalhes/${professional.id}" class="waves-effect waves-light btn-large white blue-text text-lighten-1"><strong>Ver perfil</strong></a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${empty professionals}">
                                                <div class="container">
                                                    <div class="row">
                                                        <div class="col s12 spacing-buttons     ">
                                                            <div class="none-profission center">
                                                                <i class="material-icons large"> sentiment_dissatisfied </i>
                                                                <p class="center text-form-dados">
                                                                    Nenhum profissional encontrado!
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <sec:authorize access="!isAuthenticated()">
                        <div>
                            <div class="row center-align">
                                <p> LOGIN PARA EFETIVAR O SEU PEDIDO E RECEBER OS CONTATOS DOS PROFISSIONAIS!</p>
                                <a class="waves-effect waves-light btn" href="login">Entrar</a>
                            </div>

                            <div class="divider"></div>

                            <div class="row center-align">
                                <p>VOCÊ AINDA NÃO TEM UMA CONTA?</p>
                                <a class="waves-effect waves-light btn" href="cadastrar-se">Cadastrar-se</a>
                            </div>
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </main>

    </jsp:body>
</t:template>
