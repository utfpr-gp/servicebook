<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:client title="Etapa 05">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty errors}">
                    <div class="card-panel red">
                        <c:forEach var="error" items="${errors}">
                            <span class="white-text">${error}</span><br>
                        </c:forEach>
                    </div>
                </c:if>
                <div class="section">

                    <!--   Icon Section   -->
                    <div class="row">
                        <h4 class="center secondary-color-text">Anexe fotos do serviço, se houver.</h4>

                        <div class="row center">
                            <div class="rowspacing-standard">
                                <form method="post" action="requisicoes/passo-5" enctype="multipart/form-data">
                                    <div class="col s12 m6 offset-m3 l4 offset-l4 ">
                                        <div class="file-field input-field">
                                            <div class="btn">
                                                <span>Escolha as Imagens</span>
                                                <input type="file" name="images" multiple="multiple" accept=".jpg, .jpeg, .png">
                                            </div>
                                            <div class="file-path-wrapper">
                                                <input class="file-path validate" placeholder="image.jpg"  type="text">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col s12 m6 offset-m3 l4 offset-l4 ">
                                        <div class="spacing-buttons">
                                            <button class="waves-effect waves-light btn btn-validate" >Adicionar</button>
                                        </div>
                                    </div>
                                    <div class="gallery" id="galley">
                                        <c:if test="${not empty dto.getImagesSession()}" >
                                            <c:forEach items="${dto.getImagesSession()}" var="image">
                                                <div class="box-image">
                                                    <img src="${image}">
                                                    <a class="waves-effect waves-light" onclick="deleteImage('${image}')"><i class="material-icons">delete_forever</i></a>
                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <a href="requisicoes?passo=4" class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                        </div>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <a href="requisicoes/passo=6" class="waves-effect waves-light btn" href="#!">Próximo</a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>

<script src="assets/resources/scripts/job-image.js"></script>
