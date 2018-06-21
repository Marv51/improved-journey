package de.hska.vis.webshop.core.database.model.impl;


import de.hska.vis.webshop.core.database.model.IRole;

import javax.persistence.*;

/**
 * This class contains details about roles.
 */
@Entity
@Table(name = "role")
public class Role implements IRole, java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "type")
    private String typ;

    @Column(name = "level1")
    private int level;

    public Role() {}

    public Role(String typ, int level) {
        this.typ = typ;
        this.level = level;
    }

    public Role(int id, String typ, int level) {
        this.typ = typ;
        this.level = level;
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTyp() {
        return this.typ;
    }

    @Override
    public void setTyp(String typ) {
        this.typ = typ;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }
}
