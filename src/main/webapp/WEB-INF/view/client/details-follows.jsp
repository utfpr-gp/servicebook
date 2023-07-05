<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:template title="Meus Profissionais Favoritos">
    <jsp:body>
        <main class="container">
            <div class="row">

                <t:side-panel userInfo="${userInfo}"></t:side-panel>

                <div class="col m10 offset-m1 l9">
                    <div class="row">
                        <div class="col s12">
                            <h2 class="secondary-color-text">Meus profissionais favoritos</h2>
                            <div class="section">
                                <c:if test="${empty professionals}">
                                    <div class="center">
                                        <i class="material-icons" style="font-size: 120px;">mood_bad</i>
                                        <div class="flow-text">Não há resultados!</div>
                                    </div>
                                </c:if>
                                <c:forEach var="p" items="${professionals}">
                                    <!-- cartão -->
                                    <t:professional-card professional="${p}"></t:professional-card>
                                    <!-- fim cartão -->
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </jsp:body>
</t:template>
