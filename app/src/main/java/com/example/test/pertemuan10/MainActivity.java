package com.example.test.pertemuan10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private TextView tvJSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        tvJSON = findViewById(R.id.tvJSON);

        JSONParse();
    }

    private void JSONParse() {

        String URL = "https://dl.dropboxusercontent.com/sh/6dfajq9idss2x4k/0FuT8nuKRa/data.json";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");

                            String firstName = "";
                            String address = "";
                            String homeNumber = "";

                            for (int i=0; i < dataArray.length(); i++){
                                JSONObject dataObject = dataArray.getJSONObject(i);
                                firstName = dataObject.getString("firstName");

                                JSONObject addressObject = dataObject.getJSONObject("address");
                                 address = addressObject.getString("streetAddress");


                                 JSONArray phoneArray = dataObject.getJSONArray("phoneNumber");

                                 for(int j=0; j<phoneArray.length();j++){
                                     JSONObject phoneObject = phoneArray.getJSONObject(j);
                                     if(j==0) homeNumber = phoneObject.getString("number");
                                }

                                tvJSON.append(firstName + "\n" +
                                address + "\n" +
                                homeNumber + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        queue.add(request);
    }
}
