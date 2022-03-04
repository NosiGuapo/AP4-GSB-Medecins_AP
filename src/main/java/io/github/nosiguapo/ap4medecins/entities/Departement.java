package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        name = "departements",
        schema = "gsb_api"
)
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Departement {
    @Id
    @SequenceGenerator(
            name = "departements_id_seq",
            sequenceName = "departements_id_seq",
            schema = "gsb_api",
            allocationSize = 1
    )
    @Column(
            name = "ID",
            columnDefinition = "serial"
    )
    private Long id;    
    private String nom;
    @ManyToOne
    private Pays pays;
    @OneToMany(
            mappedBy = "departement",
            cascade = CascadeType.REMOVE
    )
    private Set<Medecin> medecins;


    public Departement() {
    }

    public Departement(Long id, String num, Pays pays, Set<Medecin> medecins) {
        this.id = id;
        this.nom = num;
        this.pays = pays;
        this.medecins = medecins;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNum(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @JsonManagedReference
    public Set<Medecin> getMedecins() {
        return medecins;
    }

    public void setMedecins(Set<Medecin> medecins) {
        this.medecins = medecins;
    }

    @Override
    public String toString() {
        return "Departement{" +
                "id=" + id +
                ", num='" + nom + '\'' +
                ", medecins=" + medecins +
                '}';
    }
}
