package com.example.assignment2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String SHAREDPREF_LENSES = "User's Lenses";
    public static final String SHAREDPREF_LENS_MANAGER = "Lens Manager";
    private LensManager lensManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lensManager = LensManager.getInstance();
        getLensFromSharedPref();
        populateListView();
        updateUI();
        registerClickCallback();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddLensActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getDefaultLens(){
        lensManager.add(new Lens("Canon", 1.8, 50));
        lensManager.add(new Lens("Tamron", 2.8, 90));
        lensManager.add(new Lens("Sigma", 2.8, 200));
        lensManager.add(new Lens("Nikon", 4, 200));
    }

    private void getLensFromSharedPref(){
        SharedPreferences prefs = getSharedPreferences(SHAREDPREF_LENSES, MODE_PRIVATE);

        Gson lensGson = new Gson();
        String lensJson = prefs.getString(SHAREDPREF_LENS_MANAGER,null );
        Type type = new TypeToken<List<Lens>>() {}.getType();
        List<Lens> storedLens = lensGson.fromJson(lensJson, type);

        if(storedLens == null){
            getDefaultLens();
        }
        else {//turn arraylist to lensmanager
            for(int i = 0; i < storedLens.size(); i++){
                lensManager.add(storedLens.get(i));
            }
        }
    }

    private void storeLensToSharedPref() {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREF_LENSES, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        List<Lens> storedLens = new ArrayList<>();

        if(lensManager.getNumLenses() == 0){
            storedLens.add(new Lens("Canon", 1.8, 50));
            storedLens.add(new Lens("Tamron", 2.8, 90));
            storedLens.add(new Lens("Sigma", 2.8, 200));
            storedLens.add(new Lens("Nikon", 4, 200));
        }

        //turn manager into an arraylist
        for(int i = 0; i < lensManager.getNumLenses();i++){
            storedLens.add(lensManager.get(i));
        }

        String json = gson.toJson(storedLens);
        editor.putString(SHAREDPREF_LENS_MANAGER, json);
        editor.apply();
    }


    public void onRestart() {
        super.onRestart();
        storeLensToSharedPref();
        populateListView();
        updateUI();

    }

    private void populateListView() {
        List<String> StringLenses = new ArrayList<>();
        String lensString;

        for(int i = 0; i < lensManager.getNumLenses(); i++){
            lensString = "  " + (i+1) + ". " + lensManager.get(i).getDescription();
            StringLenses.add(lensString);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.list_items, StringLenses);

        ListView list = findViewById(R.id.listViewCameras);
        list.setAdapter(adapter);
    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.listViewCameras);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent i = CalculateDOFActivity.makeLaunchIntent(MainActivity.this, position);
                startActivity(i);
            }
        });
    }

    private void updateUI(){
        TextView tv = findViewById(R.id.selectLensTextView);
        String msg = "Please select a lens:";
        if(lensManager.getNumLenses() == 0){
            msg = "No lenses available. Please add a lens by pressing the '+' button on the bottom right.";
        }
        tv.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}