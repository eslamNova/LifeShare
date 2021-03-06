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

public class RegisterActivityA extends AppCompatActivity {
    final  String url = "http://192.168.1.2/ls2/acceptor/";
    private RequestQueue requestQueue ;

    private EditText nameE ;
    private EditText userNameE ;
    private EditText passE ;
    private EditText emailE ;
    private EditText phoneE ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_a2);
        requestQueue = Volley.newRequestQueue(RegisterActivityA.this);


        Button register = (Button) findViewById(R.id.register);

        nameE = (EditText) findViewById(R.id.name);
        userNameE = (EditText) findViewById(R.id.userNameR);
        passE = (EditText) findViewById(R.id.passR);
        emailE = (EditText) findViewById(R.id.emailR);
        phoneE = (EditText) findViewById(R.id.phone);

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if (userNameE.getText().toString().isEmpty()||passE.getText().toString().isEmpty()||phoneE.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivityA.this,"Missing fields",Toast.LENGTH_LONG).show();
                else {
                    addUser();
                    Intent registerIntent = new Intent(view.getContext(), UserActivity.class);
                    registerIntent.putExtra("userName", ((EditText)findViewById(R.id.userNameR)).getText());
                    startActivity(registerIntent);
                }
            }
        });
    }


    public void addUser(){
        final String name, userName,email, password, phone ;

        name = nameE.getText().toString();
        userName = userNameE.getText().toString();
        password = passE.getText().toString();
        email = emailE.getText().toString();
        phone = phoneE.getText().toString();
        if(name.isEmpty()||password.isEmpty()||userName.isEmpty()||email.isEmpty()||phone.isEmpty()) return;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            String status_message = jsonObject.getString("status_message") ;
                            if(status_message.equals("ok")){
                                Toast.makeText(RegisterActivityA.this,"User Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(RegisterActivityA.this,"Error Adding User",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivityA.this,"Error Adding User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getMessage());
                        Toast.makeText(RegisterActivityA.this,"Connection Error Can't add Acceptor",Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap   <String, String>();
                params.put("name", name);
                params.put("userNameA", userName);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }
}
