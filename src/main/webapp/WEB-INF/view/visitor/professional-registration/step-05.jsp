<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro de Profissional">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Vamos validar o seu número de celular?</h3>
                        <h5 class="center secondary-color-text">Enviamos um código para o seu WhatsApp. Por favor, digite o código para validar o seu celular!</h5>

                        <form action="">
                            <div class="row center spacing-buttons">
                                <div class="col s12 l6 offset-l3 spacing-buttons">
                                    <input placeholder="seu código" id="email" name="codigo" type="text" class="validate">
                                </div>
                                <div class="col s6 offset-s3">
                                    <div class="center">
                                        <button class="waves-effect waves-light btn">Fazer Depois</button>
                                    </div>
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
