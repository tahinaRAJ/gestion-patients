<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Personnes"/>
<%@ include file="_entete.jspf" %>

<h1>Personnes</h1>

<h2>Médecins <span class="badge">${medecins.size()}</span></h2>
<div class="carte tableau">
    <c:choose>
        <c:when test="${empty medecins}">
            <div class="vide">Aucun médecin.</div>
        </c:when>
        <c:otherwise>
            <table>
                <tr><th>Nom</th><th>Prénom</th><th>Sexe</th><th>Téléphone</th><th>Email</th></tr>
                <c:forEach var="m" items="${medecins}">
                    <tr>
                        <td><c:out value="${m.nom}"/></td>
                        <td><c:out value="${m.prenom}"/></td>
                        <td><c:out value="${m.sexe}"/></td>
                        <td><c:out value="${m.telephone}"/></td>
                        <td><c:out value="${m.email}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<h2>
    <c:out value="${sessionScope.role == 'MEDECIN' ? 'Patients' : 'Ma fiche'}"/>
    <c:if test="${sessionScope.role == 'MEDECIN'}"><span class="badge">${patients.size()}</span></c:if>
</h2>
<div class="carte tableau">
    <c:choose>
        <c:when test="${empty patients}">
            <div class="vide">Aucun patient.</div>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Nom</th><th>Prénom</th><th>Né(e) le</th><th>À</th><th>Sexe</th>
                    <th>Adresse</th><th>Téléphone</th><th>Email</th>
                </tr>
                <c:forEach var="p" items="${patients}">
                    <tr>
                        <td><c:out value="${p.nom}"/></td>
                        <td><c:out value="${p.prenom}"/></td>
                        <td><c:out value="${p.dateNaissance}"/></td>
                        <td><c:out value="${p.lieuNaissance}"/></td>
                        <td><c:out value="${p.sexe}"/></td>
                        <td><c:out value="${p.adresse}"/></td>
                        <td><c:out value="${p.telephone}"/></td>
                        <td><c:out value="${p.email}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_pied.jspf" %>
