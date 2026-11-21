<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titre" value="Connexion"/>
<%@ include file="_entete.jspf" %>

<div class="carte" style="max-width: 400px; margin: 60px auto; padding: 28px;">
    <h1>Connexion</h1>
    <p style="color: var(--discret); margin-top: -8px;">Gestion des patients</p>

    <c:if test="${not empty erreur}">
        <div class="erreur"><c:out value="${erreur[0]}"/></div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div style="margin-bottom: 14px;">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" value="<c:out value='${email[0]}'/>" required autofocus>
        </div>
        <div style="margin-bottom: 20px;">
            <label for="motDePasse">Mot de passe</label>
            <input type="password" id="motDePasse" name="motDePasse" required>
        </div>
        <button type="submit" class="bouton" style="width: 100%;">Se connecter</button>
    </form>
</div>

<%@ include file="_pied.jspf" %>
