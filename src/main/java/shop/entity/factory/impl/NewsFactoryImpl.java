package shop.entity.factory.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.entity.News;
import shop.entity.factory.BasicFactory;

public class NewsFactoryImpl implements BasicFactory<News> {

    @Override
    public News factory(ResultSet set) throws SQLException {
        News news = new News();
        news.setId(set.getLong("id"));
        news.setTitle(set.getString("title"));
        news.setBody(set.getString("body"));
        news.setFilename(set.getString("filename"));
        news.setDate(set.getDate("date"));
        news.setEnabled(set.getBoolean("enabled"));
        return news;
    }
    
}
