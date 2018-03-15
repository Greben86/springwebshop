package shop.model.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.springframework.beans.factory.annotation.Autowired;
import shop.entity.Good;
import shop.model.Search;

public class SearchGoodImpl implements Search<Good> {
    private List<Good> result = new LinkedList<>();
    private Directory index;
    @Autowired
    private Analyzer standardAnalyzer;
    
    public SearchGoodImpl(String url) {
        try {
            index = FSDirectory.open(Paths.get(url));
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String createIndex(List<Good> list) {
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);        
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        
        try (IndexWriter writer = new IndexWriter(index, config)) {
            list.stream()
                    .filter(good -> !good.getFolder())
                    .forEach(good -> addGoodInDoc(writer, good));
        } catch (IOException ex) {
            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create index is sucesful for " + list.size();
    }

    @Override
    public List<Good> search(String query) {
        try {
            keySearch(query, "id");
            if (result.isEmpty())
                fuzzySearch(query);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void keySearch(final String toSearch, String keyField) throws IOException, ParseException {
        try (IndexReader reader = DirectoryReader.open(index)) {
            Query q = new QueryParser(keyField, standardAnalyzer).parse(toSearch);
            
            int hitsPerPage = 10;
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, null);
            searcher.search(q, collector);
            
            List<ScoreDoc> list = Arrays.asList(collector.topDocs().scoreDocs);            
            buildList(list, searcher);
        } 
    }
    
    public void fuzzySearch(final String toSearch) throws IOException, ParseException {
        try (IndexReader reader = DirectoryReader.open(index)) { 
            final IndexSearcher searcher = new IndexSearcher(reader);

            final Term term = new Term("name", toSearch);

            final int maxEdits = 2; // fuzziness of the query
            final Query query = new FuzzyQuery(term, maxEdits);
            final TopDocs search = searcher.search(query, 200);
            
            List<ScoreDoc> list = Arrays.asList(search.scoreDocs);            
            buildList(list, searcher);
        }
    }
    
    private void buildList(List<ScoreDoc> list, IndexSearcher search) {
        list.stream().forEach(scoreDoc -> {
            try {
                Document d = search.doc(scoreDoc.doc);
                Good good = new Good();
                good.setId(Long.parseLong(d.get("id")));
                good.setName(d.get("name"));
                good.setArticle(d.get("article"));
                good.setPrice(Float.parseFloat(d.get("price")));
                good.setInstock(Float.parseFloat(d.get("instock")));
                result.add(good);
            } catch (IOException ex) {
                Logger.getLogger(
                        SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void addGoodInDoc(IndexWriter w, Good good) {
        try {
            Document doc = new Document();
            doc.add(new TextField("id", good.getId().toString(), Field.Store.YES));
            doc.add(new TextField("name", good.getName(), Field.Store.YES));
            doc.add(new TextField("article", good.getArticle()==null?"":good.getArticle(), Field.Store.YES));
            doc.add(new TextField("price", good.getPrice().toString(), Field.Store.YES));
            doc.add(new TextField("instock", good.getInstock().toString(), Field.Store.YES));
            doc.add(new TextField("info", good.toString(), Field.Store.YES));
            w.addDocument(doc);
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
