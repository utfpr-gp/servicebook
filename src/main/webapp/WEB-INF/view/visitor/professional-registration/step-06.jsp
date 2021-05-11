<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro de Profissional">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Nos conte mais sobre você!</h3>


                        <form action="">
                            <div class="row center spacing-buttons">
                                <div class="col s12 l6 offset-l3 spacing-buttons">
                                    <h4 class="center secondary-color-text">Qual o seu nome completo?</h4>
                                    <h5 class="center secondary-color-text">O cliente precisa saber quem está contratando.</h5>
                                    <input placeholder="João da Silva" id="name" name="name" type="text" class="validate">
                                </div>
                                <div class="col s12 l6 offset-l3 spacing-buttons">
                                    <h4 class="center secondary-color-text">Qual o seu CPF/CNPJ?</h4>
                                    <h5 class="center secondary-color-text">Poderá ser usado para validar a veracidade dos dados pessoais.</h5>
                                    <input placeholder="000.000.000-00" id="cpf" name="cpf" type="text" class="validate">
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
