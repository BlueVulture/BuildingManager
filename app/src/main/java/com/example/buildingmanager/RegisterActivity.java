package com.example.buildingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, lastname, password, confirmPassword, email, phone, building;
    private Button register;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        getViewVariables();
//        setHomeButton();
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
        building = findViewById(R.id.buildingField);
    }

    public void processRegistration(View view) {
        if(validateInput()){
            pushData();
        }
    }

    private void pushData() {
        final String nameText, lastnameText, passwordText, emailText, phoneText, buildingText;
        nameText = name.getText().toString().trim();
        lastnameText = lastname.getText().toString().trim();
        passwordText = password.getText().toString().trim();
        emailText = email.getText().toString().trim();
        phoneText = phone.getText().toString().trim();
        buildingText = building.getText().toString().trim();

        auth.createUserWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if(task.isSuccessful()){
                    Toast.makeText(context, "Registration successful", duration).show();

                    FirebaseUser userID = auth.getCurrentUser();
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", nameText);
                    user.put("lastname", lastnameText);
                    user.put("email", emailText);
                    user.put("phone", phoneText);
                    user.put("building", buildingText);
                    user.put("admin", false);
                    db.collection("users").document(userID.getUid()).set(user);

                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                } else {
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Toast.makeText(context, "Registration failed: " + e.getMessage(), duration).show();
                }
            }
        });
    }

    private boolean isEmptyText(String s) {
        if(s.equals("")){
            return true;
        }
        return false;
    }

    private boolean validateInput() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        String nameText, lastnameText, passwordText, confirmPasswordText, emailText, phoneText;
        nameText = name.getText().toString();
        lastnameText = lastname.getText().toString();
        passwordText = password.getText().toString();
        confirmPasswordText = confirmPassword.getText().toString();
        emailText = email.getText().toString();
        phoneText = phone.getText().toString();

        // Preverimo če so inputi prazni
        if(isEmptyText(nameText) && isEmptyText(lastnameText) && isEmptyText(passwordText) && isEmptyText(confirmPasswordText) && isEmptyText(emailText) && isEmptyText(phoneText)){
            // Vsaj nekateri inputi so prazni
            Toast.makeText(context, context.getString(R.string.sign_in_error), duration).show();

            return false;
        } else {
            // Vsi inputi so polni
            if(!validatePassword()) {
                Toast.makeText(context, context.getString(R.string.password_no_match) , duration).show();
                return false;
            } else if(!validateEmail(emailText)) {
                Toast.makeText(context, context.getString(R.string.email_wrong) , duration).show();
                return false;
            }
        }
        return true;
    }

    private boolean validatePassword() {
        if(password.getText().toString().equals(confirmPassword.getText().toString())){
            return true;
        }

        return false;
    }

    private boolean validateEmail(String mail) {
        if(mail.matches(".+@.+\\..+")) {
            return true;
        }

        return false;
    }
}

