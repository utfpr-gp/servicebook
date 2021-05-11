<%--
  Created by IntelliJ IDEA.
  User: brunow
  Date: 20/04/2021
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="a" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>


<a:admin title="Cadastro de Cidade">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text range-quantity">Cadastro de Cidade</h3>
                        <form action="cidades" method="post" enctype="multipart/form-data">
                            <input name="id" type="hidden" value="${dto.id}">
                            <div class="row center range-quantity  spacing-buttons">
                                <div class="col s12 l6 offset-l3 fomate-states-name">
                                    <select id="select-state" name="idState" >
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
                                </div>
                                <div class="col s12 l6 offset-l3 input-field">
                                    <div class="row">
                                        <div class="input-field  col s12">
                                            <i class="material-icons prefix primary-color-text">location_city</i>
                                                <input type="text" id="autocomplete-input" name="name" class="autocomplete" value="${dto.name}">
                                            <label for="autocomplete-input">Cidade</label>
                                        </div>

                                        <div class="col s12 spacing-standard">
                                            <div class="file-field input-field">
                                                <div class="btn">
                                                    <span>Escolher imagem</span>
                                                    <input type="file" name="image" accept=".jpg, .jpeg, .png" value="${imageCurrent}">
                                                </div>
                                                <div class="file-path-wrapper">
                                                    <input class="file-path validate" placeholder="image.jpg" type="text" value="${idImage}">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col s10 offset-s1 spacing-buttons">
                                            <img src="${imageCurrent}" width="100%" class="materialboxed">
                                        </div>

                                        <div class="row">
                                            <div class="col s6  spacing-buttons">
                                                <c:if test="${empty id}">
                                                    <div class="center">
                                                        <a class="waves-effect waves-light btn btn-gray disabled"
                                                           href="#!">Voltar</a>
                                                    </div>
                                                </c:if>

                                                <c:if test="${not empty id}">
                                                    <div class="center">
                                                        <a class="waves-effect waves-light btn btn-gray"
                                                           href="cidades">Voltar</a>
                                                    </div>
                                                </c:if>
                                            </div>
                                            <div class="col s6 spacing-buttons">
                                                <div class="center">
                                                    <button class="waves-effect waves-light btn" type="submit">Salvar</button>
                                                </div>
                                            </div>
                                        </div>
                                        <c:if test="${not empty msg}">
                                            <div class="row">
                                                <div class="col s12">
                                                    <div class="card-panel green lighten-1 msg-view center-align">
                                                        <span class="white-text">${msg}</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>

                                        <c:if test="${not empty errors}">
                                            <div class="row">
                                                <div class="col s12">
                                                    <div class="card-panel red  msg-view center-align">
                                                        <c:forEach var="e" items="${errors}">
                                                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </form>

                        <div class="col s12 l8 offset-l2">
                            <c:if test="${not empty cities}">
                                <table class="striped">
                                    <thead>
                                    <tr><th>#</th>
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
                                            <td><a href="cidades/${city.id}" class="btn-floating btn-small waves-effect waves-light blue"><i class="material-icons">edit</i></a></td>
                                            <td><a href="#modal-delete" data-url="${pageContext.request.contextPath}/cidades/${city.id}" data-name="${city.name}" class="btn-floating btn-small waves-effect waves-light red modal-trigger"><i class="material-icons">delete_forever</i></a></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                            </table>
                            </c:if>
                        </div>
                    </div>
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
                            <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                            <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                        </div>
                    </form>
                </div>
            </div>

        </main>

    </jsp:body>
</a:admin>
<script src="assets/resources/scripts/cities.js"></script>