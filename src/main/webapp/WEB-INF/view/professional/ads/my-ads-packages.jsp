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
              <a href="${pageContext.request.contextPath}/minha-conta/profissional/meus-anuncios">Meus Anúncios</a> &gt;
              Pacotes de Serviços
            </div>

            <t:message-box/>

            <div class="col s12">
              <h3 class="secondary-color-text">Meus Anúncios </h3>
            </div>
            <blockquote class="light-blue lighten-5 info-headers">
              <p> Pacotes de Serviços.</p>
            </blockquote>

            <div class="input-field">
              <select id="service-select" name="id">
                <option disabled selected>Selecione um serviço</option>
                <c:forEach var="service" items="${services}">
                  <option value="${service.id}">${service.name}</option>
                </c:forEach>
              </select>
            </div>

            <c:forEach var="service" items="${servicesPackages}">
              <div class="col s4">
                <div class="card">
                  <div class="card-content">
                    <span class="card-title activator grey-text text-darken-4">${service.name}<i class="material-icons right">more_vert</i></span>
                    <p class="lead texto-com-elipse">Pacote com ${service.amount} ${service.name}</p>
                  </div>
                  <div class="card-reveal">
                    <span class="card-title grey-text text-darken-4" style="font-size: 1rem">${service.name}<i class="material-icons right">close</i></span>
                    <small>Pacote contendo ${service.amount} ${service.name}</small>
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

          </div>
        </div>
      </div>
    </main>

  </jsp:body>
</t:template-side-nav>

<script>
    $(function () {

        $('.ads-price').mask('000.000.000.000.000,00', {reverse: true});

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