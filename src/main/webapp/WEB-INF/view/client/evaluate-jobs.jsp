<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="userInfo" type="br.edu.utfpr.servicebook.util.UserTemplateInfo" scope="request"/>

<head>
    <!-- Funciona apenas com caminho absoluto porque é renderizado antes da tag base -->
    <link href="${pageContext.request.contextPath}/assets/resources/styles/client/client.css" rel="stylesheet">
</head>
<style>

    @import url(//netdna.bootstrapcdn.com/font-awesome/3.2.1/css/font-awesome.css);

</style>

<t:template-side-nav title="Avaliar Solicitação" userInfo="${userInfo}">
    <jsp:body>

        <div class="row">
            <t:message-box/>
            <div class="breadcrumbs">
                <a href="${pageContext.request.contextPath}/">Início</a> &gt;
                <a href="${pageContext.request.contextPath}/minha-conta/cliente#executados">Minhas Solicitações</a> &gt;
                <a href="${pageContext.request.contextPath}/minha-conta/cliente/meus-pedidos/${job.id}">Meu Pedido</a> &gt;
                Avaliar Serviço
            </div>

            <div class="col s12">
                <h4 class="secondary-color-text spacing-standard tertiary-color-text" style="margin-bottom: 0">
                    Avalie o Serviço!</h4>
                <p> Agora que o serviço foi concluído pelo profissional, você poderá avalia-lo. </p>
            </div>

            <div class="col s12 professional-data">
                <p> <b>Profissional: </b> <i>  ${professional.name} </i> </p>
                <p> <b>Descrição: </b>  <i> ${job.description} </i> </p>
                <p> <b>Data e Horário:</b> <i> ${jobContracted.hiredDate} </i> </p>
                <p> <b>Concluído:</b> <i> ${jobContracted.finishDate} </i> </p>

            </div>

            <form action="" method="post" enctype="multipart/form-data">
                <div class="col s12">
                    <h6> <b>PONTUALIDADE</b> </h6>
                    <fieldset class="rating">
                        <input type="radio" id="star5" name="punctuality" value="5" /><label class = "full" for="star5" title="Awesome - 5 stars"></label>
                        <input type="radio" id="star4half" name="punctuality" value="4.5" /><label class="half" for="star4half" title="Pretty good - 4.5 stars"></label>
                        <input type="radio" id="star4" name="punctuality" value="4" /><label class = "full" for="star4" title="Pretty good - 4 stars"></label>
                        <input type="radio" id="star3half" name="punctuality" value="3.5" /><label class="half" for="star3half" title="Meh - 3.5 stars"></label>
                        <input type="radio" id="star3" name="punctuality" value="3" /><label class = "full" for="star3" title="Meh - 3 stars"></label>
                        <input type="radio" id="star2half" name="punctuality" value="2.5" /><label class="half" for="star2half" title="Kinda bad - 2.5 stars"></label>
                        <input type="radio" id="star2" name="punctuality" value="2" /><label class = "full" for="star2" title="Kinda bad - 2 stars"></label>
                        <input type="radio" id="star1half" name="punctuality" value="1.5" /><label class="half" for="star1half" title="Meh - 1.5 stars"></label>
                        <input type="radio" id="star1" name="punctuality" value="1" /><label class = "full" for="star1" title="Sucks big time - 1 star"></label>
                        <input type="radio" id="starhalf" name="punctuality" value="0.5" /><label class="half" for="starhalf" title="Sucks big time - 0.5 stars"></label>
                    </fieldset>
                </div>

                <div class="col s12">
                    <h6> <b>QUALIDADE DO SERVIÇO</b> </h6>

                    <fieldset class="rating">
                        <input type="radio" id="star5-quality" name="quality" value="5" /><label class = "full" for="star5-quality" title="Awesome - 5 stars"></label>
                        <input type="radio" id="star4half-quality" name="quality" value="4.5" /><label class="half" for="star4half-quality" title="Pretty good - 4.5 stars"></label>
                        <input type="radio" id="star4-quality" name="quality" value="4" /><label class = "full" for="star4-quality" title="Pretty good - 4 stars"></label>
                        <input type="radio" id="star3half-quality" name="quality" value="3.5" /><label class="half" for="star3half-quality" title="Meh - 3.5 stars"></label>
                        <input type="radio" id="star3-quality" name="quality" value="3" /><label class = "full" for="star3-quality" title="Meh - 3 stars"></label>
                        <input type="radio" id="star2half-quality" name="quality" value="2.5" /><label class="half" for="star2half-quality" title="Kinda bad - 2.5 stars"></label>
                        <input type="radio" id="star2-quality" name="quality" value="2" /><label class = "full" for="star2-quality" title="Kinda bad - 2 stars"></label>
                        <input type="radio" id="star1half-quality" name="quality" value="1.5" /><label class="half" for="star1half-quality" title="Meh - 1.5 stars"></label>
                        <input type="radio" id="star1-quality" name="quality" value="1" /><label class = "full" for="star1-quality" title="Sucks big time - 1 star"></label>
                        <input type="radio" id="starhalf-quality" name="quality" value="0.5" /><label class="half" for="starhalf-quality" title="Sucks big time - 0.5 stars"></label>
                    </fieldset>
                </div>

                <div class="col s12 input-field">
                    <h6> <b>DEIXE SEU COMENTÁRIO</b> </h6>
                    <textarea id="textarea1" class="materialize-textarea" name="comment"></textarea>
                </div>

                <div class="col s6">
                    <h6> <b>ADICIONAR IMAGEM DO SERVIÇO</b> </h6>

                    <div class="file-field input-field">
                        <div class="btn">
                            <span>File</span>
                            <input type="file" name="pathImage">
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" type="text">
                        </div>
                    </div>
                </div>

                <div class="col s6">
                    <h6> <b>ADICIONAR VIDEO DO SERVIÇO</b> </h6>

                    <div class="file-field input-field">
                        <div class="btn">
                            <span>File</span>
                            <input type="file" name="pathVideo">
                        </div>
                        <div class="file-path-wrapper">
                            <input class="file-path validate" type="text">
                        </div>
                    </div>
                </div>

                <div class="col s12"  style="text-align: right">
                    <button type="submit" class="waves-effect waves-light btn mt-3"> ADICIONAR AVALIAÇÃO </button>
                </div>
            </form>

        </div>
        <!-- Fim Modal -->
        </main>
    </jsp:body>
</t:template-side-nav>
