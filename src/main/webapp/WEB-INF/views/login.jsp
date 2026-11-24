<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Connexion"/>
<%@ include file="_entete.jspf" %>

<div class="centre">
    <div class="carte boite">
        <div class="logo">
            <span class="logo-marque">
                <svg width="18" height="18" viewBox="0 0 24 24" fill="#fff" aria-hidden="true">
                    <path d="M9 3h6v6h6v6h-6v6H9v-6H3V9h6z"/>
                </svg>
            </span>
            Gestion des patients
        </div>
        <h1>Bon retour</h1>
        <p class="sous-titre">Connectez-vous pour acceder a votre espace.</p>

        <c:if test="${not empty erreur}">
            <div class="erreur"><c:out value="${erreur[0]}"/></div>
        </c:if>

        <form action="${ctx}/login" method="post">
            <div class="champ">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="<c:out value='${email[0]}'/>"
                       placeholder="nom@exemple.mg" required autofocus>
            </div>
            <div class="champ" style="margin-bottom: 24px;">
                <label for="motDePasse">Mot de passe</label>
                <input type="password" id="motDePasse" name="motDePasse" required>
            </div>
            <button type="submit" class="bouton" style="width: 100%; padding: 11px;">Se connecter</button>
        </form>
    </div>
</div>

<%@ include file="_pied.jspf" %>
