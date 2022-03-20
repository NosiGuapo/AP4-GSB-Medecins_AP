package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(
        name = "departements",
        schema = "gsb_api"
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

    @NotBlank(message="Veuillez préciser un nom.")
    @Length(min = 2, message = "Le nom doit comporter au moins 2 caractères")
    private String nom;

    @ManyToOne
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @NotBlank(message="Aucun pays d'appartenance n'est spécifié")
    private Pays pays;
    @OneToMany(
            mappedBy = "departement"
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

//    @JsonManagedReference
    public Pays getPays() {
        return pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    @JsonBackReference
    public Set<Medecin> getMedecins() {
        return medecins;
    }

    public void setMedecins(Set<Medecin> medecins) {
        this.medecins = medecins;
    }
}
