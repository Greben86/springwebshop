package shop.model.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import shop.entity.Good;
import shop.model.Search;

public class SearchGoodImpl implements Search<Good> {

    private List<Good> result;
    private Directory index;
    private final int MAX_EDITS = 2; // fuzziness of the query

    public SearchGoodImpl(String url) {
        result = new LinkedList<>();
        try {
            index = FSDirectory.open(Paths.get(url));
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String createIndex(List<Good> list) {
        IndexWriterConfig config = new IndexWriterConfig(new RussianAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        try (IndexWriter writer = new IndexWriter(index, config)) {
            list.stream()
                    .filter(good -> !good.getFolder())
                    .forEach(good -> addGoodInDoc(writer, good));
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "Create index is sucesful for " + list.size();
    }

    @Override
    public List<Good> search(String query) {
        try {
            String[] list = query.split(" ");
            if (list.length == 1) {
                keySearch(query);
                if (result.isEmpty()) {
                    fuzzySearch(query);
                }
            } else {
                multiSearch(list);
            }
        } catch (IOException | ParseException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(SearchGoodImpl.class.getName()).log(Level.INFO, query);
        return result;
    }

    public void keySearch(final String toSearch) throws IOException, ParseException {
        try (IndexReader reader = DirectoryReader.open(index)) {
            Query q = new QueryParser("id", new RussianAnalyzer())
                    .parse(toSearch);

            int hitsPerPage = 10;
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector
                    .create(hitsPerPage, null);
            searcher.search(q, collector);

            List<ScoreDoc> list = Arrays.asList(collector.topDocs().scoreDocs);
            buildList(list, searcher);
        }
    }

    public void fuzzySearch(final String toSearch) throws IOException, 
            ParseException {
        try (IndexReader reader = DirectoryReader.open(index)) {
            final IndexSearcher searcher = new IndexSearcher(reader);
            final Term term = new Term("name", toSearch);
            final Query query = new FuzzyQuery(term, MAX_EDITS);
            final TopDocs search = searcher.search(query, 200);
            List<ScoreDoc> list = Arrays.asList(search.scoreDocs);
            buildList(list, searcher);
        }
    }

    public void multiSearch(final String... queries) throws IOException, 
            ParseException {
        try (IndexReader reader = DirectoryReader.open(index)) {
            Query q = createQueryForName(queries);

            int hitsPerPage = 10;
            IndexSearcher searcher = new IndexSearcher(reader);
            TopScoreDocCollector collector = TopScoreDocCollector
                    .create(hitsPerPage, null);
            searcher.search(q, collector);

            List<ScoreDoc> list = Arrays.asList(collector.topDocs().scoreDocs);
            buildList(list, searcher);
        }
    }
    
    private Query createQueryForName(final String... queries) throws ParseException {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(queries)
                .forEach(item -> sb.append("+name:").append(item).append(" "));
        return new QueryParser("name", new RussianAnalyzer())
                    .parse(sb.toString());
    }

    private void buildList(List<ScoreDoc> list, IndexSearcher search) {
        list.stream().forEach(scoreDoc -> buildGood(scoreDoc, search));
    }

    private void buildGood(ScoreDoc scoreDoc, IndexSearcher search) {
        try {
            Document d = search.doc(scoreDoc.doc);
            Good good = new Good();
            good.setId(Long.parseLong(d.get("id")));
            good.setName(d.get("name"));
            good.setPrice(Float.parseFloat(d.get("price")));
            good.setInstock(Float.parseFloat(d.get("instock")));
            result.add(good);
        } catch (IOException ex) {
            Logger.getLogger(
                    SearchGoodImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addGoodInDoc(IndexWriter w, Good good) {
        try {
            Document doc = new Document();
            doc.add(new TextField("id", good.getId().toString(), Field.Store.YES));
            doc.add(new TextField("name", good.getName(), Field.Store.YES));
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
