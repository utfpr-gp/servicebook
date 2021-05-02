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
                            <div class="row center range-quantity">
                                <div class="col s12 l4 offset-l4 fomate-states-name">
                                    <select id="select-state" name="idState" >
                                        <c:if test="${not empty dto.idState}">
                                            <c:forEach var="state" items="${states}">
                                                <c:if test="${dto.idState == state.id}">
                                                    <option value="${state.id}" selected>${state.name}-${state.uf}</option>
                                                </c:if>
                                            </c:forEach>
                                        </c:if>

                                        <c:forEach var="state" items="${states}">
                                            <c:if test="${state.id != idState}">
                                                <option value="${state.id}">${state.name}-${state.uf}</option>
                                            </c:if>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col s12 l4 offset-l4 input-field">
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
                                                    <input type="file" name="image" accept="image/*">
                                                </div>
                                                <div class="file-path-wrapper">
                                                    <input class="file-path validate" placeholder="image.jpg" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col s6  spacing-buttons">
                                                <div class="center">
                                                    <a class="waves-effect waves-light btn btn-gray"
                                                       href="javascript: alert('Voltou')">Voltar</a>
                                                </div>
                                            </div>
                                            <div class="col s6 spacing-buttons">
                                                <div class="center">
                                                    <button class="waves-effect waves-light btn" type="submit">Cadastrar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
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

                <c:if test="${not empty errors}">
                <div class="row">
                    <div class="col s12 l4 offset-l4">
                        <div class="card-panel red  msg-view center-align">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                        </div>
                    </div>
                </div>
                </c:if>
            </div>
        </main>

    </jsp:body>
</a:admin>
<script src="assets/resources/scripts/cities.js"></script>