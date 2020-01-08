package com.example.buildingmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();

        username = findViewById(R.id.usernameField);
        password = findViewById(R.id.passwordField);
        login = findViewById(R.id.loginButton);

        FirebaseUser user = auth.getCurrentUser();

        if(user != null) {
            finish();
            Toast.makeText(LoginActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));

        }

    }

    public void onLogin(View view) {
        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();


        if(validateInput()){
            auth.signInWithEmailAndPassword(usernameText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Context context = getApplicationContext();
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, context.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, context.getString(R.string.login_failed) + ":", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public boolean validateInput() {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        String usernameText = username.getText().toString();
        String passwordText = password.getText().toString();

        if(isEmptyText(usernameText)&& isEmptyText(passwordText)){
            toast = Toast.makeText(context, context.getString(R.string.sign_in_error), duration);
            toast.show();
            return false;
        } else if(!validateEmail(usernameText)) {
            toast = Toast.makeText(context, context.getString(R.string.email_wrong), duration);
            toast.show();
            return false;
        }



        return true;
    }

    private boolean isEmptyText(String s) {
        if(s.equals("")){
            return true;
        }
        return false;
    }

    public void goToRegisterView(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    private boolean validateEmail(String mail) {
        if(mail.matches(".+@.+\\..+")) {
            return true;
        }

        return false;
    }


}
