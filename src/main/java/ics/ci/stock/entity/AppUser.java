package ics.ci.stock.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "App_User", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name"),
                @UniqueConstraint(name = "APP_USER_UK_EMAIL", columnNames = "email"),
                })
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id", nullable = false)
    private Long userId;

    @Column(name = "User_Name", length = 36, nullable = false)
    private String userName;

    //@Column(name = "email",  nullable = false)
    @Column(name = "email")
    private String email;


    //@Column(name = "nom",  nullable = false)
    @Column(name = "nom")
    private String nom;

    //@Column(name = "prenom",  nullable = false)
    @Column(name = "prenom")
    private String prenom;

    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;

    @Column(name = "Enabled", length = 1, nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "appUser")
    private Collection<UserRole> userRoles;


    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Collection<AppRole> roles;

    public void setMesroles(String mesroles) {
        this.mesroles = mesroles;
    }

    @Transient
    private String mesroles;



    public AppUser() {
        super();
    }

    public AppUser(String userName, String encrytedPassword, boolean enabled) {
        this.userName = userName;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public AppUser(String userName, String email, String encrytedPassword, boolean enabled) {
        this.userName = userName;
        this.email = email;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public AppUser(String nom, String prenom, String userName, String email, String encrytedPassword, boolean enabled) {
        this.nom = nom;
        this.prenom = prenom;
        this.userName = userName;
        this.email = email;
        this.encrytedPassword = encrytedPassword;
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncrytedPassword() {
        return encrytedPassword;
    }

    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserRoles(Collection<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Collection<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<AppRole> roles) {
        this.roles = roles;
    }

    public String getMesroles() {
        return mesroles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String toNomComplet(){
        return this.prenom + " " + this.nom;
    }

    /*public ArrayList<String> getRoleName ()
    {

        return
    }*/
}
