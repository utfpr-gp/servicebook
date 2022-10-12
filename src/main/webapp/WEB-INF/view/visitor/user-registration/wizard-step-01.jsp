<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:visitor title="Servicebook - Cadastro - Passo 1">
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

                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">
                            Qual o seu email?
                        </h3>
                        <h5 class="center secondary-color-text">
                            O seu email será utilizado para você acessar o sistema e receber informações sobre os
                            serviços!
                        </h5>

                        <form method="post" action="cadastrar-se/passo-1">
                            <div class="row">
                                <div class="input-field col s10 m8 l6 xl6 offset-s1 offset-m2 offset-l3 offset-xl3">
                                    <input id="email" name="email" type="text" value="${dto.email}" class="validate">
                                    <label for="email">Email</label>
                                </div>
                            </div>
                            <div class="col s6 m3 offset-m3 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray" href="bem-vindo">Voltar</a>
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
