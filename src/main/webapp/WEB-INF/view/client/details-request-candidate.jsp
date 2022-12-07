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

            <c:if test="${jobCandidate.getJobRequest().status == 'AVAILABLE'}">
              <div class="row center-align">
                  <p class="contact-item center dark-color-text">Entre em contato com o profissional para solicitar um orçamento.</p>
              </div>
            </c:if>
            <c:if test="${jobCandidate.getJobRequest().status != 'AVAILABLE'}">
              <div class="row center-align">
                <p class="contact-item center dark-color-text">Entre em contato com o profissional.</p>
              </div>
            </c:if>

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

            <c:if test="${jobCandidate.getJobRequest().status == 'AVAILABLE'}">
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
            </c:if>

            <c:if test="${jobCandidate.getJobRequest().status == 'DOING'}">
              <div class="row center-align">
                <h5>Finalização do serviço:</h5>

                <form id="closeJob" method="post">
                  <label>
                    <input id="confirmCloseJob" name="isToCloseJob" type="radio" value="true"/>
                    <span>Serviço realizado</span>
                  </label>

                  <label>
                    <input id="notConfirmCloseJob" name="isToCloseJob" type="radio" value="false"/>
                    <span>Serviço não realizado</span>
                  </label>

                  <div class="row">
                    <a id="closeJobButton" href="#modal-close-job" class="waves-effect waves-light btn spacing-buttons modal-trigger">Salvar</a>
                  </div>
                </form>

                <div id="modal-close-job" class="modal">
                  <div class="modal-content">
                    <form action="minha-conta/cliente/marcar-como-finalizado/${jobCandidate.getJobRequest().id}/${jobCandidate.getIndividual().id}" method="post">
                      <input type="hidden" name="_method" value="PATCH"/>
                      <input id="isQuit" name="isQuit" type="hidden" value=""/>

                      <div class="modal-content" id="modal-content-confirm-hired">
                        <h5>Você tem certeza que deseja confirmar essa ação?</h5>
                      </div>

                      <div class="modal-footer">
                        <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                      </div>
                    </form>
                  </div>
                </div>
              </div>
            </c:if>

            <c:if test="${isAvailableToRating && jobCandidate.getJobRequest().status == 'CLOSED'}">
              <div class="row center-align">
                <h5>Avaliação:</h5>

                <a href="javascript:void(0)" onclick="Avaliar(1)">
                  <img width="50px" src="assets/resources/images/star0.png" id="s1"></a>

                <a href="javascript:void(0)" onclick="Avaliar(2)">
                  <img width="50px" src="assets/resources/images/star0.png" id="s2"></a>

                <a href="javascript:void(0)" onclick="Avaliar(3)">
                  <img width="50px" src="assets/resources/images/star0.png" id="s3"></a>

                <a href="javascript:void(0)" onclick="Avaliar(4)">
                  <img width="50px" src="assets/resources/images/star0.png" id="s4"></a>

                <a href="javascript:void(0)" onclick="Avaliar(5)">
                  <img width="50px" src="assets/resources/images/star0.png" id="s5"></a>

                <div class="row" style="margin-top: 15px">
                  <div class="input-field col s12 offset-m2 m8">
                    <textarea id="textarea1" class="materialize-textarea"></textarea>
                    <label for="textarea1">Deixe um comentário</label>
                  </div>

                  <div class="col s12">
                    <button class="waves-effect waves-light btn spacing-buttons">Salvar</button>
                  </div>
                </div>
              </div>
            </c:if>
          </div>
      </div>

    </main>
  </jsp:body>
</t:client>


<script src="assets/resources/scripts/client-rating-job-functions.js"></script>
<script>
  $(document).ready(function() {
    $('.modal').modal({
      onOpenEnd: function (modal, trigger){
        var url = $(trigger).data('url');
        var name = $(trigger).data('name');

        modal = $(modal);
        var form = modal.find('form');
        form.attr('action', url);
        modal.find('#strong-name').text(name);
      }

    });

    $('#closeJobButton').attr("disabled", "disabled");

    $('#confirmCloseJob').click(function () {
      $('#closeJobButton').removeAttr("disabled");
    });

    $('#notConfirmCloseJob').click(function () {
      $('#closeJobButton').removeAttr("disabled");
    });

    $('#closeJobButton').click(function () {
      if ($("#confirmCloseJob").is(":checked")) {
        console.log('trueeeee');
        $("#isQuit").val(true);
      } else {
        console.log('falseeeee');
        $("#isQuit").val(false);
      }
    });
  });
</script>