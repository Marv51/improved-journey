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

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
            ResponseEntity<ICategory> response = getCategoryById(stringId);
            if (response.getStatusCode() != HttpStatus.OK) {
                code = HttpStatus.NOT_FOUND;
            } else {
                ICategory category = response.getBody();
                if (!category.getProducts().isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
                }
                int parsedId = Integer.parseInt(stringId);
                // get all products for this category
                ICategory entity = new Category();
                entity.setId(parsedId);
                if (dao.deleteObject(entity)) {
                    code = HttpStatus.OK;
                } else {
                    code = HttpStatus.NOT_FOUND;
                }
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
        // this hack is necessary because hibernate criterias do not guarantee unique results
        // with left outer joins which we have with OneToMany category - product relationship
        List<ICategory> objectList = dao.getObjectList();
        Set<ICategory> set = new HashSet<>(objectList);
        return new ResponseEntity<>(new LinkedList<>(set), HttpStatus.OK);
    }
}
