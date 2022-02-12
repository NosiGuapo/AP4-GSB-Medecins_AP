package io.github.nosiguapo.ap4medecins.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        schema = "gsb_api",
        name = "users"
)
@JsonIdentityInfo(
        property = "id",
        generator = ObjectIdGenerators.PropertyGenerator.class
)
public class User {
    @Id
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
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
    private String lname;
    private String fname;
    private String username;
    private String mail;
    private String passwd;
    private Date register;
    @OneToMany(mappedBy = "owner")
    private List<Invitation> invitation;

    public User(Long id, String lname, String fname, String username, String mail, String passwd, Date register, List<Invitation> invitation) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.mail = mail;
        this.passwd = passwd;
        this.register = register;
        this.invitation = invitation;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String name) {
        this.lname = name;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register_date) {
        this.register = register_date;
    }

    public List<Invitation> getInvitation() {
        return invitation;
    }

    public void setInvitation(List<Invitation> invitation) {
        this.invitation = invitation;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lname='" + lname + '\'' +
                ", fname='" + fname + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", passwd='" + passwd + '\'' +
                ", register_date=" + register + '\'' +
                ", invitation=" + invitation +
                '}';
    }
}
