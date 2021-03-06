package s2017s40.kr.hs.mirim.remember_hi;

import s2017s40.kr.hs.mirim.remember_hi.DTO.UserDTO;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    FirebaseDatabase database  = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getInstance().getReference();

    Button datePickBtn, signUpBtn;
    Calendar cal;
    int resultYear, resultMonth, resultDate;
    TextView yearTxt, monthTxt, dateTxt;
    EditText nameEdit,phoneNumEdit, pnameEidt, pphoneNumEidt;
    String Number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Number = auto.getString("Number",null);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        TextView t =findViewById(R.id.actionbar_text);
        t.setText("회원가입");

        datePickBtn = findViewById(R.id.signup_pickBirth_btn);
        signUpBtn = findViewById(R.id.signup_signup_btn);

        yearTxt = findViewById(R.id.signup_year_text);
        monthTxt = findViewById(R.id.signup_month_text);
        dateTxt = findViewById(R.id.signup_date_text);

        nameEdit = findViewById(R.id.signup_name_edit);
        phoneNumEdit = findViewById(R.id.signup_phoneNum_edit);
        phoneNumEdit.setText(Number);
        pnameEidt = findViewById(R.id.signup_pName_edit);
        pphoneNumEidt = findViewById(R.id.signup_pPhoneNum_edit);

        cal = Calendar.getInstance();

        datePickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog d = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        resultYear = year;
                        resultMonth = month+1;
                        resultDate = dayOfMonth;
                        yearTxt.setText(resultYear + "년");
                        monthTxt.setText(resultMonth + "월");
                        dateTxt.setText(resultDate + "일");
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                d.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                d.show();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nameEdit.getText().equals("")){
                    Toast.makeText(SignUpActivity.this,"이름을 입력해 주세요",Toast.LENGTH_LONG).show(); return;
                }if(phoneNumEdit.getText().equals("")){
                    Toast.makeText(SignUpActivity.this,"전화번호를 입력해 주세요",Toast.LENGTH_LONG).show();return;
                }if(pnameEidt.getText().equals("")){
                    Toast.makeText(SignUpActivity.this,"보호자의 이름을 입력해 주세요",Toast.LENGTH_LONG).show();return;
                }if(pphoneNumEidt.getText().equals("")){
                    Toast.makeText(SignUpActivity.this,"보호자의 전화번호를 입력해 주세요",Toast.LENGTH_LONG).show();return;
                }

                UserDTO user = new UserDTO(String.valueOf(nameEdit.getText()),
                        (resultYear+"/"+resultMonth+"/"+resultDate),
                        (Calendar.YEAR -  resultYear+1),
                        phoneNumEdit.getText().toString(),
                        pnameEidt.getText().toString(),
                        pphoneNumEidt.getText().toString());

                myRef.child("User").child(Number).child("info").setValue(user);

                Intent intent = new Intent(SignUpActivity.this, CheckActivity.class);
                startActivity(intent);
            }
        });
    }
    public String phoneChange(){
        String changeNum = phoneNumEdit.getText().toString();
        changeNum = changeNum.substring(1);
        changeNum = "+82" + changeNum;
        return changeNum;
    }
}
