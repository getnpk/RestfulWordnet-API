/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getnpk.restfulwordnet;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

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

    @PostConstruct
    public void init(){
        try {
            String path = "C:\\Users\\nitinkp\\Desktop\\dict";
            url = new URL("file", null , path );
            dict = new Dictionary (new File(path));
            dict.open();
        } catch (Exception ex) {
            Logger.getLogger(WordnetBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @GET
    @Produces("text/html")
    @Path("/hyponym/{word}")
    public String getHypo(@PathParam("word") String word, @HeaderParam("host") String host){
        //System.out.println("SYS HOST: " + host);
        return this.getHyponyms(word).toString();
    }
    
    @GET
    @Produces("text/html")
    @Path("/synonym/{word}")
    public String getSyno(@PathParam("word") String word){
        return this.getSynonyms(word).toString();
    }
    
    @GET
    @Produces("text/html")
    @Path("/hypernym/{word}")
    public String getHyp(@PathParam("word") String word){
        return this.getSynonyms(word).toString();
    }
    
    //Synonyms
    public ArrayList<String> getSynonyms(String term){
        
        wordlist = new ArrayList<String>();
        
        IIndexWord idxWord = dict.getIndexWord(term, POS.NOUN );
        
        if (idxWord == null)
            return wordlist;
        
       IWordID wordID = idxWord.getWordIDs().get(0) ; // 1st meaning
        
       IWord word = dict.getWord(wordID);
       ISynset synset = word.getSynset();

       for( IWord w : synset.getWords ()){
           term = w.getLemma().replace("_", " ").toLowerCase();
           wordlist.add(term);
           //wordmap.put(term, confidence.get("full"));
       }
       return wordlist; 
   }
    
    
    //Hyponyms
        public ArrayList<String> getHyponyms(String term){
    
        wordlist = new ArrayList<String>();
        
        IIndexWord idxWord = dict.getIndexWord(term, POS.NOUN );
     
        if (idxWord == null)
            return wordlist;
        
        IWordID wordID = idxWord.getWordIDs().get(0) ;
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();

        List < ISynsetID > hyponyms = synset.getRelatedSynsets(Pointer.HYPONYM);
        List <IWord> newwords ;
        for( ISynsetID sid : hyponyms ){
            newwords = dict.getSynset(sid).getWords ();
            for( Iterator <IWord> i = newwords.iterator(); i.hasNext();){
                String item = i.next().getLemma ();
                item = item.replace("_", " ").toLowerCase();
                wordlist.add(item);
                //wordmap.put(item, confidence.get("hpo"));

            }
        }
        
        return wordlist;
        
    }    
    
    //Hypernyms
    public ArrayList<String> getHypernyms(String term){

        wordlist = new ArrayList<String>();
        
        IIndexWord idxWord = dict.getIndexWord(term, POS.NOUN );
        
        if (idxWord == null)
            return wordlist;
        
        IWordID wordID = idxWord.getWordIDs().get(0) ;
        IWord word = dict.getWord(wordID);
        ISynset synset = word.getSynset();
        
        List < ISynsetID > hypernyms = synset.getRelatedSynsets(Pointer.HYPERNYM);
        List <IWord> words ;

        for( ISynsetID sid : hypernyms ){
            words = dict.getSynset(sid).getWords ();
            for( Iterator <IWord> i = words.iterator(); i.hasNext();){
                    String item = i.next().getLemma ();
                    item = item.replace("_", " ").toLowerCase();
                    wordlist.add(item);
                    //wordmap.put(item, confidence.get("hyp"));
            }
        }
        return wordlist;
        
    }

    
    
}
