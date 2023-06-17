<%@tag description="Servicebook - Banner template" pageEncoding="UTF-8" %>
<%@attribute name="cities" type="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="index-banner" class="parallax-container">
    <div class="section no-pad-bot">
        <div class="container">
            <c:if test="${not empty msg}">
                <div class="row">
                    <div class="col s12">
                        <div class="card-panel green lighten-1 msg-view center-align">
                            <span class="white-text">${msg}</span>
                        </div>
                    </div>
                </div>
            </c:if>
            <br><br>
            <h3 class="header center logo-text logo-text-parallax">ServiceBook</h3>
            <c:if test="${not empty cities}">
                <div class="row center">
                    <div class="col s10 offset-s1 m4 offset-m4  input-field">

                        <select id="select-city" class="white-text select-city">
                            <c:forEach var="city" items="${cities}">
                                <option value="${city.image}">${city.name} - ${city.state.uf}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </c:if>
            <h4 class="header center">O MELHOR PROFISSIONAL QUE VOCÊ PRECISA ESTÁ AQUI!</h4>
            <h4 class="header center">São + de 3000 profissionais cadastrados!</h4>
        </div>
    </div>
    <div class="parallax">
        <c:if test="${not empty cities}">
            <img id="img-city" src="${cities[0].image}" alt="Imagem das cidades">
        </c:if>
        <c:if test="${empty cities}">
            <img id="img-city" src="assets/resources/images/default.jpeg" alt="Imagem das cidades">
        </c:if>
    </div>
</div>