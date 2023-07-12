<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/admin/admin.css" rel="stylesheet">
</head>

<t:admin title="Cadastro de Categoria">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="col s12 l6 offset-l3">
                            <h3 class="secondary-color-text">Categorias</h3>
                        </div>
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <a class="waves-effect waves-light btn" href="a/categorias">NOVA CATEGORIA</a>
                        </div>
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <form action="a/categorias" method="post" enctype="multipart/form-data">
                                <input name="id" type="hidden" value="${dto.id}">

                                <div class="input-field">
                                    <input placeholder="Reformas e reparos" type="text" id="autocomplete-input"
                                           name="name" value="${dto.name}">
                                    <label for="autocomplete-input">Categoria</label>
                                </div>

                                <div class="right">
                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                </div>
                            </form>
                        </div>

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
                        <div class="col s12 l6 offset-l3 spacing-buttons">
                            <c:if test="${not empty categories}">
                                <table class="striped centered">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>NOME</th>
                                        <th>EXCLUIR</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${categories}">
                                        <tr>
                                            <td>${p.id}</td>
                                            <td>${p.name}</td>
                                            <td><a href="a/categorias/${p.id}"
                                                   class="btn-floating btn-small waves-effect waves-light blue"><i
                                                    class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete"
                                                   class="btn-floating btn-small waves-effect waves-light red modal-trigger"
                                                   data-url="${pageContext.request.contextPath}/a/categorias/${p.id}"
                                                   data-name="${p.name}"><i
                                                    class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <div class="center"><t:pagination pagination="${pagination}"
                                                              relativePath="/a/categorias"></t:pagination></div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:admin>
