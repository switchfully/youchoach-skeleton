package com.switchfully.youcoach.datastore.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "members")
public class User {
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
    @Column(name = "school_year")
    private String schoolYear;

    public User(long id, String firstName, String lastName, String email, String password, String schoolYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.schoolYear = schoolYear;
    }
    public User(String firstName, String lastName, String email, String password) {
        this(0,firstName,lastName,email,password);
    }
    public User(long id, String firstName, String lastName, String email, String password){
        this(id,firstName,lastName,email,password,"");
    }

    public User() {
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

    public String getSchoolYear() {
        return schoolYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
