package s2017s40.kr.hs.mirim.remember_hi;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class SignUpActivity extends AppCompatActivity {
    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordHak;
    private EditText editTextName;
    private Spinner spinnerAge;
    private ArrayAdapter spinnerAdapter;
    int age_result;

    RadioGroup radioGroup_gender;
    RadioButton ra_man, ra_woman;

    private String email = "";
    private String password = "";
    private String password_hak = "";
    private String name = "";
    Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();
        signin = (Button)findViewById(R.id.sign_up_button);
        editTextEmail = findViewById(R.id.sign_up_email_edit);
        editTextPassword = findViewById(R.id.sign_up_password_edit);
        editTextPasswordHak = findViewById(R.id.sign_up_password_hak);
        editTextName= findViewById(R.id.sign_up_name_edit);
        spinnerAge = findViewById(R.id.sign_up_age_spinner);
        radioGroup_gender = findViewById(R.id.sign_in_gender_rg);
        ra_man = findViewById(R.id.sign_in_man_rbtn);
        ra_woman = findViewById(R.id.sign_in_woman_rbtn);

        //성별 라디오 버튼 구성 및 string 지정
        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sign_in_man_rbtn:
                        break;
                    case R.id.sign_in_woman_rbtn:
                        break;
                }
            }
        });

        //나이 스피너 설정
        final ArrayList<Integer> age = new ArrayList<>();
        for (int i = 1; i < 100; i++) { age.add(i); }
        spinnerAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, age);
        spinnerAge.setAdapter(spinnerAdapter);
        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age_result = (int) spinnerAge.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //회원가입 버튼 설정
        signin.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ra_man.isChecked() && !ra_woman.isChecked()){
                    Toast.makeText(SignUpActivity.this, "성별을 선택해 주세요", Toast.LENGTH_SHORT).show();
                }
                singUp(view);
            }
        });
    }

    public void singUp(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        password_hak = editTextPasswordHak.getText().toString();
        name = editTextName.getText().toString();
        if(isValidEmail() && isValidPasswd() && isValidPasswdHak() && isValidName()) {
            createUser(email, password, name);
        }else{
            Log.e("전체","전체 에러");
            Toast.makeText(SignUpActivity.this, "실패", Toast.LENGTH_SHORT).show();
        }
    }
    //이름 검사
    private boolean isValidName(){
        if(name.isEmpty()){
            Log.e("이름","이름 에러");
            Toast.makeText(SignUpActivity.this, "이름을 작성해주세요", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            Log.e("이메일","이메일 에러");
            Toast.makeText(SignUpActivity.this, "이메일을 작성해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            Log.e("비밀번호 공백","비밀번호 공백 에러");
            Toast.makeText(SignUpActivity.this, "비밀번호를 작성해주세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            return false;
        } else {
            return true;
        }
    }
    // 비밀번호 확인 검사
    private boolean isValidPasswdHak(){
        if (password_hak.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show();
            // 비밀번호 확인 공백
            Log.e("비밀번호 확인","비밀번호 확인 에러");
            return false;
        } else if (!password_hak.equals(password)) {
            // 비밀번호 불일치
            return false;
        } else {
            return true;
        }
    }
    // 회원가입
    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공
                            Toast.makeText(SignUpActivity.this, "성공", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            // 회원가입 실패
                            Toast.makeText(SignUpActivity.this, "실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}