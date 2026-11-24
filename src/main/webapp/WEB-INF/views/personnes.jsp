<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Personnes"/>
<c:set var="menu" value="personnes"/>
<%@ include file="_entete.jspf" %>
<c:set var="estMedecin" value="${sessionScope.role == 'MEDECIN'}"/>

<div class="entete-page">
    <div>
        <h1>Personnes</h1>
        <p class="sous-titre">Medecins de l'etablissement et ${estMedecin ? 'patients suivis' : 'votre fiche'}.</p>
    </div>
    <c:if test="${estMedecin}">
        <a class="bouton" href="${ctx}/patients/nouveau">+ Nouveau patient</a>
    </c:if>
</div>

<div class="stats">
    <div class="carte stat">
        <div class="stat-icone bleu">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                <path d="M6 3v6a6 6 0 0 0 12 0V3M12 15v2a4 4 0 0 0 8 0v-3"/><circle cx="20" cy="12" r="2"/>
            </svg>
        </div>
        <div>
            <div class="stat-valeur">${medecins.size()}</div>
            <div class="stat-libelle">Medecins</div>
        </div>
    </div>
    <c:if test="${estMedecin}">
        <div class="carte stat">
            <div class="stat-icone">
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                     stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                    <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/>
                    <path d="M23 21v-2a4 4 0 0 0-3-3.87M16 3.13a4 4 0 0 1 0 7.75"/>
                </svg>
            </div>
            <div>
                <div class="stat-valeur">${patients.size()}</div>
                <div class="stat-libelle">Patients</div>
            </div>
        </div>
    </c:if>
</div>

<div class="section">
    <div class="section-titre"><h2>Medecins</h2><span class="badge gris">${medecins.size()}</span></div>
    <div class="carte tableau">
        <c:choose>
            <c:when test="${empty medecins}">
                <div class="vide">Aucun medecin.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead><tr><th>Medecin</th><th>Sexe</th><th>Telephone</th></tr></thead>
                    <tbody>
                    <c:forEach var="m" items="${medecins}">
                        <tr>
                            <td>
                                <div class="personne">
                                    <span class="avatar medecin">${m.initiales}</span>
                                    <div>
                                        <div class="personne-nom">Dr <c:out value="${m.nomComplet}"/></div>
                                        <div class="personne-email"><c:out value="${m.email}"/></div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <c:if test="${not empty m.sexe}">
                                    <span class="badge ${m.sexe.libelle}"><c:out value="${m.sexe.libelle}"/></span>
                                </c:if>
                            </td>
                            <td class="discret"><c:out value="${m.telephone}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="section">
    <div class="section-titre">
        <h2>${estMedecin ? 'Patients' : 'Ma fiche'}</h2>
        <c:if test="${estMedecin}"><span class="badge gris">${patients.size()}</span></c:if>
    </div>
    <div class="carte tableau">
        <c:choose>
            <c:when test="${empty patients}">
                <div class="vide">Aucun patient.</div>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr><th>Patient</th><th>Naissance</th><th>Sexe</th><th>Adresse</th><th>Telephone</th></tr>
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
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="_pied.jspf" %>
