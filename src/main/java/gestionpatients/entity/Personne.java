package gestionpatients.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Une personne de l'application : etat civil + role (table role : PATIENT ou MEDECIN).
 * L'email et le mot de passe (hache BCrypt) servent a la connexion.
 *
 * Les setters portent le nom des champs des formulaires : BindParam du framework
 * remplit l'objet tout seul (setNom -> "nom", setDateNaissance -> "dateNaissance",
 * setSexe -> "sexe.id"...).
 *
 * Colonnes en camelCase : le nom du champ Java sert de nom de colonne (dateNaissance...).
 */
@Entity
@Table(name = "personne")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 150)
    private String prenom;

    private LocalDate dateNaissance;

    @Column(length = 100)
    private String lieuNaissance;

    @ManyToOne
    @JoinColumn(name = "idSexe")
    private Sexe sexe;

    @Column(length = 255)
    private String adresse;

    @Column(length = 30)
    private String telephone;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String motDePasse;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    /** Mise a jour automatiquement a chaque insertion / modification. */
    @Column(nullable = false)
    private LocalDateTime dateModification;

    public Personne() {
    }

    public Personne(String nom, String prenom, LocalDate dateNaissance, String lieuNaissance, Sexe sexe,
            String adresse, String telephone, String email, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.lieuNaissance = lieuNaissance;
        this.sexe = sexe;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.role = role;
    }

    @PrePersist
    @PreUpdate
    void majDateModification() {
        dateModification = LocalDateTime.now();
    }

    /** "Rakoto Jean" : utilise pour l'affichage et mis en session a la connexion. */
    public String getNomComplet() {
        return prenom == null || prenom.isBlank() ? nom : nom + " " + prenom;
    }

    /** "RJ" : pastille affichee a cote du nom dans les listes. */
    public String getInitiales() {
        String i = nom == null || nom.isBlank() ? "" : nom.substring(0, 1);
        if (prenom != null && !prenom.isBlank()) {
            i += prenom.substring(0, 1);
        }
        return i.toUpperCase();
    }

    /** Date de naissance au format 21/06/1995 (vide si inconnue). */
    public String getDateNaissanceAffichee() {
        return dateNaissance == null ? "" : dateNaissance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }

    public Sexe getSexe() { return sexe; }
    public void setSexe(Sexe sexe) { this.sexe = sexe; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public LocalDateTime getDateModification() { return dateModification; }
}
