package de.hska.vis.webshop.composite.search.clients;

import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.impl.Category;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

// name is the application name from the category core
@FeignClient("category-service")
public interface CategoryClient {
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    ResponseEntity<Void> saveCategory(@RequestBody Category category);

    @RequestMapping(value = "/category/{stringId}", method = RequestMethod.GET)
    ResponseEntity<ICategory> getCategoryById(@PathVariable String stringId);

    @RequestMapping(value = "/category/{stringId}", method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteCategoryById(@PathVariable String stringId);

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    ResponseEntity<List<ICategory>> getCategoryList();
}
