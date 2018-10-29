package de.hska.vis.webshop.composite.search.clients;

import de.hska.vis.webshop.composite.search.clients.configuration.OAuth2FeignClientConfiguration;
import de.hska.vis.webshop.core.database.model.ICategory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "category-service", configuration = OAuth2FeignClientConfiguration.class)
public interface CategoryClient {
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    ResponseEntity<List<ICategory>> getCategoryList();
}
