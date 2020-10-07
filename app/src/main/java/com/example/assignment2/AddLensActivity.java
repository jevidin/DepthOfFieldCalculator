package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        lensManager = LensManager.getInstance();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_back:
                //Toast.makeText(CalculateDOFActivity.this, "Back pressed", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.action_save:
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add, menu);
        return true;
    }

}