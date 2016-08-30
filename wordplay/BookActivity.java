package com.example.alex.wordplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    TextView bookInformation;
    ScrollView scroll;
    Book bookViewed;
    ArrayList<Book> books;
    Intent libraryIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bookInformation = (TextView) findViewById(R.id.bookInformation);
        scroll = (ScrollView) findViewById(R.id.scroll);
        libraryIntent = new Intent(this, LibraryActivity.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookViewed = (Book)extras.get("Selected Book");
            books = (ArrayList<Book>) getIntent().getSerializableExtra("Books");;
        }
        bookInformation.setText(bookViewed.toString() + "\n");
        Log.i("Size",bookViewed.getWords().size()+"");
        for(Word w : bookViewed.getWords()) {
            bookInformation.append(Html.fromHtml(w.toString()));
        }
    }
    @Override
    public void onBackPressed() {
        // your code.
        Log.i("BACK","Went back");
        libraryIntent.putExtra("Books",books);
        startActivity(libraryIntent);
    }
}
