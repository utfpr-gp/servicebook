<%--
  Created by IntelliJ IDEA.
  User: brunow
  Date: 20/04/2021
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="a" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>


<a:admin title="Editar Cidade">
    <jsp:body>
        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text range-quantity">Cadastro de Cidade</h3>
                        <form action="cidades" method="post" enctype="multipart/form-data">
                            <div class="row center range-quantity">
                                <div class="col s12 l4 offset-l4 fomate-states-name">
                                    <select id="select-state" class="white-text select-city">
                                        <option value="PR" selected><p class="white-text">Paraná</p></option>
                                        <option value="SC"><p>Santa Catarina</p></option>
                                        <option value="RG"><p>Rio Grande do SUl</p></option>
                                        <option value="SP"><p>São Paulo</p></option>
                                    </select>
                                </div>
                                <div class="col s12 l4 offset-l4 input-field">
                                    <div class="row">
                                        <div class="input-field  col s12">
                                            <i class="material-icons prefix primary-color-text">location_city</i>
                                            <input type="text" id="autocomplete-input" name="name" class="autocomplete" value="">
                                            <label for="autocomplete-input">Cidade</label>
                                        </div>

                                        <div class="col s12 spacing-standard">
                                            <div class="file-field input-field">
                                                <div class="btn">
                                                    <span>Escolher imagem</span>
                                                    <input type="file" name="image" accept=".jpg, .jpeg, .png">
                                                </div>
                                                <div class="file-path-wrapper">
                                                    <input class="file-path validate" placeholder="image.jpg" type="text">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col s12 spacing-buttons">
                                            <img src="http://res.cloudinary.com/dgueb0wir/image/upload/v1620067165/cities/qba9nnjuklvusnmyptzj.png" width="100%" class="materialboxed">
                                        </div>

                                        <div class="row">
                                            <div class="col s6  spacing-buttons">
                                                <div class="center">
                                                    <a class="waves-effect waves-light btn btn-gray"
                                                       href="#!">Voltar</a>
                                                </div>
                                            </div>
                                            <div class="col s6 spacing-buttons">
                                                <div class="center">
                                                    <button class="waves-effect waves-light btn" type="submit">Editar</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</a:admin>

<script src="assets/resources/scripts/cities.js"></script>