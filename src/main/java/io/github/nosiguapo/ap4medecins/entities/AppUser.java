package io.github.nosiguapo.ap4medecins.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(schema = "gsb_api")
public class AppUser {
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
    private String fname;
    private String lname;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(mappedBy = "owner")
    private List<Invitation> invitations;
}
