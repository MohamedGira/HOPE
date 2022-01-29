package com.helloworld.uifirs222;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.VectorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.helloworld.uifirs222.ui.main.Hospitalfrag;
import com.helloworld.uifirs222.ui.main.midicineadd;

import java.util.concurrent.TimeUnit;


public class Verifier extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference dref;
    String verifecationId;
    ProgressBar prog;
    EditText codae;
    CardView verify;
    ImageView back;
    private PhoneAuthCredential credential;
    public static final String PREFS_NAME = "30";


    public final static int REQUEST_CODE = 10101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifier);
        mAuth = FirebaseAuth.getInstance();
        dref = FirebaseDatabase.getInstance().getReference();

        String phoneNumber = getIntent().getStringExtra("fullnumber");
        verify = findViewById(R.id.Verify);
        prog = findViewById(R.id.prog);
        codae = findViewById(R.id.Code);
        back = findViewById(R.id.ver_img);
        back.setImageResource(R.drawable.ic_login_background);

        sendVerificationCode(phoneNumber);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = codae.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    codae.setError("Enter valid code");
                    codae.requestFocus();
                    return;
                } else if (verifecationId == null) {
                    Toast.makeText(Verifier.this, "not sent", Toast.LENGTH_SHORT).show();
                } else {
                    prog.setVisibility(View.VISIBLE);
                    VerifyCode(code);
                }
            }
        });
    }

    //Request Verification code from firebase

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifecationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                codae.setText(code);
                prog.setVisibility(View.VISIBLE);
                VerifyCode(code);

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Verifier.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void VerifyCode(String code) {
        credential = PhoneAuthProvider.getCredential(verifecationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Verifier.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            //verification successful we will start the profile activity, also we will set the user number in the Firebase database

                            final SharedPreferences storage = getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                            final SharedPreferences.Editor editor = storage.edit();
                            String phoneNumber = getIntent().getStringExtra("fullnumber");
                            editor.putString("User number", phoneNumber);
                            editor.putBoolean("signed up", true);
                            dref = FirebaseDatabase.getInstance().getReference("Users/" + phoneNumber + "/Signed up");
                            dref.setValue("True");
                            editor.commit();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Verifier.this, Tutorial.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }, 200);

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(Verifier.this, String.valueOf(task.getException()), Toast.LENGTH_SHORT).show();
                                prog.setVisibility(View.GONE);

                            } else {
                                String message = "Somthing is wrong, we will fix it soon...";
                                Toast.makeText(Verifier.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


}

