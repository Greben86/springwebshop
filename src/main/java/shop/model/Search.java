package shop.model;

import java.util.List;

public interface Search<T> {
    public String createIndex(List<T> list);
    public List<T> search(String query);
}
