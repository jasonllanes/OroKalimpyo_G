package sldevs.cdo.orokalimpyo.profile;

import static sldevs.cdo.orokalimpyo.authentication.final_sign_up.isValidEmail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.household_sign_up_details;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;


public class profile_fragment extends Fragment implements View.OnClickListener {

    Button btnEditProfile,btnViewHistory,btnLogout,btnAbout,btnYes,btnNo;
    TextView tvEditLocation;
    CircleImageView profile_image;
    public TextView tvType,tvFullname,tvHouseholdType,tvEstablishmentType,tvEstablishmentTypeL,tvBarangay,tvLocation,tvNumber,tvEmail;

    firebase_crud fc;
    FirebaseAuth mAuth;

    private LocationRequest locationRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        fc = new firebase_crud();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        mAuth = FirebaseAuth.getInstance();

        profile_image = view.findViewById(R.id.profile_image);

        tvType = view.findViewById(R.id.tvType);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvHouseholdType = view.findViewById(R.id.tvHouseholdType);
        tvEstablishmentType = view.findViewById(R.id.tvEstablishmentType);
        tvEstablishmentTypeL = view.findViewById(R.id.tvEstablishmentTypeL);
        tvBarangay = view.findViewById(R.id.tvBarangay);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvEmail = view.findViewById(R.id.tvEmail);

        tvEditLocation = view.findViewById(R.id.tvEditLocation);

//        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnViewHistory = view.findViewById(R.id.btnViewHistory);

//        fc.retrieveProfile(getActivity(),getContext(),mAuth.getUid(),tvFullname,tvType,tvHouseholdType,tvEstablishmentType,tvBarangay,tvLocation,tvNumber,tvEstablishmentTypeL,tvEmail);

        Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.fast_anim);
        profile_image.startAnimation(animation);

        tvEditLocation.setOnClickListener(this);
        profile_image.setOnClickListener(this);
//        btnEditProfile.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnViewHistory.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btnLogout){
            Dialog builder = new Dialog(getContext());
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.setContentView(R.layout.log_out_pop);
            builder.setCancelable(true);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            btnYes = builder.findViewById(R.id.btnYes);
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fc.logOut(getActivity(), getContext());
                    Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();



                }
            });

            btnNo = builder.findViewById(R.id.btnNo);
            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.dismiss();
                }
            });
            builder.show();
        }else if(id == R.id.profile_image){
            Intent i = new Intent(getContext(),show_qr.class);
            startActivity(i);
        } else if (id == R.id.btnViewHistory) {
            Intent i = new Intent(getContext(), view_contribution.class);
            startActivity(i);
        } else if (id == R.id.tvEditLocation) {
            Intent i = new Intent(getContext(), edit_location.class);
            i.putExtra("current_location",tvLocation.getText().toString());
            i.putExtra("user_type", tvType.getText().toString());
            i.putExtra("household_type", tvHouseholdType.getText().toString());
            i.putExtra("name", tvFullname.getText().toString());
            i.putExtra("barangay", tvBarangay.getText().toString());
            i.putExtra("number", tvNumber.getText().toString());
            i.putExtra("email",tvEmail.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnAbout) {
            Intent i = new Intent(getContext(), about_app.class);
            startActivity(i);
        }
    }





}