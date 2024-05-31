package org.sena.saludcontigo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "Historias_Clinicas")
public class HistoriaClinica implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhistoria;

    @NotNull
    @Column(name = "Fecha_Historia", nullable = false)
    private LocalDate fechaHistoria;

    @NotBlank
    @Column(name = "Antecedentes_Historia", length = 500)
    private String antecedentesHistoria;

    @NotBlank
    @Column(name = "Examenes_Historia", length = 500)
    private String examenesHistoria;

    @NotBlank
    @Column(name = "Diagnostico_Historia", length = 500)
    private String diagnosticoHistoria;

    @NotBlank
    @Column(name = "Tratamiento_Historia", length = 500)
    private String tratamientoHistoria;

    @NotNull
    @Column(name = "Pacientes_Idpaciente", nullable = false)
    private Long pacientesIdpaciente;

    @NotNull
    @Column(name = "Medicos_Idmedico", nullable = false)
    private Long medicosIdmedico;


    /**
     * Relaciones externas
     */


    /**
     * Constructor
     */
    public HistoriaClinica() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdhistoria() {
        return idhistoria;
    }

    public LocalDate getFechaHistoria() {
        return fechaHistoria;
    }

    public String getAntecedentesHistoria() {
        return antecedentesHistoria;
    }

    public String getExamenesHistoria() {
        return examenesHistoria;
    }

    public String getDiagnosticoHistoria() {
        return diagnosticoHistoria;
    }

    public String getTratamientoHistoria() {
        return tratamientoHistoria;
    }

    public Long getPacientesIdpaciente() {
        return pacientesIdpaciente;
    }

    public Long getMedicosIdmedico() {
        return medicosIdmedico;
    }


    /**
     * Métodos Setters
     */
    public void setIdhistoria(Long idhistoria) {
        this.idhistoria = idhistoria;
    }

    public void setFechaHistoria(LocalDate fechaHistoria) {
        this.fechaHistoria = fechaHistoria;
    }

    public void setAntecedentesHistoria(String antecedentesHistoria) {
        this.antecedentesHistoria = antecedentesHistoria;
    }

    public void setExamenesHistoria(String examenesHistoria) {
        this.examenesHistoria = examenesHistoria;
    }

    public void setDiagnosticoHistoria(String diagnosticoHistoria) {
        this.diagnosticoHistoria = diagnosticoHistoria;
    }

    public void setTratamientoHistoria(String tratamientoHistoria) {
        this.tratamientoHistoria = tratamientoHistoria;
    }

    public void setPacientesIdpaciente(Long pacientesIdpaciente) {
        this.pacientesIdpaciente = pacientesIdpaciente;
    }

    public void setMedicosIdmedico(Long medicosIdmedico) {
        this.medicosIdmedico = medicosIdmedico;
    }


    /**
     * *
     */
    private static final long serialVersionUID = 1L;
}