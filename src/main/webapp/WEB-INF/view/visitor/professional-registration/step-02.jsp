<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Cadastro de Profissional">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Qual seu email?</h3>
                        <h5 class="center secondary-color-text">O seu email será utilizado para você acessar o sistema, assim você não precisará de senha!</h5>

                        <form action="">
                            <div class="row center spacing-buttons">
                                <div class="col s12 l6 offset-l3 spacing-buttons">
                                    <input placeholder="joao@email.com" id="email" name="email" type="text" class="validate">

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

