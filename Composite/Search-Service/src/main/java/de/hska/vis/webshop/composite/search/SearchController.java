package de.hska.vis.webshop.composite.search;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import de.hska.vis.webshop.composite.search.clients.CategoryClient;
import de.hska.vis.webshop.composite.search.clients.ProductClient;
import de.hska.vis.webshop.composite.search.clients.UserClient;
import de.hska.vis.webshop.core.database.model.ICategory;
import de.hska.vis.webshop.core.database.model.IProduct;
import de.hska.vis.webshop.core.database.model.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    UserClient userClient;
    @Autowired
    ProductClient productClient;
    @Autowired
    CategoryClient categoryClient;

    /**
     * Cache map for {@link IUser}s.
     * <p>
     * Could technically be a set but therefore an equals would be necessary.
     * tl;dr shorter and easier that way.
     */
    private Map<Integer, IUser> USER_CACHE = new HashMap<>();
    /**
     * Cache map for {@link IProduct}s.
     * <p>
     * Could technically be a set but therefore an equals would be necessary.
     * tl;dr shorter and easier that way.
     */
    private Map<Integer, IProduct> PRODUCT_CACHE = new HashMap<>();
    /**
     * Cache map for {@link ICategory}s.
     * <p>
     * Could technically be a set but therefore an equals would be necessary.
     * tl;dr shorter and easier that way.
     */
    private Map<Integer, ICategory> CATEGORY_CACHE = new HashMap<>();

    // Getters for whole list

    /**
     * Getter for the whole list of {@link IUser}s via {@link #userClient}.
     * <p>
     * Fills the {@link #USER_CACHE} with the result before returning to caller.
     *
     * @return the whole user list.
     */
    @HystrixCommand(fallbackMethod = "getUsersCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")
    })
    private List<IUser> getUsers() {
        List<IUser> users = userClient.getUserList().getBody();
        users.forEach(user -> {
            USER_CACHE.put(user.getId(), user);
        });
        return users;
    }

    private List<IUser> getUsersCache() {
        return new LinkedList<>(USER_CACHE.values());
    }

    /**
     * Getter for the whole list of {@link IProduct}s via {@link #productClient}.
     * <p>
     * Fills the {@link #PRODUCT_CACHE} with the result before returning to caller.
     *
     * @return the whole product list.
     */
    @HystrixCommand(fallbackMethod = "getProductsCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1")
    })
    private List<IProduct> getProducts() {
        List<IProduct> products = productClient.getProductList().getBody();
        products.forEach(product -> {
            PRODUCT_CACHE.put(product.getId(), product);
        });
        return products;
    }

    private List<IProduct> getProductsCache() {
        return new LinkedList<>(PRODUCT_CACHE.values());
    }

    /**
     * Getter for the whole list of {@link ICategory}s via {@link #categoryClient}.
     * <p>
     * Fills the {@link #CATEGORY_CACHE} with the result before returning to caller.
     *
     * @return the whole category list.
     */
    @HystrixCommand(fallbackMethod = "getCategoriesCache", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2")
    })
    private List<ICategory> getCategories() {
        List<ICategory> categories = categoryClient.getCategoryList().getBody();
        categories.forEach(category -> {
            CATEGORY_CACHE.put(category.getId(), category);
        });
        return categories;
    }

    private List<ICategory> getCategoriesCache() {
        return new LinkedList<>(CATEGORY_CACHE.values());
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> getUserList() {
        return new ResponseEntity<>(getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<IProduct>> getProductList() {
        return new ResponseEntity<>(getProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public ResponseEntity<List<ICategory>> getCategoryList() {
        return new ResponseEntity<>(getCategories(), HttpStatus.OK);
    }

    /**
     * Method to search all products, with the help of the {@link ProductClient}.
     * <p>
     * Possible test url would be:
     * <p>
     * search-service/search/product?name=.* // all products
     * search-service/search/product?name=.*&minPrice=23&maxPrice=30 // all products within price range
     *
     * @param name     regular expression from the query parameter of the url
     * @param minPrice minimal price as integer, optional
     * @param maxPrice maximal price as integer, optional
     * @return a List of all products within the search criteria as they are supplied
     */
    @RequestMapping(value = "/search/product", method = RequestMethod.GET)
    public ResponseEntity<List<IProduct>> searchProduct(@RequestParam(value = "name") String name,
                                                        @RequestParam(value = "minPrice", required = false) Integer minPrice,
                                                        @RequestParam(value = "maxPrice", required = false) Integer maxPrice) {
        return new ResponseEntity<>(searchProductByName(name, minPrice, maxPrice), HttpStatus.I_AM_A_TEAPOT);
    }

    private List<IProduct> searchProductByName(String name, Integer min, Integer max) {
        List<IProduct> products = getProducts();
        List<IProduct> result = new LinkedList<>();

        products.forEach(c -> {
            // name cannot be null or empty, as its required by the request
            // therefore you need to supply '.*' for all names
            if (c.getName().matches(name)) {
                if (min == null && max == null
                        || min != null && max != null && isBetween((int) c.getPrice(), min, max)
                        || min != null && (int) c.getPrice() > min
                        || max != null && (int) c.getPrice() < max) {
                    result.add(c);
                }
            }
        });

        return result;
    }

    private boolean isBetween(int value, int min, int max) {
        return value > min && value < max;
    }

    /**
     * Method to search all products, with the help of the {@link CategoryClient}.
     * <p>
     * Possible test url would be:
     * <p>
     * search-service/search/category?name=.* // all categories
     * search-service/search/category?name=.*bcd.* // all categories, containing 'bcd' in their names
     *
     * @param name regular expression from the query parameter of the url
     * @return a List of all categories within the search criteria
     */
    @RequestMapping(value = "/search/category", method = RequestMethod.GET)
    public ResponseEntity<List<ICategory>> searchCategory(@RequestParam("name") String name) {
        List<ICategory> categories = getCategories();
        List<ICategory> result = new LinkedList<>();

        categories.forEach(c -> {
            if (c.getName().matches(name)) {
                result.add(c);
            }
        });
        return new ResponseEntity<>(result, HttpStatus.I_AM_A_TEAPOT);
    }


    /**
     * Method to search all users, with the help of the {@link UserClient}.
     * <p>
     * Possible test url would be:
     * <p>
     * search-service/search/user?name=.* // all users
     * search-service/search/user?name=.*bcd.* // all users, containing 'bcd' in their names
     * <p>
     * !!!! CAUTION !!!!
     * INTENDED FOR DEBUG PURPOSES
     * TODO: either fix security, or delete entirely
     * simply because there are users by default, but no categories and products.
     * !!!! CAUTION !!!!
     *
     * @param name regular expression from the query parameter of the url
     * @return a List of all users within the search criteria
     */
    @RequestMapping(value = "/search/user", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> searchUsers(@RequestParam("name") String name) {
        List<IUser> users = getUsers();
        List<IUser> result = new LinkedList<>();

        users.forEach(c -> {
            if (c.getUsername().matches(name)) {
                result.add(c);
            }
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}