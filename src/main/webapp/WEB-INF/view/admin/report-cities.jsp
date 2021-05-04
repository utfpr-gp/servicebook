<%--
  Created by IntelliJ IDEA.
  User: brunow
  Date: 20/04/2021
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="a" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>


<a:admin title="Cidades">
    <jsp:body>

        <main>
            <div class="container">
                <div class="section">
                    <div class="row">
                        <h3 class="center primary-color-text">Ordem de serviços</h3>
                        <div class="col s12 l8 offset-l2">
                            <table class="striped">
                                <thead>
                                <tr>
                                    <th>NOME</th>
                                    <th>ESTADO</th>
                                    <th>EDITAR</th>
                                    <th>EXCLUIR</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>Guarapuava</td>
                                    <td>Paraná</td>
                                    <td><a class="btn-floating btn-medium waves-effect waves-light blue"><i class="material-icons">edit</i></a></td>
                                    <td><a class="btn-floating btn-medium waves-effect waves-light red"><i class="material-icons">delete_forever</i></a></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </main>

    </jsp:body>
</a:admin>
