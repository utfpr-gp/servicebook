<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro de Profissional">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Qual o seu endereço?</h3>
                        <h5 class="center secondary-color-text">Será útil para filtrar as solicitações de serviços por cidade e de acordo com a distância para o local do serviço ofertado.</h5>



                        <form action="">
                            <div class="row center spacing-buttons">
                                <div class="col s9 l5 offset-l2 spacing-buttons">
                                    <input placeholder="00000-000" id="cep" name="cep" type="text" class="validate">
                                    <span id="error-cep" class="hide helper-text red-text darken-3"></span>
                                </div>
                                <div class="col s3 l3 spacing-buttons">
                                    <div class="center">
                                        <a id="btn-buscar" class="waves-effect waves-light btn">Buscar</a>
                                    </div>
                                </div>
                                <div class="col s9 l5 offset-l2 spacing-buttons">
                                    <input placeholder="Rua 15 de Novembro" id="rua" name="rua" type="text" class="validate">
                                </div>
                                <div class="col s3 l3 spacing-buttons">
                                    <input placeholder="Número" id="numero" name="numero" type="text" class="validate">
                                </div>
                                <div class="col s9 l5 offset-l2 spacing-buttons">
                                    <input placeholder="Guarapuava" id="cidade" name="cidade" type="text" class="validate">
                                </div>
                                <div class="col s3 l3 spacing-buttons">
                                    <input placeholder="PR" id="uf" name="uf" type="text" class="validate">
                                </div>
                            </div>

                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn">Próximo</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
<script src="assets/resources/scripts/cep-professional.js"></script>
