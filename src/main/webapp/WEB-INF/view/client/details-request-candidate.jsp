<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@ taglib
uri="http://www.springframework.org/tags/form" prefix="form" %> <%@taglib
prefix="t" tagdir="/WEB-INF/tags"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<t:client title="Detalhes do candidato">
  <jsp:body>
    <main>
      <div class="row">
        <t:side-panel individual="${user}"></t:side-panel>
        <div>${candidateId}</div>
        <div>${jobId}</div>
        <div></div>
      </div>
    </main>
  </jsp:body>
</t:client>
