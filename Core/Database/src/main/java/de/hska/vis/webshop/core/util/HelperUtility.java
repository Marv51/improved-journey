package de.hska.vis.webshop.core.util;

import de.hska.vis.webshop.core.database.dao.IGenericDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

public class HelperUtility<T, P extends Serializable> {
    public ResponseEntity<T> getResponse(String stringId, IGenericDAO<T, P> dao, Function<String, P> castStringTo) {
        HttpStatus code = HttpStatus.OK;
        T responseObject = null;
        try {
            P parsedId = castStringTo.apply(stringId);
            List<T> result = dao.get(parsedId);
            if (result == null || result.isEmpty() || result.get(0) == null) {
                code = HttpStatus.NOT_FOUND;
            } else {
                responseObject = result.get(0);
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(responseObject, code);
    }
}
