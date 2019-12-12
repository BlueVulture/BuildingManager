package com.example.buildingmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, lastname, password, confirmPassword, email, phone;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getViewVariables();
        setHomeButton();

    }

    private void setHomeButton () {
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getViewVariables() {
        name = findViewById(R.id.nameField);
        lastname = findViewById(R.id.lastnameField);
        password = findViewById(R.id.passwordRegisterField);
        confirmPassword = findViewById(R.id.passwordConfirmField);
        email = findViewById(R.id.emailField);
        phone = findViewById(R.id.phoneField);
        register = findViewById(R.id.registerButton);
    }

    private void validateInput() {
        if(name.getText() != null && lastname.getText() != null);
    }
}
