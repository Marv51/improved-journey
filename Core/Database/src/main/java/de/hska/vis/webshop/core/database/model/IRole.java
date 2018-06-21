package de.hska.vis.webshop.core.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.hska.vis.webshop.core.database.model.deserializer.RoleDeserializer;

@JsonDeserialize(using = RoleDeserializer.class)
public interface IRole {

    int getId();

    void setId(int id);

    String getTyp();

    void setTyp(String typ);

    int getLevel();

    void setLevel(int level);
}
