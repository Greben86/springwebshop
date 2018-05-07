package shop.entity.factory.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import shop.entity.News;
import shop.entity.factory.BasicFactory;

public class NewsFactoryImpl implements BasicFactory<News> {

    @Override
    public News factory(ResultSet set) throws SQLException {
        News news = new News();
        news.setId(set.getLong("id"));
        news.setTitle(set.getString("title"));
        news.setBody(set.getString("body"));

//        InputStream stream = set.getBlob("body").getBinaryStream();
//        
//        byte[] buffer = new byte[1024];
//                
//        StringBuffer sb = new StringBuffer();
//        sb.
//        
//        news.setBody(stream.toString());
//
//        byte[] buffer = new byte[1];
//        InputStream is = set.getBlob("body").getBinaryStream();
//        StringBuffer sb = new StringBuffer();
//        try {
//            while (is.read(buffer) > 0) {
//                sb.append(new String(buffer));
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(NewsFactoryImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        byte[] buffer = set.getBlob("body").getBytes(1, (int) set.getBlob("body").length());
//        news.setBody(sb.toString());

        news.setFilename(set.getString("filename"));
        news.setDate(set.getDate("date"));
        news.setEnabled(set.getBoolean("enabled"));
        return news;
    }

}
