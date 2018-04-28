package com.example.android.databaseui1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    final  String deleteURL = "Edite this";
    final  String addURL = "http://192.168.1.2/ls2/request/";
    final  String url_H = "http://192.168.1.2/ls2/hospitals/";
    private String url;
    private RequestQueue requestQueue ;
    private ArrayList<Hospitals> HospitalsList;
    private Hospitals currentHospital ;

    private EditText hos,blood;
    private Button add, remove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        requestQueue = Volley.newRequestQueue(UserActivity.this);


        getAllHospitals();

        add = (Button) findViewById(R.id.add);
        remove = (Button) findViewById(R.id.remove);
        hos = (EditText) findViewById(R.id.hos);
        blood = (EditText) findViewById(R.id.b);

        add.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                for (int i=0;i< HospitalsList.size() ;i++){
                    currentHospital = HospitalsList.get(i);
                    if (currentHospital.getName().contentEquals(hos.getText())) {

                        url = addURL;
                        deleteaddrequest();
                        add.setEnabled(false);
                        hos.setEnabled(false);
                        blood.setEnabled(false);
                    }
                    else{
                        Toast.makeText(UserActivity.this,"Hospital not found ",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                url = deleteURL;
                deleteaddrequest();
                add.setEnabled(true);
                hos.setEnabled(true);
                blood.setEnabled(true);
            }
        });

    }
    public void deleteaddrequest(){
        final String hospital, userName, bloodType ;
        userName = getIntent().getStringExtra("userName");
        EditText hospitalE = (EditText) findViewById(R.id.hos);
        EditText bloodTypeE = (EditText) findViewById(R.id.blood);
        hospital = hospitalE.getText().toString();
        bloodType = bloodTypeE.getText().toString();
        if(hospital.isEmpty()||bloodType.isEmpty()) return;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            String status_message = jsonObject.getString("status_message") ;
                            if(status_message.equals("ok")){
                                Toast.makeText(UserActivity.this,"Request Added Successfully",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(UserActivity.this,"Error Adding Request",Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Toast.makeText(UserActivity.this,"Error Adding Request",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(UserActivity.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("hospitalName", hospital);
                params.put("userName", userName);
                params.put("bloodType", bloodType);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    public void getAllHospitals(){
        StringRequest postRequest = new StringRequest(Request.Method.GET, url_H,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
                            for(int i=0 ;i<jsonArray.length() ;i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name") ;
                                String address = object.getString("address") ;
                                HospitalsList.add(new Hospitals(name, address));
                            }

                        } catch (JSONException e) {
                            Toast.makeText(UserActivity.this,"Error Getting Hospitals",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(UserActivity.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(postRequest);
    }
}
