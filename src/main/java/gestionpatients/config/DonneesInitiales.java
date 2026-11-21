package gestionpatients.config;

import java.time.LocalDate;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;
import gestionpatients.entity.Sexe;
import gestionpatients.service.PersonneService;

/**
 * Remplit la table au premier démarrage (table vide) pour pouvoir se connecter :
 *   medecin@hopital.mg / medecin123   (rôle MEDECIN)
 *   rabe@mail.mg       / patient123   (rôle PATIENT)
 * Fait en Java plutôt qu'en SQL car les mots de passe doivent être hachés en BCrypt.
 */
@Component
public class DonneesInitiales {

    private final PersonneService personneService;

    public DonneesInitiales(PersonneService personneService) {
        this.personneService = personneService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initialiser() {
        if (!personneService.aucunePersonne()) {
            return;
        }
        personneService.creer(new Personne("Rakoto", "Jean", LocalDate.of(1980, 3, 12), "Antananarivo", Sexe.M,
                "Lot II A 45 Analakely", "034 11 222 33", "medecin@hopital.mg", Role.MEDECIN), "medecin123");
        personneService.creer(new Personne("Randria", "Hanta", LocalDate.of(1975, 9, 2), "Fianarantsoa", Sexe.F,
                "Lot IV B 12 Ambohijatovo", "033 44 555 66", "randria@hopital.mg", Role.MEDECIN), "medecin123");
        personneService.creer(new Personne("Rabe", "Paul", LocalDate.of(1995, 6, 21), "Toamasina", Sexe.M,
                "Lot 67 Ankorondrano", "032 77 888 99", "rabe@mail.mg", Role.PATIENT), "patient123");
        personneService.creer(new Personne("Rasoa", "Marie", LocalDate.of(2001, 1, 30), "Antsirabe", Sexe.F,
                "Lot 3 Ampefiloha", "034 00 111 22", "rasoa@mail.mg", Role.PATIENT), "patient123");
        System.out.println("[INIT] Données initiales créées (2 médecins, 2 patients).");
    }
}
