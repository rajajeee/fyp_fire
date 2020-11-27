package com.fyp.fyp_firebrigade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner account;
    EditText email,password;
    String[] accounts = {"Select Account Type","User","Fire Brigade"};
    Button signIn;
    TextView signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    account = findViewById(R.id.spinAccount);
    email = findViewById(R.id.edtEmail);
    password = findViewById(R.id.edtPassword);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,accounts);
        account.setAdapter(arrayAdapter);
    signIn = findViewById(R.id.btnSignIn);
    signUp = findViewById(R.id.tvSignUp);

    signUp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this,UserRegister.class));
        }
    });

    }
}