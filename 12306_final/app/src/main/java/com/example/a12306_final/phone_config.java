package com.example.a12306_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12306_final.javabean._User;

import org.w3c.dom.Text;

import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class phone_config extends AppCompatActivity {

    private TextView btn_phone_config;
    private TextView btn_send_ms;
    private EditText confirm_number;
    private EditText phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_config);
        btn_send_ms = (TextView)findViewById(R.id.sendMs);
        btn_phone_config = (TextView)findViewById(R.id.phone_config_submit);
        phone_number = (EditText)findViewById(R.id.Phone_number);
        confirm_number = (EditText)findViewById(R.id.Confirm_number);
       // BmobSMS.initialize(this,"22488e6103913f621999fda39c18cdec");
        btn_send_ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.requestSMSCode(phone_number.getText().toString(), "12306",new QueryListener<Integer>() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        if(ex==null) {//验证码发送成功
                            Toast.makeText(getBaseContext(), "发送成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        btn_phone_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.verifySmsCode(phone_number.getText().toString(), confirm_number.getText().toString(), new UpdateListener() {

                    @Override
                    public void done(BmobException ex) {
                        if(ex==null){//短信验证码已验证成功
                            Toast.makeText(getBaseContext(),"验证成功",Toast.LENGTH_SHORT).show();
                           /* Intent intent = new Intent();
                            intent.putExtra("data_return","true");
                            setResult(RESULT_OK,intent);*/
                           Intent intent = new Intent(phone_config.this,My12306_Logined.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getBaseContext(),"验证失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
/*
    _User user =new _User();
                user.setMobilePhoneNumber(phone_number.getText().toString());
                        user.setMobilePhoneNumberVerified(true);
                        _User cur = BmobUser.getCurrentUser(_User.class);
        user.update(cur.getObjectId(), new UpdateListener() {
@Override
public void done(BmobException e) {

        }
        });*/