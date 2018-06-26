package de.hska.vis.webshop.composite.search.commands;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import de.hska.vis.webshop.composite.search.SearchController;
import de.hska.vis.webshop.core.database.model.IUser;

import java.util.List;

public class GetUserCommand extends HystrixCommand<List<IUser>> {
    private SearchController searchController;

    public GetUserCommand(SearchController searchController) {
        super(HystrixCommandGroupKey.Factory.asKey(GetUserCommand.class.toString()));
        this.searchController = searchController;
    }

    @Override
    protected List<IUser> run() throws Exception {
        List<IUser> body = searchController.userClient.getUserList().getBody();
        body.forEach((e) -> searchController.addToUserCache(e));
        return body;
    }

    @Override
    protected List<IUser> getFallback() {
        return searchController.getUsersCache();
    }
}
