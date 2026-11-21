<%-- Vue technique : le framework n'a pas de redirection, les controllers renvoient
     Vues.redirection("/patients") qui arrive ici. ${url[0]} est relatif à l'application. --%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:redirect url="${url[0]}"/>
