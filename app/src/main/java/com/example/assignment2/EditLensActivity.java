package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;

public class EditLensActivity extends AppCompatActivity {
    private LensManager lensManager;
    private int lensIdx;
    String newMake;
    double newAperture;
    int newFocalLength;
    private Lens selectedLens;
    private static final String EXTRA_LENS_INDEX = "com.example.assignment2.EditLensActivity - selected lens";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lens);

        lensManager = LensManager.getInstance();
        setupCancelButton();
        setupSaveButton();
        extractDataFromIntent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    private void setupSaveButton() {

        Button btn = findViewById(R.id.edit_btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validInput = true;

                EditText textInputMake = findViewById(R.id.edit_inputMake);
                newMake = textInputMake.getText().toString();
                if(newMake.length() == 0){
                    validInput = false;
                    textInputMake.setError("Please enter make!");
                }
                EditText textInputAperture = findViewById(R.id.edit_inputAperture);
                newAperture = Double.parseDouble(textInputAperture.getText().toString());
                if(newAperture < 1.4){
                    validInput = false;
                    textInputAperture.setError("Invalid Aperture!");
                }
                EditText textInputFocalLength = findViewById(R.id.edit_inputFocalLength);
                newFocalLength = Integer.parseInt(textInputFocalLength.getText().toString());
                if(newFocalLength <= 0){
                    validInput = false;
                    textInputFocalLength.setError("Invalid Focal Length!");
                }
                if(validInput) {
                    selectedLens.setMake(newMake);
                    selectedLens.setMaxAperture(newAperture);
                    selectedLens.setFocalLengthInMM(newFocalLength);
                    Toast.makeText(EditLensActivity.this, "Lens Edited.", Toast.LENGTH_SHORT);
                    finish();
                }

            }
        });
    }

    private void setupCancelButton(){
        Button btn = findViewById(R.id.edit_btnCancel);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        lensIdx = intent.getIntExtra(EXTRA_LENS_INDEX, 0);
        selectedLens = lensManager.get(lensIdx);
    }

    public static Intent makeLaunchIntent(Context context, int lensIdx){
        Intent intent = new Intent(context, EditLensActivity.class);
        intent.putExtra(EXTRA_LENS_INDEX, lensIdx);
        return intent;
    }
}