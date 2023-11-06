package sldevs.cdo.orokalimpyo.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.terms_and_data.external_link;

public class final_sign_up_2 extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    EditText etEmail,etPassword;
    CheckBox cbAgree;

    ProgressBar pbLoading;
    Button btnSignUp;

    TextView tvTerms,tvData;

    Bitmap bitmap;

    firebase_crud fc;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    Map<String, Object> user_details;
    String user_type,collector_type, consolidator_type,accreditation,name,contact_person,number,all_data;

    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_sign_up2);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fc = new firebase_crud();


        ivBack = findViewById(R.id.ivBack);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        cbAgree = findViewById(R.id.cbAgree);

        pbLoading = findViewById(R.id.pbLoading);

        btnSignUp = findViewById(R.id.btnSignUp);

        tvTerms = findViewById(R.id.tvTerms);
        tvData = findViewById(R.id.tvData);


        //Get intent value from recent activities
        user_type = getIntent().getStringExtra("user_type");
        collector_type = getIntent().getStringExtra("collector_type");
        consolidator_type = getIntent().getStringExtra("consolidator_type");
        accreditation = getIntent().getStringExtra("accreditation");
        name = getIntent().getStringExtra("name");
        contact_person = getIntent().getStringExtra("contact_person");
        number = getIntent().getStringExtra("number");

        retrieveDate();

        ivBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.ivBack){
            finish();
        } else if(id == R.id.btnSignUp){
            if(etEmail.getText().toString().isEmpty()){
                etEmail.setError("Please input your email.");
            }else if (!isValidEmail(etEmail.getText().toString())) {
                etEmail.setError("Invalid email.");
            } else if (etPassword.getText().toString().isEmpty()) {
                etPassword.setError("Please input your password.");
            } else if (etPassword.getText().toString().length() < 6) {
                etPassword.setError("Password should be more 6 characters.");
            } else if (!cbAgree.isChecked()) {
                cbAgree.setError("Please check the Terms and Service and Data Policy.");
            } else{
                pbLoading.setVisibility(View.VISIBLE);
                btnSignUp.setVisibility(View.GONE);
                signUp();
            }

        } else if(id == R.id.tvTerms){
            Intent i = new Intent(final_sign_up_2.this, external_link.class);
            i.putExtra("title","Terms and Condition");
            i.putExtra("external_link","https://terms-and-condition-okapp.netlify.app/?fbclid=IwAR2lZEvMlRtneZKyKL1n0Sh6xC0YWVrRNvwYvGPx77N4AfSr6Z39haAmwyE#liability");
            startActivity(i);
        } else if(id == R.id.tvData){
            Intent i = new Intent(final_sign_up_2.this, external_link.class);
            i.putExtra("title","Data Policy");
            i.putExtra("external_link","https://privacy.gov.ph/data-privacy-act/?fbclid=IwAR0u0WwsJHx-IsXrJf6gjGpi1I91iIjucho7kWL9nxJw1gYz5HVzdSmHdaI");
            startActivity(i);
        }
    }



    public void signUp(){
            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        retrieveAllData();
                        db.collection(user_type).document(mAuth.getUid()).set(user_details).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                generateQRCode();
                                fc.saveUserQRCode(bitmap,final_sign_up_2.this,getApplicationContext(),user_type,mAuth.getUid(),name,pbLoading,btnSignUp);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(final_sign_up_2.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                                pbLoading.setVisibility(View.GONE);
                                btnSignUp.setVisibility(View.VISIBLE);
                            }
                        });
                    }else{

                    }
                }
            });
    }


    public void retrieveAllData(){
        if(user_type.equalsIgnoreCase("Waste Collector")){
            if(collector_type.equalsIgnoreCase("Private Collector")){
                user_details = new HashMap<>();
                user_details.put("user_id",mAuth.getUid());
                user_details.put("name", name);
                user_details.put("user_type", user_type);
                user_details.put("collector_type", collector_type);
                user_details.put("accreditation_number", accreditation);
                user_details.put("contact_person",contact_person);
                user_details.put("number", number);
                user_details.put("email", etEmail.getText().toString().trim());
                user_details.put("date_created", currentDate + " " + currentTime);

                all_data = mAuth.getUid();

            }else{
                user_details = new HashMap<>();
                user_details.put("user_id",mAuth.getUid());
                user_details.put("name", name);
                user_details.put("user_type", user_type);
                user_details.put("collector_type", collector_type);
                user_details.put("contact_person",contact_person);
                user_details.put("number", number);
                user_details.put("email", etEmail.getText().toString().trim());
                user_details.put("date_created", currentDate + " " + currentTime);

                all_data = mAuth.getUid();

            }
        } else if(user_type.equalsIgnoreCase("Waste Consolidator")){
            user_details = new HashMap<>();
            user_details.put("user_id",mAuth.getUid());
            user_details.put("name", name);
            user_details.put("user_type", user_type);
            user_details.put("consolidator_type", consolidator_type);
            user_details.put("contact_perso n",contact_person);
            user_details.put("number", number);
            user_details.put("email", etEmail.getText().toString().trim());
            user_details.put("date_created", currentDate + " " + currentTime);

            all_data = mAuth.getUid();

        }


    }


    public void generateQRCode(){
        QRGEncoder qrgEncoder = new QRGEncoder(all_data, null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.rgb(10,147,81));
        qrgEncoder.setColorWhite(Color.rgb(255,255,255));
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
//            imageView.setImageBitmap(bitmap);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,name + "_QR Code" , "A QR Code that is generated base on the data given");
        } else {
            ActivityCompat.requestPermissions(final_sign_up_2.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

    }

    public void retrieveDate(){
        month = new SimpleDateFormat("MM");
        day = new SimpleDateFormat("dd");
        year = new SimpleDateFormat("yy");

        week = new SimpleDateFormat("W");

        date = new SimpleDateFormat("MM/dd/yy");

        hours = new SimpleDateFormat("hh");
        minutes = new SimpleDateFormat("mm");
        seconds = new SimpleDateFormat("ss");

        time = new SimpleDateFormat("hh:mm:ss a");

        currentMonth = month.format(new Date());
        currentDay = day.format(new Date());
        currentYear = year.format(new Date());

        currentWeek = week.format(new Date());

        currentDate = date.format(new Date());

        currentHour = hours.format(new Date());
        currentMinute = minutes.format(new Date());
        currentSeconds = seconds.format(new Date());

        currentTime = time.format(new Date());
    }
    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}