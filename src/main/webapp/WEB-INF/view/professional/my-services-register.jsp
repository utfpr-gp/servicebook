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

                        <t:message-box/>

                        <div class="col s12">
                            <h3 class="secondary-color-text">Adicione um serviço de ${expertise.name}</h3>
                        </div>

                        <!-- Formulário de adição de especialidade -->
                        <div class="col s12">
                            <form action="minha-conta/profissional/servicos"
                                  method="post">
                                <input type="hidden" name="expertiseId" id="id-input" value="${expertise.id}">

                                <div class="input-field">
                                    <label for="service-select">Selecione uma especiali</label>
                                    <select name="id" id="service-select">
                                        <option disabled selected>Selecione um serviço</option>
                                        <c:forEach var="service" items="${services}">
                                            <option value="${service.id}">${service.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="input-file">
                                    <label for="name-input">Nome</label>
                                    <input id="name-input" type="text" name="name"/>
                                </div>

                                <div class="input-field" style="margin-top: 30px">
                                <textarea id="description-textarea" class="materialize-textarea" name="description"
                                          placeholder="Eu realizo serviços de consertos em geral">${dto.description}</textarea>
                                    <label for="description-textarea">Descrição</label>
                                </div>

                                <blockquote id="description-blockquote" class="light-blue lighten-5 info-headers" style="margin-top: 30px; display: none">
                                    <p>Edite o texto padrão com as suas habilidades e experiências neste serviço.
                                        Este texto será usado para formar a sua página pública de divulgação dos serviços.</p>
                                </blockquote>

                                <div class="right">
                                    <a href="minha-conta/profissional/servicos/${expertise.id}"
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
    });
</script>