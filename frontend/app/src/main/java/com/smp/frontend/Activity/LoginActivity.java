package com.smp.frontend.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.androidgamesdk.gametextinput.Listener;
import com.smp.frontend.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private Button btn_login, btn_register;
    private EditText et_id, et_pass;
    private String kakaoId;

    private static final String TAG = "logError"; //에러 확인


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
            }
        });
    }
}