package org.sena.saludcontigo.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "Citas_Medicas")
public class CitaMedica implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcita;

    @NotBlank
    @Column(name = "Tipo_Cita", nullable = false)
    private String tipoCita;

    @NotNull
    @Column(name = "Fecha_Cita", nullable = false)
    private LocalDate fechaCita;

    @NotNull
    @Column(name = "Hora_Cita", nullable = false)
    private LocalTime horaCita;

    @NotBlank
    @Column(name = "Lugar_Cita", nullable = false)
    private String lugarCita;

    @Column(name = "Estado_Cita", nullable = false)
    private String estadoCita;

    @Column(name = "Observacion_Cita")
    private String observacionCita;

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
    public CitaMedica() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdcita() {
        return idcita;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public String getLugarCita() {
        return lugarCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public String getObservacionCita() {
        return observacionCita;
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
    public void setIdcita(Long idcita) {
        this.idcita = idcita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public void setLugarCita(String lugarCita) {
        this.lugarCita = lugarCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public void setObservacionCita(String observacionCita) {
        this.observacionCita = observacionCita;
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