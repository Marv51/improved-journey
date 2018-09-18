package de.hska.vis.webshop.core.util;

import de.hska.vis.webshop.core.database.dao.IGenericDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

/**
 * HelperUtility can be used to ask a DAO for an instance of Element with the primary key PrimaryKey.
 *
 * @param <Element>    different Entities. In our case {@link de.hska.vis.webshop.core.database.model.ICategory},
 *                     {@link de.hska.vis.webshop.core.database.model.IUser} and
 *                     {@link de.hska.vis.webshop.core.database.model.IProduct}
 * @param <PrimaryKey> the primary key for the Element. In our case usually an {@link Integer}
 */
public class HelperUtility<Element, PrimaryKey extends Serializable> {
    /**
     * Helper method to create a {@link ResponseEntity} containing an Element and a {@link HttpStatus}.
     * <p>
     * The status could possibly be:
     * * 200 / OK
     * * 404 / Not Found
     * * 400 / Bad Request
     * The 400 if the conversion of the PrimaryKey via the #castStringTo function fails.
     *
     * @param stringId     the primary key in a string representation
     * @param dao          the dao which should be asked for the element
     * @param castStringTo a function which transforms a {@link String} to the PrimaryKey
     * @return the {@link ResponseEntity} with the resulting object or null and the according
     * {@link HttpStatus} code.
     */
    public ResponseEntity<Element> getResponse(String stringId, IGenericDAO<Element, PrimaryKey> dao, Function<String, PrimaryKey> castStringTo) {
        HttpStatus code = HttpStatus.OK;
        Element responseObject = null;
        try {
            responseObject = getResponseObject(stringId, dao, castStringTo);
            if (responseObject == null) {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseObject, code);
    }

    public Element getResponseObject(String stringId, IGenericDAO<Element, PrimaryKey> dao, Function<String, PrimaryKey> castStringTo) throws NumberFormatException {
        PrimaryKey parsedId = castStringTo.apply(stringId);
        List<Element> result = dao.get(parsedId);
        if (result == null || result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }
}
