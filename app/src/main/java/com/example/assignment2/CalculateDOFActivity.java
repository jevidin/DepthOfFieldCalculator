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
import android.widget.Toast;

import com.example.assignment2.model.DoFCalculator;
import com.example.assignment2.model.Lens;
import com.example.assignment2.model.LensManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CalculateDOFActivity extends AppCompatActivity {
    private static final String EXTRA_LENS_INDEX = "com.example.assignment2.CalculateDOFActivity - selected lens";
    private LensManager lensManager;
    private Lens selectedLens;



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
                boolean validInput = true;
                EditText textInputCOC = findViewById(R.id.inputCOC);
                double COC = Double.parseDouble(textInputCOC.getText().toString());
                EditText textInputDistance = findViewById(R.id.inputDistance);
                double distance = Double.parseDouble(textInputDistance.getText().toString());
                EditText textInputAperture = findViewById(R.id.inputAperture);
                double aperture = Double.parseDouble(textInputAperture.getText().toString());
                if(aperture < selectedLens.getMaxAperture()){
                    validInput = false;
                    Toast.makeText(CalculateDOFActivity.this, "Invalid Aperture!", Toast.LENGTH_LONG).show();
                }
                if(validInput) {
                    DoFCalculator dofCalculator = new DoFCalculator(COC, selectedLens, aperture, distance);
                    TextView hyperfocalText = findViewById(R.id.textViewHFP);
                    hyperfocalText.setText("Hyper Focal Point: " + formatM(dofCalculator.getHyperfocalDistanceInM()) + "m");
                    TextView nearfocalText = findViewById(R.id.textViewNFP);
                    nearfocalText.setText("Near Focal Point: " + formatM(dofCalculator.getNearFocalPointInM()) + "m");
                    TextView farfocalText = findViewById(R.id.textViewFFP);
                    farfocalText.setText("Far Focal Point: " + formatM(dofCalculator.getFarFocalPointInM()) + "m");
                    TextView dofText = findViewById(R.id.textViewDOF);
                    dofText.setText("Depth of Field: " + formatM(dofCalculator.getDepthOfFieldInM()) + "m");
                }

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

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }

    public static Intent makeLaunchIntent(Context context, int lensIdx){
        Intent intent = new Intent(context, CalculateDOFActivity.class);
        intent.putExtra(EXTRA_LENS_INDEX, lensIdx);
        return intent;
    }
}