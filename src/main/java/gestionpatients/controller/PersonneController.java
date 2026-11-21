package gestionpatients.controller;

import java.util.List;

import dev.framework.annotation.Auth;
import dev.framework.annotation.Controller;
import dev.framework.annotation.UrlMapping;
import dev.framework.util.Injection;
import dev.framework.util.ViewUtil;
import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;
import gestionpatients.service.PersonneService;

/**
 * Liste des personnes, séparées selon leur rôle.
 * Un médecin voit tous les patients ; un patient ne voit que sa propre fiche
 * (l'état civil des autres patients ne le concerne pas).
 */
@Controller("/personnes")
public class PersonneController {

    private final PersonneService personneService;

    public PersonneController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @UrlMapping(method = "GET")
    @Auth
    public ViewUtil liste(Injection session) {
        ViewUtil vue = Vues.vue("personnes");
        vue.addValue("medecins", personneService.listerParRole(Role.MEDECIN));

        if (Role.MEDECIN.name().equals(session.getSessionAttribute("role"))) {
            vue.addValue("patients", personneService.listerParRole(Role.PATIENT));
        } else {
            Long userId = (Long) session.getSessionAttribute("userId");
            List<Personne> moi = personneService.trouver(userId).map(List::of).orElse(List.of());
            vue.addValue("patients", moi);
        }
        return vue;
    }
}
