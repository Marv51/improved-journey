package de.hska.vis.webshop.core.database.model.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.database.model.impl.Product;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Used to serialize a {@link Category} object.
 * <p>
 * {@link de.hska.vis.webshop.core.database.model.ICategory} configures the
 * {@link CategoryDeserializer} as the default deserializer for
 * {@link de.hska.vis.webshop.core.database.model.ICategory}.
 */
public class CategoryDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        // name always has to be there
        String name = node.get("name").asText();

        // id could be null if it's a new unsaved object
        JsonNode idNode = node.get("id");
        if (idNode == null) {
            // new unsaved object
            // therefore also no products are saved with it
            return new Category(name);
        }

        // known category object, with id and possible saved products
        int id = idNode.asInt();
        Product[] asArray = mapper.readValue(node.get("products").toString(), Product[].class);


        return new Category(id, name, new HashSet<>(Arrays.asList(asArray)));
    }
}
