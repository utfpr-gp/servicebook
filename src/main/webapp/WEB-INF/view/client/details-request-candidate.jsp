<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@taglib
prefix="t" tagdir="/WEB-INF/tags"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Detalhes do candidato">
  <jsp:body>
    <main>
        <div class="blue lighten-1">
          <div class="section no-pad-bot">
            <div class="container">
              <div class="row center-align">
                <c:choose>
                  <c:when test="${jobCandidate.getIndividual().profilePicture != null}">
                    <img src="${jobCandidate.getIndividual().profilePicture}" class="avatar" alt="Foto de perfil">
                  </c:when>
                  <c:otherwise>
                    <img src="assets/resources/images/no-photo.png" class="avatar" alt="Sem foto de perfil">
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="row">
                <div class="col s6">
                  <c:forEach var="star" begin="1" end="5">
                    <c:if test="${star <= jobCandidate.getIndividual().rating}">
                      <i class="material-icons yellow-text small">star</i>
                    </c:if>
                    <c:if test="${star > jobCandidate.getIndividual().rating}">
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
            
            ${jobCandidate.getIndividual().name}
          </p>
      </div>

      <div class="container">
          <div class="section">
            <div class="row center-align">
              <div class="col s6">
                <i class="left material-icons">email</i>
                <span class="left">
                  ${jobCandidate.getIndividual().email}
                </span>
              </div>
              <div class="col s6">
                <i class="left material-icons">local_phone</i>
                <span class="left">
                  ${jobCandidate.getIndividual().phoneNumber}
                </span>
              </div>
            </div>
              <div class="row center-align">
                  <p class="contact-item center dark-color-text">${jobCandidate.getIndividual().description}</p>
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

              <div>
                <p class="contact-item center dark-color-text">Realizar serviço com este candidato?</p>
                <div class="center">
                  <div class="switch">
                  <label> Não
                    <input type="checkbox" id="botao">
                    <span class="lever"></span>
                    Sim</label>
                  </div>
                </div>
              </div>

              <div class="row center-align">
                <p class="contact-item center dark-color-text">Solicitar ou cancelar orçamento</p>
                <form action="minha-conta/cliente/marcar-como-orcamento/${jobCandidate.getJobRequest().id}/${jobCandidate.getIndividual().id}" method="post">
                  <input type="hidden" name="_method" value="PATCH"/>
                    <button type="submit" class="btn">
                      <c:choose>
                        <c:when test="${jobCandidate.chosenByBudget}">
                          Cancelar orçamento
                        </c:when>
                        <c:otherwise>
                          Marcar para orçamento
                        </c:otherwise>
                      </c:choose>
                    </button>
                </form>
              </div>
              <div class="row center-align">
              </div>
          </div>
      </div>

    </main>
  </jsp:body>
</t:client>

<script>
  $('.lever').click(function (){
    if(document.getElementById("botao").value == true){
      $.post("contratacao/${jobCandidate.getIndividual().id}").done(function(){
        swal({
          title: "Deu certo!",
          text: "${jobCandidate.getIndividual().name} Solicitado para relizar serviço",
          icon: "success",
        });
      });
    }
  });
</script>
