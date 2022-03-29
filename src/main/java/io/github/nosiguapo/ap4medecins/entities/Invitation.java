package io.github.nosiguapo.ap4medecins.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        schema = "gsb_api",
        name = "invitations"
)
@Data @AllArgsConstructor @NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String key;
    private Integer maxuses;
    private Integer currentuses;
    private Date creation;
    @ManyToOne
    private AppUser owner;
}
