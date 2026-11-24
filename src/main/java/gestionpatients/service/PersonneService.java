package gestionpatients.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;
import gestionpatients.entity.Sexe;
import gestionpatients.repository.PersonneRepository;
import gestionpatients.repository.RoleRepository;
import gestionpatients.repository.SexeRepository;

/**
 * Regles metier sur les personnes. Les erreurs de saisie sont signalees par une
 * IllegalArgumentException dont le message est affiche tel quel dans le formulaire.
 */
@Service
public class PersonneService {

    private final PersonneRepository personneRepository;
    private final RoleRepository roleRepository;
    private final SexeRepository sexeRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonneService(PersonneRepository personneRepository, RoleRepository roleRepository,
            SexeRepository sexeRepository, PasswordEncoder passwordEncoder) {
        this.personneRepository = personneRepository;
        this.roleRepository = roleRepository;
        this.sexeRepository = sexeRepository;
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

    /** libelleRole : Role.PATIENT ou Role.MEDECIN. */
    public List<Personne> listerParRole(String libelleRole) {
        return personneRepository.findByRoleLibelleOrderByNomAscPrenomAsc(libelleRole);
    }

    /** Les valeurs de la table sexe, pour la liste deroulante du formulaire. */
    public List<Sexe> listerSexes() {
        return sexeRepository.findAll();
    }

    /** Le role de ce libelle ; cree s'il n'existe pas encore dans la table role. */
    @Transactional
    public Role role(String libelle) {
        return roleRepository.findByLibelle(libelle).orElseGet(() -> roleRepository.save(new Role(libelle)));
    }

    /** Le sexe de ce libelle ; cree s'il n'existe pas encore dans la table sexe. */
    @Transactional
    public Sexe sexe(String libelle) {
        return sexeRepository.findByLibelle(libelle).orElseGet(() -> sexeRepository.save(new Sexe(libelle)));
    }

    public Optional<Personne> trouver(Long id) {
        return id == null ? Optional.empty() : personneRepository.findById(id);
    }

    public Optional<Personne> trouverPatient(Long id) {
        return id == null ? Optional.empty() : personneRepository.findByIdAndRoleLibelle(id, Role.PATIENT);
    }

    @Transactional
    public Personne ajouterPatient(Personne patient) {
        patient.setId(null);
        patient.setRole(role(Role.PATIENT));
        valider(patient);
        if (estVide(patient.getMotDePasse()) || patient.getMotDePasse().length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caracteres.");
        }
        if (personneRepository.existsByEmailIgnoreCase(patient.getEmail())) {
            throw new IllegalArgumentException("Cet email est deja utilise.");
        }
        patient.setMotDePasse(passwordEncoder.encode(patient.getMotDePasse()));
        return personneRepository.save(patient);
    }

    /**
     * Remplace l'etat civil du patient par celui du formulaire.
     * Mot de passe laisse vide = mot de passe inchange.
     */
    @Transactional
    public Personne modifierPatient(Personne formulaire) {
        Personne patient = trouverPatient(formulaire.getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient introuvable."));
        valider(formulaire);
        if (personneRepository.existsByEmailIgnoreCaseAndIdNot(formulaire.getEmail(), patient.getId())) {
            throw new IllegalArgumentException("Cet email est deja utilise.");
        }
        if (!estVide(formulaire.getMotDePasse())) {
            if (formulaire.getMotDePasse().length() < 6) {
                throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caracteres.");
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

    /** Ajoute une personne deja complete (mot de passe en clair, hache ici). Utilise pour les donnees initiales. */
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
            throw new IllegalArgumentException("La date de naissance ne peut pas etre dans le futur.");
        }
        p.setEmail(p.getEmail().trim());
        p.setSexe(sexeDuFormulaire(p.getSexe()));
    }

    /** Le formulaire n'envoie que "sexe.id" : on charge la vraie ligne de la table sexe. */
    private Sexe sexeDuFormulaire(Sexe sexe) {
        if (sexe == null || sexe.getId() == null) {
            return null;
        }
        return sexeRepository.findById(sexe.getId())
                .orElseThrow(() -> new IllegalArgumentException("Sexe invalide."));
    }

    private static boolean estVide(String s) {
        return s == null || s.isBlank();
    }
}
