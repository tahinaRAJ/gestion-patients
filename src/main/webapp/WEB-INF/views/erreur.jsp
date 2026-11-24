<%-- Page d'erreur declaree dans le web.xml pour les 401 / 403 envoyes par @Auth. --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="code" value="${pageContext.errorData.statusCode}"/>
<c:set var="titre" value="${code == 401 ? 'Connexion requise' : 'Acces refuse'}"/>
<%@ include file="_entete.jspf" %>

<div class="centre">
    <div class="carte boite" style="text-align: center;">
        <div class="stat-icone" style="margin: 0 auto 16px; background: var(--danger-doux); color: var(--danger);">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
                 stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
                <rect x="4" y="11" width="16" height="10" rx="2"/><path d="M8 11V7a4 4 0 0 1 8 0v4"/>
            </svg>
        </div>
        <h1>${titre}</h1>
        <c:choose>
            <c:when test="${code == 401}">
                <p class="sous-titre">Vous devez etre connecte pour acceder a cette page.</p>
                <a class="bouton" href="${ctx}/login">Se connecter</a>
            </c:when>
            <c:otherwise>
                <p class="sous-titre">Cette page est reservee aux medecins.</p>
                <a class="bouton" href="${ctx}/personnes">Retour</a>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<%@ include file="_pied.jspf" %>
