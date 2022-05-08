package com.smp.frontend.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonParser;
import com.smp.frontend.R;
import com.smp.frontend.PreferencesManager;
import com.smp.frontend.restAPi.RestApi;
import com.smp.frontend.restAPi.RetrofitClient;
import com.smp.frontend.restAPi.SginInResponse;
import com.smp.frontend.restAPi.SingletonGson;
import com.smp.frontend.restAPi.WorkBookTestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Button btn_login, btn_register;
    private EditText et_id, et_pass;

    private  RetrofitClient retrofitClient;

    Toast toast;
    private void SginInConnection(HashMap<String,Object> LoginForm){
        retrofitClient = RetrofitClient.getInstance();
        RestApi restApi =  RetrofitClient.getRetrofitInterface();
        try { // 서버 종료되어있으면 catch 예외처리
            Call<SginInResponse> signrequest =  restApi.SignIn(LoginForm);
            signrequest.enqueue(new Callback<SginInResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<SginInResponse> call, Response<SginInResponse> response) {
                    //응답으로 토큰값이 와야되는데
                    if(response.code() == 200){
                        Object responToken =response.body().Gettoken();
                        PreferencesManager.setToken(getApplication(),"token","Bearer "+responToken);
                        toast.makeText(getBaseContext(), PreferencesManager.getString(getApplication(),"token"),Toast.LENGTH_LONG).show();

                        Call<SginInResponse> UserInfo = restApi.GetUser(PreferencesManager.getString(getBaseContext(),"token"));
                        UserInfo.enqueue(new Callback<SginInResponse>() {
                            @Override
                            public void onResponse(Call<SginInResponse> call, Response<SginInResponse> response2) {
                                if(response2.code() == 200) {
                                    System.out.println("response = " + response2.body().getNickname());
                                    PreferencesManager.setData(getApplication(),"nickname",response2.body().getNickname());
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response2.errorBody().string());
                                        String message = jsonObject.get("message").toString();
                                        Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
                                    } catch (IOException|JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<SginInResponse> call, Throwable t) {
                            }
                        });
                    }else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.get("message").toString();
                            Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                            System.out.println("JSON 메세지 오류");
                        }
                    }

                }
                @Override
                public void onFailure(Call<SginInResponse> call, Throwable t) {
                    toast.makeText(getBaseContext(),"서버 통신에러",Toast.LENGTH_LONG).show();
                    t.printStackTrace();
                }
            });
        }catch (Exception e){
            toast.makeText(getBaseContext(),"서버 확인",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //loginAutoTokenCheck
        if(!(PreferencesManager.getString(getApplication(),"token").equals(null))){
            retrofitClient = RetrofitClient.getInstance();
            RestApi restApi =  RetrofitClient.getRetrofitInterface();
            Call<SginInResponse> UserInfo = restApi.GetUser(PreferencesManager.getString(getApplication(),"token"));
            UserInfo.enqueue(new Callback<SginInResponse>() {
                @Override
                public void onResponse(Call<SginInResponse> call, Response<SginInResponse> response) {
                    if(response.code() == 200) {
                        System.out.println("response2 = " + response.body().getEmail());
                        PreferencesManager.setData(getApplication(),"nickname",response.body().getNickname());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        try {
                            if(response.code() != 500) {
                                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                String message = jsonObject.get("message").toString();
                                Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException|JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(Call<SginInResponse> call, Throwable t) {
                    Toast.makeText(getBaseContext(),"서버에러",Toast.LENGTH_LONG).show();
                }
            });
        }



        btn_login = (Button)findViewById(R.id.btn_login); //로그인 버튼
        btn_register = (Button)findViewById(R.id.btn_register); //회원가입 버튼

        et_id = (EditText)findViewById( R.id.et_id ); //아이디 텍스트
        et_pass = (EditText)findViewById( R.id.et_pass ); //비밀번호 텍스트

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //회원가입시
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        btn_login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 입력값 아이디 비밀번호 가져옴
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                HashMap<String,Object> LoginForm = new HashMap<String,Object>();
                LoginForm.put("email", userID);
                LoginForm.put("password", userPass);

                SginInConnection(LoginForm);
            }
        });
    }
}