<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${empty jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div class="none-profission">
                    <p class="center text-form-dados">
                        Nenhum pedido em disputa encontrado!
                    </p>
                    <p class="center">
                        Um novo pedido pode chegar aqui a qualquer momento.
                    </p>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:forEach var="job" items="${jobs}">
    <div class="container">
        <div class="row">
            <div class="col s12 spacing-buttons">
                <div style="border: solid 1px black">
                    <div class="secondary-background-color">
                        <div class="row">
                            <div class="col s8 offset-s2">
                                <h5 class="center white-text"> ${job.jobRequest.expertise.name} </h5>
                            </div>
                            <div class="col s2">
                                <h5 class="right white-text badge-service"> ${job.jobRequest.totalCandidates}/${job.jobRequest.quantityCandidatorsMax}</h5>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">person</i>
                            </p>
                            <p class="center text-form-dados primary-color-text"> ${job.jobRequest.individual.name} </p>
                        </div>
                        <div class="col s4">
                            <p class="center center-align text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">location_on</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.jobRequest.individual.address.neighborhood}, ${job.jobRequest.individual.address.city.name}/${job.jobRequest.individual.address.city.state.uf}
                            </p>
                        </div>
                        <div class="col s4">
                            <p class="center text-form-dados primary-color-text">
                                <i class="material-icons small dark-color-text">access_time</i>
                            </p>
                            <p class="center text-form-dados primary-color-text">
                                    ${job.jobRequest.textualDate}
                            </p>
                        </div>
                    </div>
                    <blockquote class="light-blue lighten-5 info-headers">
                        <p>${job.jobRequest.description}</p>
                    </blockquote>
                    <div>
                        <div class="center">
                            <a href="" class="waves-effect waves-light btn spacing-buttons">
                                Detalhes
                            </a>

                            <c:if test="${job.hiredDate == null}">
                                <a href="#modal-hired" data-url="${pageContext.request.contextPath}/candidaturas/contratacao/${job.jobRequest.id}" data-name="${city.name}" class="waves-effect waves-light btn spacing-buttons modal-trigger">Contratação</a>
                            </c:if>
                            <a href="#modal-delete" data-url="${pageContext.request.contextPath}/candidaturas/${job.jobRequest.id}" data-name="${city.name}" class="waves-effect waves-light btn spacing-buttons red modal-trigger">Desistir</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div id="modal-delete" class="modal">
            <div class="modal-content">
                <form action="" method="post">

                    <input type="hidden" name="_method" value="DELETE"/>

                    <div class="modal-content">
                        <h4>Você tem certeza que deseja excluir <strong id="strong-name"></strong>?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Sim</button>
                    </div>
                </form>
            </div>
        </div>

        <div id="modal-hired" class="modal">
            <div class="modal-content center" style="padding: 45px !important;">
                <span id="confirmHiredButton" class="btn waves-effect waves-light gray">Confirmar contratação</span>
                <span id="confirmNotHiredButton" class="btn-flat waves-effect waves-light btn btn-gray">Não aceitar contratação</span>
                <form id="confirm-hired" action="" method="post" style="display: none">
                    <input type="hidden" name="_method" value="POST"/>
                    <input type="hidden" name="chosenByBudget" value="true"/>

                    <div class="modal-content">
                        <span>Adicione abaixo a data para a realização do serviço e confirme sua ação:</span>
                        <input type="date" id="hired-date-form-confirm" name="hiredDate">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Confirmar</button>
                    </div>
                </form>
                <form id="confirm-not-hired" action="" method="post" style="display: none">
                    <input type="hidden" name="_method" value="POST"/>
                    <input type="hidden" name="chosenByBudget" value="false"/>

                    <div class="modal-content">
                        <span>Pedido será salvo como não contratado, confirme sua ação!</span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="modal-close btn-flat waves-effect waves-light btn btn-gray">Cancelar</button>
                        <button type="submit" class="modal-close btn waves-effect waves-light gray">Confirmar</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<div class="container col s12 center-align">
    <t:pagination-tab-ajax pagination="${pagination}"></t:pagination-tab-ajax>
</div>

<script>
    $(document).ready(function() {
        $('.modal').modal({
            onOpenEnd: function (modal, trigger){
                var url = $(trigger).data('url');
                var name = $(trigger).data('name');

                modal = $(modal);
                var form = modal.find('form');
                form.attr('action', url);
                modal.find('#strong-name').text(name);
            }

        });

        $('#confirmHiredButton').click(function () {
            $("#confirm-hired").show();
            $("#confirm-not-hired").hide();
        });

        $('#confirmNotHiredButton').click(function () {
            $("#confirm-not-hired").show();
            $("#confirm-hired").hide();
        });
    });
</script>