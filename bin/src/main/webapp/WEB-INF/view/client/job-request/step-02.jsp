<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:client title="Etapa 02">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center secondary-color-text">Precisa para quando?</h3>
                        <form>
                            <div class="row center">
                                <div class="col col s12 m6 offset-m3 l4 offset-l4 input-field">
                                    <select class="white-text select-city">
                                        <option value="0" selected><p class="white-text">Hoje </p></option>
                                        <option value="1"><p>Amanhã</p></option>
                                        <option value="2"><p>Esta semana</p></option>
                                        <option value="3"><p>Próxima semana</p></option>
                                        <option value="4"><p>Este mês</p></option>
                                        <option value="5"><p>Próxima mês</p></option>
                                    </select>
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                </div>
                            </div>
                            <div class="col s6 m6 spacing-buttons">
                                <div class="center">
                                    <button class="waves-effect waves-light btn">Próximo</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            </div>
        </main>

    </jsp:body>
</t:client>
