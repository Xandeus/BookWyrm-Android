package com.example.alex.wordplay;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView display;
    Intent libaryIntent;
    ArrayList<Book> books = new ArrayList<>();
    FileOutputStream outputStream;
    ObjectOutputStream objectStream;
    FileInputStream inputStream;
    ObjectInputStream objInStream;
    String fileName = "bookData";
    final String PREFS_NAME = "prefFile";
    boolean firstOpen = true;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        libaryIntent = new Intent(this, LibraryActivity.class);
        display = (TextView) findViewById(R.id.wordDefinition);
        Button addWordButton = (Button) findViewById(R.id.addWordButton);
        final Button addBookButton = (Button) findViewById(R.id.addBookButton);
        if (settings.getBoolean("firstOpen", true)) {
            //the app is being launched for first time, do something
            Log.d("Comments", "First time");
            display.setText("FIRST TIME");
            // first time task
            try {
                outputStream = openFileOutput(fileName,MODE_PRIVATE);
                objectStream = new ObjectOutputStream(outputStream);
                objectStream.writeObject(books);
                outputStream.close();
                Log.v("MyApp","Read input");
            } catch (Exception e) {
                Log.v("MyApp","File did not write");
                e.printStackTrace();
            }
            // record the fact that the app has been started at least once
            settings.edit().putBoolean("firstOpen", false).apply();
        }
        else{
            try {
                inputStream = openFileInput(fileName);
                objInStream = new ObjectInputStream(inputStream);
                books = (ArrayList<Book>) objInStream.readObject();
                inputStream.close();
                Log.v("MyApp","Read input");
            } catch (Exception e) {
                Log.v("MyApp","File did not write");
                e.printStackTrace();
            }
            // rec
            display.setText("Not the first time");
        }
        addWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libaryIntent.putExtra("Books",books);
                startActivity(libaryIntent);
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
                                    try {
                                        outputStream = openFileOutput(fileName,MODE_PRIVATE);
                                        objectStream = new ObjectOutputStream(outputStream);
                                        objectStream.writeObject(books);
                                        outputStream.close();
                                        Log.v("MyApp","Read input");
                                    } catch (Exception e) {
                                        Log.v("MyApp","File did not write");
                                        e.printStackTrace();
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


