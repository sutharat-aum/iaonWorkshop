package com.example.brave.workshop;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.tls.OkHostnameVerifier;

public class Register extends AppCompatActivity {

    private EditText edDisplay;
    private EditText edUsername;
    private EditText edPassword;
    private EditText edPasswordCon;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edDisplay = (EditText) findViewById(R.id.edDisplay);
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edPasswordCon = (EditText) findViewById(R.id.edPasswordCon);
        btRegister = (Button) findViewById(R.id.btRegister);

        setListener();
        setValidate();
    }

    private boolean setValidate() {
        String username = edUsername.getText().toString();
        String password = edPassword.getText().toString();
        String passwordCon = edPasswordCon.getText().toString();
        String displayName = edDisplay.getText().toString();

        if (username.isEmpty()) return false;
        if (password.isEmpty()) return false;
        if (passwordCon.isEmpty()) return false;
        if (!password.equals(passwordCon)) return false;
        if (displayName.isEmpty()) return false;
        return true;
    }

    private void setListener() {
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidate()) {
                    //ติดต่อ Server
                    //นำแทคมาช่วย
                    new RegisterA(edUsername.getText().toString()
                    ,edPassword.getText().toString()
                    ,edPasswordCon.getText().toString()
                    ,edDisplay.getText().toString()).execute();
                } else {
                    Toast.makeText(Register.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class RegisterA extends AsyncTask<Void, Void, String> {
        //กำหนดตัวแปลขึ้นมา เพื่อเชื่อมกับ server
        private String username;
        private String password;
        private String passwordCon;
        private String display;

        public RegisterA(String username, String password, String passwordCon, String display) {
            this.username = username;
            this.password = password;
            this.passwordCon = passwordCon;
            this.display = display;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        //ขั้นที่1
        @Override
        protected String doInBackground(Void... voids) {
            //เชื่อมต่อ server
            OkHttpClient client = new OkHttpClient();
            Request request;
            Response response;
            //ตัวส่งข้อมูล
            RequestBody requestBody = new FormBody.Builder()
                    .add ("username",username)
                    .add("password",password)
                    .add("password_con",passwordCon)
                    .add("display_name",display)
                    .build();
            //เรียกใช้
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/signup.php")
                    .post (requestBody)
                    .build();

            try {
                response = client.newCall(request).execute();

                if (response.isSuccessful()){
                    return  response.body().string();
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
            return null;
        }

        //ขั้นที่2
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
         //   Toast.makeText(Register.this,s,Toast.LENGTH_SHORT).show();

            //เช็คค่า json แกะจาก
  //           {
  //              "result": {
  //              "result": 1,
  //                      "result_desc": " success"
  //          }
  //          }

        try {
            JSONObject rootObj = new JSONObject(s);
            if(rootObj.has("result")){
                JSONObject resultObj = rootObj.getJSONObject("result");
                if (resultObj.getInt("result")==1){
                    Toast.makeText(Register.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(Register.this,resultObj.getString("result_desc"),Toast.LENGTH_SHORT).show();
                }
            }
        }catch (JSONException ex){

        }
        }


    }
}
