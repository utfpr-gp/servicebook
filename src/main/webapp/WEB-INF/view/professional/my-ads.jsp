<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<head>
  <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
  <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css"
        rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/ads.css"
        rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Minha conta">
  <jsp:body>
    <main>
      <div class="">
        <div class="section">
          <div class="row">
            <div class="breadcrumbs">
              <a href="${pageContext.request.contextPath}/">Início</a> &gt;
              Meus anúncios
            </div>

            <t:message-box/>

            <div class="col s12">
              <h3 class="secondary-color-text">Meus Anúncios </h3>
            </div>
            <blockquote class="light-blue lighten-5 info-headers">
              <p> Adicione anúncios dos serviços que você oferece dentro do Servicebook.</p>
              <p> Se ativado o agendamento, o cliente possuira acesso a sua agenda, podendo visualizar os dias e horários
                disponíveis. Além disso, você pode adicioanar o preço e duração dos seus serviços, dessa forma
                permitindo que clientes adquiram seus serviços de forma rápida. </p>
            </blockquote>

            <!-- Formulário de adição de especialidade -->
            <div class="col s12">
              <div class="center spacing-buttons">
                <a class="waves-effect waves-light btn" href="minha-conta/profissional/meus-anuncios/novo">
                  NOVO ANÚNCIO
                </a>
              </div>
            </div>

            <div id="ads"></div>

            <c:if test="${empty servicesIndividuals}">
              <h6 class="left-align" style="font-weight: bold">Serviços Individuais</h6>
              <t:empty-list message="Não há serviços individuais cadastrados"></t:empty-list>
            </c:if>

            <c:if test="${not empty servicesIndividuals}">

                <div class="col s12">
                  <span class="left-align" style="font-weight: bold; font-size: 1.5rem">Serviços Individuais</span>
                  <a href="minha-conta/profissional/meus-anuncios/individuais" class="btn-flat btn_view">ver mais <i class="material-icons right">navigate_next</i></a>
                </div>
              <c:forEach var="service" items="${servicesIndividuals}">

                <div class="col s4 div_cards_services">
                  <p class="label_especialidade"> ${service.service.expertise.name} </p>
                  <div class="card" style="margin: 0">
                    <div class="label_duration">
                      <span class="">${service.duration}</span>
                    </div>
                    <div class="card-content">
                      <div class="row">
                            <c:if test="${not empty service.service.pathIcon}">
                              <div class="col s12 center">
                                <img id="previewImage" src="${service.service.pathIcon}" width="60%">
                              </div>
                            </c:if>

                        <div class="col s12">
                            ${service.description}
                        </div>
                      </div>
                    </div>
                    <div class="label_price">
                      <p style="margin: 0"><fmt:formatNumber value="${service.price/100}"   type = "currency"/> </p>
                    </div>
                  </div>
                  <p class="label_especialidade">${service.service.name}</p>
                </div>
              </c:forEach>

            </c:if>

            <div class="col s12">
              <c:if test="${empty teste}">
                <h6 class="left-align" style="font-weight: bold">Serviços Combinados</h6>
                <t:empty-list message="Não há serviços combinados cadastrados"></t:empty-list>
              </c:if>

              <c:if test="${not empty teste}">
                <t:my-ads-combined items="${teste}"></t:my-ads-combined>
              </c:if>
            </div>

            <div class="col s12">
              <c:if test="${empty servicesPackages}">
                <h6 class="left-align" style="font-weight: bold">Pacotes de Serviços</h6>
                <t:empty-list message="Não há pacotes de serviços cadastrados"></t:empty-list>
              </c:if>

              <c:if test="${not empty servicesPackages}">
                <t:my-ads-package itemsService="${servicesPackages}"></t:my-ads-package>
              </c:if>
            </div>

          </div>
        </div>
      </div>
    </main>

  </jsp:body>
</t:template-side-nav>

<script>
    $(function () {

        $('.ads-price').mask('0?00.0?00.0?00.0?00,00', {reverse: true});

        //inicializa o select de serviços com os serviços da especialidade selecionada
        $('#expertise-select-filter').change(function () {
            let expertiseId = $(this).val();
            let url = 'minha-conta/profissional/meus-anuncios/especialidade/' + expertiseId;

            $.get(url, function (data) {

                $('#ads').html(data);
            });
        });
    });
</script>