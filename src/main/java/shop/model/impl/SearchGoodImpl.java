package shop.model.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.MultiTerms;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;


import org.springframework.beans.factory.annotation.Autowired;
import shop.entity.Good;
import shop.model.Search;

public class SearchGoodImpl implements Search<Good> {
    final private Directory index = new RAMDirectory();
    //final private Directory dir = FSDirectory.open(Paths.get(""));
    @Autowired
    private RussianAnalyzer standardAnalyzer;

    @Override
    public String createIndex(List<Good> list) {
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);

        try (IndexWriter writer = new IndexWriter(index, config)) {
            list.stream().forEach((Good good) -> addGoodInDoc(writer, good));
        } catch (IOException ex) {
            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create index is sucesful for " + list.size();
    }

    @Override
    public List<String> search(String query) {  
        try {
            return fuzzySearch(query);
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
//        List<String> result = new LinkedList<>();
//        try (IndexReader reader = DirectoryReader.open(index)) {        
//            // the "title" arg specifies the default field to use
//            // when no field is explicitly specified in the query.
//            Query q = new QueryParser("id", standardAnalyzer).parse(query);
//            
//            // 3. search
//            int hitsPerPage = 10;
//            IndexSearcher searcher = new IndexSearcher(reader);
//            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, null);
//            searcher.search(q, collector);
//            ScoreDoc[] hits = collector.topDocs().scoreDocs;
//            // 4. display results
//            result.add("Найдено позиций: " + hits.length);
//            for (int i = 0; i < hits.length; ++i) {
//                int docId = hits[i].doc;
//                Document d = searcher.doc(docId);
//                result.add((i + 1) + ". " + d.toString());
//            }
//            // reader can only be closed when there
//            // is no need to access the documents any more.
//            
//            return result;
//        } catch (IOException | ParseException ex) {
//            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
//            return null;
//        }        
    }
    
    public List<String> fuzzySearch(final String toSearch) throws IOException, ParseException {
        List<String> result = new LinkedList<>();
        try (IndexReader reader = DirectoryReader.open(index)) { 
            final IndexSearcher indexSearcher = new IndexSearcher(reader);

            final Term term = new Term("info", toSearch);

            final int maxEdits = 2; // This is very important variable. It regulates fuzziness of the query
            final Query query = new FuzzyQuery(term, maxEdits);
            final TopDocs search = indexSearcher.search(query, 100);
            final ScoreDoc[] hits = search.scoreDocs;
            
            result.add("Найдено позиций: " + hits.length);
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = indexSearcher.doc(docId);
                result.add((i + 1) + ". " + d.toString());
            }
        }
        return result;
    }
    
    private void addGoodInDoc(IndexWriter w, Good good) {
        try {
            Document doc = new Document();
            doc.add(new TextField("id", good.getId().toString(), Field.Store.YES));
            doc.add(new TextField("name", good.getName()==null?"":good.getName(), Field.Store.YES));
            doc.add(new TextField("description", good.getDescription()==null?"":good.getDescription(), Field.Store.YES));
            doc.add(new TextField("article", good.getArticle()==null?"":good.getArticle(), Field.Store.YES));
            doc.add(new TextField("info", good.toString(), Field.Store.YES));
            w.addDocument(doc);
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
