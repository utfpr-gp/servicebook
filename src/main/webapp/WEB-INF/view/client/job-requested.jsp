<%@page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@taglib
prefix="t" tagdir="/WEB-INF/tags" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Status do pedido">
  <jsp:body>
    <main>
      <div class="container">
        <div class="section">
          <c:if test="${param.erro}">
            <div class="section">
              <h5 class="center secondary-color-text">
                Não foi possível persistir a ordem de serviço cadastrada!
              </h5>

              <h5 class="center">Tente novamente</h5>

              <div class="section center-align">
                <a class="waves-effect waves-light btn" href="requisicoes"
                  >Cadastrar um pedido</a
                >
              </div>
            </div>
          </c:if>

          <c:if test="${not param.erro}">
            <h4 class="center">Olá ${client}, recebemos seu pedido!</h4>

            <h5 class="center">
              Acompanhe seu pedido para ter acesso aos dados dos profissionais
              que se candidataram ao seu pedido.
            </h5>
            <h5 class="center">
              Selecione alguns profissionais para orçamento, converse com eles e
              escolha o melhor para o seu serviço!
            </h5>
            <h5 class="center">
              Não esqueça de informar pelo acompanhamento do pedido qual foi o
              profissional escolhido para que você possa avaliar depois!
            </h5>

            <div class="section center-align">
              <a class="waves-effect waves-light btn" href="minha-conta/cliente"
                >Acompanhe seu pedido</a
              >
            </div>
          </c:if>
        </div>
      </div>
    </main>
  </jsp:body>
</t:client>
