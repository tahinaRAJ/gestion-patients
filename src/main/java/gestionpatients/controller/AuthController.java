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
 * Connexion / deconnexion.
 * Une fois connecte, la session contient :
 *   "user"   -> nom complet (cle auth.user-session-key du web.xml, verifiee par @Auth)
 *   "role"   -> "PATIENT" ou "MEDECIN" (cle auth.role-session-key, verifiee par @Auth(roles = ...))
 *   "userId" -> id de la personne connectee
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

        // Nouvel identifiant de session a la connexion (protection contre la fixation de session)
        request.changeSessionId();

        Personne p = personne.get();
        session.injectSessionAttribute("user", p.getNomComplet());
        session.injectSessionAttribute("role", p.getRole().getLibelle());
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
