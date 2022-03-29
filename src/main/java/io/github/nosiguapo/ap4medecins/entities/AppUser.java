package io.github.nosiguapo.ap4medecins.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
@Table(schema = "gsb_api")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fname;
    private String lname;
    private String username;
    private String password;
    private boolean isAdmin;
    @OneToMany(mappedBy = "owner")
    private List<Invitation> invitations;
}
