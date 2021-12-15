<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:professional title="Meu anúncio">
    <jsp:body>

        <main>
            <div class="row">
                <div class="container">
                    <div class="row">
                        <h3>Nos conte sobre o seu perfil profissional</h3>
                        <h6>Descreva suas experiências, sua forma de trabalho e especialidades. Enfim, este é o seu espaço para o marketing profissional.</h6>
                    </div>
                    <c:if test="${professional.description == null}">
                        <div class="row">
                            <form class="col s12">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <textarea cols="10" rows="5" class="browser-default">${professional.description}</textarea>
                                    </div>
                                    <button class="waves-effect waves-light btn">salvar</button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                    <c:if test="${professional.description != null}">
                        <div class="row">
                            <p>${professional.description}</p>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>

    </jsp:body>
</t:professional>
