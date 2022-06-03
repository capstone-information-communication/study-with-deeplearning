package com.smp.frontend.global.acitivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.smp.frontend.R;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.member.MemberController;
import com.smp.frontend.member.RetrofitClientMember;
import com.smp.frontend.member.activity.LoginActivity;
import com.smp.frontend.member.activity.MainActivity;
import com.smp.frontend.member.dto.MemberSginInResponseDto;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private RetrofitClientMember retrofitClient = RetrofitClientMember.getInstance();
    private MemberController memberController =  RetrofitClientMember.getRetrofitInterface();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Handler handler = new Handler();
        super.onCreate(savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!(PreferencesManager.getString(getApplication(), "token").equals(null))) {
                    Call<MemberSginInResponseDto> UserInfo = memberController.GetUser(PreferencesManager.getString(getApplication(), "token"));
                    UserInfo.enqueue(new Callback<MemberSginInResponseDto>() {
                        @Override
                        public void onResponse(Call<MemberSginInResponseDto> call, Response<MemberSginInResponseDto> response) {
                            if (response.code() == 200) {
                                System.out.println("response2 = " + response.body().getEmail());
                                PreferencesManager.setData(getApplication(), "nickname", response.body().getNickname());
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else if(response.code() == 500){
                                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                            }
                            else if(response.code() == 400){
                                try {
                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                    String message = jsonObject.get("message").toString();
                                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                } catch (IOException | JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MemberSginInResponseDto> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "서버에러", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },1000);
    }
}