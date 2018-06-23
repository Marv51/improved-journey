package de.hska.vis.webshop.core.category;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.ICategoryDAO;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;
import de.hska.vis.webshop.core.util.HelperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private ICategoryDAO dao = DaoFactory.getCategoryDao();
    private HelperUtility<ICategory, Integer> helper = new HelperUtility<>();

    @HystrixCommand
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ResponseEntity<Void> saveCategory(@RequestBody Category category) {
        HttpStatus code;
        if (dao.saveObject(category)) {
            code = HttpStatus.OK;
        } else {
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/category/{stringId}", method = RequestMethod.GET)
    public ResponseEntity<ICategory> getCategoryById(@PathVariable String stringId) {
        return helper.getResponse(stringId, dao, Integer::parseInt);
    }

    @HystrixCommand
    @RequestMapping(value = "/category/{stringId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategoryById(@PathVariable String stringId) {
        HttpStatus code;
        try {
            Integer parsedId = Integer.parseInt(stringId);
            if (dao.deleteById(parsedId)) {
                code = HttpStatus.OK;
            } else {
                code = HttpStatus.NOT_FOUND;
            }
        } catch (NumberFormatException ex) {
            // invalid number supplied; returning null and HTTP/400
            code = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(code);
    }

    @HystrixCommand
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<ICategory>> getCategoryList() {
        return new ResponseEntity<>(dao.getObjectList(), HttpStatus.OK);
    }
}
