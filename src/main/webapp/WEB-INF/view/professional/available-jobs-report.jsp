<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@taglib prefix="t"
tagdir="/WEB-INF/tags" %>

<c:if test="${empty jobs}">
  <div class="container">
    <div class="row">
      <div class="col s12 spacing-buttons">
        <div class="none-profission">
          <p class="center text-form-dados">Nenhum pedido encontrado!</p>
          <p class="center">
            Um novo pedido pode chegar aqui a qualquer momento.
          </p>
        </div>
      </div>
    </div>
  </div>
</c:if>

<c:forEach var="job" items="${jobs}">
  <div class="container">
    <div class="row">
      <div class="col s12 spacing-buttons">
        <div style="border: solid 1px black">
          <div class="secondary-background-color">
            <div class="row">
              <div class="col s8 offset-s2">
                <h5 class="center white-text">${job.expertise.name}</h5>
              </div>
              <div class="col s2">
                <h5 class="right white-text badge-service">
                  ${job.totalCandidates}/${job.quantityCandidatorsMax}
                </h5>
              </div>
            </div>
          </div>
          <div class="row">
            <div class="col s4">
              <p class="center text-form-dados primary-color-text">
                <i class="material-icons small dark-color-text">person</i>
              </p>
              <p class="center text-form-dados primary-color-text">
                ${job.individual.name}
              </p>
            </div>
            <div class="col s4">
              <p class="center center-align text-form-dados primary-color-text">
                <i class="material-icons small dark-color-text">location_on</i>
              </p>
              <p class="center text-form-dados primary-color-text">
                ${job.individual.address.neighborhood},
                ${job.individual.address.city.name}/${job.individual.address.city.state.uf}
              </p>
            </div>
            <div class="col s4">
              <p class="center text-form-dados primary-color-text">
                <i class="material-icons small dark-color-text">access_time</i>
              </p>
              <p class="center text-form-dados primary-color-text">
                ${job.textualDate}
              </p>
            </div>
          </div>
          <blockquote class="light-blue lighten-5 info-headers">
            <p>${job.description}</p>
          </blockquote>
          <div class="col s6 m6 l2 offset-l8 spacing-buttons center">
            <!-- <div class="center">
              <a
                href="#modal-delete"
                data-url="${pageContext.request.contextPath}/minha-conta/profissional/detalhes-servico/${jobRequest.id}"
                data-name="${jobRequest.description}"
                class="waves-effect waves-light btn modal-trigger"
                >Ocultar</a
              >
            </div> -->
          </div>
          <div>
            <div class="center">
              <a
                href="minha-conta/profissional/detalhes-servico/${job.id}"
                class="waves-effect waves-light btn spacing-buttons"
              >
                Detalhes
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</c:forEach>

<div class="container col s12 center-align">
  <t:pagination-tab-ajax pagination="${pagination}"></t:pagination-tab-ajax>
</div>
