package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(
        schema = "gsb_api",
        name = "medecins"
)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
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
    private String nom;
    private String prenom;
    private String adresse;
    private String tel;
    private String spec;
    @ManyToOne
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
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

    @JsonBackReference
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

    @Override
    public String toString() {
        return "Medecin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", tel='" + tel + '\'' +
                ", spec='" + spec + '\'' +
//                ", departement=" + departement +
                '}';
    }
}
