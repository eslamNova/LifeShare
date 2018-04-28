package com.example.android.databaseui1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivityD extends AppCompatActivity {
    final  String url = "http://192.168.1.2/ls2/donner/";
    RequestQueue requestQueue ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_d);
        requestQueue = Volley.newRequestQueue(RegisterActivityD.this);

        Button register = (Button) findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                    addUser(view);
                    Intent registerIntent = new Intent(view.getContext(), RequistesActivity.class);
                    startActivity(registerIntent);
                }
            });
        }


    public void addUser(View v){
        final String name, userName,email, password, phone, bloodType ;
        EditText nameE = (EditText) findViewById(R.id.name);
        EditText userNameE = (EditText) findViewById(R.id.userNameR);
        EditText passE = (EditText) findViewById(R.id.passR);
        EditText emailE = (EditText) findViewById(R.id.emailR);
        EditText phoneE = (EditText) findViewById(R.id.phone);
        EditText bloodTypeE = (EditText) findViewById(R.id.blood);
        name = nameE.getText().toString();
        userName = userNameE.getText().toString();
        password = passE.getText().toString();
        email = emailE.getText().toString();
        phone = phoneE.getText().toString();
        bloodType = bloodTypeE.getText().toString();
        if(name.isEmpty()||password.isEmpty()||userName.isEmpty()||email.isEmpty()||phone.isEmpty()||bloodType.isEmpty()) return;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            String status_message = jsonObject.getString("status_message") ;
                            if(status_message.equals("ok")){
                                Toast.makeText(RegisterActivityD.this,"User Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(RegisterActivityD.this,"Error Adding User",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivityD.this,"Error Adding User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(RegisterActivityD.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", name);
                params.put("userName", userName);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                params.put("bloodType", bloodType);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}
