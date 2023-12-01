package sldevs.cdo.orokalimpyo.profile;

import static sldevs.cdo.orokalimpyo.authentication.final_sign_up.isValidEmail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import org.w3c.dom.Text;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.github.cutelibs.cutedialog.CuteDialog;
import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.final_sign_up;
import sldevs.cdo.orokalimpyo.authentication.household_sign_up_details;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.terms_and_data.external_link;


public class profile_fragment extends Fragment implements View.OnClickListener {

    Button btnProfile, btnViewHistory, btnRedeemedCodes, btnLogout, btnAbout, btnYes, btnNo;
    StorageReference storageReference;
    TextView tvEditLocation;
    CircleImageView profile_image;
    public TextView tvName, tvType, tvFullname, tvHouseholdType, tvEstablishmentType, tvEstablishmentTypeL, tvBarangay, tvLocation, tvNumber, tvEmail,tvTerm,tvData;

    firebase_crud fc;
    FirebaseAuth mAuth;
    Bitmap bitmap;

    private LocationRequest locationRequest;

    LinearLayout llMenu;

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

        tvName = view.findViewById(R.id.tvName);
        tvType = view.findViewById(R.id.tvType);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvHouseholdType = view.findViewById(R.id.tvHouseholdType);
        tvEstablishmentType = view.findViewById(R.id.tvEstablishmentType);
        tvEstablishmentTypeL = view.findViewById(R.id.tvEstablishmentTypeL);
        tvBarangay = view.findViewById(R.id.tvBarangay);
        tvLocation = view.findViewById(R.id.tvLocation);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvTerm = view.findViewById(R.id.tvTerm);
        tvData = view.findViewById(R.id.tvData);

        tvEditLocation = view.findViewById(R.id.tvEditLocation);

        btnProfile = view.findViewById(R.id.btnProfile);
        btnAbout = view.findViewById(R.id.btnAbout);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnViewHistory = view.findViewById(R.id.btnViewHistory);
        btnRedeemedCodes = view.findViewById(R.id.btnRedeemedCodes);

        llMenu = view.findViewById(R.id.llMenu);

//        fc.retrieveProfile(getActivity(),getContext(),mAuth.getUid(),tvFullname,tvType,tvHouseholdType,tvEstablishmentType,tvBarangay,tvLocation,tvNumber,tvEstablishmentTypeL,tvEmail);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.opacity_anim);
        profile_image.startAnimation(animation);

        Animation animation2 = AnimationUtils.loadAnimation(getContext(), R.anim.fast_anim);
        llMenu.startAnimation(animation2);

        tvTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), external_link.class);
                i.putExtra("title","Terms and Condition");
                i.putExtra("external_link","https://terms-and-condition-okapp.netlify.app/?fbclid=IwAR2lZEvMlRtneZKyKL1n0Sh6xC0YWVrRNvwYvGPx77N4AfSr6Z39haAmwyE#liability");
                startActivity(i);
            }
        });

        tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), external_link.class);
                i.putExtra("title","Data Policy");
                i.putExtra("external_link","https://privacy.gov.ph/data-privacy-act/?fbclid=IwAR0u0WwsJHx-IsXrJf6gjGpi1I91iIjucho7kWL9nxJw1gYz5HVzdSmHdaI");
                startActivity(i);
            }
        });


        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //if task is successful
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser() == null) {
                        Intent intent = new Intent(getContext(), log_in.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        generateQRCode();
                        fc.retrieveName(getActivity(), getContext(), mAuth.getUid(), tvName);
                    }
                } else {
                    //if task is not successful
                    Toast.makeText(getContext(), "Please log in again.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), log_in.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        tvEditLocation.setOnClickListener(this);
        profile_image.setOnClickListener(this);
        btnProfile.setOnClickListener(this);
        btnAbout.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnViewHistory.setOnClickListener(this);
        btnRedeemedCodes.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnLogout) {
            new CuteDialog.withIcon(getContext())
                    .setIcon(R.drawable.log_out_2)
                    .setTitle("Log Out").setTitleTextColor(R.color.green)
                    .setDescription("Are you sure you want to log out?").setPositiveButtonColor(R.color.green)
                    .setPositiveButtonText("Yes", v2 -> {
                        fc.logOut(getActivity(), getContext());
                        Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();

                    })
                    .setNegativeButtonText("No", v2 -> {
                        Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    })
                    .show();
//            Dialog builder = new Dialog(getContext());
//            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            builder.setContentView(R.layout.log_out_pop);
//            builder.setCancelable(true);
//            builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//            btnYes = builder.findViewById(R.id.btnYes);
//            btnYes.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    fc.logOut(getActivity(), getContext());
//                    Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();
//
//
//
//                }
//            });
//
//            btnNo = builder.findViewById(R.id.btnNo);
//            btnNo.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    builder.dismiss();
//                }
//            });
//            builder.show();
        } else if (id == R.id.btnProfile) {
            Intent i = new Intent(getContext(), view_profile.class);
            startActivity(i);
        } else if (id == R.id.profile_image) {
            Intent i = new Intent(getContext(), show_qr.class);
            startActivity(i);
        } else if (id == R.id.btnViewHistory) {
            Intent i = new Intent(getContext(), view_contribution.class);
            startActivity(i);
        } else if (id == R.id.btnRedeemedCodes) {
            Intent i = new Intent(getContext(), redeemed_codes.class);
            startActivity(i);
        } else if (id == R.id.tvEditLocation) {
            Intent i = new Intent(getContext(), edit_location.class);
            i.putExtra("current_location", tvLocation.getText().toString());
            i.putExtra("user_type", tvType.getText().toString());
            i.putExtra("household_type", tvHouseholdType.getText().toString());
            i.putExtra("name", tvFullname.getText().toString());
            i.putExtra("barangay", tvBarangay.getText().toString());
            i.putExtra("number", tvNumber.getText().toString());
            i.putExtra("email", tvEmail.getText().toString());
            startActivity(i);
        } else if (id == R.id.btnAbout) {
            Intent i = new Intent(getContext(), about_app.class);
            startActivity(i);
        }
    }


    public void generateQRCode() {

        QRGEncoder qrgEncoder = new QRGEncoder(mAuth.getUid(), null, QRGContents.Type.TEXT, 800);
        qrgEncoder.setColorBlack(Color.rgb(10, 147, 81));
        qrgEncoder.setColorWhite(Color.rgb(255, 255, 255));
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        profile_image.setImageBitmap(bitmap);

    }


}