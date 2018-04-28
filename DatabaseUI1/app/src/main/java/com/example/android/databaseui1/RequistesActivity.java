package com.example.android.databaseui1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequistesActivity extends AppCompatActivity {
    private ArrayList<Request> requestList;
    private RequistAdaptor reqAdaptor ;
    private RequestQueue requestQueue ;
    private Request currentreq;
    final  String url = "http://192.168.1.2/ls2/request/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requistes);
        requestQueue = Volley.newRequestQueue(RequistesActivity.this);
        requestList = new ArrayList<>();
        getAllRequests();
        reqAdaptor = new RequistAdaptor(this, requestList);
        final GridView reqView = (GridView) findViewById(R.id.list);
        reqView.setAdapter(reqAdaptor);

        reqView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentreq = requestList.get(position);
                String tel = "tel:";
                tel = tel + currentreq.getPhoneNumber();
                Uri number = Uri.parse(tel);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });

    }
    public void getAllRequests(){

        StringRequest postRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response) ;
                            JSONArray jsonArray = jsonObject.getJSONArray("data") ;
                            for(int i=0 ;i<jsonArray.length() ;i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String userName = object.getString("userName") ;
                                String hospitalName = object.getString("hospitalName") ;
                                String hospitalAddress = object.getString("address");
                                String bloodType = object.getString("bloodType");
                                String phoneNumber = object.getString("phone");
                                requestList.add(new Request(hospitalName,bloodType,userName, hospitalAddress,phoneNumber));
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RequistesActivity.this,"Error Getting User",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                        Toast.makeText(RequistesActivity.this,"Connection Error",Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(postRequest);
    }
}
