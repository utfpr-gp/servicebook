<%@tag description="Template para paginação" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="pagination" type="br.edu.utfpr.servicebook.util.pagination.PaginationDTO" %>
<%@attribute name="relativePath" %>

<c:set var="relativePath" value="${pagination.route != null ? pagination.route:relativePath}"></c:set>

<div>
    <c:if test="${pagination.totalPages > 1}">
        <ul class="pagination">
            <li class="waves-effect ${(pagination.currentPage < 1) ? 'disabled': ''}">
                <a href="${pageContext.request.contextPath}${relativePath}?pag=${pagination.currentPage}">
                    <i class="material-icons">chevron_left</i>
                </a>
            </li>

            <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                <li class="waves-effect ${(pagination.currentPage + 1 == i) ? 'active': ''}">
                    <a href="${pageContext.request.contextPath}${relativePath}?pag=${i}">${i}</a>
                </li>
            </c:forEach>

            <li class="waves-effect ${(pagination.currentPage + 1 >= pagination.totalPages) ? 'disabled': ''}">
                <a href="${pageContext.request.contextPath}${relativePath}?pag=${pagination.currentPage + 2}">
                    <i class="material-icons">chevron_right</i>
                </a>
            </li>
        </ul>
    </c:if>
</div>

<script>
    $('.pagination li a').click(function (e) {
        e.preventDefault();

        if ($(this).parent().hasClass('disabled')) {
            return;
        }

        let url = $(this).attr("href");
        let href = window.location.hash;

        let urlAux = url.split('?pag=');
        let urlParams = new URLSearchParams(window.location.search);
        let id = (urlParams.has('id')) ? urlParams.get('id') : 0;
        url = urlAux[0] + '?id=' + id + '&pag=' + urlAux[1];

        $(href).load(url, function (result) {
        });
    });
</script>
