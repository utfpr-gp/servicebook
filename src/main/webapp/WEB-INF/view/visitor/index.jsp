<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Servicebook - Início">
    <jsp:body>
        <main>
        <t:banner cities="${cities}"></t:banner>

        <t:search-bar></t:search-bar>
            <div class="container center-align">
                <div class="row">
                    <div class="col s12 cards-container">
                        <h4>QUAL É A QUANTIDADE DE SERVIÇOS OFERECIDOS?</h4>
                        <div class="col s3 card indigo primary_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalExpertises}</span>
                                <p>ESPECIALIDADES</p>
                            </div>
                        </div>
                        <div class="col s3 offset-s1 card indigo middle_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalProfessionals}</span>
                                <p>PROFISSIONAIS AUTÔNOMOS</p>
                            </div>
                        </div>
                        <div class="col s3 offset-s1 card indigo last_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalCompanies}</span>
                                <p>EMPRESAS</p>
                            </div>
                        </div>
                    </div>
                    <div class="col s12 cards-container">
                        <h4>QUAL É A QUANTIDADE DE CLIENTES INTERESSADOS?</h4>
                        <div class="col s3 card indigo primary_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalClients}</span>
                                <p>CLIENTES</p>
                            </div>
                        </div>
                        <div class="col s3 offset-s1 card indigo middle_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobContracted}</span>
                                <p>ANÚNCIOS</p>
                            </div>
                        </div>
                        <div class="col s3 offset-s1  card indigo last_card">
                            <div class="card-content white-text">
                                <span class="card-title">${totalJobRequests}</span>
                                <p>AVALIAÇÕES</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
