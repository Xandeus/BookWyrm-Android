package com.example.alex.wordplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

public class BookActivity extends AppCompatActivity {
    TextView bookInformation;
    ScrollView scroll;
    Book bookViewed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bookInformation = (TextView) findViewById(R.id.bookInformation);
        scroll = (ScrollView) findViewById(R.id.scroll);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bookViewed = (Book)extras.get("Selected Book");
        }
        bookInformation.setText(bookViewed.toString() + "\n");
        Log.i("Size",bookViewed.getWords().size()+"");
        for(Word w : bookViewed.getWords()) {
            bookInformation.append(Html.fromHtml(w.toString()));
        }
    }
}
