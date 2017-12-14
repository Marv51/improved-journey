package de.hska.vis.webshop.core.database.model;

public interface IRole {

    int getId();

    void setId(int id);

    String getTyp();

    void setTyp(String typ);

    int getLevel();

    void setLevel(int level);
}
