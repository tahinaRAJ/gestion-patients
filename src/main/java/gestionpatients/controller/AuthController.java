package gestionpatients.controller;

import java.util.Optional;

import dev.framework.annotation.Controller;
import dev.framework.annotation.UrlMapping;
import dev.framework.util.Injection;
import dev.framework.util.ViewUtil;
import gestionpatients.entity.Personne;
import gestionpatients.service.PersonneService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Connexion / déconnexion.
 * Une fois connecté, la session contient :
 *   "user"   -> nom complet (clé auth.user-session-key du web.xml, vérifiée par @Auth)
 *   "role"   -> "PATIENT" ou "MEDECIN" (clé auth.role-session-key, vérifiée par @Auth(roles = ...))
 *   "userId" -> id de la personne connectée
 */
@Controller
public class AuthController {

    private final PersonneService personneService;

    public AuthController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @UrlMapping(value = "/", method = "GET")
    public ViewUtil accueil(Injection session) {
        boolean connecte = session.getSessionAttribute("user") != null;
        return Vues.redirection(connecte ? "/personnes" : "/login");
    }

    @UrlMapping(value = "/login", method = "GET")
    public ViewUtil formulaireLogin() {
        return Vues.vue("login");
    }

    @UrlMapping(value = "/login", method = "POST")
    public ViewUtil login(String email, String motDePasse, Injection session, HttpServletRequest request) {
        Optional<Personne> personne = personneService.authentifier(email, motDePasse);
        if (personne.isEmpty()) {
            ViewUtil vue = Vues.vue("login");
            vue.addValue("erreur", "Email ou mot de passe incorrect.");
            vue.addValue("email", email == null ? "" : email);
            return vue;
        }

        // Nouvel identifiant de session à la connexion (protection contre la fixation de session)
        request.changeSessionId();

        Personne p = personne.get();
        session.injectSessionAttribute("user", p.getNomComplet());
        session.injectSessionAttribute("role", p.getRole().name());
        session.injectSessionAttribute("userId", p.getId());
        return Vues.redirection("/personnes");
    }

    @UrlMapping(value = "/logout", method = "GET")
    public ViewUtil logout(Injection session) {
        session.removeSessionAttribute("user");
        session.removeSessionAttribute("role");
        session.removeSessionAttribute("userId");
        return Vues.redirection("/login");
    }
}
