<%@tag description="Template para paginação" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="pagination" type="br.edu.utfpr.crudmvcspring.util.pagination.PaginationDTO" %>
<%@attribute name="relativePath" %>
<!-- Se tem rota no DTO, usa. Caso contrário, usa a qual foi configurada como entrada na tag-->
<c:set var="relativePath" value="${pagination.route != null ? pagination.route:relativePath}"></c:set>

<div>
    <br>

    <c:if test="${pagination.totalPages > 1}">
        <ul class="pagination">
            <li class="waves-effect ${(pagination.currentPage < 1) ? 'disabled': ''}">
                <a href="${pageContext.request.contextPath}${relativePath}?pag=${pagination.currentPage}"><i class="material-icons">chevron_left</i></a>
            </li>

            <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                <li class="waves-effect ${(pagination.currentPage + 1 == i) ? 'active': ''}">
                    <a href="${pageContext.request.contextPath}${relativePath}?pag=${i}">${i}</a>
                </li>
            </c:forEach>
            <li class="waves-effect ${(pagination.currentPage + 1 >= pagination.totalPages) ? 'disabled': ''}"><a href="${pageContext.request.contextPath}${relativePath}?pag=${pagination.currentPage + 2}"><i class="material-icons">chevron_right</i></a></li>
        </ul>
    </c:if>
</div>