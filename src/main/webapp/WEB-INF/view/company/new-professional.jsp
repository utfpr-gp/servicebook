<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template-side-nav title="ServiceBook - Adicionar profissional">
    <jsp:body>
        <main class="">
            <div class="row">
                <div class="col s12">
                    <h2 class="secondary-color-text">Meus Funcionários</h2>
                    <blockquote class="light-blue lighten-5 info-headers">
                        <p> Adicione novos funcionários em sua empresa.</p>
                    </blockquote>
                </div>

                <div class="center spacing-buttons">
                    <button class="waves-effect waves-light btn">
                        <a href="#modal-expertises" class="modal-trigger">
                            Adicionar novo funcionário
                        </a>
                    </button>
                </div>

                <div id="modal-expertises" class="modal">
                    <div class="modal-content ui-front">
                        <div class="row">
                            <div class="col s9">
                                <h4>Adicionar Funcionário</h4>
                            </div>
                            <div class="col s3">
                                <button class="modal-close modal-expertise-close right">
                                    <i class="material-icons">close</i>
                                </button>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col s12">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <label>Para adicionar um novo funcionário, é necessário que ele possua cadastro
                                            na plataforma.</label>
                                    </div>
                                </div>
                            </div>

                            <div class="col s12">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <input type="text" id="txtBusca" class=""
                                               placeholder="Endereço de e-mail ou nome">
                                    </div>
                                </div>
                            </div>

                            <div class="col s12">
                                <div class="row">
                                    <div class="input-field col s12">
                                        <i class="material-icons prefix">link</i>
                                        <label for="txtBusca">
                                            Convidar funcionário para fazer parte do ServiceBook.
                                            <a>Copiar link</a>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template-side-nav>
