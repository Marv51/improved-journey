package de.hska.vis.webshop.composite.search.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import de.hska.vis.webshop.composite.search.SearchController;
import de.hska.vis.webshop.core.database.model.ICategory;

import java.util.List;

public class GetCategoryCommand extends HystrixCommand<List<ICategory>> {
    private SearchController searchController;

    public GetCategoryCommand(SearchController searchController) {
        super(HystrixCommandGroupKey.Factory.asKey(GetCategoryCommand.class.toString()));
        this.searchController = searchController;
    }

    @Override
    protected List<ICategory> run() throws Exception {
        List<ICategory> body = searchController.categoryClient.getCategoryList().getBody();
        body.forEach((e) -> searchController.addToCategoryCache(e));
        return body;
    }

    @Override
    protected List<ICategory> getFallback() {
        return searchController.getCategoriesCache();
    }
}
