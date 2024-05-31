package org.sena.saludcontigo.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Audit_Log")
public class Auditoria implements Serializable {

    /**
     * Atributos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="Action")
    private String action;

    @Column(name="Table_Name")
    private String tableName;

    @Column(name="Column_Name")
    private String columnName;

    @Column(name="Old_Value")
    private String oldValue;

    @Column(name="New_Value")
    private String newValue;

    @Column(name="Action_Time")
    private String actionTime;


    /**
     * Constructor
     */
    public Auditoria() {
    }


    /**
     * Metodos Getter
     */
    public Long getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getOldValue() {
        return oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public String getActionTime() {
        return actionTime;
    }


    /**
     * Metodos Setter
     */
    public void setId(Long id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    /**
     * *
     */
    private static final long serialVersionUID = 1L;

}
