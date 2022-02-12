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
    private Integer max_uses;
    private Integer current_uses;
    private Date creation_date;
    @ManyToOne
    private User owner;

    public Invitation(Long id, String key, Integer max_uses, Integer current_uses, Date creation_date, User owner) {
        this.id = id;
        this.key = key;
        this.max_uses = max_uses;
        this.current_uses = current_uses;
        this.creation_date = creation_date;
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

    public Integer getMax_uses() {
        return max_uses;
    }

    public void setMax_uses(Integer max_uses) {
        this.max_uses = max_uses;
    }

    public Integer getCurrent_uses() {
        return current_uses;
    }

    public void setCurrent_uses(Integer current_uses) {
        this.current_uses = current_uses;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
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
                ", max_uses=" + max_uses +
                ", current_uses=" + current_uses +
                ", creation_date=" + creation_date +
//                ", owner=" + owner +
                '}';
    }
}
