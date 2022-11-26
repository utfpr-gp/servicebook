<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@taglib
prefix="t" tagdir="/WEB-INF/tags"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Detalhes do profissional">
  <jsp:body>
    <main>
        <div class="blue lighten-1">
          <div class="section no-pad-bot">
            <div class="container">
              <div class="row center-align">
                <c:choose>
                  <c:when test="${jobCandidate.profilePicture != null}">
                    <img src="${jobCandidate.profilePicture}" class="avatar" alt="Foto de perfil">
                  </c:when>
                  <c:otherwise>
                    <img src="assets/resources/images/no-photo.png" class="avatar" alt="Sem foto de perfil">
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="row">
                <div class="col s6">
                  <c:forEach var="star" begin="1" end="5">
                    <c:if test="${star <= jobCandidate.rating}">
                      <i class="material-icons yellow-text small">star</i>
                    </c:if>
                    <c:if test="${star > jobCandidate.rating}">
                      <i class="material-icons yellow-text small">star_border</i>
                    </c:if>
                  </c:forEach>
                </div>
                <div class="col s6">
                  <div class="right check-circle-candidate">
                    <i class="material-icons green-text darken-4-text">check_circle</i>
                  </div>
                </div>
              </div>
            </div>
          </div>
      </div>
      <div class="tertiary-background-color white-text center-align no-margin">
          <p class="upper-case job-details-professional-name">

            ${jobCandidate.name}
          </p>
      </div>

      <div class="container">
          <div class="section">
            <div class="row center-align">
              <div class="col s6">
                <i class="left material-icons">email</i>
                <span class="left">
                  ${jobCandidate.email}
                </span>
              </div>
              <div class="col s6">
                <i class="left material-icons">local_phone</i>
                <span class="left">
                  ${jobCandidate.phoneNumber}
                </span>
              </div>
            </div>
              <div class="row center-align">
                  <p class="contact-item center dark-color-text">${jobCandidate.description}</p>
              </div>
              <div class="row center-align">
                  <p class="contact-item center dark-color-text">Entre em contato com o profissional para solicitar um orçamento.</p>
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

              <div class="row center-align">
                <p class="contact-item center dark-color-text">Ative solicitado realização do serviço</p>
                <button class="btn">Para Fazer</button>
              </div>
              <div class="row center-align">
              </div>
          </div>
      </div>
    </main>
  </jsp:body>
</t:client>