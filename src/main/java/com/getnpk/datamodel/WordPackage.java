/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getnpk.datamodel;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nitinkp
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WordPackage {

    private ArrayList<String> hyponyms;
    private ArrayList<String> synonyms;
    private ArrayList<String> hypernyms;

    public ArrayList<String> getHyponyms() {
        return hyponyms;
    }

    public void setHyponyms(ArrayList<String> hyponyms) {
        this.hyponyms = hyponyms;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }

    public ArrayList<String> getHypernyms() {
        return hypernyms;
    }

    public void setHypernyms(ArrayList<String> hypernyms) {
        this.hypernyms = hypernyms;
    }
    
}
