<%-- Formulaire partage ajout / modification. Les "name" des champs correspondent aux
     setters de Personne : BindParam construit l'objet tout seul ("sexe.id" -> setSexe). --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="p" value="${patient[0]}"/>
<c:set var="creation" value="${empty p.id}"/>
<c:set var="titre" value="${creation ? 'Nouveau patient' : 'Modifier un patient'}"/>
<c:set var="menu" value="patients"/>
<%@ include file="_entete.jspf" %>

<div style="max-width: 860px; margin: 0 auto;">
    <div class="entete-page">
        <div>
            <a href="${ctx}/patients" style="font-size: 14px; text-decoration: none;">&larr; Retour aux patients</a>
            <h1 style="margin-top: 6px;">${titre}</h1>
            <p class="sous-titre">Les champs marques <span class="requis">*</span> sont obligatoires.</p>
        </div>
    </div>

    <c:if test="${not empty erreur}">
        <div class="erreur"><c:out value="${erreur[0]}"/></div>
    </c:if>

    <form class="carte" method="post" action="${ctx}/patients/${creation ? 'ajouter' : 'modifier'}">
        <c:if test="${not creation}">
            <input type="hidden" name="id" value="${p.id}">
        </c:if>

        <div class="bloc">
            <p class="bloc-titre">Etat civil</p>
            <p class="bloc-aide">Identite du patient.</p>
            <div class="grille">
                <div>
                    <label for="nom">Nom <span class="requis">*</span></label>
                    <input id="nom" name="nom" value="<c:out value='${p.nom}'/>" required maxlength="100">
                </div>
                <div>
                    <label for="prenom">Prenom(s)</label>
                    <input id="prenom" name="prenom" value="<c:out value='${p.prenom}'/>" maxlength="150">
                </div>
                <div>
                    <label for="sexe">Sexe</label>
                    <select id="sexe" name="sexe.id">
                        <option value="">Non renseigne</option>
                        <c:forEach var="s" items="${sexes}">
                            <option value="${s.id}" ${p.sexe.id == s.id ? 'selected' : ''}><c:out value="${s.libelle}"/></option>
                        </c:forEach>
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
            </div>
        </div>

        <div class="bloc">
            <p class="bloc-titre">Coordonnees</p>
            <p class="bloc-aide">Pour joindre le patient.</p>
            <div class="grille">
                <div>
                    <label for="telephone">Telephone</label>
                    <input id="telephone" name="telephone" value="<c:out value='${p.telephone}'/>"
                           maxlength="30" placeholder="034 00 000 00">
                </div>
                <div class="pleine">
                    <label for="adresse">Adresse</label>
                    <input id="adresse" name="adresse" value="<c:out value='${p.adresse}'/>" maxlength="255">
                </div>
            </div>
        </div>

        <div class="bloc" style="border-bottom: none;">
            <p class="bloc-titre">Acces a l'application</p>
            <p class="bloc-aide">Le patient se connecte avec son email et ce mot de passe.</p>
            <div class="grille">
                <div>
                    <label for="email">Email <span class="requis">*</span></label>
                    <input type="email" id="email" name="email" value="<c:out value='${p.email}'/>"
                           required maxlength="150" placeholder="nom@exemple.mg">
                </div>
                <div>
                    <label for="motDePasse">Mot de passe <c:if test="${creation}"><span class="requis">*</span></c:if></label>
                    <input type="password" id="motDePasse" name="motDePasse" minlength="6"
                           autocomplete="new-password" ${creation ? 'required' : ''}>
                    <div class="aide">${creation ? '6 caracteres minimum.' : 'Laisser vide pour ne pas le changer.'}</div>
                </div>
            </div>
        </div>

        <div class="boutons">
            <a class="bouton secondaire" href="${ctx}/patients">Annuler</a>
            <button type="submit" class="bouton">${creation ? 'Ajouter le patient' : 'Enregistrer'}</button>
        </div>
    </form>
</div>

<%@ include file="_pied.jspf" %>
