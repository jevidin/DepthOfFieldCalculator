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
    private int lensIdx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_dof);
        lensManager = LensManager.getInstance();
        extractDataFromIntent();
        displayLens();
        setUpCalculateBtn();
        setUpBackBtn();
        setUpEditBtn();
        setUpDeleteBtn();
    }

    public void onRestart() {
        super.onRestart();
        displayLens();
    }

    private void setUpCalculateBtn() {
        Button btn = findViewById(R.id.calculateBtn);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean validInput = true;

                EditText textInputCOC = findViewById(R.id.inputCOC);
                double COC = Double.parseDouble(textInputCOC.getText().toString());
                if (COC < 0) {
                    validInput = false;
                    textInputCOC.setError("Invalid COC!");
                }
                EditText textInputDistance = findViewById(R.id.inputDistance);
                double distance = Double.parseDouble(textInputDistance.getText().toString());
                if (distance < 0) {
                    validInput = false;
                    textInputDistance.setError("Invalid Distance!");
                }
                EditText textInputAperture = findViewById(R.id.inputAperture);
                double aperture = Double.parseDouble(textInputAperture.getText().toString());
                if (aperture < selectedLens.getMaxAperture()) {
                    validInput = false;
                    textInputAperture.setError("Invalid Aperture!");
                }
                if (validInput) {
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
        lensIdx = intent.getIntExtra(EXTRA_LENS_INDEX, 0);
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

    private void setUpEditBtn(){
        Button btn = findViewById(R.id.btnEdit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = EditLensActivity.makeLaunchIntent(CalculateDOFActivity.this, lensIdx);
                startActivity(i);
            }
        });
    }

    private void setUpDeleteBtn(){
        Button btn = findViewById(R.id.btnDelete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lensManager.remove(selectedLens);
                finish();
            }
        });
    }

    private String formatM(double distanceInM) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(distanceInM);
    }
}