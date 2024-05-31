package org.sena.saludcontigo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Entity
@Table(name = "Examenes_Medicos")
public class ExamenMedico implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idexamen;

    @NotBlank
    @Column(name = "Tipo_Examen", nullable = false)
    private String tipoExamen;

    @NotNull
    @Column(name = "Fecha_Examen", nullable = false)
    private LocalDate fechaExamen;

    @NotNull
    @Column(name = "Hora_Examen", nullable = false)
    private LocalTime horaExamen;

    @NotBlank
    @Column(name = "Lugar_Examen", nullable = false)
    private String lugarExamen;

    @Column(name = "Estado_Examen", nullable = false)
    private String estadoExamen;

    @Column(name = "Observacion_Examen")
    private String observacionExamen;

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
    public ExamenMedico() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdexamen() {
        return idexamen;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public LocalDate getFechaExamen() {
        return fechaExamen;
    }

    public LocalTime getHoraExamen() {
        return horaExamen;
    }

    public String getLugarExamen() {
        return lugarExamen;
    }

    public String getEstadoExamen() {
        return estadoExamen;
    }

    public String getObservacionExamen() {
        return observacionExamen;
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
    public void setIdexamen(Long idexamen) {
        this.idexamen = idexamen;
    }

    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    public void setFechaExamen(LocalDate fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public void setHoraExamen(LocalTime horaExamen) {
        this.horaExamen = horaExamen;
    }

    public void setLugarExamen(String lugarExamen) {
        this.lugarExamen = lugarExamen;
    }

    public void setEstadoExamen(String estadoExamen) {
        this.estadoExamen = estadoExamen;
    }

    public void setObservacionExamen(String observacionExamen) {
        this.observacionExamen = observacionExamen;
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