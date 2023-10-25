<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
  <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
  <link href="${pageContext.request.contextPath}/assets/resources/styles/professional/professional.css"
        rel="stylesheet">
</head>

<t:template-side-nav title="ServiceBook - Minha conta">
  <jsp:body>
    <main>
      <div class="">
        <div class="section">
          <div class="row">
            <div class="row">
              <div class="breadcrumbs">
                <a href="${pageContext.request.contextPath}/">Início</a> &gt;
                <a href="${pageContext.request.contextPath}/minha-conta/profissional/servicos">Meus Serviços</a> &gt;
                Adicionar Serviço
              </div>
              <t:message-box/>

              <div class="col s12">
                <h3 class="secondary-color-text">Adicione um serviço de ${expertise.name}</h3>
              </div>

              <!-- Formulário de adição de especialidade -->
              <div class="col s12">
                <form action="minha-conta/profissional/servicos"
                      method="post">

                  <div class="input-field">
                    <select id="expertise-select" name="expertiseId" value="${expertise.id}">
                      <option disabled selected>Selecione uma especialidade</option>
                      <c:forEach var="e" items="${expertises}">
                        <option value="${e.id}" ${e.id eq expertise.id ? 'selected="selected"' : ''}>${e.name}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="input-field">
                    <select id="service-select" name="id">
                      <option disabled selected>Selecione um serviço</option>
                      <c:forEach var="service" items="${services}">
                        <option value="${service.id}">${service.name}</option>
                      </c:forEach>
                    </select>
                  </div>

                  <div class="input-file">
                    <label for="name-input">Nome</label>
                    <input id="name-input" type="text" name="name" readonly/>
                    <label>
                      <input type="checkbox" id="sobrescrever_nome" class="sobrescrever"/>
                      <span>Sobrescrever</span>
                    </label>
                  </div>

                  <div class="input-field" style="margin-top: 30px; margin-bottom: 0">
                                <textarea id="description-textarea" class="materialize-textarea" name="description"
                                          readonly
                                          placeholder="Eu realizo serviços de consertos em geral">${dto.description}</textarea>
                    <label for="description-textarea">Descrição</label>
                  </div>
                  <label>
                    <input type="checkbox" id="sobrescrever_descricao" class="sobrescrever"/>
                    <span>Sobrescrever</span>
                  </label>
                  <blockquote id="description-blockquote" class="light-blue lighten-5 info-headers"
                              style="margin-top: 30px; display: none">
                    <p>Edite o texto padrão com as suas habilidades e experiências neste serviço.
                      Este texto será usado para formar a sua página pública de divulgação dos serviços.</p>
                  </blockquote>

                  <div class="right" style="margin-top: 30px">
                    <a href="minha-conta/profissional/servicos?id=${expertise.id}"
                       class="waves-effect waves-light btn-flat">Cancelar</a>
                    <button type="submit"
                            class="btn waves-effect waves-light">Salvar
                    </button>
                  </div>
                </form>
              </div>
              <!-- Fim do formulário de adição de especialidade -->
            </div>
          </div>
        </div>
    </main>

    <!-- Modal para remoção de uma especialidade -->
    <div id="modal-delete" class="modal">
      <div class="modal-content">
        <form action="" method="post">
          <input type="hidden" name="_method" value="DELETE"/>
          <div class="modal-content">
            <h4>Você tem certeza que deseja remover <strong id="strong-name"></strong> das suas
              especialidades?</h4>
          </div>
          <div class="modal-footer">
            <button type="button"
                    class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar
            </button>
            <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim
            </button>
          </div>
        </form>
      </div>
    </div>
    <!-- Fim Modal para remoção de uma especialidade -->

  </jsp:body>
</t:template-side-nav>

<script>

    $(function () {
        //inicializa os campos nome e descrição com os dados do serviço selecionado
        $('#service-select').change(function () {
            let serviceId = $(this).val();
            let url = 'servicos/' + serviceId;

            $.get(url, function (data) {
                $('#name-input').val(data.name);
                $('#description-textarea').val(data.description);
                $('#description-blockquote').show();
            });
        });

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
        $('#name-input').css('color', 'gray');
        $('#description-textarea').css('color', 'gray');

        //Sobreescrever o nome do serviço
        $('#sobrescrever_nome').change(function () {
            var service = document.getElementById('name-input');
            if (!service.readOnly) {
                service.readOnly = true;
                $('#name-input').css('color', 'gray');
            } else {
                service.readOnly = false;
                $('#name-input').css('color', 'black');
            }
        });
        // Sobreescrever a descrição do serviço
        $("#sobrescrever_descricao").change(function () {
            var service = document.getElementById('description-textarea');
            if (!service.readOnly) {
                service.readOnly = true;
                $('#description-textarea').css('color', 'gray');
            } else {
                service.readOnly = false;
                $('#description-textarea').css('color', 'black');
            }
        });
    });
</script>