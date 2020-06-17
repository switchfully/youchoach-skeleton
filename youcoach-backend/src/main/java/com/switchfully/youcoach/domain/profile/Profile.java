package com.switchfully.youcoach.domain.profile;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true,nullable = false)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "class_year")
    private String classYear;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "account_enabled", nullable = false)
    private boolean accountEnabled = false;

    public Profile(long id, String firstName, String lastName, String email, String password, String classYear, String photoUrl) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.classYear = classYear;
        this.photoUrl = photoUrl;
    }
    public Profile(String firstName, String lastName, String email, String password) {
        this(0,firstName,lastName,email,password);
    }
    public Profile(long id, String firstName, String lastName, String email, String password){
        this(id,firstName,lastName,email,password,"","");
    }

    public Profile() {
    }



    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getClassYear() {
        return classYear;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isAccountEnabled(){
        return accountEnabled;
    }

    public void setAccountEnabled(boolean isEnabled){
        accountEnabled = isEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return id == profile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
