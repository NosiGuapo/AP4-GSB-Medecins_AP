package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(
        schema = "gsb_api",
        name = "invitations"
)
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class Invitation {
    @Id
    @SequenceGenerator(
            name = "invitations_id_seq",
            sequenceName = "invitations_id_seq",
            schema = "gsb_api",
            // Incrementation by 1
            allocationSize = 1,
            // Default value of 1
            initialValue = 1
    )
    @Column(
            name = "ID",
            columnDefinition = "serial"
    )
    private Long id;
    private String key;
    private Integer maxuses;
    private Integer currentuses;
    private Date creation;
    @ManyToOne
    private User owner;

    public Invitation(Long id, String key, Integer max_uses, Integer current_uses, Date creation, User owner) {
        this.id = id;
        this.key = key;
        this.maxuses = max_uses;
        this.currentuses = current_uses;
        this.creation = creation;
        this.owner = owner;
    }

    public Invitation() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getMaxuses() {
        return maxuses;
    }

    public void setMaxuses(Integer max_uses) {
        this.maxuses = max_uses;
    }

    public Integer getCurrentuses() {
        return currentuses;
    }

    public void setCurrentuses(Integer current_uses) {
        this.currentuses = current_uses;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation_date) {
        this.creation = creation_date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", max_uses=" + maxuses +
                ", current_uses=" + currentuses +
                ", creation_date=" + creation +
//                ", owner=" + owner +
                '}';
    }
}
