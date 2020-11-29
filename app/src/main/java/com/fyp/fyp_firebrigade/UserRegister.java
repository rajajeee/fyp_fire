package com.fyp.fyp_firebrigade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserRegister extends AppCompatActivity {

    TextView signIn;
    EditText name,email,age,password,contact;
    String gender;
    RadioGroup radioGroup;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        webMethods methods = new webMethods(getApplicationContext());

signIn = findViewById(R.id.tvSignIn);
name = findViewById(R.id.edtSignUpName);
email = findViewById(R.id.edtSignUpEmail);
age = findViewById(R.id.edtSignUpAge);
password = findViewById(R.id.edtSignUpPassword);
contact = findViewById(R.id.edtSignUpContact);
radioGroup = findViewById(R.id.rdg);
signUp = findViewById(R.id.btnSignUp);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rdm){
                gender = "Male";
            }
            else if(checkedId == R.id.rdf){
                gender = "Female";
            }
        }
    });
signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        methods.registerUser(name.getText().toString(),email.getText().toString(),password.getText().toString(),age.getText().toString(),
                contact.getText().toString(),gender,email);
    }
});

signIn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(UserRegister.this,MainActivity.class));
        }


        });

    }
}