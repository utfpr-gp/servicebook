<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="a" tagdir="/WEB-INF/tags" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/admin/admin.css" rel="stylesheet">
</head>

<a:admin title="Cadastro de Cidade">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="col s12 l6 offset-l3">
                            <h3>Cidades</h3>
                        </div>
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <a class="waves-effect waves-light btn" href="a/cidades">NOVA CIDADE</a>
                        </div>

                        <!-- Formulário -->
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <form action="a/cidades" method="post" enctype="multipart/form-data">
                                <input name="id" type="hidden" value="${dto.id}">

                                <!-- select de estados -->
                                <select id="select-state" name="idState">
                                    <option disabled selected>Selecione um estado</option>
                                    <c:if test="${empty dto.idState}">
                                        <c:forEach var="state" items="${states}">
                                            <option value="${state.id}">${state.name}-${state.uf}</option>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${not empty dto.idState}">
                                        <c:forEach var="state" items="${states}">
                                            <c:if test="${dto.idState == state.id}">
                                                <option value="${state.id}" selected>${state.name}-${state.uf}</option>
                                            </c:if>

                                            <c:if test="${dto.idState != state.id}">
                                                <option value="${state.id}">${state.name}-${state.uf}</option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </select>
                                <!-- fim select de estados -->

                                <div class="input-field">
                                    <input type="text" id="autocomplete-input" name="name" class="autocomplete"
                                           value="${dto.name}">
                                    <label for="autocomplete-input">Cidade</label>
                                </div>

                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>imagem</span>
                                        <input id="imageInput" type="file" name="image" accept=".jpg, .jpeg, .png"
                                               value="${dto.image}">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" placeholder="image.jpg" type="text">
                                    </div>
                                </div>

                                <div class="col s10 offset-s1 spacing-buttons center">
                                    <img id="previewImage" src="${dto.pathImage}" width="30%">
                                </div>

                                <div class="right">
                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                </div>
                            </form>
                        </div>
                        <!-- Fim Formulário -->

                        <!-- Mensagens -->
                        <div class="row">
                            <div class="col s12 l6 offset-l3 spacing-buttons">
                                <c:if test="${not empty msg}">
                                    <div class="card-panel green lighten-1 msg-view center-align">
                                        <span class="white-text">${msg}</span>
                                    </div>
                                </c:if>
                                <c:if test="${not empty msgError}">
                                    <div class="card-panel red msg-view center-align">
                                        <span class="white-text">${msgError}</span>
                                    </div>
                                </c:if>
                                <c:if test="${not empty errors}">
                                    <div class="card-panel red msg-view center-align">
                                        <c:forEach var="e" items="${errors}">
                                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                                        </c:forEach>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <!-- Fim Mensagens -->

                        <!-- Tabela -->
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <c:if test="${not empty cities}">
                                <table class="striped centered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>NOME</th>
                                        <th>ESTADO</th>
                                        <th>EDITAR</th>
                                        <th>EXCLUIR</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="city" items="${cities}">
                                        <tr>
                                            <td>${city.id}</td>
                                            <td>${city.name}</td>
                                            <td>${city.state.name}</td>
                                            <td><a href="a/cidades/${city.id}"
                                                   class="btn-floating btn-small waves-effect waves-light blue"><i
                                                    class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete"
                                                   data-url="${pageContext.request.contextPath}/a/cidades/${city.id}"
                                                   data-name="${city.name}"
                                                   class="btn-floating btn-small waves-effect waves-light red modal-trigger"><i
                                                    class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <div class="center">
                                <a:pagination pagination="${pagination}" relativePath="/a/cidades"></a:pagination>
                            </div>
                        </div>
                        <!-- Fim Tabela -->

                    </div>
                </div>
            </div>

            <!-- Modal -->
            <div id="modal-delete" class="modal">
                <div class="modal-content">
                    <form action="" method="post">

                        <input type="hidden" name="_method" value="DELETE"/>

                        <div class="modal-content">
                            <h4>Você tem certeza que deseja excluir <strong id="strong-name"></strong>?</h4>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">
                                Cancelar
                            </button>
                            <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- Fim Modal -->

        </main>
    </jsp:body>
</a:admin>
<script src="assets/resources/scripts/cities.js"></script>
<script>
    document.getElementById("imageInput").addEventListener("change", function () {
        var preview = document.getElementById("previewImage");
        var file = this.files[0];

        // Verifique se um arquivo foi selecionado
        if (file) {
            var reader = new FileReader();

            // Defina a função de callback para quando a leitura do arquivo estiver completa
            reader.onload = function (e) {
                preview.src = e.target.result;
                preview.style.display = "block";
            };

            // Leia o arquivo como URL de dados
            reader.readAsDataURL(file);
        }
    });
</script>
