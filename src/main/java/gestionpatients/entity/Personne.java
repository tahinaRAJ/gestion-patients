package gestionpatients.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Une personne de l'application : état civil + rôle (PATIENT ou MEDECIN).
 * L'email et le mot de passe (haché BCrypt) servent à la connexion.
 *
 * Les setters portent le nom des champs des formulaires : BindParam du framework
 * remplit l'objet tout seul (setNom -> "nom", setDateNaissance -> "dateNaissance"...).
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

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Column(name = "lieu_naissance", length = 100)
    private String lieuNaissance;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Sexe sexe;

    @Column(length = 255)
    private String adresse;

    @Column(length = 30)
    private String telephone;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "mot_de_passe", nullable = false, length = 100)
    private String motDePasse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

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

    /** "Rakoto Jean" : utilisé pour l'affichage et mis en session à la connexion. */
    public String getNomComplet() {
        return prenom == null || prenom.isBlank() ? nom : nom + " " + prenom;
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
}
