package de.hska.vis.webshop.core.database.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.hska.vis.webshop.core.database.model.impl.Role;

import java.io.IOException;

/**
 * Used to serialize a {@link Role} object.
 * <p>
 * {@link de.hska.vis.webshop.core.database.model.IRole} configures the
 * {@link RoleDeserializer} as the default deserializer for
 * {@link de.hska.vis.webshop.core.database.model.IRole}.
 */
public class RoleDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        // obviously the json serializer doesn't take the column names but the variable names
        // who would have thought of that?
        String typ = node.get("typ").asText();
        int level = node.get("level").asInt();

        JsonNode idNode = node.get("id");
        if (idNode == null || idNode.asInt() < 0) {
            // new unsaved role
            return new Role(typ, level);
        }
        // known role
        int id = idNode.asInt();


        return new Role(id, typ, level);
    }
}
