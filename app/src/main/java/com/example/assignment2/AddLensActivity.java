package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;

public class AddLensActivity extends AppCompatActivity {
    private LensManager lensManager;
    String newMake;
    double newAperture;
    int newFocalLength;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lens);
        //Toolbar addToolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(addToolbar);
        lensManager = LensManager.getInstance();
        setupCancelButton();
        setupSaveButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

    private void setupSaveButton() {

        Button btn = findViewById(R.id.add_btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validInput = true;

                EditText textInputMake = findViewById(R.id.add_inputMake);
                newMake = textInputMake.getText().toString();
                if(newMake.length() == 0){
                    validInput = false;
                    textInputMake.setError("Please enter make!");
                }
                EditText textInputAperture = findViewById(R.id.add_inputAperture);
                newAperture = Double.parseDouble(textInputAperture.getText().toString());
                if(newAperture < 1.4){
                    validInput = false;
                    textInputAperture.setError("Invalid Aperture!");
                }
                EditText textInputFocalLength = findViewById(R.id.add_inputFocalLength);
                newFocalLength = Integer.parseInt(textInputFocalLength.getText().toString());
                if(newFocalLength <= 0){
                    validInput = false;
                    textInputFocalLength.setError("Invalid Focal Length!");
                }
                if(validInput) {
                    lensManager.add(new Lens(newMake, newAperture, newFocalLength));
                    finish();
                }

            }
        });
    }


    private void setupCancelButton(){
        Button btn = findViewById(R.id.add_btnCancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}