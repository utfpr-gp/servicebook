<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:visitor title="Servicebook - Cadastro - Passo 7">
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
                        <h3 class="row center secondary-color-text">
                            Escolha uma ou mais especialidades!
                        </h3>
                        <h5 class="row center secondary-color-text">
                            Se você tem alguma habilidade e deseja receber solicitações para execução de serviços, então
                            nos informe suas especialidades.
                        </h5>

                        <form method="post" action="cadastrar-se/passo-7">
                            <div class="row center spacing-buttons">
                                <h4><strong>Categoria: Construção e Reformas</strong></h4>
                                <div class="col s12 l4 offset-l4 spacing-buttons">
                                    <div class="none-profission">
                                        <h5 class="center">Nenhuma profissão foi selecionada!</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s12 l4 spacing-buttons">
                                    <div class="active-profission">
                                        <h4 class="center"><strong>ELETRICISTA</strong></h4>
                                        <h5 class="center">Serviços elétricos.</h5>
                                    </div>
                                </div>
                                <div class="col s12 l4 spacing-buttons">
                                    <div class="active-profission">
                                        <h4 class="center"><strong>PEDREIRO</strong></h4>
                                        <h5 class="center">Alvenaria.</h5>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="center">
                                    <a href="#modal1" class="waves-effect waves-light btn modal-trigger">
                                        ADICIONAR ESPECIALIDADE
                                    </a>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col s6 m3 offset-m3 spacing-buttons">
                                    <div class="center">
                                        <a href="cadastrar-se?passo=6" class="waves-effect waves-light btn btn-gray">
                                            Voltar
                                        </a>
                                    </div>
                                </div>
                                <div class="col s6 m3 spacing-buttons">
                                    <div class="center">
                                        <button type="submit" class="waves-effect waves-light btn">Fim</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <div id="modal1" class="modal modal-fixed-footer">
                <div class="modal-content">
                    <h4 class="center secondary-color-text">Escolha uma ou mais profissões!</h4>
                    <div>
                        <form method="post" action="cadastrar-se/passo-7">
                            <div class="row">
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox"/>
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>ELETRICISTA</strong></h4>
                                                <h5 class="center">Instalações elétricas.</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox"/>
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>ENCANADOR</strong></h4>
                                                <h5 class="center">Tubulações.</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox"/>
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>JARDINEIRO</strong></h4>
                                                <h5 class="center">Jardinagem.</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col s12 l6 spacing-buttons">
                                    <div class="selected-profission">
                                        <div class="row">
                                            <div class="area-check-profission col s2">
                                                <label>
                                                    <input type="checkbox" checked/>
                                                    <span></span>
                                                </label>
                                            </div>
                                            <div class="col s10">
                                                <h4 class="center"><strong>PEDREIRO</strong></h4>
                                                <h5 class="center">Alvenaria.</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="modal-footer">
                    <a class="modal-close waves-effect waves-green btn-flat">Fechar</a>
                </div>
            </div>
        </main>

    </jsp:body>
</t:visitor>
