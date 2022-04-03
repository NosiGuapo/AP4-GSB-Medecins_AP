package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(
        schema = "gsb_api",
        name = "medecins"
)
public class Medecin {
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "medecins_id_seq"
//    )
    @SequenceGenerator(
            name = "medecins_id_seq",
            sequenceName = "medecins_id_seq",
            schema = "gsb_api",
            // Incrementation by 1
//            allocationSize = 1,
            allocationSize = 1
            // Default value of 1
//            initialValue = 1
    )
    @Column(
            name = "ID",
            columnDefinition = "serial"
    )
    @Id
    private Long id;

    @NotBlank(message="Veuillez préciser un nom.")
    @Pattern(regexp = "^[a-zA-Z\\sàâäéèêëîïôöùûüçÀÂÄÉÈÊËÎÏÔÖÙÛÜÇ]{2,30}$", message = "Le nom ne doit contenir que des lettres et des espaces")
    private String nom;

    @NotBlank(message="Veuillez préciser un prénom.")
    @Pattern(regexp = "^[a-zA-Z\\sàâäéèêëîïôöùûüçÀÂÄÉÈÊËÎÏÔÖÙÛÜÇ]{2,30}$", message = "Le prénom ne doit contenir que des lettres et des espaces")
    private String prenom;

    @NotBlank(message="Veuillez préciser une adresse.")
    @Pattern(regexp = "^[a-zA-Z0-9\\sàâäéèêëîïôöùûüçÀÂÄÉÈÊËÎÏÔÖÙÛÜÇ,]{10,50}$", message = "Uniquement des lettres, chiffres et espaces")
    private String adresse;

    @NotBlank(message="Veuillez préciser un numéro de téléphone.")
    @Pattern(regexp = "^[0-9]{1,14}$", message = "Seulement des chiffres entre 1 et 14 caractères")
    private String tel;

    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Uniquement des lettres et des espaces sont autorisés.")
    private String spec;
    @ManyToOne
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @NotNull(message="Veuillez préciser un département.")
    private Departement departement;

    public Medecin(Long id, String nom, String prenom, String adresse, String tel, String spec, Departement departement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
        this.spec = spec;
        this.departement = departement;
    }

    public Medecin(){

    }

//    @JsonManagedReference
    public Departement getDepartement(){
        return departement;
    }

    public void setDepartement(Departement leDepartement){
        this.departement = leDepartement;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getTel() {
        return tel;
    }

    public String getSpec() {
        return spec;
    }
}
