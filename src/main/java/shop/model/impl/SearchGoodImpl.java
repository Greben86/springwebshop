package shop.model.impl;

import java.io.IOException;
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
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import shop.entity.Good;
import shop.model.Search;

public class SearchGoodImpl implements Search<Good> {
    final private Directory index = new RAMDirectory();
    @Autowired
    private RussianAnalyzer standardAnalyzer;

    @Override
    public String createIndex(List<Good> list) {
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);

        try (IndexWriter w = new IndexWriter(index, config)) {
            //list.stream().forEach((good) -> addGoodInDoc(good));
            addDoc(w, "Lucene in Action", "193398817");
            addDoc(w, "Lucene for Dummies", "55320055Z");
            addDoc(w, "Managing Gigabytes", "55063554A");
            addDoc(w, "The Art of Computer Science", "9900333X");
        } catch (IOException ex) {
            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Create index is sucesful for " + list.size();
    }

    @Override
    public List<Good> search(String query) {   
        List<Good> result = new LinkedList<>();
        try (IndexReader reader = DirectoryReader.open(index)) {        
            // the "title" arg specifies the default field to use
            // when no field is explicitly specified in the query.
            Query q = new QueryParser("isbn", standardAnalyzer).parse(query);
            
            // 3. search
            int hitsPerPage = 10;
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, null);
            searcher.search(q, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            // 4. display results
            /*result.add("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; ++i) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                result.add((i + 1) + ". " + d.get("isbn") + "\t" + d.get("title"));
            }*/
            // reader can only be closed when there
            // is no need to access the documents any more.
            
            return result;
        } catch (IOException | ParseException ex) {
            Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));

        // use a string field for isbn because we don't want it tokenized
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        w.addDocument(doc);
    }
    
    private void addGoodInDoc(Good good) {
        //
    }
}
