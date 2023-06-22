<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:admin title="Cadastro de Especialidades">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="col s12 l6 offset-l3">
                            <h3 class="secondary-color-text">Especialidades</h3>
                        </div>
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <a class="waves-effect waves-light btn" href="a/especialidades">NOVA ESPECIALIDADE</a>
                        </div>

                        <!-- Formulário -->
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <form action="a/especialidades" method="post" enctype="multipart/form-data">
                                <input name="id" type="hidden" value="${dto.id}">

                                <select id="select-state" name="categoryId" >
                                    <c:if test="${empty dto.categoryId}">
                                        <c:forEach var="category" items="${categories}">
                                            <option value="${category.id}">${category.name}</option>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${not empty dto.categoryId}">
                                        <c:forEach var="category" items="${categories}">
                                            <c:if test="${dto.categoryId == category.id}">
                                                <option value="${category.id}" selected>${category.name}</option>
                                            </c:if>

                                            <c:if test="${dto.categoryId != category.id}">
                                                <option value="${category.id}">${category.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </select>

                                <div class="input-field">
                                    <input placeholder="Pedreiro" type="text" id="autocomplete-input" name="name" value="${dto.name}">
                                    <label for="autocomplete-input">Especialidade</label>
                                </div>

                                <div class="input-field input-name-expertise">
                                    <textarea id="description" class="materialize-textarea" name="description" value="${dto.description}" placeholder="Realiza serviços de consertos em geral">${dto.description}</textarea>
                                    <label for="description">Descrição</label>
                                </div>

                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>Ícone</span>
                                        <input id="imageInput" type="file" value="${dto.icon}" name="icon" accept=".svg">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" placeholder="icon.svg" type="text">
                                    </div>
                                </div>
                                <div class="col s10 offset-s1 spacing-buttons center">
                                    <img id="previewImage" src="${dto.pathIcon}" width="30%">
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
                        <!-- Fim Modal de exclusão -->

                        <!-- Tabela de especialidades -->
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <c:if test="${not empty expertises}">
                                <table class="striped centered">
                                    <thead>
                                        <tr>
                                            <th>NOME</th>
                                            <th>CATEGORIA</th>
                                            <th>ÍCONE</th>
                                            <th>EDITAR</th>
                                            <th>EXCLUIR</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="p" items="${expertises}">
                                            <tr>
                                                <td>${p.name}</td>
                                                <td>${p.categoryName}</td>
                                                <td>
                                                    <img class="circle" src="${p.pathIcon}" height="24" width="24">
                                                </td>
                                                <td><a href="a/especialidades/${p.id}" class="btn-floating btn-small waves-effect waves-light blue"><i class="material-icons">edit</i></a></td>
                                                <td><a href="#modal-delete" class="btn-floating btn-small waves-effect waves-light red modal-trigger" data-url="${pageContext.request.contextPath}/a/especialidades/${p.id}" data-name="${p.name}"><i class="material-icons">delete_forever</i></a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <div class="center"><t:pagination pagination="${pagination}" relativePath="/a/especialidades"></t:pagination></div>
                        </div>
                        <!-- Fim Tabela de especialidades -->
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:admin>
<script>
    document.getElementById("imageInput").addEventListener("change", function() {
        console.log('entrou....')
        var preview = document.getElementById("previewImage");
        var file = this.files[0];

        // Verifique se um arquivo foi selecionado
        if (file) {
            var reader = new FileReader();

            // Defina a função de callback para quando a leitura do arquivo estiver completa
            reader.onload = function(e) {
                preview.src = e.target.result;
                preview.style.display = "block";
            };

            // Leia o arquivo como URL de dados
            reader.readAsDataURL(file);
        }
        // else {
        //     preview.src = "#";
        //     preview.style.display = "none";
        // }
    });
</script>
