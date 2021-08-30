<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Cadastro - Passo 3">
    <jsp:body>

        <main>
            <div class="container">
                <c:if test="${not empty errors}">
                    <div class="card-panel red">
                        <c:forEach var="e" items="${errors}">
                            <span class="white-text">${e.getDefaultMessage()}</span><br>
                        </c:forEach>
                    </div>
                </c:if>

                <c:if test="${not empty msg}">
                    <div class="row">
                        <div class="col s12">
                            <div class="card-panel green lighten-1 msg-view center-align">
                                <span class="white-text">${msg}</span>
                            </div>
                        </div>
                    </div>
                </c:if>

                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Qual o seu número de celular ?
                        </h3>
                        <h4 class="center secondary-color-text">
                            (de preferência com whatsapp)
                        </h4>
                        <h5 class="center secondary-color-text">
                            Este número será usado para comunicação com os clientes
                            ou profissionais.
                        </h5>

                        <form method="post" action="cadastrar-se/passo-3">
                            <div class="row center spacing-buttons">
                                <div class="input-field col s12 l6 offset-l3 spacing-buttons">
                                    <input id="phoneNumber" name="phoneNumber" type="text" value="${dto.phoneNumber}"
                                           class="validate">
                                    <label for="phoneNumber">Telefone</label>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray"
                                       href="cadastrar-se?passo=2">
                                        Voltar
                                    </a>
                                </div>
                            </div>
                            <div class="col s6 m3 spacing-buttons">
                                <div class="center">
                                    <button type="submit" class="waves-effect waves-light btn">Próximo</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>

<script src="assets/libraries/jquery.mask.js"></script>

<script>
    $(document).ready(function () {
        $('#cpf').mask('000.000.000-00', {reverse: true});
    });
</script>
