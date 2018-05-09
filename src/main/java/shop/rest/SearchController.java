package shop.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import shop.entity.Good;
import shop.model.Search;
import shop.model.impl.SearchGoodImpl;
import shop.service.GoodService;

@RestController
@RequestMapping("/search")
public class SearchController {
//    private final String MAGIC_PATH = new String("D:\\luceneindex");
    private final String MAGIC_PATH = "/opt/index";
    @Autowired
    private GoodService goodService;
//    @Autowired
//    private Search search;
    
    @RequestMapping(value = "/createindex", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    @ResponseBody
    public String createIndex() {
        Search search = new SearchGoodImpl(MAGIC_PATH);
        return search.createIndex(goodService.getList(new Long(-1)));
    }
    
    @RequestMapping(value = "/goods", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<Good> searchGood(
            @RequestParam(value="query", required=true) String query) {
        Search search = new SearchGoodImpl(MAGIC_PATH);
        return search.search(query);
    } 
}
