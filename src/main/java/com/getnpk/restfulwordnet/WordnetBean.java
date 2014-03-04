/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getnpk.restfulwordnet;

import com.getnpk.datamodel.WordPackage;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author nitinkp
 */
@Stateless
@Path("/wordnet")
public class WordnetBean {

    /**
     * Creates a new instance of WordnetBean
     */
    public WordnetBean() {
    }
    private IDictionary dict;
    private URL url;
    private ArrayList<String> wordlist;
    private Properties prop;
    private Response.ResponseBuilder builder;
    private WordPackage pack;
    private IIndexWord idxWord;
    private CacheControl cache;
    private IWordID wordID;
    private IWord word;
    private ISynset synset;

    //hyponyms and hypernyms
    private List< ISynsetID> hyponyms, hypernyms;
    private List<IWord> newwords;
    
    
    @PostConstruct
    public void init() {

        prop = new Properties();
        try {
            System.out.println("SYS DIR: " + System.getProperty("user.dir"));
            prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
            String path = prop.getProperty("wordnet_dictionary_path");

            url = new URL("file", null, path);
            dict = new Dictionary(new File(path));
            dict.open();
        } catch (Exception ex) {
            Logger.getLogger(WordnetBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Produces("application/json")
    @Path("/hyponym/{word}")
    public String getHypo(@PathParam("word") String word, @HeaderParam("host") String host) {
        //System.out.println("SYS HOST: " + host);
        return this.getHyponyms(word).toString();
    }

    @GET
    @Produces("application/json")
    @Path("/synonym/{word}")
    public String getSyno(@PathParam("word") String word) {
        return this.getSynonyms(word).toString();
    }

    @GET
    @Produces("application/json")
    @Path("/hypernym/{word}")
    public String getHyp(@PathParam("word") String word) {
        return this.getSynonyms(word).toString();
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/all/{word}")
    public Response getAll(@PathParam("word") String word) {

        if (null == word) {
            builder = Response.status(Response.Status.NOT_FOUND);
        } else {

            pack = new WordPackage();
            pack.setHypernyms(this.getHypernyms(word));
            pack.setHyponyms(this.getHyponyms(word));
            pack.setSynonyms(this.getSynonyms(word));

            builder = Response.ok(pack);
            cache = new CacheControl();
            cache.setNoCache(true);
            builder.cacheControl(cache);

        }
        return builder.build();
    }

    //Synonyms
    public ArrayList<String> getSynonyms(String term) {

        wordlist = new ArrayList<String>();

        idxWord = dict.getIndexWord(term, POS.NOUN);

        if (idxWord == null) {
            return wordlist;
        }

        wordID = idxWord.getWordIDs().get(0); // 1st meaning

        word = dict.getWord(wordID);
        synset = word.getSynset();

        for (IWord w : synset.getWords()) {
            term = w.getLemma().replace("_", " ").toLowerCase();
            wordlist.add(term);
        }
        return wordlist;
    }

    //Hyponyms
    public ArrayList<String> getHyponyms(String term) {

        wordlist = new ArrayList<String>();

        idxWord = dict.getIndexWord(term, POS.NOUN);

        if (idxWord == null) {
            return wordlist;
        }

        wordID = idxWord.getWordIDs().get(0);
        word = dict.getWord(wordID);
        synset = word.getSynset();

        hyponyms = synset.getRelatedSynsets(Pointer.HYPONYM);

        String item;

        for (ISynsetID sid : hyponyms) {
            newwords = dict.getSynset(sid).getWords();
            for (Iterator<IWord> i = newwords.iterator(); i.hasNext();) {
                item = i.next().getLemma();
                item = item.replace("_", " ").toLowerCase();
                wordlist.add(item);
            }
        }

        return wordlist;

    }

    //Hypernyms
    public ArrayList<String> getHypernyms(String term) {

        wordlist = new ArrayList<String>();

        idxWord = dict.getIndexWord(term, POS.NOUN);

        if (idxWord == null) {
            return wordlist;
        }

        wordID = idxWord.getWordIDs().get(0);
        word = dict.getWord(wordID);
        synset = word.getSynset();

        hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);

        String item;

        for (ISynsetID sid : hypernyms) {
            newwords = dict.getSynset(sid).getWords();
            for (Iterator<IWord> i = newwords.iterator(); i.hasNext();) {
                item = i.next().getLemma();
                item = item.replace("_", " ").toLowerCase();
                wordlist.add(item);
            }
        }
        return wordlist;

    }
}
