package gestionpatients.controller;

import dev.framework.annotation.Auth;
import dev.framework.annotation.Controller;
import dev.framework.annotation.UrlMapping;
import dev.framework.util.ViewUtil;
import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;
import gestionpatients.service.PersonneService;

/**
 * CRUD des patients, réservé aux médecins.
 *
 *   GET  /patients                -> liste
 *   GET  /patients/nouveau        -> formulaire vide
 *   POST /patients/ajouter        -> création
 *   GET  /patients/modifier?id=   -> formulaire pré-rempli
 *   POST /patients/modifier       -> mise à jour
 *   POST /patients/supprimer      -> suppression (id dans le formulaire)
 *
 * Le paramètre "Personne patient" est rempli par BindParam depuis les champs du formulaire.
 */
@Controller("/patients")
public class PatientController {

    private final PersonneService personneService;

    public PatientController(PersonneService personneService) {
        this.personneService = personneService;
    }

    @UrlMapping(method = "GET")
    @Auth(roles = "MEDECIN")
    public ViewUtil liste() {
        ViewUtil vue = Vues.vue("patients");
        vue.addValue("patients", personneService.listerParRole(Role.PATIENT));
        return vue;
    }

    @UrlMapping(value = "/nouveau", method = "GET")
    @Auth(roles = "MEDECIN")
    public ViewUtil nouveau() {
        return formulaire(new Personne(), null);
    }

    @UrlMapping(value = "/ajouter", method = "POST")
    @Auth(roles = "MEDECIN")
    public ViewUtil ajouter(Personne patient) {
        try {
            personneService.ajouterPatient(patient);
            return Vues.redirection("/patients");
        } catch (IllegalArgumentException e) {
            patient.setId(null);
            return formulaire(patient, e.getMessage());
        }
    }

    @UrlMapping(value = "/modifier", method = "GET")
    @Auth(roles = "MEDECIN")
    public ViewUtil modifier(Long id) {
        return personneService.trouverPatient(id)
                .map(p -> formulaire(p, null))
                .orElseGet(() -> Vues.redirection("/patients"));
    }

    @UrlMapping(value = "/modifier", method = "POST")
    @Auth(roles = "MEDECIN")
    public ViewUtil enregistrer(Personne patient) {
        try {
            personneService.modifierPatient(patient);
            return Vues.redirection("/patients");
        } catch (IllegalArgumentException e) {
            return formulaire(patient, e.getMessage());
        }
    }

    @UrlMapping(value = "/supprimer", method = "POST")
    @Auth(roles = "MEDECIN")
    public ViewUtil supprimer(Long id) {
        personneService.supprimerPatient(id);
        return Vues.redirection("/patients");
    }

    private ViewUtil formulaire(Personne patient, String erreur) {
        // Le mot de passe (haché) ne doit jamais repartir vers le navigateur
        patient.setMotDePasse(null);
        ViewUtil vue = Vues.vue("patient-form");
        Vues.ajouter(vue, "patient", patient);
        if (erreur != null) {
            vue.addValue("erreur", erreur);
        }
        return vue;
    }
}
