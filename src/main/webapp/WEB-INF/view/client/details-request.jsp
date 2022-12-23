<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Detalhes da Solicitação">
  <jsp:body>
    <main>
      <div class="row">
        <t:side-panel individualInfo="${user}"></t:side-panel>
        <div class="col m10 offset-m1 l9">
          <a id="show-area-perfil" class="hide-on-large-only show-area-perfil waves-effect waves-light btn btn-floating grey darken-3 z-depth-A">
            <i class="material-icons">compare_arrows</i>
          </a>
          <div class="row">
            <div class="container">
              <div class="col s12">
                <div class="section">
                  <div class="row">
                    <c:if test="${empty candidates}">
                      <div class="col s12 center">
                        <i class="material-icons large"> sentiment_dissatisfied </i>
                        <h2 class="secondary-color-text"> Não há nenhum candidato.</h2>
                      </div>
                    </c:if>
                    <c:if test="${not empty candidates}">
                      <div class="col s12">
                        <h2 class="secondary-color-text spacing-standard tertiary-color-text">Escolha um ${expertise.name}!</h2>
                      </div>
                     </c:if>
                    <div class="col s12 m6 tertiary-color-text description-job  text-info-request">
                      <p>${jobRequest.description}</p>
                      <p>Pedido expedido em ${jobRequest.dateExpired}</p>
                    </div>

                    <c:if test="${jobRequest.status == 'AVAILABLE'}">
                      <div class="col s12 m6 l3">
                        <div class="center">
                          <a href="#modal-close" class="spacing-buttons waves-effect waves-light btn modal-trigger">Parar de receber propostas</a>
                        </div>
                      </div>
                    </c:if>

                    <c:if test="${jobRequest.status == 'DOING'}">
                      <div class="col s12 m6 l3">
                        <div class="center">
                          <a id="closeJobButton" href="#modal-close-job" class="spacing-buttons waves-effect waves-light btn modal-trigger">PEDIDO FINALIZADO</a>
                        </div>
                      </div>

                      <div id="modal-close-job" class="modal">
                        <div class="modal-content">
                          <form action="minha-conta/cliente/informa-finalizado/${jobRequest.id}" method="post">
                            <input type="hidden" name="_method" value="PATCH"/>

                            <div class="modal-content" id="modal-content-confirm-hired">
                              <h5>Finalização do pedido</h5>

                              <label>
                                <input id="confirmCloseJob" name="isQuit" type="radio" value="true"/>
                                <span>Serviço foi finalizado</span>
                              </label>

                              <label>
                                <input id="notConfirmCloseJob" name="isQuit" type="radio" value="false"/>
                                <span>Serviço não foi finalizado</span>
                              </label>
                            </div>

                            <div class="modal-footer">
                              <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                              <button type="submit" disabled class="modal-close btn waves-effect waves-light gray" id="confirm-job-modal-button">Sim</button>
                            </div>
                          </form>
                        </div>
                      </div>
                    </c:if>

                    <div class="col s12 m6 l3">
                      <div class="center">
                        <a
                          href="#modal-delete"
                          data-url="${pageContext.request.contextPath}/minha-conta/cliente/desistir/${jobRequest.id}"
                          class="waves-effect waves-light btn spacing-buttons red modal-trigger"
                          >Excluir</a
                        >
                      </div>
                    </div>

                    <div class="col s12 m6 right">
                      <div class="center">
                        <a href="minha-conta/cliente" class="spacing-buttons waves-effect waves-light btn">Voltar para solicitações</a>
                      </div>
                    </div>

                    <div class="col s12 tertiary-color-text description-orcamento text-info-request">
                      <hr class="hr-request-area">
                      <c:if test="${jobRequest.status == 'AVAILABLE'}">
                        <p>Entre em contato com um ou mais profissionais que se interessaram em realizar o serviço para marcar um orçamento.</p>
                        <c:if test="${candidates.size() > 1}">
                          <p>${candidates.size()} profissionais responderam a sua solicitação:</p>
                        </c:if>
                        <c:if test="${candidates.size() == 1}">
                          <p>${candidates.size()} profissional respondeu a sua solicitação:</p>
                        </c:if>
                      </c:if>
                      <c:if test="${jobRequest.status == 'BUDGET'}">
                        <p>Solicite e analise o(s) orçamento(s) para escolher o profissional que melhor atende a sua necessidade.</p>
                      </c:if>
                      <c:if test="${jobRequest.status == 'BUDGET'}">
                        <p>Aguarde a confirmação do profissional para realizar o serviço.</p>
                      </c:if>
                      <c:if test="${jobRequest.status == 'TO_DO'}">
                        <p>O profissional confirmou que realizará o serviço. Você poderá avaliar o profissional após a expiração da data combinada.</p>
                      </c:if>
                      <c:if test="${jobRequest.status == 'DOING'}">
                        <p>Conforme a data de agendamento, este é o profissional que está realizando o serviço.
                          Quando ele finalizar o serviço, informe o término e também faça a avaliação do profissional.</p>
                      </c:if>
                      <c:if test="${jobRequest.status == 'CLOSED'}">
                        <p>Este é o profissional que realizou o serviço.</p>
                      </c:if>

                    </div>
                    <c:forEach var="jobCandidate" items="${candidates}">
                      <div class="col s12 l6 xl4">
                        <div class="card-panel card-candidate">
                          <div class="row ${(jobCandidate.chosenByBudget) ? 'primary-background-color': ''} no-margin">
                            <div class="col s12 icons-area-request center padding">
                              <div class="row">
                                <div class="col s6">
                                  <div class="left star-icons candidate dark-color-text">
                                    <c:forEach var="star" begin="1" end="5">
                                      <c:if test="${star <= jobCandidate.individual.rating}">
                                        <i class="material-icons dark-text small">star</i>
                                      </c:if>
                                      <c:if test="${star > jobCandidate.individual.rating}">
                                        <i class="material-icons dark-text small">star_border</i>
                                      </c:if>
                                    </c:forEach>
                                  </div>
                                </div>
                                <div class="col s6">
                                  <div class="col s4 left">
                                    <div class="right check-circle-candidate">
                                      <i class="material-icons green-text darken-3-text">check_circle</i>
                                    </div>
                                  </div>
                                  <div class="col s8 right">
                                        <div class="left black-text">${jobCandidate.individual.followsAmount != 0 ? jobCandidate.individual.followsAmount : " "}</div>
                                        <div class="right"><i alt="seguir" class="material-icons black-text small">thumb_up</i></div>
                                  </div>
                                </div>
                              </div>
                            </div>
                            <div class="col s12 center">
                              <c:if test="${jobCandidate.individual.profilePicture == null}">
                                <svg style="width:220px;height:220px" viewBox="0 0 24 24">
                                  <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                </svg>
                              </c:if>
                              <c:if test="${jobCandidate.individual.profilePicture != null}">
                                <div class="row">
                                  <img src="${jobCandidate.individual.profilePicture}"
                                  alt="Profissional - Imagem de perfil."
                                  style="width:200px;height:200px">
                                </div>
                              </c:if>
                            </div>
                            <div class="col s12">
                              <div class="center title-card-resquest">
                                <p class="truncate">${jobCandidate.individual.name}</p>
                              </div>
                              <p class="contact-item center-block dark-color-text">
                                <c:if test="${jobCandidate.individual.emailVerified}">
                                  <i class="medium material-icons green-text tooltipped middle truncate" data-position="top"
                                    data-tooltip="Email verificado.">email </i>${jobCandidate.individual.email}
                                </c:if>
                                <c:if test="${!jobCandidate.individual.emailVerified}">
                                  <i class="medium material-icons gray-text tooltipped middle" data-position="top"
                                    data-tooltip="Email não verificado.">email</i> ${jobCandidate.individual.email}
                                </c:if>
                              </p>
                              <p class="contact-item center-block dark-color-text">
                                <c:if test="${jobCandidate.individual.phoneVerified}">
                                  <i class="medium material-icons green-text tooltipped middle" data-position="top"
                                        data-tooltip="Telefone verificado.">phone </i>${jobCandidate.individual.phoneNumber}
                                </c:if>
                                <c:if test="${!jobCandidate.individual.phoneVerified}">
                                  <i class="medium material-icons gray-text tooltipped middle" data-position="top"
                                        data-tooltip="Telefone não verificado.">phone</i> ${jobCandidate.individual.phoneNumber}
                                </c:if>
                              </p>
                              <div class="center">
                                <p><a class="tertiary-color-text " href="minha-conta/cliente/meus-pedidos/${jobRequest.id}/detalhes/${jobCandidate.id}">Detalhes</a></p>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="modal-close" class="modal">
        <div class="modal-content">
          <form action="${pageContext.request.contextPath}/minha-conta/meus-pedidos/encerra-pedido/${jobRequest.id}" method="post">
            <input type="hidden" name="_method" value="PATCH"/>
            <div class="modal-content">
              <h4>Você tem certeza que deseja encerrar o recebimento de propostas para este pedido? Não será possível reativar depois</h4>
            </div>
            <div class="modal-footer">
              <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
              <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
            </div>
          </form>
        </div>
      </div>
      <div id="modal-delete" class="modal">
        <div class="modal-content">
          <form action="${pageContext.request.contextPath}/minha-conta/cliente/desistir/${jobRequest.id}" method="post">
            <input type="hidden" name="_method" value="DELETE" />

            <div class="modal-content">
              <h4>
                Você tem certeza que deseja excluir? Entre em contato com o
                profissional informando sobre sua decisão!
              </h4>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="modal-close btn-flat waves-effect waves-light btn btn-gray"
              >
                Cancelar
              </button>
              <button
                type="submit"
                class="modal-close btn waves-effect waves-light gray"
              >
                Sim, excluir!
              </button>
            </div>
          </form>
        </div>
      </div>
    </main>
  </jsp:body>
</t:template>
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

        $('#confirmCloseJob').click(function (){
          $('#confirm-job-modal-button').attr('disabled', false);
        });

        $('#notConfirmCloseJob').click(function (){
          $('#confirm-job-modal-button').attr('disabled', false);
        });
      }
    });


  });
</script>
