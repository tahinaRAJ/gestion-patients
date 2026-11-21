<%-- Formulaire partagé ajout / modification. Les "name" des champs correspondent aux
     setters de Personne : BindParam construit l'objet tout seul. --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="p" value="${patient[0]}"/>
<c:set var="creation" value="${empty p.id}"/>
<c:set var="titre" value="${creation ? 'Nouveau patient' : 'Modifier un patient'}"/>
<%@ include file="_entete.jspf" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h1>${titre}</h1>

<c:if test="${not empty erreur}">
    <div class="erreur"><c:out value="${erreur[0]}"/></div>
</c:if>

<form class="carte" style="padding: 24px;" method="post"
      action="${ctx}/patients/${creation ? 'ajouter' : 'modifier'}">
    <c:if test="${not creation}">
        <input type="hidden" name="id" value="${p.id}">
    </c:if>

    <div class="grille">
        <div>
            <label for="nom">Nom *</label>
            <input id="nom" name="nom" value="<c:out value='${p.nom}'/>" required maxlength="100">
        </div>
        <div>
            <label for="prenom">Prénom(s)</label>
            <input id="prenom" name="prenom" value="<c:out value='${p.prenom}'/>" maxlength="150">
        </div>
        <div>
            <label for="sexe">Sexe</label>
            <select id="sexe" name="sexe">
                <option value="">—</option>
                <option value="M" ${p.sexe == 'M' ? 'selected' : ''}>Masculin</option>
                <option value="F" ${p.sexe == 'F' ? 'selected' : ''}>Féminin</option>
            </select>
        </div>
        <div>
            <label for="dateNaissance">Date de naissance</label>
            <input type="date" id="dateNaissance" name="dateNaissance" value="${p.dateNaissance}">
        </div>
        <div>
            <label for="lieuNaissance">Lieu de naissance</label>
            <input id="lieuNaissance" name="lieuNaissance" value="<c:out value='${p.lieuNaissance}'/>" maxlength="100">
        </div>
        <div>
            <label for="telephone">Téléphone</label>
            <input id="telephone" name="telephone" value="<c:out value='${p.telephone}'/>" maxlength="30">
        </div>
        <div style="grid-column: 1 / -1;">
            <label for="adresse">Adresse</label>
            <input id="adresse" name="adresse" value="<c:out value='${p.adresse}'/>" maxlength="255">
        </div>
        <div>
            <label for="email">Email *</label>
            <input type="email" id="email" name="email" value="<c:out value='${p.email}'/>" required maxlength="150">
            <div class="aide">Sert d'identifiant de connexion au patient.</div>
        </div>
        <div>
            <label for="motDePasse">Mot de passe ${creation ? '*' : ''}</label>
            <input type="password" id="motDePasse" name="motDePasse" minlength="6"
                   autocomplete="new-password" ${creation ? 'required' : ''}>
            <div class="aide">${creation ? '6 caractères minimum.' : 'Laisser vide pour ne pas le changer.'}</div>
        </div>
    </div>

    <div class="boutons">
        <button type="submit" class="bouton">${creation ? 'Ajouter' : 'Enregistrer'}</button>
        <a class="bouton secondaire" href="${ctx}/patients">Annuler</a>
    </div>
</form>

<%@ include file="_pied.jspf" %>
