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


<a:admin title="Cadastro de Cidade">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text range-quantity">Cadastro de Cidade</h3>
                        <form action="cidades" method="post">
                            <div class="row center range-quantity">
                                <div class="col s4 offset-s4 fomate-states-name">
                                    <select id="select-state" name="idState">
                                        <c:forEach var="state" items="${states}">
                                            <option value="${state.id}"><p>${state.name}-${state.uf}</p></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col s4 offset-s4 input-field">
                                    <div class="row">
                                        <div class="input-field  col s12">
                                            <i class="material-icons prefix primary-color-text">location_city</i>
                                                <input type="text" id="autocomplete-input" name="name" class="autocomplete" value="${dto.name}">
                                            <label for="autocomplete-input">Cidade</label>
                                        </div>

                                        <div class="row">
                                            <div class="col s6 m6 spacing-buttons">
                                                <div class="center">
                                                    <a class="waves-effect waves-light btn btn-gray"
                                                       href="javascript: alert('Voltou')">Voltar</a>
                                                </div>
                                            </div>
                                            <div class="col s6 m6 spacing-buttons">
                                                <div class="center">
                                                    <div class="right">
                                                        <button class="waves-effect waves-light btn" type="submit">Cadastrar</button>
                                                    </div>
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
                        <div class="col s12">
                            <div class="card-panel green lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <p>${teste}</p>
                <c:if test="${not empty errors}">
                <div class="row">
                    <div class="col s12">
                        <div class="card-panel red msg-view center-align">
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





