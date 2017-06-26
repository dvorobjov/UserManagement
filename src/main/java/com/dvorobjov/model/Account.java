package com.dvorobjov.model;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * Created by Dmytro Vorobiov on 13.06.2017.
 */
public class Account implements Cloneable{
    private OptionalLong id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull @Email
    private String email;

    public Account() {
        this(OptionalLong.empty(), null, null, null);
    }
    public Account(String name, String surname, String email) {
        this(OptionalLong.empty(), name, surname, email);
    }
    public Account(long id, String name, String surname, String email) {
        this(OptionalLong.of(id), name, surname, email);
    }

    public Account(OptionalLong id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public OptionalLong getId() {
        return id;
    }

    public void setId(OptionalLong id) {
        this.id = id;
    }

    public void setId(long id) {
        this.id = OptionalLong.of(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account cloneInstance() {
        try {
            return (Account) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Account class doesn't support clone operation", e);
        }
    }
}
