package sldevs.cdo.orokalimpyo.game_levels;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;
import sldevs.cdo.orokalimpyo.home.guess_the_brand_game;
import sldevs.cdo.orokalimpyo.home.guess_the_waste_game;
import sldevs.cdo.orokalimpyo.home.home;
import sldevs.cdo.orokalimpyo.ml.CanModel;
import sldevs.cdo.orokalimpyo.ml.CartonModel;
import sldevs.cdo.orokalimpyo.ml.CocaColaModel;
import sldevs.cdo.orokalimpyo.ml.GlassBottleModel;
import sldevs.cdo.orokalimpyo.ml.MegaSardinesModel;
import sldevs.cdo.orokalimpyo.ml.ModelUnquant;
import sldevs.cdo.orokalimpyo.ml.NatureSpringModel;
import sldevs.cdo.orokalimpyo.ml.NestleaModel;
import sldevs.cdo.orokalimpyo.ml.PvcSpoonModel;
import sldevs.cdo.orokalimpyo.ml.UnileverModel;

public class brand_game extends AppCompatActivity {
    int imageSize = 224;
    Bitmap image;
    int camera;
    String game_level;
    String stars_collected;
    TextView tvLevel,tvBrandType,tvResult;
    LottieAnimationView lWasteType, lWaiting;
    ImageView ivAnswer;

