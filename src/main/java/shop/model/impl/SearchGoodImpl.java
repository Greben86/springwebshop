package shop.model.impl;

import java.util.List;
import shop.entity.Good;
import shop.model.Search;

public class SearchGoodImpl implements Search<Good> {

    @Override
    public String createIndex(List<Good> list) {
        return "Create index is sucesful for ";
    }

    @Override
    public List<Good> search(String query) {
        return null;
    }
    
}
