<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/admin/admin.css" rel="stylesheet">
</head>

<t:template-admin title="Cadastro de Serviços">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="col s12">
                            <h3 class="secondary-color-text">Serviços</h3>
                        </div>
                        <div class="col s12 spacing-buttons">
                            <a class="waves-effect waves-light btn" href="a/servicos">NOVO SERVIÇO</a>
                        </div>

                        <!-- Formulário de Serviços -->
                        <div class="col s12 spacing-buttons">
                            <form action="a/servicos" class="form-expertises" method="post"
                                  enctype="multipart/form-data">
                                <input name="id" type="hidden" value="${dto.id}">

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

                                <div class="input-field">
                                    <input placeholder="" type="text" id="autocomplete-input" name="name"
                                           class="autocomplete" value="${dto.name}">
                                    <label for="autocomplete-input">Serviços</label>
                                </div>

                                <div class="input-field">
                                    <textarea id="description" class="materialize-textarea" name="description"
                                              value="${dto.description}"
                                              placeholder="Realiza serviços de consertos em geral">${dto.description}</textarea>
                                    <label for="description">Descrição</label>
                                </div>

                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>Ícone</span>
                                        <input id="imageInput" type="file" value="${dto.icon}" name="icon"
                                               accept=".svg">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" placeholder="icon.svg" type="text">
                                    </div>
                                </div>
                                <c:if test="${not empty dto.pathIcon}">
                                    <div class="col s10 offset-s1 center">
                                        <img id="previewImage" src="${dto.pathIcon}" width="30%">
                                    </div>
                                </c:if>

                                <p>
                                    <label>
                                        <input type="checkbox"/>
                                        <span>Permitir agendamento para este serviço.</span>
                                    </label>
                                </p>

                                <div class="right">
                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                </div>
                            </form>
                        </div>
                        <!-- Fim Formulário de Serviços -->

                        <!-- Mensagens -->
                        <div class="row">
                            <div class="col s12 spacing-buttons">
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

                        <!-- Modal -->
                        <div id="modal-delete" class="modal">
                            <div class="modal-content">
                                <form action="" method="post">

                                    <input type="hidden" name="_method" value="DELETE"/>

                                    <div class="modal-content">
                                        <h4>Você tem certeza que deseja excluir <strong id="strong-name"></strong>?</h4>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button"
                                                class="modal-close btn-flat waves-effect waves-light btn btn-gray">
                                            Cancelar
                                        </button>
                                        <button type="submit" class="modal-close btn waves-effect waves-light gray">
                                            Sim
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- Fim Modal -->

                        <!-- Tabela de Serviços -->
                        <div class="col s12 spacing-buttons">
                            <c:if test="${not empty services}">
                                <table class="striped">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>ESPECIALIDADE</th>
                                        <th>NOME</th>
                                        <th>EDITAR</th>
                                        <th>EXCLUIR</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${services}">
                                        <tr>
                                            <td>${p.id}</td>
                                            <td>${p.expertise.name}</td>
                                            <td>${p.name}</td>
                                            <td><a href="a/servicos/${p.id}"
                                                   class="btn-floating btn-small waves-effect waves-light blue"><i
                                                    class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete"
                                                   class="btn-floating btn-small waves-effect waves-light red modal-trigger"
                                                   data-url="${pageContext.request.contextPath}/a/servicos/${p.id}"
                                                   data-name="${p.name}"><i
                                                    class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <!-- Paginação -->
                            <div class="center">
                                <t:pagination pagination="${pagination}" relativePath="/a/servicos"></t:pagination>
                            </div>
                            <!-- Fim Paginação -->
                        </div>
                        <!-- Fim Tabela de Serviços -->
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template-admin>
