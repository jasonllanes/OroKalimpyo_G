package sldevs.cdo.orokalimpyo.firebase;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.saadahmedsoft.popupdialog.PopupDialog;
import com.saadahmedsoft.popupdialog.Styles;
import com.saadahmedsoft.popupdialog.listener.OnDialogButtonClickListener;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import sldevs.cdo.orokalimpyo.GlideApp;
import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.authentication.final_sign_up;
import sldevs.cdo.orokalimpyo.authentication.log_in;
import sldevs.cdo.orokalimpyo.data_fetch.UserDetails;
import sldevs.cdo.orokalimpyo.home.home;
import sldevs.cdo.orokalimpyo.profile.show_qr;
import sldevs.cdo.orokalimpyo.profile.view_profile;

public class firebase_crud {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Map<String, Object> waste_contribution;
    StorageReference storageReference;

    //Authentication Functions----------------------------

    //Log In Function
    public void logInUser(Activity activity, Context context,String user_type, String email, String password, ProgressBar progressBar,Button btnLogIn){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Query query = db.collection("Waste Generator").whereEqualTo("email",email);
                    AggregateQuery count = query.count();
                    count.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                            if(task.isSuccessful()){
                                AggregateQuerySnapshot snapshot = task.getResult();
                                if(snapshot.getCount() > 0){
                                    Toast.makeText(context, "Maayong pag abot!", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, home.class)
                                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(i);
                                    activity.finish();
                                }else {
                                    Toast.makeText(context, "Please use a Waste Generator account.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    btnLogIn.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });
                }else{
                    progressBar.setVisibility(View.GONE);
                    btnLogIn.setVisibility(View.VISIBLE);
                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    //
    public void sendWasteContribution(String user_id,String name,String household_type,String establishment_type,String collector_name,String collector_type,String waste_type,String kilo,String month,String day,String year,String hour,String minutes,String seconds,String date){
        db.collection("WasteContribution").document(user_id.indexOf(0,5)+month+day+year+hour+minutes+seconds).set(waste_contribution).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                waste_contribution = new HashMap<>();
                if(household_type.equalsIgnoreCase("Household")){
                    waste_contribution.put("user_id",mAuth.getUid());
                    waste_contribution.put("name", name);
                    waste_contribution.put("household_type", household_type);
                    waste_contribution.put("collector_name", collector_name);
                    waste_contribution.put("collector_type", collector_type);
                    waste_contribution.put("waste_type",waste_type);
                    waste_contribution.put("kilo",kilo);
                    waste_contribution.put("date", date);
                }else if(household_type.equalsIgnoreCase("Non-Household")){
                    waste_contribution.put("user_id",mAuth.getUid());
                    waste_contribution.put("name", name);
                    waste_contribution.put("household_type", household_type);
                    waste_contribution.put("establishment_type", establishment_type);
                    waste_contribution.put("collector_name", collector_name);
                    waste_contribution.put("collector_type", collector_type);
                    waste_contribution.put("waste_type",waste_type);
                    waste_contribution.put("kilo",kilo);
                    waste_contribution.put("date", date);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(final_sign_up.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //Getting profile

    public void retrieveQRCode(Activity activity, Context context, String id, ImageView qr_code){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                    } else {

                    }
                    String name = document.get("name").toString();
                    storageReference = FirebaseStorage.getInstance().getReference("QR Codes/").child("Waste Generator"+"/"+ name +"_"+ id+".png");
                    GlideApp.with(context).load(storageReference).into(qr_code);

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    public void retrieveName(Activity activity,Context context,String id,TextView name){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                    } else {

                    }
                    name.setText(document.get("name").toString());;

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void retrievePoints(Activity activity,Context context,String id,TextView points){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                    } else {

                    }
                    points.setText(document.get("total_points").toString());

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void retrieveTotalContribution(Activity activity,Context context,String id,TextView residual,TextView recyclable, TextView biodegradable,TextView special_waste){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {


                    } else {

                    }
                    residual.setText(document.get("residual").toString() + " kg");
                    recyclable.setText(document.get("recyclable").toString()+ " kg");
                    biodegradable.setText(document.get("biodegradable").toString()+ " kg");
                    special_waste.setText(document.get("special_waste").toString()+ " kg");

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void wasteGame(Activity activity, Context context, String id, TextView game_level, LottieAnimationView star1){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e);
//                    return;
//                }

                if (value != null && value.exists()) {
                    game_level.setText(value.get("waste_game_level").toString());
                    if(value.get("waste_game_level").toString() == "1"){
                        star1.setAnimation(R.raw.lottie_star);
                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    public void brandGame(Activity activity, Context context, String id, TextView game_level, LottieAnimationView star1){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e);
//                    return;
//                }

                if (value != null && value.exists()) {
                    game_level.setText(value.get("brand_game_level").toString());
                    if(value.get("brand_game_level").toString() == "1"){
                        star1.setAnimation(R.raw.lottie_star);
                    }

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    public void retrieveProfile(Activity activity, Context context, String id, TextView name, TextView user_type, TextView household_type, TextView establishment_type, TextView barangay, TextView location, TextView number, TextView establishment_type_label,TextView email){
        DocumentReference docRef = db.collection("Waste Generator").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {



                    } else {

                    }
                    name.setText(document.get("name").toString());
                    user_type.setText(document.get("user_type").toString());
                    household_type.setText(document.get("household_type").toString());
                    if(document.get("household_type").toString().equalsIgnoreCase("Household")){
                        establishment_type.setVisibility(View.GONE);
                        establishment_type_label.setVisibility(View.GONE);
                        barangay.setText(document.get("barangay").toString());
                        location.setText(document.get("location").toString());
                        number.setText(document.get("number").toString());
                        email.setText(document.get("email").toString());
                    }else if(document.get("household_type").toString().equalsIgnoreCase("Non-Household")){
                        establishment_type.setVisibility(View.VISIBLE);
                        establishment_type_label.setVisibility(View.VISIBLE);
                        establishment_type.setText(document.get("establishment_type").toString());
                        barangay.setText(document.get("barangay").toString());
                        location.setText(document.get("location").toString());
                        number.setText(document.get("number").toString());
                        email.setText(document.get("email").toString());
                    }

                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    //Saving QR Code to Firebase Storage
    public void saveUserQRCode(Bitmap bitmap,Activity activity, Context context, String user_type, String id, String name, ProgressBar loading, Button btnSignUp){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        StorageReference ref = storageRef.child("QR Codes/" + user_type + "/" + name + "_" + id + ".png");

        ref.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Successfully Created", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
                Intent i = new Intent(context, log_in.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                context.startActivity(i);
                activity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.INVISIBLE);
                btnSignUp.setVisibility(View.VISIBLE);
            }
        });

    }

    public void updateQRCode(Bitmap bitmap,Activity activity, Context context, String user_type, String id, String name, ProgressBar loading, Button btnFind,Button btnUpdate){

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        StorageReference ref = storageRef.child("QR Codes/" + user_type + "/" + name + "_" + id + ".png");

        ref.putBytes(byteArray).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.INVISIBLE);
                btnFind.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.INVISIBLE);
                btnFind.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
            }
        });

    }

    public void updateName(Activity activity,String name){
        DocumentReference nameRef = db.collection("Waste Generator").document(mAuth.getUid());
        nameRef.update("name", name).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(activity, view_profile.class);
                        activity.startActivity(i);
                        activity.finish();
                        Toast.makeText(activity, "Successfully updated name!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Successfully updated name!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error updating location.", e);
                    }
                });
    }
    public void updateBarangay(Activity activity,String barangay){
        DocumentReference barangayRef = db.collection("Waste Generator").document(mAuth.getUid());
        barangayRef.update("barangay", barangay).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(activity, view_profile.class);
                        activity.startActivity(i);
                        activity.finish();
                        Toast.makeText(activity, "Successfully updated barangay!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Successfully updated barangay!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error updating barangay.", e);
                    }
                });
    }

    public void updateNumber(Activity activity,String number){
        DocumentReference numberRef = db.collection("Waste Generator").document(mAuth.getUid());
        numberRef.update("number", number).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(activity, view_profile.class);
                        activity.startActivity(i);
                        activity.finish();
                        Toast.makeText(activity, "Successfully updated number!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Successfully updated number!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error updating number.", e);
                    }
                });
    }

    public void updateEmail(Activity activity,String currentEmail, String password,String email){
        DocumentReference emailRef = db.collection("Waste Generator").document(mAuth.getUid());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(currentEmail, password);

        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            emailRef.update("email", email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Intent i = new Intent(activity, view_profile.class);
                                                            activity.startActivity(i);
                                                            activity.finish();
                                                            Toast.makeText(activity, "Successfully updated email!", Toast.LENGTH_SHORT).show();
                                                            Log.d(TAG, "Successfully updated email!");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();

                                                            Log.w(TAG, "Error updating location.", e);
                                                        }
                                                    });
                                        }else{
                                            Toast.makeText(activity, task.getException()+ "Something went wrong!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });






    }
    //Update Location
    public void updateLocation(Activity activity,String location){
        DocumentReference locationRef = db.collection("Waste Generator").document(mAuth.getUid());
        locationRef.update("location", location).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(activity, view_profile.class);
                        activity.startActivity(i);
                        activity.finish();
                        Toast.makeText(activity, "Successfully updated location!", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "Successfully updated location!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "Something went wrong!", Toast.LENGTH_SHORT).show();

                        Log.w(TAG, "Error updating location.", e);
                    }
                });
    }

    public void updateWasteStar(){
        DocumentReference locationRef = db.collection("Waste Generator").document(mAuth.getUid());
        locationRef.update("waste_game_level", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Successfully updated location!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating location.", e);
                    }
                });
    }

    public void updatePoints(Context context,double new_points){
        DocumentReference numberRef = db.collection("Waste Generator").document(mAuth.getUid());
        numberRef.update("total_points", new_points).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        PopupDialog.getInstance(context)
                                .setStyle(Styles.SUCCESS)
                                .setHeading("Success!" + "\n" + "You have successfully redeemed your reward.")
                                .setDescription("Go check your redeemed codes in your profile.")
                                .setCancelable(false)
                                .showDialog(new OnDialogButtonClickListener() {
                                    @Override
                                    public void onDismissClicked(Dialog dialog) {
                                        super.onDismissClicked(dialog);
                                        Intent i = new Intent(context, home.class);
                                        context.startActivity(i);
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PopupDialog.getInstance(context)
                                .setStyle(Styles.FAILED)
                                .setHeading("Sorry!" + "\n" + "Redeem Unsuccessful.")
                                .setDescription("Insufficient Balance.")
                                .setCancelable(false)
                                .showDialog(new OnDialogButtonClickListener() {
                                    @Override
                                    public void onDismissClicked(Dialog dialog) {
                                        super.onDismissClicked(dialog);
                                    }
                                });
                    }
                });
    }

    //Log Out
    public void logOut(Activity activity, Context context){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(context, log_in.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
        context.startActivity(i);
        activity.finish();
    }

}
