<%-- Page d'erreur déclarée dans le web.xml pour les 401 / 403 envoyés par @Auth. --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="code" value="${pageContext.errorData.statusCode}"/>
<c:set var="titre" value="${code == 401 ? 'Connexion requise' : 'Accès refusé'}"/>
<%@ include file="_entete.jspf" %>

<div class="carte" style="max-width: 480px; margin: 60px auto; padding: 28px;">
    <h1>${titre}</h1>
    <c:choose>
        <c:when test="${code == 401}">
            <p>Vous devez être connecté pour accéder à cette page.</p>
            <a class="bouton" href="${pageContext.request.contextPath}/login">Se connecter</a>
        </c:when>
        <c:otherwise>
            <p>Cette page est réservée aux médecins.</p>
            <a class="bouton" href="${pageContext.request.contextPath}/personnes">Retour</a>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="_pied.jspf" %>
