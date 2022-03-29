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
    @SequenceGenerator(
            name = "invitations_id_seq",
            sequenceName = "invitations_id_seq",
            schema = "gsb_api",
            allocationSize = 1
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
    private AppUser owner;
}
