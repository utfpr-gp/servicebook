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
                            <h3 class="secondary-color-text">Adicione uma especialidade</h3>
                        </div>

                        <!-- Formulário de adição de especialidade -->
                        <div class="col s12">
                            <form id="expertise-form" action="minha-conta/profissional/especialidades"
                                  method="post">
                                <input type="hidden" name="id" id="id-input" value="${dto.id}">
                                <div class="input-field">
                                    <label for="category-select">Selecione uma categoria</label>
                                    <select name="categoryId" id="category-select">
                                        <option disabled selected>Selecione uma categoria</option>
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.id}">${category.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="input-field">
                                    <label for="expertise-select">Selecione uma especialidade</label>
                                    <select name="expertiseId" id="expertise-select">
                                        <option disabled selected>Selecione uma especialidade</option>
                                        <c:forEach var="expertise" items="${expertises}">
                                            <option value="${expertise.id}">${expertise.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="input-field" style="margin-top: 30px">
                                <textarea id="description-textarea" class="materialize-textarea" name="description"
                                          placeholder="Eu realizo serviços de consertos em geral">${dto.description}</textarea>
                                    <label for="description-textarea">Descrição</label>
                                </div>

                                <blockquote id="description-blockquote" class="light-blue lighten-5 info-headers" style="margin-top: 30px; display: none">
                                    <p>Edite o texto padrão com as suas habilidades e experiências nesta especialidade.
                                        Este texto será usado para formar a sua página pública de divulgação de suas
                                        especialidades.</p>
                                </blockquote>

                                <div class="right">
                                    <a href="minha-conta/profissional/especialidades" class="btn-flat">Cancelar</a>
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
        //inicializa o select de expertises após a seleção de uma categoria
        $("#category-select").change(function () {
            let categoryId = $(this).val();
            $.ajax({
                url: "minha-conta/profissional/especialidades/categorias/" + categoryId,
                type: "GET",
                success: function (expertises) {
                    $("#expertise-select").empty();

                    //se o array for vazio, coloca o option que não há especialidades
                    if (expertises.length === 0)
                        $("#expertise-select").append("<option disabled selected>Não há especialidades</option>");
                    else
                        $("#expertise-select").append("<option disabled selected>Selecione uma especialidade</option>");

                    $.each(expertises, function (index, expertise) {
                        $("#expertise-select").append("<option value='" + expertise.id + "'>" + expertise.name + "</option>");
                    });
                    $("#expertise-select").formSelect();
                }
            });
        });

        //inicializa o campo de descrição da expertise após a seleção de uma expertise
        $("#expertise-select").change(function () {
            let expertiseId = $(this).val();
            $.ajax({
                url: "minha-conta/profissional/especialidades/" + expertiseId,
                type: "GET",
                success: function (expertise) {
                    $("#description-textarea").val(expertise.description);
                    $('#id-input').val(expertise.id);
                    M.textareaAutoResize($("#description-textarea"));
                    $("#description-blockquote").show();
                }
            });
        });
    });

    $(".myclass").hover(function (e) {
        $(this).css("color", e.type === "mouseenter" ? "red" : "grey")
    })
</script>