    Button btnUpload,btnHome,btnBack;
    FirebaseAuth mAuth;
    firebase_crud fc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_game);

        fc = new firebase_crud();
        mAuth = FirebaseAuth.getInstance();

        tvLevel = findViewById(R.id.tvLevel);
        tvResult = findViewById(R.id.tvResult);
        tvBrandType = findViewById(R.id.tvBrandType);

        lWaiting = findViewById(R.id.lWaiting);

        ivAnswer = findViewById(R.id.ivAnswer);

        btnUpload = findViewById(R.id.btnUpload);
        btnBack = findViewById(R.id.btnBack);
        btnHome = findViewById(R.id.btnHome);



        game_level = getIntent().getStringExtra("game_level");
        stars_collected = getIntent().getStringExtra("stars_collected");

        showGameLevel();


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(brand_game.this, guess_the_brand_game.class);
                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(brand_game.this, home.class);
                startActivity(i);
                finish();
            }
        });


        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        camera = 1;
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 3);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
            }
        });


    }
    public void showGameLevel(){
        if(game_level.equalsIgnoreCase("Level 1")){
            if(Integer.parseInt(stars_collected) > 0){
                tvBrandType.setText("Great Capture!");
                lWaiting.setAnimation(R.raw.lottie_applause);
                btnUpload.setVisibility(View.GONE);
            } else {
                tvLevel.setText("Level 1");
                tvBrandType.setText("Coca Cola");
            }


        } else if(game_level.equalsIgnoreCase("Level 2")){
            if(Integer.parseInt(stars_collected) > 1){
                tvBrandType.setText("Great Capture!");
                lWaiting.setAnimation(R.raw.lottie_applause);
                btnUpload.setVisibility(View.GONE);
            } else {
                tvLevel.setText("Level 2");
                tvBrandType.setText("Nature Spring");
            }


        } else if(game_level.equalsIgnoreCase("Level 3")){
            if(Integer.parseInt(stars_collected) > 2){
                tvBrandType.setText("Great Capture!");
                lWaiting.setAnimation(R.raw.lottie_applause);
                btnUpload.setVisibility(View.GONE);
            } else {
                tvLevel.setText("Level 3");
                tvBrandType.setText("Unilever");
            }

        } else if(game_level.equalsIgnoreCase("Level 4")){
            if(Integer.parseInt(stars_collected) > 3){
                tvBrandType.setText("Great Capture!");
                lWaiting.setAnimation(R.raw.lottie_applause);
                btnUpload.setVisibility(View.GONE);
            } else {
                tvLevel.setText("Level 4");
                tvBrandType.setText("Nestlea");
            }

        } else if(game_level.equalsIgnoreCase("Level 5")){
            if(Integer.parseInt(stars_collected) > 4){
                tvBrandType.setText("Great Capture!");
                lWaiting.setAnimation(R.raw.lottie_applause);
                btnUpload.setVisibility(View.GONE);
            } else {
                tvLevel.setText("Level 5");
                tvBrandType.setText("MEGA Sardines");
            }

        }
    }

    public void classifyBrand(Bitmap image){
        try {
            if(tvBrandType.getText().toString().equalsIgnoreCase("Coca Cola")){
                CocaColaModel model = CocaColaModel.newInstance(getApplicationContext());

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for(int i = 0; i < imageSize; i ++){
                    for(int j = 0; j < imageSize; j++){
                        int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                CocaColaModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"It is Coca Cola","I'm not so sure, Please try again"};

                String s = "";
                for(int i = 0; i < classes.length; i++){
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
                lWaiting.setVisibility(View.GONE);
                ivAnswer.setImageBitmap(image);

                tvResult.setText(classes[maxPos] + ".");



                if(classes[maxPos] == "It is Coca Cola"){
                    tvBrandType.setText("Great Capture!");
                    btnUpload.setVisibility(View.GONE);
                    fc.updateStar("brand_game_level");
                }

                model.close();

            } else if (tvBrandType.getText().toString().equalsIgnoreCase("Nature Spring")) {
                NatureSpringModel model = NatureSpringModel.newInstance(getApplicationContext());

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for(int i = 0; i < imageSize; i ++){
                    for(int j = 0; j < imageSize; j++){
                        int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                NatureSpringModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"It is Nature Spring","I'm not so sure, Please try again"};

                String s = "";
                for(int i = 0; i < classes.length; i++){
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
                lWaiting.setVisibility(View.GONE);
                ivAnswer.setImageBitmap(image);

                tvResult.setText(classes[maxPos] + ".");
//                tvResult.setText("It is a " + classes[maxPos] + "." + "\n\nConfidence Level:\n" + s);


                if(classes[maxPos] == "It is Nature Spring"){
                    tvBrandType.setText("Great Capture!");
                    btnUpload.setVisibility(View.GONE);
                    fc.updateStar("brand_game_level");
                }


                model.close();
            } else if (tvBrandType.getText().toString().equalsIgnoreCase("Unilever")) {
                UnileverModel model = UnileverModel.newInstance(getApplicationContext());

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for(int i = 0; i < imageSize; i ++){
                    for(int j = 0; j < imageSize; j++){
                        int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                UnileverModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"It is Unilever","I'm not so sure, Please try again"};

                String s = "";
                for(int i = 0; i < classes.length; i++){
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
                lWaiting.setVisibility(View.GONE);
                ivAnswer.setImageBitmap(image);

                tvResult.setText(classes[maxPos] + ".");
//                tvResult.setText("It is a " + classes[maxPos] + "." + "\n\nConfidence Level:\n" + s);


                if(classes[maxPos] == "It is Unilever"){
                    tvBrandType.setText("Great Capture!");
                    btnUpload.setVisibility(View.GONE);
                    fc.updateStar("brand_game_level");
                }

//            builder.show();
//            result.setText(classes[maxPos]);

                // Releases model resources if no longer used.
                model.close();
            } else if (tvBrandType.getText().toString().equalsIgnoreCase("Nestlea")) {
                NestleaModel model = NestleaModel.newInstance(getApplicationContext());

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for(int i = 0; i < imageSize; i ++){
                    for(int j = 0; j < imageSize; j++){
                        int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                NestleaModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"It is Nestlea","I'm not so sure, Please try again"};

                String s = "";
                for(int i = 0; i < classes.length; i++){
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
                lWaiting.setVisibility(View.GONE);
                ivAnswer.setImageBitmap(image);

                tvResult.setText(classes[maxPos] + ".");
//                tvResult.setText("It is a " + classes[maxPos] + "." + "\n\nConfidence Level:\n" + s);


                if(classes[maxPos] == "It is Nestlea"){
                    tvBrandType.setText("Great Capture!");
                    btnUpload.setVisibility(View.GONE);
                    fc.updateStar("brand_game_level");
                }


                model.close();

            } else if (tvBrandType.getText().toString().equalsIgnoreCase("MEGA Sardines")) {
                MegaSardinesModel model = MegaSardinesModel.newInstance(getApplicationContext());

                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;
                //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
                for(int i = 0; i < imageSize; i ++){
                    for(int j = 0; j < imageSize; j++){
                        int val = intValues[pixel++]; // RGB
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                    }
                }

                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                MegaSardinesModel.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences = outputFeature0.getFloatArray();
                // find the index of the class with the biggest confidence.
                int maxPos = 0;
                float maxConfidence = 0;
                for (int i = 0; i < confidences.length; i++) {
                    if (confidences[i] > maxConfidence) {
                        maxConfidence = confidences[i];
                        maxPos = i;
                    }
                }
                String[] classes = {"It is MEGA Sardines","I'm not so sure, Please try again"};

                String s = "";
                for(int i = 0; i < classes.length; i++){
                    s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
                }
                lWaiting.setVisibility(View.GONE);
                ivAnswer.setImageBitmap(image);

                tvResult.setText(classes[maxPos] + ".");
//                tvResult.setText("It is a " + classes[maxPos] + "." + "\n\nConfidence Level:\n" + s);


                if(classes[maxPos] == "It is a PVC Spoon"){
                    tvBrandType.setText("Great Capture!");
                    btnUpload.setVisibility(View.GONE);
                    fc.updateStar("brand_game_level");
                }

                model.close();
            }

            // Creates inputs for reference.

        } catch (IOException e) {
            // TODO Handle the exception
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(camera == 1){
                if(requestCode == 3){

                    image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyBrand(image);
                    camera = 0;
                }else{
                    Uri dat = data.getData();
                    image = null;
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyBrand(image);
                    camera = 0;
                }
            }else if(camera == 2) {
                if(requestCode == 4) {

                    image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                    classifyBrand(image);
                    camera = 0;
                } else {
                    Uri dat = data.getData();
                    image = null;
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
//                    classifyBrand(image);
                    camera = 0;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(brand_game.this, guess_the_brand_game.class);
        startActivity(i);

    }
}