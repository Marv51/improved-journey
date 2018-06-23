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

/**
 * Used to serialize a {@link Product} object.
 * <p>
 * Internally uses the {@link CategoryDeserializer} as the {@link Product}
 * has the property {@link Product#category}.
 * {@link de.hska.vis.webshop.core.database.model.IProduct} configures the
 * {@link ProductDeserializer} as the default deserializer for
 * {@link de.hska.vis.webshop.core.database.model.IProduct}.
 */
public class ProductDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        String name = node.get("name").asText();
        double price = node.get("price").asDouble();
        Category category = mapper.readValue(node.get("category").toString(), Category.class);
        String details = node.get("details").asText();

        JsonNode idNode = node.get("id");
        if (idNode == null) {
            // new unsaved product
            return new Product(name, price, category, details);
        }
        // already known product
        int id = idNode.asInt();

        return new Product(id, name, price, category, details);
    }
}
