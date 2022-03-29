package io.github.nosiguapo.ap4medecins.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "gsb_api")
public class Role {
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
    private String name;
}
