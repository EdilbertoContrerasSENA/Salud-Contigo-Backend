package org.sena.saludcontigo.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "Roles")
public class Role implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idrole;

    @Column(name = "Rolename", nullable = false)
    private String rolename;

    @Column(name = "Description")
    private String description;


    /**
     * Relaciones externas
     */


    /**
     * Constructores
     */
    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }


    /**
     * Métodos Getters
     */
    public Long getIdrole() {
        return idrole;
    }

    public String getRolename() {
        return rolename;
    }

    public String getDescription() {
        return description;
    }


    /**
     * Métodos Setters
     */
    public void setIdrole(Long idrole) {
        this.idrole = idrole;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * *
     */
    private static final long serialVersionUID = 1L;

}
