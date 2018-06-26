package de.hska.vis.webshop.composite.search;

import de.hska.vis.webshop.composite.search.clients.CategoryClient;
import de.hska.vis.webshop.composite.search.clients.ProductClient;
import de.hska.vis.webshop.composite.search.clients.UserClient;
import de.hska.vis.webshop.composite.search.commands.GetCategoryCommand;
import de.hska.vis.webshop.composite.search.commands.GetProductsCommand;
import de.hska.vis.webshop.composite.search.commands.GetUserCommand;
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

    public static final String ALL_CHARS_REGEX = ".*";
    @Autowired
    public UserClient userClient;
    @Autowired
    public ProductClient productClient;
    @Autowired
    public CategoryClient categoryClient;

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

    public void addToUserCache(IUser user) {
        USER_CACHE.put(user.getId(), user);
    }

    public void addToProductCache(IProduct product) {
        PRODUCT_CACHE.put(product.getId(), product);
    }

    public void addToCategoryCache(ICategory category) {
        CATEGORY_CACHE.put(category.getId(), category);
    }

    /**
     * Getter for the whole list of {@link IUser}s via {@link #userClient}.
     * <p>
     * Fills the {@link #USER_CACHE} with the result before returning to caller.
     *
     * @return the whole user list.
     */
    private List<IUser> getUsers() {
        return new GetUserCommand(this).execute();
    }

    public List<IUser> getUsersCache() {
        return new LinkedList<>(USER_CACHE.values());
    }

    /**
     * Getter for the whole list of {@link IProduct}s via {@link #productClient}.
     * <p>
     * Fills the {@link #PRODUCT_CACHE} with the result before returning to caller.
     *
     * @return the whole product list.
     */
    private List<IProduct> getProducts() {
        return new GetProductsCommand(this).execute();
    }

    public List<IProduct> getProductsCache() {
        return new LinkedList<>(PRODUCT_CACHE.values());
    }

    /**
     * Getter for the whole list of {@link ICategory}s via {@link #categoryClient}.
     * <p>
     * Fills the {@link #CATEGORY_CACHE} with the result before returning to caller.
     *
     * @return the whole category list.
     */
    private List<ICategory> getCategories() {
        return new GetCategoryCommand(this).execute();
    }

    public List<ICategory> getCategoriesCache() {
        return new LinkedList<>(CATEGORY_CACHE.values());
    }

    @RequestMapping(value = "/search/users", method = RequestMethod.GET)
    public ResponseEntity<List<IUser>> getUserList() {
        return new ResponseEntity<>(getUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/products", method = RequestMethod.GET)
    public ResponseEntity<List<IProduct>> getProductList() {
        return new ResponseEntity<>(getProducts(), HttpStatus.OK);
    }

    @RequestMapping(value = "/search/categories", method = RequestMethod.GET)
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
        return new ResponseEntity<>(searchProductByName(name, minPrice, maxPrice), HttpStatus.OK);
    }

    private List<IProduct> searchProductByName(String name, Integer min, Integer max) {
        List<IProduct> products = getProducts();
        List<IProduct> result = new LinkedList<>();

        String regex = ALL_CHARS_REGEX + name + ALL_CHARS_REGEX;
        products.forEach(c -> {
            // name cannot be null or empty, as its required by the request
            // therefore you need to supply '.*' for all names
            if (c.getName().matches(regex)) {
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

        String regex = ALL_CHARS_REGEX + name + ALL_CHARS_REGEX;
        categories.forEach(c -> {
            if (c.getName().matches(regex)) {
                result.add(c);
            }
        });
        return new ResponseEntity<>(result, HttpStatus.OK);
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

        String regex = ALL_CHARS_REGEX + name + ALL_CHARS_REGEX;
        users.forEach(c -> {
            if (c.getUsername().matches(regex)) {
                result.add(c);
            }
        });

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}