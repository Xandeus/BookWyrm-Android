package com.example.alex.wordplay;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Alex on 8/26/2016.
 */
public class Word implements Serializable{
    private String word,associatedBook;
    private String[] definitions,types;
    public Word(String word,String[] definitions,String[] types, String associatedBook) {
        this.word = word;
        this.definitions = definitions;
        this.types = types;
        this.associatedBook = associatedBook;
    }
    public String getWord(){
        return word;
    }
    public String[] getDefinitions(){
        return definitions;
    }
    public String[] getTypes(){
        return types;
    }
    public String getAssociatedBook(){
        return associatedBook;
    }
    public String toString() {
        String definition = "<p><b>" +word + "</b></p>";
        for (int i = 0; i < definitions.length; i++) {
            Log.i("Loop",definitions[i]+" "+types[i]);
            definition+="Type: <i>" + types[i] + "</i></p>";
            definition+="<p>Definition: " + definitions[i] + "</p>";
        }
        return definition;
    }
}
