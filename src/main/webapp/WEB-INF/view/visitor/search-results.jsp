<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Início">
    <jsp:body>

        <div id="index-banner" class="parallax-container">
            <div class="section no-pad-bot">
                <div class="container">
                    <br><br>
                    <h3 class="header center logo-text logo-text-parallax">ServiceBook</h3>
                    <c:if test="${cities.size() > 1}">
                        <div class="row center">
                            <div class="col s10 offset-s1 m4 offset-m4  input-field">
                                <select id="select-city" class="white-text select-city">
                                    <c:forEach var="city" items="${cities}">
                                        <option value="${city.image}">${city.name} - ${city.state.uf}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </c:if>
                    <h4 class="header center">O MELHOR PROFISSIONAL QUE VOCÊ PRECISA ESTÁ AQUI!</h4>
                    <h4 class="header center">São + de 3000 profissionais cadastrados!</h4>
                </div>
            </div>
            <div class="parallax">
                <c:if test="${not empty cities}">
                    <img id="img-city" src="${cities[0].image}" alt="Imagem das cidades">
                </c:if>
                <c:if test="${empty cities}">
                    <img id="img-city" src="assets/resources/images/default.jpeg" alt="Imagem das cidades">
                </c:if>
            </div>
        </div>

        <t:search-bar></t:search-bar>

        <div class="container">
            <div class="section">
                <div class="row">
                    <div class="col s12">

                        <div class="row">

                            <c:forEach var="professional" items="${professionals}">
                                <div class="col s12 m6">
                                    <div class="card-panel card-result blue lighten-1 white-text">
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
                                            <h5>${professional.name}</h5>
                                            <div class="divider"></div>
                                        </div>
                                        <div class="row center-align">
                                            <div class="col expertise-label">pedreiro</div>
                                            <div class="col expertise-label">encanador </div>
                                        </div>
                                        <div class="row center-align ">
                                            <a href="detalhes-do-profissional/${professional.id}" class="waves-effect waves-light btn-large white blue-text text-lighten-1"><strong>Ver perfil</strong></a>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </jsp:body>
</t:visitor>
