package de.hska.vis.webshop.core.util;

import de.hska.vis.webshop.core.database.dao.IGenericDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

public class HelperUtility<T, P extends Number> {
    public ResponseEntity<T> getResponse(String stringId, IGenericDAO<T, P> dao, Function<String, P> castStringTo) {
        HttpStatus code = HttpStatus.OK;
        T responseObject;
        try {
            P parsedId = castStringTo.apply(stringId);
            responseObject = dao.getObjectById(parsedId);
            if (responseObject == null) {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            responseObject = null;
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseObject, code);
    }
}
