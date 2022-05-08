package com.smp.frontend.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.smp.frontend.BuildConfig;
import com.smp.frontend.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.restAPi.RestApi;
import com.smp.frontend.restAPi.RetrofitClient;
import com.smp.frontend.restAPi.SginUpResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText join_email, join_password, join_pwck, join_name, join_nickName;
    private Button join_button,Btn_delete;
    private AlertDialog dialog;

    private  RetrofitClient retrofitClient;
    private  HashMap<String,Object> SignForm = new HashMap<String,Object>();
    Toast toast;

    protected  void setSginUp(HashMap<String,Object> SginUp){
        RetrofitClient retrofitClient = RetrofitClient.getInstance();
        RestApi restApi =  RetrofitClient.getRetrofitInterface();
        Call<SginUpResponse> signrequest =  restApi.SignUp(SginUp);
        signrequest.enqueue(new Callback<SginUpResponse>() {
            @Override
            public void onResponse(Call<SginUpResponse> call, Response<SginUpResponse> response) {
                //응답으로 토큰값이 와야되는데
                if(response.code() == 200){
                    toast.makeText(getApplication(),"회원가입 완료" + response.body().getNickname() + "님 환영해요",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
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
            public void onFailure(Call<SginUpResponse> call, Throwable t) {
                System.out.println("call = " + call + "둘");
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_register);

        //아이디값 찾아주기
        join_email = findViewById( R.id.join_email );
        join_password = findViewById( R.id.join_password );
        join_pwck = findViewById(R.id.join_passChk);
        join_name = findViewById(R.id.join_name);
        join_nickName = findViewById(R.id.join_nickName);
        //취소버튼
        Btn_delete = findViewById(R.id.delete);
        Btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //회원가입 버튼 클릭 시 수행
        join_button = findViewById( R.id.join_button );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String UserEmail = join_email.getText().toString();
                final String UserPwd = join_password.getText().toString();
                final String PassCk = join_pwck.getText().toString();
                final String UserName = join_name.getText().toString();
                final String UserNickName = join_nickName.getText().toString();


                //빈칸 확인
                if (UserEmail.equals("") || UserName.equals("") || UserNickName.equals("") || UserPwd.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("빈칸이 존재합니다..").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                if (!UserPwd.equals(PassCk)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //비밀번호와 비밀번호 중복이 같지 않다면

                SignForm.put("email", UserEmail);
                SignForm.put("password", UserPwd);
                SignForm.put("nickname", UserNickName);
                SignForm.put("name", UserName);
                SignForm.put("role","USER");
                setSginUp(SignForm);


            }
        });
    }
}