package shop.rest;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shop.entity.Good;
import shop.model.Search;
import shop.service.GoodService;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private GoodService goodService;
    @Resource(name = "search")
    private Search search;

    @GetMapping(value = "/createindex", produces = MediaType.TEXT_PLAIN_VALUE)
    public String createIndex() {
        return search.createIndex(goodService.getList(-1L));
    }

    @GetMapping(value = "/goods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Good> searchGood(
            @RequestParam(value = "query", required = true) String query) {
        return search.search(query);
    }
}
