package com.smp.frontend.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.smp.frontend.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText join_email, join_password, join_pwck, join_name, join_nickName;
    private Button join_button,Btn_delete;
    private ImageButton email_check, nickName_check;
    private AlertDialog dialog;
    private boolean validate = false;

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

        //이메일 중복 체크
        email_check = findViewById(R.id.email_check);
        email_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserEmail = join_email.getText().toString();
                if (validate) {
                    return; //검증 완료
                }

                if (UserEmail.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("이메일을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });

        nickName_check = findViewById(R.id.nickName_check);
        nickName_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserNickname = join_nickName.getText().toString();
                if(validate){
                    return; //검증완료
                }

                if(UserNickname.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("닉네임을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
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
                System.out.println(validate);

                //아이디 중복체크 했는지 확인
                if (!validate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //한 칸이라도 입력 안했을 경우
                if (UserEmail.equals("") || UserPwd.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }

                //비밀번호와 비밀번호 중복이 같지 않다면
                if (UserPwd != PassCk) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 확인해주세요").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }


            }
        });
    }
}