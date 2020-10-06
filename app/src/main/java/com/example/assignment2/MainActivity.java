package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;

import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LensManager lensManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lensManager = LensManager.getInstance();
        lensManager.add(new Lens("Canon", 1.8, 50));
        lensManager.add(new Lens("Tamron", 2.8, 90));
        lensManager.add(new Lens("Sigma", 2.8, 200));
        lensManager.add(new Lens("Nikon", 4, 200));
        populateListView();
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

    public void onRestart() {
        super.onRestart();
        populateListView();
    }

    private void populateListView() {
        List<String> StringLenses = new ArrayList<>();
        String lensString;
        for(int i = 0; i < lensManager.getNumLenses(); i++){
            lensString = "  " + i + ". " + lensManager.get(i).getDescription();
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
                TextView textView = (TextView)viewClicked;
                String message = "you clicked number " + position + ", which is string :" + textView.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}