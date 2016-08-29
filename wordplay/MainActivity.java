package com.example.alex.wordplay;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;

import android.text.InputType;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    EditText wordText;
    TextView wordDefinition;
    Intent myIntent;
    ArrayList<Word> words = new ArrayList<>();
    ArrayList<Book> books = new ArrayList<>();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myIntent = new Intent(this, BookActivity.class);
        wordText = (EditText) findViewById(R.id.wordText);
        wordDefinition = (TextView) findViewById(R.id.wordDefinition);
        Button addWordButton = (Button) findViewById(R.id.addWordButton);
        final Button addBookButton = (Button) findViewById(R.id.addBookButton);
        FileInputStream fis;
        try {
            fis = openFileInput("Books");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Object> returnlist = (ArrayList<Object>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent.putExtra("Books",books);
                startActivity(myIntent);
            }
        });

        if (addBookButton != null) {
            addBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            MainActivity.this);

                    // set title
                    alertDialogBuilder.setTitle("Add Book");

                    // set dialog message
                    final EditText bookTitle = new EditText(MainActivity.this);
                    final EditText bookAuthor = new EditText(MainActivity.this);
                    final EditText numPages = new EditText(MainActivity.this);

                    bookTitle.setHint("Title");
                    bookAuthor.setHint("Author");
                    numPages.setHint("Number of Pages");
                    numPages.setInputType(InputType.TYPE_CLASS_NUMBER);
                    alertDialogBuilder
                            .setMessage("Enter book information.")
                            .setCancelable(false)
                            .setPositiveButton("Add",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, close
                                    // current activity
                                    books.add(new Book(bookTitle.getText().toString(),bookAuthor.getText().toString(),Integer.parseInt(numPages.getText().toString())));
                                    for(int i = 0;i<100;i++){
                                        books.add(new Book(""+i,"Mister Robot",1));
                                    }
                                }
                            })
                            .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // if this button is clicked, just close
                                    // the dialog box and do nothing
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    LinearLayout layout = new LinearLayout(MainActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.addView(bookTitle);
                    layout.addView(bookAuthor);
                    layout.addView(numPages);
                    alertDialogBuilder.setView(layout);
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                }

            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client.connect();
         // TODO: choose an action type.
                 // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.


    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        //client.disconnect();
    }


}


