<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Início">
    <jsp:body>

        <t:banner></t:banner>

        <t:search-bar></t:search-bar>

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

        <script>
            $(document).ready(function(){
                $('.tooltipped').tooltip();
            });
        </script>
    </jsp:body>
</t:visitor>

