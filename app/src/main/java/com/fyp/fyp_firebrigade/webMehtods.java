package com.fyp.fyp_firebrigade;

import android.content.Context;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class webMehtods {

    Context context;

    public webMehtods(Context context) {
        this.context = context;
    }

    public void checkLogin(String email, String password, EditText eEmail,EditText ePassword){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.USER_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            if (response.trim().equals("failed"))
                            {
                                eEmail.setError("Invalid Email");
                                ePassword.setError("Invalid Password");
                            }
                            else{

                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("authEmail",email);
                map.put("authPassword",password);
                map.put("authRole","2");
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


    public void registerUser(String name,String email,String password,String contact,String gender,EditText emailField){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.USER_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("exist"))
                        {
            emailField.setError("Already Exist");
                        }
                        else if(response.trim().equals("success"))
                        {

                        }
                        else{

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userName",name);
                map.put("userEmail",email);
                map.put("userPassword",password);
                map.put("userContact",contact);
                map.put("userGender",gender);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
