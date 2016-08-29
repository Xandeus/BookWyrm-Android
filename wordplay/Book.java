package com.example.alex.wordplay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Alex on 8/27/2016.
 */
public class Book implements Comparable<Book>, Serializable {
    private String title,author;
    private int numPages;
    ArrayList<Word> associatedWords;
    public Book(String title,String author, int numPages){
        this.title = title;
        this.author = author;
        this.numPages = numPages;
        associatedWords = new ArrayList<>();
    }
    public boolean addWord(Word word){
        return associatedWords.add(word);
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }

    public String toString(){
        return title + " " + "by: " + author;
    }

    @Override
    public int compareTo(Book book) {
        if(getTitle().equalsIgnoreCase(book.getTitle())&& getAuthor().equalsIgnoreCase(book.getAuthor())){
            return 0;
        }
        return getTitle().compareTo(book.getTitle());
    }
}
