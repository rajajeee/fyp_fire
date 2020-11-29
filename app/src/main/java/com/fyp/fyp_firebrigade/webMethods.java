package com.fyp.fyp_firebrigade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class webMethods {

    Context context;

    public webMethods(Context context) {
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
                            else if (response.trim().equals("failed")){
                                Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                            }
                            else{
                                generateSharedPref(response);
                                Intent i = new Intent(context,RequestActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(i);
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
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


    public void registerUser(String name,String email,String password,String age,String contact,String gender,EditText emailField){
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
                            Intent i = new Intent(context,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                        }
                        else{
                            Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userName",name);
                map.put("userEmail",email);
                map.put("userPassword",password);
                map.put("userAge",age);
                map.put("userContact",contact);
                map.put("userGender",gender);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
public void addNewFireRequest(String name,String address,String lat,String lng,Bitmap img){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Apis.ADD_REQUEST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Intent i = new Intent(context,MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);

                        }
                        else {
                            Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                        }
                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String imageData = imageToString(img);
                Map<String,String> map = new HashMap<>();
                map.put("requestName",name);
                map.put("requestAddress",address);
                map.put("requestLat",lat);
                map.put("requestLng",lng);
                map.put("requestImage",imageData);
                map.put("requestBy",getUser());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
}
public void generateSharedPref(String id){
    SharedPreferences sharedPreferences =context.getSharedPreferences("loginID",Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("UserID",id);
    editor.commit();
    editor.apply();
}
public String getUser(){
    SharedPreferences sharedPreferences =context.getSharedPreferences("loginID",Context.MODE_PRIVATE);
    String getID = sharedPreferences.getString("UserID","");
    return getID;
}

    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        return encodeImage;


    }
}
