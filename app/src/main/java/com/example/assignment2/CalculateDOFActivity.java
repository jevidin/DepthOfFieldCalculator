package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;

import java.util.ArrayList;
import java.util.List;

public class CalculateDOFActivity extends AppCompatActivity {
    private static final String EXTRA_LENS_INDEX = "com.example.assignment2.CalculateDOFActivity - selected lens";
    private LensManager lensManager;
    private Lens selectedLens;

    private String[] viewCalculated = {"Near Focal Point: Enter all values", "Far Focal Point: Enter all values",
                                    "Depth of Field: Enter all values", "Hyper Focal Point: Enter all values"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_dof);

        lensManager = LensManager.getInstance();
        extractDataFromIntent();
        displayLens();
        setUpCalculateBtn();
        setUpBackBtn();
    }

    private void setUpCalculateBtn() {
        Button btn = findViewById(R.id.calculateBtn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText textInputCOC = findViewById(R.id.inputCOC);
                double COC = Double.parseDouble(textInputCOC.getText().toString());
                EditText textInputDistance = findViewById(R.id.inputDistance);
                double distance = Double.parseDouble(textInputDistance.getText().toString());
                EditText textInputAperture = findViewById(R.id.inputAperture);
                double aperture = Double.parseDouble(textInputAperture.getText().toString());

            }
        });
    }

    private void displayLens() {
        String lensDetail = getString(R.string.displayLensDetails);
        lensDetail += " " + selectedLens.getDescription();
        TextView viewLens = (TextView)findViewById(R.id.viewLens);
        viewLens.setText(lensDetail);
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        int lensIdx = intent.getIntExtra(EXTRA_LENS_INDEX, 0);
        selectedLens = lensManager.get(lensIdx);
    }


    private void setUpBackBtn() {
        Button btn = findViewById(R.id.Calculate_btnBack);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static Intent makeLaunchIntent(Context context, int lensIdx){
        Intent intent = new Intent(context, CalculateDOFActivity.class);
        intent.putExtra(EXTRA_LENS_INDEX, lensIdx);
        return intent;
    }
}