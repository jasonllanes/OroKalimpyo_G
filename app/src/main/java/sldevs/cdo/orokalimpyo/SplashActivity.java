package sldevs.cdo.orokalimpyo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.home.home;
import sldevs.cdo.orokalimpyo.onboarding.getting_started;

public class SplashActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 8000;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    ImageView ivLogo;

    String app_version;
    String currentVersion;


    TextView tvVersion;
    boolean isConnected;

    Dialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
//        ff = new firebase_functions();


        ivLogo = findViewById(R.id.ivLogo);
        tvVersion = findViewById(R.id.tvVersion);

//        ff.retrieveVersion(tvVersion);
        app_version = getResources().getString(R.string.version);



        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        ivLogo.startAnimation(animation);


        checkNetwork();


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                currentVersion = tvVersion.getText().toString();
//                Toast.makeText(SplashActivity.this, currentVersion, Toast.LENGTH_SHORT).show();
                if(isConnected == false){
                    Toast.makeText(SplashActivity.this, "No internet connection. Please try to open the application again.", Toast.LENGTH_SHORT).show();
                }else {
                    if (user != null) {
                        if (!(app_version.equalsIgnoreCase(currentVersion))) {
//                            Toast.makeText(SplashActivity.this, "Please update your application.", Toast.LENGTH_SHORT).show();
                            Query query = db.collection("Waste Generator").whereEqualTo("email", user.getEmail());
                            AggregateQuery count = query.count();
                            count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        AggregateQuerySnapshot snapshot = task.getResult();
                                        if (snapshot.getCount() > 0) {
                                            Intent i = new Intent(SplashActivity.this, home.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            Toast.makeText(getApplicationContext(), "Maayong pag abot!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Intent i = new Intent(SplashActivity.this, log_in.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }
                            });
                        } else {
                            Query query = db.collection("Waste Generator").whereEqualTo("email", user.getEmail());
                            AggregateQuery count = query.count();
                            count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        AggregateQuerySnapshot snapshot = task.getResult();
                                        if (snapshot.getCount() > 0) {
                                            Intent i = new Intent(SplashActivity.this, home.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            Toast.makeText(getApplicationContext(), "Maayong pag abot!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Intent i = new Intent(SplashActivity.this, log_in.class)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                }
                            });
                        }
                    } else {
//                        if(!(app_version.equalsIgnoreCase(currentVersion))){
////                            Toast.makeText(SplashActivity.this, "Please update your application.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(SplashActivity.this, getting_started.class);
//                            startActivity(intent);
//                            finish();
//                        }else{
                        SharedPreferences sharedPreferences = getSharedPreferences("getting_started", MODE_PRIVATE);
                        String value = sharedPreferences.getString("was_launched", "");

                        if (value.equalsIgnoreCase("true")) {
                            Intent intent = new Intent(SplashActivity.this, log_in.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(SplashActivity.this, getting_started.class);
                            startActivity(intent);
                            finish();
                        }
                    }
//                }
                }




            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    public void checkNetwork(){
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(ConnectivityManager.class);
        connectivityManager.requestNetwork(networkRequest, networkCallback);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);

        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Toast.makeText(SplashActivity.this, "Internet connection lost", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
    };

}