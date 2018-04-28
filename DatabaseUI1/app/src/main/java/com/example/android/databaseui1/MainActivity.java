package com.example.android.databaseui1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button registerDonner =  (Button) findViewById(R.id.regDonner);
        Button registerAcceptor =  (Button) findViewById(R.id.regAcceptor);
        Button login = (Button) findViewById(R.id.login);

        registerAcceptor.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent registerIntent =new Intent(view.getContext(),RegisterActivityA.class);
                startActivity(registerIntent);
            }
        });


        registerDonner.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent registerIntent =new Intent(view.getContext(),RegisterActivityD.class);
                startActivity(registerIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(view.getContext(),LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}
