package de.hska.vis.webshop.core.database.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.hska.vis.webshop.core.database.model.deserializer.UserDeserializer;
import de.hska.vis.webshop.core.database.model.impl.Role;

@JsonDeserialize(using = UserDeserializer.class)
public interface IUser {

    int getId();

    void setId(int id);

    String getUsername();

    void setUsername(String username);

    String getFirstname();

    void setFirstname(String firstname);

    String getLastname();

    void setLastname(String lastname);

    String getPassword();

    void setPassword(String password);

    Role getRole();

    void setRole(Role role);
}
