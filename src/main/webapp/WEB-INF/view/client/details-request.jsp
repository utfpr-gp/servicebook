<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:client title="Detalhes da Solicitação">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text center spacing-standard tertiary-color-text">Escolha um Pedreiro!</h2>
                        </div>

                        <div class="col s12 m6 tertiary-color-text text-info-request">
                            <p>Preciso de um pedreiro para trocar o piso....</p>
                            <p>Pedido expedido em 08/04/2021</p>
                        </div>
                        <div class="col s12 m6 l3">
                            <div class="center">
                                <a href="#!" class="spacing-buttons waves-effect waves-light btn">Parar de receber propostas</a>
                            </div>
                        </div>
                        <div class="col s12 m6 l3">
                            <div class="center">
                                <a href="#!" class="spacing-buttons waves-effect waves-light btn">Excluir o pedido</a>
                            </div>
                        </div>
                        <div class="col s12 tertiary-color-text  text-info-request">
                            <hr class="hr-request-area">
                            <p>Entre em contato com um ou mais profissionais que se interessaram em realizar o serviço para marcar um orçamento.</p>
                            <p>4 profissionais responderam a sua solicitação:</p>
                        </div>

                        <div class="col s12 l4">
                            <div class="card-panel card-resquest-first">
                                <div class="row">
                                    <div class="col s12 icons-area-request">
                                        <div class="left star-icons dark-color-text">
                                            <i class="material-icons ">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star_border</i>
                                            <i class="material-icons">star_border</i>
                                        </div>
                                        <div class="left">
                                            <i class="material-icons green-text">check_circle</i>
                                        </div>
                                        <div class="right">
                                            <span class="dark-color-text">17</span> <i class="material-icons icon-like-area-request dark-color-text">thumb_up</i>
                                        </div>

                                    </div>
                                    <div class="col s12 center">
                                        <svg style="width:150px;height:150px" viewBox="0 0 24 24">
                                            <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                        </svg>
                                    </div>
                                    <div class="col s12">
                                        <div class="center title-card-resquest">
                                            <P>PEDRO DE SOUZA</P>
                                        </div>
                                        <p class="contact-item"><i class="material-icons dark-color-text">email</i> pedro.pedreiro@mail.com</p>
                                        <p class="contact-item"><i class="material-icons dark-color-text">phone</i> (42) 9 9999-9090</p>
                                        <div class="center">
                                            <p><a class="tertiary-color-text " href="#!">Detalhes</a></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col s12 l4">
                            <div class="card-panel card-resquest">
                                <div class="row">
                                    <div class="col s12 icons-area-request">
                                        <div class="left star-icons dark-color-text">
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star_border</i>
                                            <i class="material-icons">star_border</i>
                                        </div>
                                        <div class="left">
                                            <i class="material-icons green-text ">check_circle</i>
                                        </div>
                                        <div class="right">
                                            <span class="dark-color-text">17</span> <i class="material-icons icon-like-area-request dark-color-text">thumb_up</i>
                                        </div>

                                    </div>
                                    <div class="col s12 center">
                                        <svg style="width:150px;height:150px" viewBox="0 0 24 24">
                                            <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                        </svg>
                                    </div>
                                    <div class="col s12">
                                        <div class="center title-card-resquest">
                                            <P>PEDRO DE SOUZA</P>
                                        </div>
                                        <p class="contact-item"><i class="material-icons dark-color-text">email</i> pedro.pedreiro@mail.com</p>
                                        <p class="contact-item"><i class="material-icons dark-color-text">phone</i> (42) 9 9999-9090</p>
                                        <div class="center">
                                            <p><a class="tertiary-color-text" href="#!">Detalhes</a></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="col s12 l4">
                            <div class="card-panel card-resquest">
                                <div class="row">
                                    <div class="col s12 icons-area-request ">
                                        <div class="left star-icons dark-color-text">
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star</i>
                                            <i class="material-icons">star_border</i>
                                            <i class="material-icons">star_border</i>
                                        </div>
                                        <div class="left">
                                            <i class="material-icons green-text">check_circle</i>
                                        </div>
                                        <div class="right">
                                            <span>17</span> <i class="material-icons icon-like-area-request dark-color-text">thumb_up</i>
                                        </div>

                                    </div>
                                    <div class="col s12 center">
                                        <svg style="width:150px;height:150px" viewBox="0 0 24 24">
                                            <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                                        </svg>
                                    </div>
                                    <div class="col s12">
                                        <div class="center title-card-resquest">
                                            <P>PEDRO DE SOUZA</P>
                                        </div>
                                        <p class="contact-item"><i class="material-icons dark-color-text">email</i> pedro.pedreiro@mail.com</p>
                                        <p class="contact-item"><i class="material-icons dark-color-text">phone</i> (42) 9 9999-9090</p>
                                        <div class="center">
                                            <p><a class="tertiary-color-text" href="#!">Detalhes</a></p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>
