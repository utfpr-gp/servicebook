<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:client title="Etapa 05">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">

                    <!--   Icon Section   -->
                    <div class="row">
                        <h4 class="center secondary-color-text">Anexe fotos do serviço, se houver.</h4>

                        <div class="row center">
                            <div class="rowspacing-standard">
                                <form>
                                    <div class="col s12 m6 offset-m3 l4 offset-l4 ">
                                        <div class="file-field input-field">
                                            <div class="btn">
                                                <span>Choose File</span>
                                                <input type="file"  multiple>
                                            </div>
                                            <div class="file-path-wrapper">
                                                <input class="file-path validate" placeholder="image.jpg"  type="text">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <a class="waves-effect waves-light btn btn-gray" href="#!">Voltar</a>
                                        </div>
                                    </div>
                                    <div class="col s6 m6 spacing-buttons">
                                        <div class="center">
                                            <button class="waves-effect waves-light btn" >Próximo</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</t:client>
