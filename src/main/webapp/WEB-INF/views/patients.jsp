<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Patients"/>
<c:set var="menu" value="patients"/>
<%@ include file="_entete.jspf" %>

<div class="entete-page">
    <div>
        <h1>Patients</h1>
        <p class="sous-titre">${patients.size()} patient(s) enregistre(s).</p>
    </div>
    <a class="bouton" href="${ctx}/patients/nouveau">+ Nouveau patient</a>
</div>

<div class="carte tableau">
    <c:choose>
        <c:when test="${empty patients}">
            <div class="vide">
                Aucun patient enregistre.<br><br>
                <a class="bouton" href="${ctx}/patients/nouveau">+ Ajouter le premier patient</a>
            </div>
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <th>Patient</th><th>Naissance</th><th>Sexe</th><th>Adresse</th><th>Telephone</th>
                    <th style="text-align: right;">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="p" items="${patients}">
                    <tr>
                        <td>
                            <div class="personne">
                                <span class="avatar">${p.initiales}</span>
                                <div>
                                    <div class="personne-nom"><c:out value="${p.nomComplet}"/></div>
                                    <div class="personne-email"><c:out value="${p.email}"/></div>
                                </div>
                            </div>
                        </td>
                        <td>
                            <div>${p.dateNaissanceAffichee}</div>
                            <div class="personne-email"><c:out value="${p.lieuNaissance}"/></div>
                        </td>
                        <td>
                            <c:if test="${not empty p.sexe}">
                                <span class="badge ${p.sexe.libelle}"><c:out value="${p.sexe.libelle}"/></span>
                            </c:if>
                        </td>
                        <td class="discret"><c:out value="${p.adresse}"/></td>
                        <td class="discret"><c:out value="${p.telephone}"/></td>
                        <td>
                            <div class="actions">
                                <a class="bouton secondaire petit" href="${ctx}/patients/modifier?id=${p.id}">Modifier</a>
                                <form class="en-ligne" action="${ctx}/patients/supprimer" method="post"
                                      onsubmit="return confirm('Supprimer ce patient ?');">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <button type="submit" class="bouton danger petit">Supprimer</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_pied.jspf" %>
