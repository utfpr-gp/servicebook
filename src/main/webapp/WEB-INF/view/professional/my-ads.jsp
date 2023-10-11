<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

            <div class="input-field">
              <select id="service-select" name="id">
                <option disabled selected>Selecione um serviço</option>
                <c:forEach var="service" items="${services}">
                  <option value="${service.id}">${service.name}</option>
                </c:forEach>
              </select>
            </div>

            <!-- Formulário de adição de especialidade -->
            <div class="col s12">
              <div class="center spacing-buttons">
                <a class="waves-effect waves-light btn" href="minha-conta/profissional/meus-anuncios/novo">
                  NOVO ANÚNCIO
                </a>
              </div>
            </div>

            <c:if test="${empty servicesIndividuals}">
              <h6 class="left-align" style="font-weight: bold">Serviços Individuais</h6>
              <t:empty-list message="Não há serviços individuais cadastrados"></t:empty-list>
            </c:if>

            <c:if test="${not empty servicesIndividuals}">
              <c:forEach var="service" items="${servicesIndividuals}">

                <div class="col s12">
                  <span class="left-align" style="font-weight: bold;">Serviços Individuais</span>
                  <a href="minha-conta/profissional/meus-anuncios/individuais" class="btn-flat" style="float: right">ver mais</a>
                </div>
                <div class="col s4">
                  <div class="card">
                    <div class="card-content">
                      <span class="card-title grey-text text-darken-4">${service.service.name}</span>
                      <p class="lead texto-com-elipse">${service.name}</p>
                    </div>
                    <div class="card-reveal">
                      <span class="card-title grey-text text-darken-4" style="font-size: 1rem">${service.name}</span>
                      <small>${service.description}</small>
                    </div>
                    <div class="card-content grey lighten-4">
                      <div class="row">
                        <div class="col" style="display: inline-flex">
                          <span style="margin: 12px">R$</span> <input type="text" class="ads-price ads-price-label" value="${service.price}" disabled>
                        </div>

                        <div class="col">
                          <span class="grey-text text-darken-4 left"><i class="material-icons left">access_time</i> ${service.duration} </span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </c:if>

            <div class="col s12">
              <c:if test="${empty servicesCombined}">
                <h6 class="left-align" style="font-weight: bold">Serviços Combinados</h6>
                <t:empty-list message="Não há serviços combinados cadastrados"></t:empty-list>
              </c:if>

              <c:if test="${not empty servicesCombined}">
                <t:my-ads-combined items="${servicesCombined}"></t:my-ads-combined>
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
        $('#expertise-select').change(function () {
            let expertiseId = $(this).val();
            let url = 'minha-conta/profissional/especialidades/' + expertiseId + '/servicos';

            $.get(url, function (data) {
                let options = '<option disabled selected>Selecione um serviço</option>';
                data.forEach(function (service) {
                    options += '<option value="' + service.id + '">' + service.name + '</option>';
                });
                $('#service-select').html(options);
                $('#service-select').formSelect();
            });
        });
    });
</script>