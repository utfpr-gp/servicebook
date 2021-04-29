<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:client title="Detalhes de Contato">
    <jsp:body>

        <main>
            <div class="section no-padding">
                <div class="row">
                    <div class="row no-margin col s12 area-detail-contact">
                        <div class="col s12 icons-area-request">
                            <div class="row">
                                <div class="col s4 star-icons dark-color-text">
                                    <i class="material-icons ">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star</i>
                                    <i class="material-icons">star_border</i>
                                    <i class="material-icons">star_border</i>
                                </div>
                                <div class="col s4 center">
                                    <i class="material-icons dark-color-text">check_circle</i>
                                </div>
                                <div class="col s4 right">
                                    <div class="right">
                                        <span>17</span> <i class="material-icons icon-like-area-request dark-color-text">thumb_up</i>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col s12 center">
                            <svg style="width:200px;height:200px" viewBox="0 0 24 24">
                                <path class="dark-color-icon" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                            </svg>
                        </div>
                    </div>
                    <div class="col s12 no-padding">
                        <div class="center title-card-resquest">
                            <P class="no-margin">PEDRO DE SOUZA</P>
                        </div>
                    </div>
                    <div class="col s12 m6  text-info-request">
                        <p class="contact-item center"><i class="material-icons dark-color-text">email</i> pedro.pedreiro@mail.com</p>
                    </div>
                    <div class="col s12 m6  text-info-request">
                        <p class="contact-item center"><i class="material-icons dark-color-text">phone</i> (42) 9 9999-9090</p>
                    </div>
                    <div class="col s12">
                        <div class="container secondary-color-text  text-info-request">
                            <p>Após entrar em contato com o profissional e contratar o serviço, nos informe que foi este o profissional contratado para que você possa avaliá-lo  após a realização do serviço</p>
                        </div>
                    </div>
                    <div class="col s12">
                        <div class="container">
                            <h4>18 avaliações</h4>
                            <blockquote class="cyan lighten-5"><p>Muito bom profissional</p></blockquote>
                            <blockquote class="cyan lighten-5"><p>Muito bom profissional</p></blockquote>
                            <blockquote class="red lighten-4"><p>pèssimo</p></blockquote>
                        </div>
                    </div>
                    <div class="col s4 offset-s4  spacing-standard">
                        <div class="container">
                            <div class="row">
                                <div class="col s10 offset-s1 m6 spacing-buttons">
                                    <div class="center">
                                        <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                    </div>
                                </div>
                                <div class="col s10 offset-s1 m6 spacing-buttons">
                                    <div class="center">
                                        <a class="waves-effect waves-light btn" href="#!">Contratar</a>
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
