package com.example.android.databaseui1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    final  String url_D = "http://192.168.1.2/ls2/donner/";
    final  String url_A = "http://192.168.1.2/ls2/acceptor/";
    private int flag;
    private RequestQueue requestQueue ;
    private ArrayList<Users> DonnersList = new ArrayList<>();
    private ArrayList<Users> AcceptorsList = new ArrayList<>() ;
    private Users currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);

        getAllDonners();
        getAllAcceptors();

        Button login = (Button) findViewById(R.id.login2);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                EditText userName = (EditText) findViewById(R.id.userNameLO);
                EditText pass = (EditText) findViewById(R.id.passLO);
                flag=0;
                Intent reqView = new Intent(view.getContext(), MainActivity.class);
                for (int i=0;i<DonnersList.size();i++){
                    currentUser = DonnersList.get(i);
                    if (currentUser.getUserName().contentEquals(userName.getText()) && currentUser.getPassword().contentEquals(pass.getText())) {
                        flag=1;
                        reqView = new Intent(view.getContext(), RequistesActivity.class);
                        reqView.putExtra("userName", currentUser.getUserName());
                        break;
                    }
                }
                if (flag==0) {
                    for (int i = 0; i < AcceptorsList.size(); i++) {
                        currentUser = AcceptorsList.get(i);
                        if (currentUser.getUserName().contentEquals(userName.getText()) && currentUser.getPassword().contentEquals(pass.getText())) {
                            reqView = new Intent(view.getContext(), UserActivity.class);
                            reqView.putExtra("userName", currentUser.getUserName());
                            break;
                        }
                        else if (i==AcceptorsList.size()-1) Toast.makeText(LoginActivity.this,"Wrong user name or password",Toast.LENGTH_LONG).show();
                    }
                }
                userName.setText("");
                pass.setText("");
                startActivity(reqView);
            }
        });

    }
    public void getAllDonners(){
        StringRequest postRequest = new StringRequest(Request.Method.GET, url_D,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
                            for(int i=0 ;i<jsonArray.length() ;i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String userName = object.getString("userNameD") ;
                                String pass = object.getString("password") ;
                                DonnersList.add(new Users(userName, pass));
                            }

                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,"Error Getting User 77",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log.d("Error.Response", error.getMessage());
                        Toast.makeText(LoginActivity.this,"Connection Error Donners",Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(postRequest);
    }

    public void getAllAcceptors(){
        StringRequest postRequest = new StringRequest(Request.Method.GET, url_A,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
                            for(int i=0 ;i<jsonArray.length() ;i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String userName = object.getString("userNameA") ;
                                String pass = object.getString("password") ;
                                AcceptorsList.add(new Users(userName, pass));
                            }

                        } catch (JSONException e) {
                            Toast.makeText(LoginActivity.this,"Error Getting User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.d("Error.Response", error.getMessage());
                        Toast.makeText(LoginActivity.this,"Connection Error Acceptors",Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(postRequest);
    }
}
