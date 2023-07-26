<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template-side-nav title="Cadastro de Vagas de Emprego">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <!-- Formulário de cadastro de vagas de emprego -->
                    <div class="row">
                        <div class="col s12">
                            <h3 class="secondary-color-text">Vagas de Emprego</h3>
                        </div>
                        <t:message-box/>
                        <div class="col s12 spacing-buttons">
                            <a class="waves-effect waves-light btn" href="minha-conta/empresa/vagas-de-emprego">Nova Vaga</a>
                        </div>
                        <div class="col s12 spacing-buttons">
                            <form action="minha-conta/empresa/vagas-de-emprego" method="post" enctype="multipart/form-data">
                                <input name="id" type="hidden" value="${dto.id}">

                                <div class="col s12 input-field">
                                    <input  type="text" id="title" name="title" value="${dto.title}">
                                    <label for="title">Título da vaga</label>
                                </div>

                                <div class="col s12 input-field">
                                    <select name="expertiseId" id="expertise-select">
                                        <option disabled selected>Selecione uma especialidade</option>
                                        <c:forEach var="expertise" items="${expertises}">
                                            <option value="${expertise.id}"
                                                    <c:if test="${expertise.id == dto.expertise.id}">
                                                        selected
                                                    </c:if>
                                            >${expertise.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="col s12 input-field">
                                    <input type="text" id="description" name="description" value="${dto.description}">
                                    <label for="description">Descrição</label>
                                </div>

                                <div class="col s12 input-field">
                                    <input type="number" id="salary-input" name="salary">
                                    <label for="salary-input">Salário</label>
                                </div>

                                <div class="right">
                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- Fim do formulário de cadastro de vagas de emprego -->

                    <!-- Lista de vagas de emprego -->
                    <div class="row">
                        <div class="col s12 spacing-buttons">
                            <c:if test="${not empty jobs}">
                                <table class="striped centered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Título da vaga</th>
                                        <th>Descrição</th>
                                        <th>Salário</th>
                                        <th>EDITAR</th>
                                        <th>EXCLUIR</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${jobs}">
                                        <tr>
                                            <td>${p.id}</td>
                                            <td>${p.title}</td>
                                            <td>${p.description}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${p.salary != null}">
                                                        <fmt:formatNumber value="${p.salary / 100}" type="currency" currencySymbol="R$"/>
                                                    </c:when>
                                                    <c:otherwise>À combinar</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td><a href="minha-conta/empresa/vagas-de-emprego/${p.id}" class="btn-floating btn-small waves-effect waves-light blue"><i class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete" class="btn-floating btn-small waves-effect waves-light red modal-trigger" data-url="minha-conta/empresa/vagas-de-emprego/${p.id}" data-name="${p.title}"><i class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <div class="center"><t:pagination pagination="${pagination}" relativePath="/minha-conta/empresa/vagas-de-emprego"></t:pagination></div>
                        </div>
                    </div>
                    <!-- Fim da lista de vagas de emprego -->
                </div>

                <!-- Modal de exclusão -->
                <div id="modal-delete" class="modal">
                    <div class="modal-content">
                        <form action="" method="post">
                            <input type="hidden" name="_method" value="DELETE"/>
                            <div class="modal-content">
                                <h4>Você tem certeza que deseja excluir <strong id="strong-name"></strong>?</h4>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                                <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- Fim do modal de exclusão -->

            </div>
        </main>

    </jsp:body>
</t:template-side-nav>
<script src="assets/libraries/jquery.mask.js"></script>
<!--script>
    $(document).ready(function() {
        $('#salary-input').mask("#.##0,00", {reverse: true});
    });
</script-->
<script>
    $(document).ready(function() {
        $('.modal').modal({
            onOpenEnd: function(modal, trigger) {
                var url = $(trigger).data('url');
                var name = $(trigger).data('name');

                modal = $(modal);
                var form = modal.find('form');
                form.attr('action', url);
                modal.find('#strong-name').text(name);
            }
        });
    });
</script>
