package com.example.brave.workshop;

import android.content.Intent;
import android.os.AsyncTask;
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

public class Login extends AppCompatActivity {
    public Button btRegisterLog;
    private EditText edUserLog;
    private EditText edPassLog;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserLog = (EditText) findViewById(R.id.edUserLog);
        edPassLog = (EditText) findViewById(R.id.edPassLog);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegisterLog = (Button) findViewById(R.id.btRegisterLog);

        setListener();
    }

    private boolean setValidate() {
        String username = edUserLog.getText().toString();
        String password = edPassLog.getText().toString();

        if (username.isEmpty()) return false;
        if (password.isEmpty()) return false;
        return true;
    }

    private void setListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (setValidate()) {
                    //ติดต่อ Server
                    //นำแทคมาช่วย
                    new login(edUserLog.getText().toString()
                            , edPassLog.getText().toString())
                            .execute();



                } else {
                    Toast.makeText(Login.this, "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRegisterLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }

    private class login extends AsyncTask<Void, Void, String> {
        //กำหนดตัวแปลขึ้นมา เพื่อเชื่อมกับ server
        private String username;
        private String password;


        public login(String username, String password) {
            this.username = username;
            this.password = password;

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
                    .add("username", username)
                    .add("password", password)
                    .build();
            //เรียกใช้
            request = new Request.Builder()
                    .url("http://kimhun55.com/pollservices/login.php")
                    .post(requestBody)
                    .build();

            try {
                response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException ex) {
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
                if (rootObj.has("result")) {
                    JSONObject resultObj = rootObj.getJSONObject("result");
                    if (resultObj.getInt("result") == 1) {
                        Toast.makeText(Login.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(Login.this, NewsList.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(Login.this, resultObj.getString("result_desc"), Toast.LENGTH_SHORT).show();

                    }
                }
            } catch (JSONException ex) {

            }

        }


    }
}
