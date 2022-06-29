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
                        <h3 class="center secondary-color-text range-quantity">Especialidades</h3>
                        <div class="col s6 l4 offset-l2 spacing-buttons">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="especialidades">Novo</a>
                            </div>
                        </div>
                        <form action="especialidades" method="post" enctype="multipart/form-data">
                            <input name="id" type="hidden" value="${dto.id}">
                            <div class="col s12 l4 offset-l4 input-field range-quantity">

                                <div class="input-field">
                                        <i class="material-icons prefix primary-color-text">work</i>
                                        <input placeholder="Pedreiro" type="text" id="autocomplete-input" name="name" class="autocomplete" value="${dto.name}">
                                        <label for="autocomplete-input">Especialidade</label>
                                </div>
                                <div class="input-field">
                                    <textarea id="description" class="materialize-textarea" name="description" value="${dto.description}" placeholder="Realiza serviços de consertos em geral">${dto.description}</textarea>
                                    <label for="description">Descrição</label>
                                </div>
                                <div class="file-field input-field">
                                    <div class="btn">
                                        <span>Adicionar ícone</span>
                                        <input type="file" value="${icon}" name="icon" accept=".svg">
                                    </div>
                                    <div class="file-path-wrapper">
                                        <input class="file-path validate" placeholder="icon.svg"  type="text" value="${idIcon}">
                                    </div>
                                </div>

                            </div>
                            <div class="col s6 offset-m6 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                </div>
                            </div>
                            <c:if test="${not empty msg}">
                                <div class="row">
                                    <div class="col s12 l4 offset-l4">
                                        <div class="card-panel green lighten-1 msg-view center-align">
                                            <span class="white-text">${msg}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty msgError}">
                                <div class="row">
                                    <div class="col s12 l4 offset-l4">
                                        <div class="card-panel red msg-view center-align">
                                            <span class="white-text">${msgError}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${not empty errors}">
                                <div class="row">
                                    <div class="col s12 l4 offset-l4">
                                        <div class="card-panel red msg-view center-align">
                                            <c:forEach var="e" items="${errors}">
                                                <span class="white-text">${e.getDefaultMessage()}</span><br>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </form>
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
                        <div class="col s12 l8 offset-l2 spacing-buttons">
                            <c:if test="${not empty professions}">
                                <table class="striped">
                                    <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>NOME</th>
                                        <th>ICONE</th>
                                        <th>EDITAR</th>
                                        <th>EXCLUIR</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="p" items="${professions}">
                                        <tr>
                                            <td>${p.id}</td>
                                            <td>${p.name}</td>
                                            <td><img class="circle" src="${p.pathIcon}"></td>
                                            <td><a href="especialidades/${p.id}" class="btn-floating btn-small waves-effect waves-light blue"><i class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete" class="btn-floating btn-small waves-effect waves-light red modal-trigger" data-url="${pageContext.request.contextPath}/especialidades/${p.id}" data-name="${p.name}"><i class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                            <div class="center"><t:pagination pagination="${pagination}" relativePath="/especialidades"></t:pagination></div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:admin>
