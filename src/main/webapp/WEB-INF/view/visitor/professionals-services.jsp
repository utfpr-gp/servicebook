<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/visitor/visitor.css" rel="stylesheet">
</head>

<t:template title="Servicebook - Início">
    <jsp:body>
        <div class="container">
            <div class="section">
                <div class="row">
                    <div class="breadcrumbs">
                        <a href="${pageContext.request.contextPath}/">Início</a> &gt;
                        Serviços de ${expertise.name}
                    </div>
                    <c:if test="${not empty expertise}">

                        <div class="col s12" style="position: relative;">
                            <h4 class="white-text card-name-expertise"><img src="${expertise.pathIcon}"
                                                                            class="avatar-expertise"
                                                                            alt="Foto de especialidade">
                                <span class="span-name">${expertise.name}</span>
                            </h4>

                            <h4 class="blue-text card-name-service">${service}</h4>
                        </div>
                    </c:if>

                    <div class="col s12" style="margin-top: 30px">
                        <span class="left-align"
                              style="font-weight: bold; font-size: 1.5rem">${countProfessionals} RESULTADOS ENCONTRADOS</span>
                        <hr>
                    </div>

                    <div class="col s12 div_filter">
                        <span style="font-size: 1.2rem; margin: 0 50px 0 30px">Classificar por</span>

                        <label class="labels_filter">
                            <input name="group1" type="radio" />
                            <span class="blue-text ">Mais Relevantes</span>
                        </label>

                        <label class="labels_filter">
                            <input name="group1" type="radio" />
                            <span class="blue-text">Últimos Avaliados</span>
                        </label>

                        <label class="labels_filter">
                            <input name="group1" type="radio" />
                            <span class="blue-text">Últimos Cadastrados</span>
                        </label>

                        <label class="labels_filter">
                            <input name="group1" type="radio" checked />
                            <span class="blue-text">Popular</span>
                        </label>

                    </div>
                    <div class="col s12">

                        <div class="row">
                            <c:if test="${not empty professionalServiceOfferingDTOS}">
                                <c:forEach var="professional" items="${professionalServiceOfferingDTOS}">
                                    <div class="col s4 div_cards_services">
                                        <div class="card" style="margin: 0">
                                            <div class="label_duration">
                                                <span class=" white-text">${professional.user.address.neighborhood} - ${professional.user.address.city.state.uf}</span>
                                            </div>
                                            <div class="card-content">
                                                <div class="row center-align">
                                                    <c:choose>
                                                        <c:when test="${professional.user.profilePicture != null}">
                                                            <img src="${professional.user.profilePicture}"
                                                                 class="avatar" alt="Foto de perfil">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="assets/resources/images/no-photo.png"
                                                                 class="avatar" alt="Sem foto de perfil">
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <div class="col s12">
                                                        <h6 class="truncate">${professional.user.name}</h6>

                                                        <small> ${professional.description} </small>
                                                    </div>
                                                    <div class="col s12 button-profile">
                                                        <a href="profissionais/detalhes/${professional.user.id}"
                                                           class="waves-effect waves-light btn-small white-text text-lighten-1"><strong>Ver
                                                            perfil</strong></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="label_price">
                                                <c:if test="${empty professional.price}">
                                                    <button id="combine" type="button"
                                                            class="waves-effect waves-light btn btn_combined">
                                                        À COMBINAR
                                                    </button>
                                                </c:if>

                                                <c:if test="${!empty professional.price}">
                                                    <p class=" white-text" style="margin: 0"><fmt:formatNumber
                                                            value="${professional.price/100}" type="currency"/></p>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>

                                </c:forEach>
                            </c:if>
                            <c:if test="${empty professionalServiceOfferingDTOS}">
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
                    <c:if test="${logged == false}">
                        <div class="container">
                            <p class="center-align">Entre ou cadastre-se para ter acesso ao demais profissionais</p>
                        </div>
                    </c:if>
                    <c:if test="${logged == true}">
                        <div class="container col s12 center-align">
                            <t:pagination pagination="${pagination}" isParam="${isParam}"></t:pagination>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

    </jsp:body>
</t:template>

