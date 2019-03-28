package s2017s40.kr.hs.mirim.remember_hi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {
    Button datePickBtn, signUpBtn;
    Calendar cal;
    int resultYear, resultMonth, resultDate;
    TextView yearTxt, monthTxt, dateTxt;
    EditText nameEdit,phoneNumEdit, emailEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        datePickBtn = findViewById(R.id.signup_pickBirth_btn);
        signUpBtn = findViewById(R.id.signup_signup_btn);

        yearTxt = findViewById(R.id.signup_year_text);
        monthTxt = findViewById(R.id.signup_month_text);
        dateTxt = findViewById(R.id.signup_date_text);

        nameEdit = findViewById(R.id.signup_name_edit);
        phoneNumEdit = findViewById(R.id.signup_phoneNum_edit);
        emailEdit = findViewById(R.id.signup_email_edit);

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
                },  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                d.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                d.show();
            }
        });



    }
}
