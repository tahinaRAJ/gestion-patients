package gestionpatients.controller;

import java.util.List;

import dev.framework.util.ViewUtil;

/**
 * Raccourcis pour construire les ViewUtil renvoyes par les controllers.
 *
 * Le framework ne gere pas la redirection : redirection(url) renvoie vers la vue
 * redirect.jsp, qui fait un <c:redirect> (reponse 302). Cela evite qu'un rafraichissement
 * de la page apres un POST renvoie le formulaire.
 */
public final class Vues {

    private Vues() {
    }

    public static ViewUtil vue(String nom) {
        ViewUtil vue = new ViewUtil();
        vue.setView(nom);
        return vue;
    }

    /** url relative a l'application, ex: "/patients". */
    public static ViewUtil redirection(String url) {
        ViewUtil vue = vue("redirect");
        vue.addValue("url", url);
        return vue;
    }

    /** Un seul objet a passer a la JSP (lu avec ${nom[0]} car ViewUtil ne stocke que des listes). */
    public static void ajouter(ViewUtil vue, String nom, Object valeur) {
        vue.addValue(nom, List.of(valeur));
    }
}
