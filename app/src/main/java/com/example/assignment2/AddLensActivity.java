package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        lensManager = LensManager.getInstance();
        setupCancelButton();
        setupSaveButton();
    }

    private void setupSaveButton() {

        Button btn = findViewById(R.id.add_btnSave);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validInput = true;
                String makeErrorMessage = "";
                String focalErrorMessage = "";
                String apertureErrorMessage = "";
                EditText textInputMake = findViewById(R.id.add_inputMake);
                newMake = textInputMake.getText().toString();
                if(newMake.length() == 0){
                    validInput = false;
                    makeErrorMessage += "Please enter make!\n";
                }
                EditText textInputAperture = findViewById(R.id.add_inputAperture);
                newAperture = Double.parseDouble(textInputAperture.getText().toString());
                if(newAperture < 1.4){
                    validInput = false;
                    apertureErrorMessage += "Aperture must be 1.4 or larger!";
                }
                EditText textInputFocalLength = findViewById(R.id.add_inputFocalLength);
                newFocalLength = Integer.parseInt(textInputFocalLength.getText().toString());
                if(newFocalLength <= 0){
                    validInput = false;
                    focalErrorMessage += "Invalid focal length!\n";
                }
                if(validInput) {
                    lensManager.add(new Lens(newMake, newAperture, newFocalLength));
                    finish();
                }
                else{
                    String errorMessage = "ERROR: \n" + makeErrorMessage + focalErrorMessage + apertureErrorMessage;
                    Toast.makeText(AddLensActivity.this, errorMessage, Toast.LENGTH_LONG).show();
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