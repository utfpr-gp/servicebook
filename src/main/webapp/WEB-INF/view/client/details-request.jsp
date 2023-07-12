<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" scope="request"/>
<jsp:useBean id="jobRequest" type="br.edu.utfpr.servicebook.model.dto.JobRequestFullDTO" scope="request"/>
<jsp:useBean id="candidates" type="java.util.List<br.edu.utfpr.servicebook.model.dto.JobCandidateDTO>" scope="request"/>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/client/client.css" rel="stylesheet">
</head>


<t:template-side-nav title="Detalhes da Solicitação" userInfo="${userInfo}">
    <jsp:body>

        <div class="row">
            <div class="col s12">
                <div class="section">
                    <div class="row">
                        <!-- Caso não haja nenhum candidato -->
                        <c:if test="${empty candidates}">
                            <div class="col s12 center">
                                <i class="material-icons large"> sentiment_dissatisfied </i>
                                <h2 class="secondary-color-text"> Não há nenhum candidato.</h2>
                            </div>
                        </c:if>
                        <!-- Fim Caso não haja nenhum candidato -->

                        <!-- Cabeçalho da listagem de candidatos -->
                        <div>
                            <c:if test="${not empty candidates}">
                                <div class="col s12">
                                    <h2 class="secondary-color-text spacing-standard tertiary-color-text">
                                        Escolha um ${expertise.name}!</h2>
                                </div>
                            </c:if>
                            <div class="col s12 m6 tertiary-color-text description-job  text-info-request">
                                <p>${jobRequest.description}</p>
                                <p>Pedido expedido em ${jobRequest.dateTarget}</p>
                            </div>

                            <c:if test="${jobRequest.status == 'AVAILABLE'}">
                                <div class="col s12 m6 l3">
                                    <div class="center">
                                        <a href="#modal-close"
                                           class="spacing-buttons waves-effect waves-light btn modal-trigger">Parar
                                            de receber propostas</a>
                                    </div>
                                </div>
                            </c:if>

                            <c:if test="${jobRequest.status == 'DOING'}">
                                <div class="col s12 m6 l3">
                                    <div class="center">
                                        <a id="closeJobButton" href="#modal-close-job"
                                           class="spacing-buttons waves-effect waves-light btn modal-trigger">PEDIDO
                                            FINALIZADO</a>
                                    </div>
                                </div>

                                <div id="modal-close-job" class="modal">
                                    <div class="modal-content">
                                        <form action="minha-conta/cliente/informa-finalizado/${jobRequest.id}"
                                              method="post">
                                            <input type="hidden" name="_method" value="PATCH"/>

                                            <div class="modal-content" id="modal-content-confirm-hired">
                                                <h5>Finalização do pedido</h5>

                                                <label>
                                                    <input id="confirmCloseJob" name="isQuit" type="radio"
                                                           value="true"/>
                                                    <span>Serviço foi finalizado</span>
                                                </label>

                                                <label>
                                                    <input id="notConfirmCloseJob" name="isQuit"
                                                           type="radio" value="false"/>
                                                    <span>Serviço não foi finalizado</span>
                                                </label>
                                            </div>

                                            <div class="modal-footer">
                                                <button type="button"
                                                        class="modal-close btn-flat waves-effect waves-light btn btn-gray">
                                                    Cancelar
                                                </button>
                                                <button type="submit" disabled
                                                        class="modal-close btn waves-effect waves-light gray"
                                                        id="confirm-job-modal-button">Sim
                                                </button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </c:if>

                            <div class="col s12 m6 l3">
                                <div class="center">
                                    <a
                                            href="#modal-delete"
                                            data-url="${pageContext.request.contextPath}/minha-conta/cliente/desistir/${jobRequest.id}"
                                            class="waves-effect waves-light btn spacing-buttons red modal-trigger"
                                    >Excluir</a
                                    >
                                </div>
                            </div>
                        </div>

                        <!-- Subtítulo da listagem -->
                        <div class="col s12 tertiary-color-text description-orcamento text-info-request">
                            <hr class="hr-request-area">
                            <c:if test="${jobRequest.status == 'AVAILABLE'}">
                                <p>Entre em contato com um ou mais profissionais que se interessaram em
                                    realizar o serviço para marcar um orçamento.</p>
                                <c:if test="${candidates.size() > 1}">
                                    <p>${candidates.size()} profissionais responderam a sua
                                        solicitação:</p>
                                </c:if>
                                <c:if test="${candidates.size() == 1}">
                                    <p>${candidates.size()} profissional respondeu a sua
                                        solicitação:</p>
                                </c:if>
                            </c:if>
                            <c:if test="${jobRequest.status == 'BUDGET'}">
                                <p>Solicite e analise o(s) orçamento(s) para escolher o profissional que
                                    melhor atende a sua necessidade.</p>
                            </c:if>
                            <c:if test="${jobRequest.status == 'BUDGET'}">
                                <p>Aguarde a confirmação do profissional para realizar o serviço.</p>
                            </c:if>
                            <c:if test="${jobRequest.status == 'TO_DO'}">
                                <p>O profissional confirmou que realizará o serviço. Você poderá avaliar
                                    o profissional após a expiração da data combinada.</p>
                            </c:if>
                            <c:if test="${jobRequest.status == 'DOING'}">
                                <p>Conforme a data de agendamento, este é o profissional que está
                                    realizando o serviço.
                                    Quando ele finalizar o serviço, informe o término e também faça a
                                    avaliação do profissional.</p>
                            </c:if>
                            <c:if test="${jobRequest.status == 'CLOSED'}">
                                <p>Este é o profissional que realizou o serviço.</p>
                            </c:if>
                        </div>
                        <!-- Fim do subtítulo da listagem -->

                        <!-- Listagem de candidatos -->
                        <c:forEach var="c" items="${candidates}">
                            <div class="col s12 l6 xl4">
                                <t:professional-order-card jobCandidate="${c}"/>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <!-- Fim conteúdo -->

        <!-- Modal -->
        <div id="modal-close" class="modal">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/minha-conta/meus-pedidos/encerra-pedido/${jobRequest.id}"
                      method="post">
                    <input type="hidden" name="_method" value="PATCH"/>
                    <div class="modal-content">
                        <h4>Você tem certeza que deseja encerrar o recebimento de propostas para este pedido? Não
                            será possível reativar depois</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">
                            Cancelar
                        </button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                    </div>
                </form>
            </div>
        </div>
        <div id="modal-delete" class="modal">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/minha-conta/cliente/desistir/${jobRequest.id}"
                      method="post">
                    <input type="hidden" name="_method" value="DELETE"/>

                    <div class="modal-content">
                        <h4>
                            Você tem certeza que deseja excluir? Entre em contato com o
                            profissional informando sobre sua decisão!
                        </h4>
                    </div>
                    <div class="modal-footer">
                        <button
                                type="button"
                                class="modal-close btn-flat waves-effect waves-light btn btn-gray"
                        >
                            Cancelar
                        </button>
                        <button
                                type="submit"
                                class="modal-close btn waves-effect waves-light gray"
                        >
                            Sim, excluir!
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <!-- Fim Modal -->
        </main>
    </jsp:body>
</t:template-side-nav>
<script>
    $(document).ready(function () {
        $('.modal').modal({
            onOpenEnd: function (modal, trigger) {
                let url = $(trigger).data('url');
                let name = $(trigger).data('name');

                modal = $(modal);
                let form = modal.find('form');
                form.attr('action', url);
                modal.find('#strong-name').text(name);

                $('#confirmCloseJob').click(function () {
                    $('#confirm-job-modal-button').attr('disabled', false);
                });

                $('#notConfirmCloseJob').click(function () {
                    $('#confirm-job-modal-button').attr('disabled', false);
                });
            }
        });
    });
</script>

