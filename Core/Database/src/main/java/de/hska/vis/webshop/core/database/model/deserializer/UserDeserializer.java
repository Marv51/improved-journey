package de.hska.vis.webshop.core.database.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vis.webshop.core.database.model.impl.Role;
import de.hska.vis.webshop.core.database.model.impl.User;

import java.io.IOException;

/**
 * Used to serialize an {@link User} object.
 * <p>
 * Internally uses the {@link RoleDeserializer} as the {@link User}
 * has the property {@link User#role}.
 * {@link de.hska.vis.webshop.core.database.model.IUser} configures the
 * {@link UserDeserializer} as the default deserializer for
 * {@link de.hska.vis.webshop.core.database.model.IUser}.
 */
public class UserDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        // these are all non-nullable therefore have to be there if correctly sent
        String username = node.get("username").asText();
        String firstname = node.get("firstname").asText();
        String lastname = node.get("lastname").asText();
        String password = node.get("password").asText();
        Role role = mapper.readValue(node.get("role").toString(), Role.class);

        JsonNode idNode = node.get("id");
        if (idNode == null || idNode.asInt() < 0) {
            // new unsaved user
            return new User(username, firstname, lastname, password, role);
        }

        // known users
        int id = idNode.asInt();
        return new User(id, username, firstname, lastname, password, role);
    }
}
