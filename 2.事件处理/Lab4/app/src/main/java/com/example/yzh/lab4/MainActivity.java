package com.example.yzh.lab4;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView mImage = (ImageView) findViewById(R.id.image);
        final TextInputLayout mNumberlayout = (TextInputLayout) findViewById(R.id.text1);
        final TextInputLayout mPasswordlayout = (TextInputLayout) findViewById(R.id.text2);
        final EditText mNumber = (EditText) findViewById(R.id.numbertext);
        final EditText mPassword = (EditText) findViewById(R.id.passwordtext);
        final ConstraintLayout view = (ConstraintLayout) findViewById(R.id.action_bar_root);
        final RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radio);
        final RadioButton stu = (RadioButton) findViewById(R.id.student);
        final RadioButton tea = (RadioButton) findViewById(R.id.teacher);
        final Button mbutton = (Button) findViewById(R.id.button1);
        final Button mbutton2 = (Button) findViewById(R.id.button2);
        mImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("上传头像");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "你选择了取消" , Toast.LENGTH_SHORT).show();
                    }

                });
                final String[] items = {"拍摄","从相册选择"};
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Toast.makeText(MainActivity.this, "你选择了" + items[which], Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        mbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String number = mNumber.getText().toString();
                String password = mPassword.getText().toString();
                mNumberlayout.setErrorEnabled(false);
                mPasswordlayout.setErrorEnabled(false);
                if (TextUtils.isEmpty(number)) {
                    mNumberlayout.setError("学号不能为空");
                } else if (TextUtils.isEmpty(password)) {
                    mPasswordlayout.setError("密码不能为空");
                } else if (mNumber.getText().toString().equals("123456")&& mPassword.getText().toString().equals("6666")){
                    Snackbar.make(v,"登录成功",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setActionTextColor(Color.BLUE).show();
                } else {
                    Snackbar.make(v,"学号或密码错误",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setActionTextColor(Color.BLUE).show();
                }


            }

        });
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == stu.getId()) {
                    Snackbar.make(view,"您已选择了学生",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                        }
                    }).setActionTextColor(Color.BLUE).show();
                } else if (checkedId == tea.getId()) {
                    Snackbar.make(view,"您已选择了教职工",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MainActivity.this,"Snackbar 的确定按钮被点击了", Toast.LENGTH_SHORT).show();
                        }
                    }).setActionTextColor(Color.BLUE).show();
                }
            }
        });
        mbutton2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (mRadioGroup.getCheckedRadioButtonId() == stu.getId()) {
                    Snackbar.make(view,"学生注册功能尚未启用",Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).setActionTextColor(Color.BLUE).show();
                } else if (mRadioGroup.getCheckedRadioButtonId() == tea.getId()) {
                    Toast.makeText(MainActivity.this,"教职工注册功能尚未启用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
