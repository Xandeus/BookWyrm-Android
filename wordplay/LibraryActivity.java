package com.example.alex.wordplay;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Book> books;
    Book selectedBook;
    Intent bookIntent;
    Intent mainIntent;
    ArrayAdapter<String> adapter;
    String defWord;

    FileOutputStream outputStream;
    ObjectOutputStream objectStream;
    String fileName = "bookData";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_library);
            bookIntent = new Intent(this, BookActivity.class);
            mainIntent = new Intent(this, MainActivity.class);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Log.i("EXTRA","Did it");
                books = (ArrayList<Book>) getIntent().getSerializableExtra("Books");
                //The key argument here must match that used in the other activity
            }

            // Get ListView object from xml
            listView = (ListView) findViewById(R.id.bookList);

            // Defined Array values to show in ListView
            String[] bookNames = new String[books.size()];
            for(int i = 0;i<bookNames.length;i++){
                Log.i("Words",books.get(i).getWords().toString());
                bookNames[i] = books.get(i).toString();
            }
            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data

            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, bookNames);


            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // ListView Item Click Listener
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    selectedBook = books.get(position);
                    buildDialog();
                }

            });
        }
    @Override
    public void onBackPressed() {
        // your code.
        Log.i("BACK","Went back");
        startActivity(mainIntent);
    }
    public void buildDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LibraryActivity.this);

        // set title
        alertDialogBuilder.setTitle("Add Word");

        // set dialog message
        final EditText word = new EditText(LibraryActivity.this);
        word.setHint("Word");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Define",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        defWord = word.getText().toString();
                        new RetrieveFeedTask().execute();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });


        alertDialogBuilder.setView(word);
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
        //Word to be used in define query
        //Word definitions
        String[] wordDefs;
        //Type of words i.e. verb, noun, adjective
        String[] wordTypes;

        JSONObject rec;
        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            // Do some validation here
            try {
                //URL for dictionary queries
                String address = "https://owlbot.info/api/v1/dictionary/"+ defWord +"?format=json";
                URL url = new URL(address);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    JSONArray wordQuery = new JSONArray(bufferedReader.readLine());
                    wordDefs = new String[wordQuery.length()];
                    wordTypes = new String[wordQuery.length()];
                    for(int i = 0;i<wordQuery.length();i++){
                        rec = wordQuery.getJSONObject(i);
                        wordDefs[i] = rec.getString("defenition");
                        wordTypes[i] = rec.getString("type");

                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                } finally {
                    urlConnection.disconnect();
                }
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE WAS AN ERROR";
            }
            selectedBook.addWord(new Word(defWord,wordDefs,wordTypes,selectedBook.getTitle()));
            Log.i("INFO", response);
            adapter.notifyDataSetChanged();
            bookIntent.putExtra("Selected Book", selectedBook);
            bookIntent.putExtra("Books",books);
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
            startActivity(bookIntent);
        }
    }
}
