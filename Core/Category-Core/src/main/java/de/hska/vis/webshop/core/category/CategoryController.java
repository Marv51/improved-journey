package de.hska.vis.webshop.core.category;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.hska.vis.webshop.core.database.dao.DaoFactory;
import de.hska.vis.webshop.core.database.dao.impl.CategoryDAO;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private CategoryDAO dao = DaoFactory.getCategoryDao();

    @HystrixCommand
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public ResponseEntity<Void> saveCategory(Category category) {
        return new ResponseEntity<>((dao.saveObject(category) ? HttpStatus.OK : HttpStatus.BAD_REQUEST));
    }

    @HystrixCommand
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public ResponseEntity<ICategory> getCategoryById(@PathVariable("id") int id) {
        Category cat = dao.getObjectById(id);
        if (cat == null){
            return new ResponseEntity<>(cat, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cat, HttpStatus.OK);
    }

    @HystrixCommand
    @RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") int id) {
        return new ResponseEntity<>((dao.deleteById(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @HystrixCommand
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategoryList() {
        return new ResponseEntity<List<Category>>(dao.getObjectList(), HttpStatus.OK);
    }
}
