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
 * Remplit la table au premier demarrage (table vide) pour pouvoir se connecter :
 *   medecin@hopital.mg / medecin123   (role MEDECIN)
 *   rabe@mail.mg       / patient123   (role PATIENT)
 * Fait en Java plutot qu'en SQL car les mots de passe doivent etre haches en BCrypt.
 */
@Component
public class DonneesInitiales {

    private final PersonneService personneService;

    public DonneesInitiales(PersonneService personneService) {
        this.personneService = personneService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initialiser() {
        // Valeurs des tables role et sexe (deja inserees par sql/init.sql, sinon creees ici)
        Role patient = personneService.role(Role.PATIENT);
        Role medecin = personneService.role(Role.MEDECIN);
        Sexe m = personneService.sexe(Sexe.MASCULIN);
        Sexe f = personneService.sexe(Sexe.FEMININ);

        if (!personneService.aucunePersonne()) {
            return;
        }
        personneService.creer(new Personne("Rakoto", "Jean", LocalDate.of(1980, 3, 12), "Antananarivo", m,
                "Lot II A 45 Analakely", "034 11 222 33", "medecin@hopital.mg", medecin), "medecin123");
        personneService.creer(new Personne("Randria", "Hanta", LocalDate.of(1975, 9, 2), "Fianarantsoa", f,
                "Lot IV B 12 Ambohijatovo", "033 44 555 66", "randria@hopital.mg", medecin), "medecin123");
        personneService.creer(new Personne("Rabe", "Paul", LocalDate.of(1995, 6, 21), "Toamasina", m,
                "Lot 67 Ankorondrano", "032 77 888 99", "rabe@mail.mg", patient), "patient123");
        personneService.creer(new Personne("Rasoa", "Marie", LocalDate.of(2001, 1, 30), "Antsirabe", f,
                "Lot 3 Ampefiloha", "034 00 111 22", "rasoa@mail.mg", patient), "patient123");
        System.out.println("[INIT] Donnees initiales creees (2 medecins, 2 patients).");
    }
}
