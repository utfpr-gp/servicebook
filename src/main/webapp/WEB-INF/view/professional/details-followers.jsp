<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:professional title="Meus Seguidores">
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


                    <div class="col s12 m6 tertiary-color-text description-job  text-info-request">
                      <h3>Meus seguidores</h3>
                    </div>


                    </div>
                    <c:forEach var="follows" items="${individuallist}">
                      <div class="row">
                          ${follows.gender}
                      </div>

<%--                      <div class="col s12 l6 xl4">--%>
<%--                        <div class="card-panel card-candidate">--%>
<%--                          <div class="row no-margin">--%>
<%--                            <div class="col s12 icons-area-request center padding">--%>
<%--                              <div class="row">--%>
<%--                                <div class="col s6 center">--%>
<%--                                  <div class="left star-icons candidate dark-color-text">--%>
<%--                                    <c:forEach var="star" begin="1" end="5">--%>
<%--                                      <c:if test="${star <= individual.get().rating}">--%>
<%--                                        <i class="material-icons dark-text small">star</i>--%>
<%--                                      </c:if>--%>
<%--                                      <c:if test="${star > jobCandidate.individual.rating}">--%>
<%--                                        <i class="material-icons dark-text small">star_border</i>--%>
<%--                                      </c:if>--%>
<%--                                    </c:forEach>--%>
<%--                                  </div>--%>
<%--                                </div>--%>
<%--                                <div class="col s6">--%>
<%--                                  <div class="col s6 left">--%>
<%--                                    <div class="right check-circle-candidate">--%>
<%--                                      <i class="material-icons green-text darken-3-text">check_circle</i>--%>
<%--                                    </div>--%>
<%--                                  </div>--%>
<%--                                  <div class="col s6 right">--%>
<%--&lt;%&ndash;                                    FAZER  VALIDAÇÃO SE ESTA SEGUINDO CORRETO&ndash;%&gt;--%>
<%--                                    <c:if test="${true}">--%>
<%--                                      <div class="right check-circle-candidate">--%>
<%--                                        <form action="follow/subscribe/${1}/${4}" method="post">--%>
<%--                                            <input type="hidden" name="_method" value="POST"/>--%>
<%--                                            <button alt="seguir" class="waves-effect waves-light btn"><i alt="seguir" class="material-icons black-text small">thumb_up</i></button>--%>
<%--                                          </form>--%>
<%--                                      </div>--%>
<%--                                      </c:if>--%>
<%--                                      <c:if test="${false}">--%>
<%--                                        <div class="right check-circle-candidate">--%>
<%--                                          <form action="follow/unfollow/${1}/${3}" method="post">--%>
<%--                                            <input type="hidden" name="_method" value="DELETE"/>--%>
<%--                                            <button alt="Unfollow" class="waves-effect waves-light btn"><i alt="Unfollow"  class="material-icons black-text small">thumb_down</i></button>--%>
<%--                                          </form>--%>
<%--                                        </div>--%>
<%--                                      </c:if>--%>
<%--                                  </div>--%>
<%--                                </div>--%>
<%--                              </div>--%>
<%--                            </div>--%>
<%--                            <div class="col s12 center">--%>
<%--                              <c:if test="${jobCandidate.individual.profilePicture == null}">--%>
<%--                                <svg style="width:220px;height:220px" viewBox="0 0 24 24">--%>
<%--                                  <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />--%>
<%--                                </svg>--%>
<%--                              </c:if>--%>
<%--                              <c:if test="${jobCandidate.individual.profilePicture != null}">--%>
<%--                                <div class="row">--%>
<%--                                  <img src="${jobCandidate.individual.profilePicture}"--%>
<%--                                  alt="Profissional - Imagem de perfil."--%>
<%--                                  style="width:200px;height:200px">--%>
<%--                                </div>--%>
<%--                              </c:if>--%>
<%--                            </div>--%>
<%--                            <div class="col s12">--%>
<%--                              <div class="center title-card-resquest">--%>
<%--                                <p class="truncate">${jobCandidate.individual.name}</p>--%>
<%--                              </div>--%>
<%--                              <p class="contact-item center-block dark-color-text">--%>
<%--                                <c:if test="${jobCandidate.individual.emailVerified}">--%>
<%--                                  <i class="medium material-icons green-text tooltipped middle truncate" data-position="top"--%>
<%--                                    data-tooltip="Email verificado.">email </i>${jobCandidate.individual.email}--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${!jobCandidate.individual.emailVerified}">--%>
<%--                                  <i class="medium material-icons gray-text tooltipped middle" data-position="top"--%>
<%--                                    data-tooltip="Email não verificado.">email</i> ${jobCandidate.individual.email}--%>
<%--                                </c:if>--%>
<%--                              </p>--%>
<%--                              <p class="contact-item center-block dark-color-text">--%>
<%--                                <c:if test="${jobCandidate.individual.phoneVerified}">--%>
<%--                                  <i class="medium material-icons green-text tooltipped middle" data-position="top"--%>
<%--                                        data-tooltip="Telefone verificado.">phone </i>${jobCandidate.individual.phoneNumber}--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${!jobCandidate.individual.phoneVerified}">--%>
<%--                                  <i class="medium material-icons gray-text tooltipped middle" data-position="top"--%>
<%--                                        data-tooltip="Telefone não verificado.">phone</i> ${jobCandidate.individual.phoneNumber}--%>
<%--                                </c:if>--%>
<%--                              </p>--%>
<%--                              <div class="center">--%>
<%--                                <p><a class="tertiary-color-text " href="minha-conta/cliente/meus-pedidos/${jobRequest.id}/detalhes/${jobCandidate.id}">Detalhes</a></p>--%>
<%--                              </div>--%>
<%--                            </div>--%>
<%--                          </div>--%>
<%--                        </div>--%>
<%--                      </div>--%>
                    </c:forEach>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </main>
  </jsp:body>
</t:professional>
