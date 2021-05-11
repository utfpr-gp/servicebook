<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Bem-vindo ao</h3>
                        <h3 class="header center logo-text">ServiceBook</h3>

                        <div class="row center">
                            <h3 class="center secondary-color-text">Receba solicitações de serviços de diferentes clientes todos os dias!</h3>
                            <div class="center">
                                <img src="assets/resources/images/hand-icon.png" width="300px">
                            </div>
                        </div>
                        <div class="col s6 m3 offset-m3 spacing-buttons">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="#!">Entrar</a>
                            </div>
                        </div>
                        <div class="col s6 m3 spacing-buttons">
                            <div class="center">
                                <a class="waves-effect waves-light btn" href="#!">Cadastrar</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
