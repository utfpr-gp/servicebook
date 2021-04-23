<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:visitor title="Login">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">

                    <div class="row">
                        <div class="col s12 m10 offset-m1  l6 offset-l3 area-msg-email spacing-standard">
                            <h4 class="secondary-color-text center">Falta pouco!</h4>
                            <h5 class="white-text center">Enviamos um email para joao@gmail.com</h5>
                            <h5 class="white-text center">Acesse o link enviado para ter acesso a sua conta.</h5>

                        </div>
                        <div class="col s12">
                            <div class="center">
                                <a href="../../index.html" class="waves-effect waves-light btn">ENTRAR</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
