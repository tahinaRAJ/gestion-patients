package gestionpatients.controller;

import java.util.List;

import dev.framework.util.ViewUtil;

/**
 * Raccourcis pour construire les ViewUtil renvoyés par les controllers.
 *
 * Le framework ne gère pas la redirection : redirection(url) renvoie vers la vue
 * redirect.jsp, qui fait un <c:redirect> (réponse 302). Cela évite qu'un rafraîchissement
 * de la page après un POST renvoie le formulaire.
 */
public final class Vues {

    private Vues() {
    }

    public static ViewUtil vue(String nom) {
        ViewUtil vue = new ViewUtil();
        vue.setView(nom);
        return vue;
    }

    /** url relative à l'application, ex: "/patients". */
    public static ViewUtil redirection(String url) {
        ViewUtil vue = vue("redirect");
        vue.addValue("url", url);
        return vue;
    }

    /** Un seul objet à passer à la JSP (lu avec ${nom[0]} car ViewUtil ne stocke que des listes). */
    public static void ajouter(ViewUtil vue, String nom, Object valeur) {
        vue.addValue(nom, List.of(valeur));
    }
}
