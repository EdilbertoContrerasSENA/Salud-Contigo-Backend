package org.sena.saludcontigo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "Administradores")
public class Administrador implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idadministrador;

    @NotBlank
    @Column(name = "Nombres", nullable = false)
    private String nombres;

    @NotBlank
    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @NotBlank
    @Column(name = "Tipo_Documento", nullable = false)
    private String tipoDocumento;

    @NotBlank
    @Column(name = "Numero_Documento", nullable = false, unique = true)
    private String numeroDocumento;

    @NotNull
    @Column(name = "Fecha_Nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotBlank
    @Column(name = "Sexo", nullable = false)
    private String sexo;

    @NotBlank
    @Column(name = "Telefono", nullable = false)
    private String telefono;

    @NotBlank
    @Email
    @Column(name = "Correo", nullable = false)
    private String correo;

    @NotBlank
    @Column(name = "Direccion", nullable = false)
    private String direccion;

    @NotBlank
    @Column(name = "Ocupacion", nullable = false)
    private String ocupacion;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Active", nullable = false)
    private Integer active;

    @Column(name = "Created_At", nullable = true)
    private Date createdAt;

    @Column(name = "Updated_At", nullable = true)
    private Date updated_At;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;


    /**
     * Relaciones externas
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Roles_Idrole", nullable = false)
    private Role role;


    /**
     * Constructor
     */
    public Administrador() {
        this.role = new Role();
    }


    /**
     * Métodos Getters
     */
    public Long getIdadministrador() {
        return idadministrador;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Integer getActive() {
        return active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdated_At() {
        return updated_At;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return admin;
    }


    /**
     * Métodos Setters
     */
    public void setIdadministrador(Long idadministrador) {
        this.idadministrador = idadministrador;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdated_At(Date updated_At) {
        this.updated_At = updated_At;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    /**
     * *
     */
    private static final long serialVersionUID = 1L;

}