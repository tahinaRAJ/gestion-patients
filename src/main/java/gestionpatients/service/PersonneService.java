package gestionpatients.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;
import gestionpatients.repository.PersonneRepository;

/**
 * Règles métier sur les personnes. Les erreurs de saisie sont signalées par une
 * IllegalArgumentException dont le message est affiché tel quel dans le formulaire.
 */
@Service
public class PersonneService {

    private final PersonneRepository personneRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonneService(PersonneRepository personneRepository, PasswordEncoder passwordEncoder) {
        this.personneRepository = personneRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** La personne dont l'email et le mot de passe correspondent, sinon vide. */
    public Optional<Personne> authentifier(String email, String motDePasse) {
        if (email == null || motDePasse == null) {
            return Optional.empty();
        }
        return personneRepository.findByEmailIgnoreCase(email.trim())
                .filter(p -> passwordEncoder.matches(motDePasse, p.getMotDePasse()));
    }

    public List<Personne> listerParRole(Role role) {
        return personneRepository.findByRoleOrderByNomAscPrenomAsc(role);
    }

    public Optional<Personne> trouver(Long id) {
        return id == null ? Optional.empty() : personneRepository.findById(id);
    }

    public Optional<Personne> trouverPatient(Long id) {
        return id == null ? Optional.empty() : personneRepository.findByIdAndRole(id, Role.PATIENT);
    }

    @Transactional
    public Personne ajouterPatient(Personne patient) {
        patient.setId(null);
        patient.setRole(Role.PATIENT);
        valider(patient);
        if (estVide(patient.getMotDePasse()) || patient.getMotDePasse().length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères.");
        }
        if (personneRepository.existsByEmailIgnoreCase(patient.getEmail())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }
        patient.setMotDePasse(passwordEncoder.encode(patient.getMotDePasse()));
        return personneRepository.save(patient);
    }

    /**
     * Remplace l'état civil du patient par celui du formulaire.
     * Mot de passe laissé vide = mot de passe inchangé.
     */
    @Transactional
    public Personne modifierPatient(Personne formulaire) {
        Personne patient = trouverPatient(formulaire.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient introuvable."));
        valider(formulaire);
        if (personneRepository.existsByEmailIgnoreCaseAndIdNot(formulaire.getEmail(), patient.getId())) {
            throw new IllegalArgumentException("Cet email est déjà utilisé.");
        }
        if (!estVide(formulaire.getMotDePasse())) {
            if (formulaire.getMotDePasse().length() < 6) {
                throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères.");
            }
            patient.setMotDePasse(passwordEncoder.encode(formulaire.getMotDePasse()));
        }

        patient.setNom(formulaire.getNom());
        patient.setPrenom(formulaire.getPrenom());
        patient.setDateNaissance(formulaire.getDateNaissance());
        patient.setLieuNaissance(formulaire.getLieuNaissance());
        patient.setSexe(formulaire.getSexe());
        patient.setAdresse(formulaire.getAdresse());
        patient.setTelephone(formulaire.getTelephone());
        patient.setEmail(formulaire.getEmail());
        return personneRepository.save(patient);
    }

    @Transactional
    public void supprimerPatient(Long id) {
        trouverPatient(id).ifPresent(personneRepository::delete);
    }

    /** Ajoute une personne déjà complète (mot de passe en clair, haché ici). Utilisé pour les données initiales. */
    @Transactional
    public Personne creer(Personne personne, String motDePasse) {
        personne.setMotDePasse(passwordEncoder.encode(motDePasse));
        return personneRepository.save(personne);
    }

    public boolean aucunePersonne() {
        return personneRepository.count() == 0;
    }

    private void valider(Personne p) {
        if (estVide(p.getNom())) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (estVide(p.getEmail()) || !p.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("L'email est invalide.");
        }
        if (p.getDateNaissance() != null && p.getDateNaissance().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La date de naissance ne peut pas être dans le futur.");
        }
        p.setEmail(p.getEmail().trim());
    }

    private static boolean estVide(String s) {
        return s == null || s.isBlank();
    }
}
