package de.hska.vis.webshop.composite.search.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import de.hska.vis.webshop.composite.search.SearchController;
import de.hska.vis.webshop.core.database.model.IProduct;

import java.util.List;

public class GetProductsCommand extends HystrixCommand<List<IProduct>> {
    private SearchController searchController;

    public GetProductsCommand(SearchController searchController) {
        super(HystrixCommandGroupKey.Factory.asKey(GetProductsCommand.class.toString()));
        this.searchController = searchController;
    }

    @Override
    protected List<IProduct> run() throws Exception {
        List<IProduct> body = searchController.productClient.getProductList().getBody();
        body.forEach((e) -> searchController.addToProductCache(e));
        return body;
    }

    @Override
    protected List<IProduct> getFallback() {
        return searchController.getProductsCache();
    }
}
