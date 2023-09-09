package sldevs.cdo.orokalimpyo.scanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.final_sign_up;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class collector_scanner_result extends AppCompatActivity implements View.OnClickListener {

    TextView tvUserID,tvName,tvHouseholdType,tvBarangay,tvLocation,tvNumber,tvEmail,
                tvUserIDN,tvNameN,tvHouseholdTypeN,tvEstablishmentType,tvBarangayN,tvLocationN,tvNumberN,tvEmailN,
                    tvCollectorID,tvCollectorName,tvDate;

    LinearLayout llH,llNH;

    MaterialSpinner sWaste;

    EditText etKilo;
    Button btnSend,btnScan;

    firebase_crud fc;
    String user_id,user_type,houshold_type,establishment_type,others,name,barangay,location,number;

    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collector_scanner_result);

        fc = new firebase_crud();

        tvUserID = findViewById(R.id.tvUserID);
        tvName = findViewById(R.id.tvName);
        tvHouseholdType = findViewById(R.id.tvHouseholdType);
        tvBarangay = findViewById(R.id.tvBarangay);
        tvLocation = findViewById(R.id.tvLocation);
        tvNumber = findViewById(R.id.tvNumber);
        tvEmail = findViewById(R.id.tvEmail);

        tvUserIDN = findViewById(R.id.tvUserIDN);
        tvNameN = findViewById(R.id.tvName);
        tvHouseholdTypeN = findViewById(R.id.tvHouseholdTypeN);
        tvEstablishmentType = findViewById(R.id.tvEstablishmentTypeN);
        tvBarangayN = findViewById(R.id.tvBarangayN);
        tvLocationN = findViewById(R.id.tvLocationN);
        tvNumberN = findViewById(R.id.tvNumberN);
        tvEmailN = findViewById(R.id.tvEmailN);

        tvCollectorID = findViewById(R.id.tvCollectorID);
        tvCollectorName = findViewById(R.id.tvCollectorName);
        tvDate = findViewById(R.id.tvDate);

        llH = findViewById(R.id.llH);
        llNH = findViewById(R.id.llNH);

        sWaste = findViewById(R.id.sWaste);

        etKilo = findViewById(R.id.etKilo);

        btnSend = findViewById(R.id.btnSend);
        btnScan = findViewById(R.id.btnScan);

        user_id = getIntent().getStringExtra("user_id");
        user_type = getIntent().getStringExtra("user_type");
        houshold_type = getIntent().getStringExtra("household_type");
        establishment_type = getIntent().getStringExtra("establishment_type");
        others = getIntent().getStringExtra("others");
        name = getIntent().getStringExtra("name");
        barangay = getIntent().getStringExtra("barangay");
        location = getIntent().getStringExtra("location");
        number = getIntent().getStringExtra("number");

        retrieveDate();


        tvDate.setText(date + " " + time);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnSend){
            if(houshold_type.equalsIgnoreCase("Household")){

            }else if(houshold_type.equalsIgnoreCase("Non-Household")){

            }


        }else if(id == R.id.btnScan){

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

}