package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                EditText textInputMake = findViewById(R.id.add_inputMake);
                newMake = textInputMake.getText().toString();
                EditText textInputAperture = findViewById(R.id.add_inputAperture);
                newAperture = Double.parseDouble(textInputAperture.getText().toString());
                EditText textInputFocalLength = findViewById(R.id.add_inputFocalLength);
                newFocalLength = Integer.parseInt(textInputFocalLength.getText().toString());
                lensManager.add(new Lens(newMake, newAperture, newFocalLength));
                finish();
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