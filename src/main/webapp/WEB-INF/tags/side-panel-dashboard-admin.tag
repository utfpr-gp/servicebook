<%@ tag description="Template para painel lateral" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@ attribute name="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" %>
<%@ attribute name="followdto" type="br.edu.utfpr.servicebook.model.dto.FollowsDTO" %>

<c:set var="currenturl" value="${requestScope['javax.servlet.forward.request_uri']}"/>

<div class="col s12" style="padding-left: 0" id="area-perfil">
    <div class="row center primary-background-color area-perfil no-margin" >

        <!-- Foto do perfil -->
        <div class="col s12 center">
            <c:if test="${userInfo.profilePicture == null}">
                <svg class="icon-person" style="width:250px;height:250px" viewBox="0 0 24 24">
                    <path class="dark-color-icon"
                          d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z"/>
                </svg>
            </c:if>

            <c:if test="${userInfo.profilePicture != null}">
                <div class="row">
                    <img src="${userInfo.profilePicture}" alt="Profissional - Imagem de perfil."
                         class="col s10 offset-s1 responsive-img">
                </div>
            </c:if>
        </div>
        <!-- Foto do perfil -->
    </div>

    <!-- Nome da empresa -->
    <div class="row secondary-background-color no-margin">
        <div class="col s12">
            <h5 class="name-header no-margin center white-text">
                <strong>${userInfo.name}</strong>
            </h5>
        </div>
    </div>
    <!-- Fim nome da empresa -->

    <!-- Filtragem -->
    <div class="row">
        <div class="col s12 no-margin no-padding">
            <p class="header-verification secondary-color-text center">FILTRAGEM</p>
            <div class="col s12">
                <form action="a/dashboard/q" method="get">
                    <h6>Data do serviço</h6>
                    <div class="input-field col s12">
                        <select name="startDate" id="select-status" class="white-text mt-1 no-padding center" style="padding: 0 !important;">
                            <c:choose>
                                <c:when  test="${not empty setMonth}">
                                    <option value="${setMonth}" disabled selected>${jobRequestFilterParam}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" disabled selected>Mês / Ano</option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="entry" items="${months}">
                                <option value="${entry.key}">${entry.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <h6>Especialidade do usuário</h6>
                    <div class="input-field col s12">
                        <select name="expertiseId" id="select-professional" class="white-text mt-1 no-padding center" style="padding: 0 !important;">
                            <c:choose>
                                <c:when  test="${not empty setExpertise}">
                                    <option value="${setExpertise}" disabled selected>${userFilterParam}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="" disabled selected>Qualquer especialidade</option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="expertise" items="${expertises}">
                                <option value="${expertise.id}">${expertise.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button class="waves-effect waves-light btn col s12 mt-1 center" type="submit">Filtrar</button>
                    <a class="waves-effect waves-light btn grey col s12 mt-1 center" href="a/dashboard">Limpar</a>
                </form>
            </div>
        </div>
    </div>
    <!-- Fim filtragem -->

    <!-- Links de cadastros -->
    <div class="row">
        <div class="col s12 no-margin no-padding">
            <p class="header-verification secondary-color-text center">CADASTROS</p>
            <div class="col s12">
                <div class="collection">
                    <a href="a/categorias" class="collection-item primary-color-text">CATEGORIA</a>
                    <a href="a/especialidades" class="collection-item primary-color-text">ESPECIALIDADE</a>
                    <a href="a/servicos" class="collection-item primary-color-text">SERVIÇO</a>
                    <a href="a/cidades" class="collection-item primary-color-text">CIDADE</a>
                </div>
            </div>
        </div>
    </div>
    <!-- Fim links de cadastros -->
</div>

