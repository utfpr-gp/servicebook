<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Detalhes da Solicitação">
  <jsp:body>
    <main>
      <div class="row">
        <t:side-panel individual="${user}"></t:side-panel>
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
                    <c:if test="${jobRequest.status != 'BUDGET'}">
                      <div class="col s12 m6 l3">
                        <div class="center">
                          <a href="#modal-close" class="spacing-buttons waves-effect waves-light btn modal-trigger">Parar de receber propostas</a>
                        </div>
                      </div>
                    </c:if>
                    <div class="col s12 m6 l3">
                      <div class="center">
                        <a href="#!" class="spacing-buttons waves-effect waves-light btn">Excluir o pedido</a>
                      </div>
                    </div>
                    <div class="col s12 tertiary-color-text description-orcamento text-info-request">
                      <hr class="hr-request-area">
                      <p>Entre em contato com um ou mais profissionais que se interessaram em realizar o serviço para marcar um orçamento.</p>
                      <c:if test="${candidates.size() > 1}">
                          <p>${candidates.size()} profissionais responderam a sua solicitação:</p>
                      </c:if>
                      <c:if test="${candidates.size() == 1}">
                          <p>${candidates.size()} profissional respondeu a sua solicitação:</p>
                      </c:if>
                    </div>
                    <c:forEach var="jobCandidate" items="${candidates}">
                      <div class="col s12 l4">
                        <div class="card-panel card-candidate">
                          <div class="row">
                            <div class="col s12 icons-area-request center padding">
                              <div class="row">
                                <div class="col s6 center">
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
                                  <div class="right check-circle-candidate">
                                    <i class="material-icons green-text darken-3-text">check_circle</i>
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
                                <p><a class="tertiary-color-text " href="minha-conta/cliente/meus-pedidos/${jobCandidate.id}/detalhes">Detalhes</a></p>
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
    </main>
  </jsp:body>
</t:client>
