<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Patients"/>
<%@ include file="_entete.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="entete-page">
    <h1>Patients <span class="badge">${patients.size()}</span></h1>
    <a class="bouton" href="${ctx}/patients/nouveau">+ Nouveau patient</a>
</div>

<div class="carte tableau">
    <c:choose>
        <c:when test="${empty patients}">
            <div class="vide">Aucun patient enregistré.</div>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Id</th><th>Nom</th><th>Prénom</th><th>Né(e) le</th><th>À</th><th>Sexe</th>
                    <th>Adresse</th><th>Téléphone</th><th>Email</th><th></th>
                </tr>
                <c:forEach var="p" items="${patients}">
                    <tr>
                        <td>${p.id}</td>
                        <td><c:out value="${p.nom}"/></td>
                        <td><c:out value="${p.prenom}"/></td>
                        <td><c:out value="${p.dateNaissance}"/></td>
                        <td><c:out value="${p.lieuNaissance}"/></td>
                        <td><c:out value="${p.sexe}"/></td>
                        <td><c:out value="${p.adresse}"/></td>
                        <td><c:out value="${p.telephone}"/></td>
                        <td><c:out value="${p.email}"/></td>
                        <td class="actions">
                            <a class="bouton secondaire petit" href="${ctx}/patients/modifier?id=${p.id}">Modifier</a>
                            <form class="en-ligne" action="${ctx}/patients/supprimer" method="post"
                                  onsubmit="return confirm('Supprimer ce patient ?');">
                                <input type="hidden" name="id" value="${p.id}">
                                <button type="submit" class="bouton danger petit">Supprimer</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_pied.jspf" %>
