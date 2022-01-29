package com.helloworld.uifirs222.ui.main;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.helloworld.uifirs222.MainActivity;
import com.helloworld.uifirs222.R;
import com.helloworld.uifirs222.Tutorial;
import com.helloworld.uifirs222.Verifier;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.os.Environment.getExternalStoragePublicDirectory;


/**
 * A simple {@link Fragment} subclass.
 */
public class ACCCfragg extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    String pathtfile;
    CardView carcard,firecard, dangercard,uploadcard,sendcard, ambulancecard ;
    EditText injuryEdit;
    DatabaseReference dref;
    ImageView pic1,pic2;
    TextView inhu;
    Uri photouri,photouri2,imageuri;
    ProgressBar prog;
    Uri pathtfilee = null;

    Location Alfouad,Rehan,Qasr,Demrdash,Cleopatra,DarElFouad,
            MahmoudSpecial,Dreamland,medicalcenteroctober,CityClinic,Alwaha,OctoberMilitary,AsiaClinics,ElShoroukHospital,
            SphinxSpecialized,Alharam,Tabarak,OctoberClinic,AlMehwar,
            Wafa,Salam,MaadiMilitary,QueenClinic,Sudan,ShifaSpecialist,Helal,
            Mohamady,Miswsud,Pola,zohoor,Horus,Ainshams;

    String injuries,Type;
    double nearest;
    double[] target;
    double[] prevdisreportedlistarray;
    private StorageReference mStorageRef;
    final String Names []={
            "Al-Fouad","Rehan","Elqasr Eleiny","Demerdash",
            "Cleopatra","Dar ElFouad ","Mahmoud ","Dreamland ","October Medical center "
            ,"City Clinic","Al-waha ","October ","Asia Clinic","Al-Shorouk ",
            "Sphinx","Al-haram ","Tabarak ","October ","Al-Mehwar",
            "Al-Wafa","Al-Salam ","Maadi","Queen Clinic","Al-Sudan",
            "Al-Shifaa","Al-Helal","Al-Mohamady","Miswsud ","Saint Pola",
            "Al-zohoor","Horus ","Ainshams "};

    final String Cars []={
            "Car1","Car2","Car3","Car4","Car5","Car6","Car7",
            "Car8","Car9","Car10","Car11","Car12","Car13","Car14",
            "Car15","Car16","Car17","Car18","Car19","Car20","Car21",
            "Car22","Car23","Car24","Car25","Car26","Car27","Car28",
            "Car29","Car30"};
    int x;

    ArrayList<String> mylist = new ArrayList<String>();
    ArrayList<Double> mylistdouble = new ArrayList<>();
    ArrayList<Double> prevdisreportedlist = new ArrayList<>();




    public ACCCfragg() {
        // Required empty public constructor
    }

    public static final String TAG= Hospitalfrag.class.getSimpleName();
    private  static int PLAY_SERVICE_RESOLUTION_REQUEST=1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiCleint;


    private LocationRequest mLocationReq;

    public static final String PREFS_NAME ="30" ;


    public final static int REQUEST_CODE = 10101;

    private Uri imageToUploadUri;
    Dialog dialog;
    int requestCode;
    int resultCod;
    Intent data;
    Bitmap captureimage;
    TextView textView;
    boolean xxxxxxx = false;
    int photonum,photonum2;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View v = inflater.inflate(R.layout.fragment_acccfragg, container, false);
        carcard= v.findViewById(R.id.car_card);
        firecard=v.findViewById(R.id.fire_card);
        dangercard=v.findViewById(R.id.person_card);
        ambulancecard=v.findViewById(R.id.ambulance_card);
        dref = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        buildGoogleApiClient();
        createLocationRequest();
        {
            Alfouad
                    = new Location("");
            Alfouad.setLatitude(30.0785468);
            Alfouad.setLongitude(31.2765421);

            Rehan = new Location("");
            Rehan.setLatitude(29.992168);
            Rehan.setLongitude(31.166988);

            Qasr = new Location("");
            Qasr.setLatitude(30.0262814);
            Qasr.setLongitude(31.234475);

            Demrdash = new Location("");
            Demrdash.setLatitude(30.0781932);
            Demrdash.setLongitude(31.2756256);

            Cleopatra = new Location("");
            Cleopatra.setLatitude(30.0929476);
            Cleopatra.setLongitude(31.3299626);



            DarElFouad = new Location("");
            DarElFouad.setLatitude(29.9973825);
            DarElFouad.setLongitude(30.9652677);

            MahmoudSpecial = new Location("");
            MahmoudSpecial.setLatitude(30.0417599);
            MahmoudSpecial.setLongitude(31.0937094);

            Dreamland = new Location("");
            Dreamland.setLatitude(29.9699343);
            Dreamland.setLongitude(31.0344061);

            medicalcenteroctober = new Location("");
            medicalcenteroctober.setLatitude(29.9343231);
            medicalcenteroctober.setLongitude(30.9769704);

            CityClinic = new Location("");
            CityClinic .setLatitude(29.9486387);
            CityClinic .setLongitude(31.0360332);

            Alwaha = new Location("");
            Alwaha.setLatitude(29.9323505);
            Alwaha.setLongitude(31.0132771);

            OctoberMilitary = new Location("");
            OctoberMilitary.setLatitude(29.9323505);
            OctoberMilitary.setLongitude(31.0132771);

            AsiaClinics = new Location("");
            AsiaClinics .setLatitude(29.9323505);
            AsiaClinics .setLongitude(31.0132771);

            ElShoroukHospital = new Location("");
            ElShoroukHospital .setLatitude(29.9323135);
            ElShoroukHospital .setLongitude(31.0132769);

            SphinxSpecialized = new Location("");
            SphinxSpecialized.setLatitude(29.9322764);
            SphinxSpecialized.setLongitude(31.0132767);

            Alharam = new Location("");
            Alharam.setLatitude(29.9322394);
            Alharam.setLongitude(31.0132765);

            Tabarak = new Location("");
            Tabarak.setLatitude(29.9941414);
            Tabarak.setLongitude(31.1460766);

            OctoberClinic = new Location("");
            OctoberClinic .setLatitude(29.9705175);
            OctoberClinic .setLongitude(30.9389081);

            AlMehwar = new Location("");
            AlMehwar.setLatitude(30.0094253 );
            AlMehwar.setLongitude(30.9950951);

            Wafa = new Location("");
            Wafa.setLatitude(29.9732546);
            Wafa.setLongitude(31.1812977);

            Salam = new Location("");
            Salam.setLatitude(30.0266682);
            Salam.setLongitude(31.1817866);

            MaadiMilitary = new Location("");
            MaadiMilitary .setLatitude(30.0502055);
            MaadiMilitary .setLongitude(31.2070936);

            QueenClinic = new Location("");
            QueenClinic .setLatitude(29.9587115);
            QueenClinic .setLongitude(31.2495729 );



            Sudan = new Location("");
            Sudan.setLatitude(30.0737429);
            Sudan.setLongitude(31.1996323);

            ShifaSpecialist = new Location("");
            ShifaSpecialist .setLatitude(30.1421833);
            ShifaSpecialist .setLongitude(31.3100228);

            Helal = new Location("");
            Helal .setLatitude(30.1422016);
            Helal .setLongitude(31.3201765);

            Mohamady = new Location("");
            Mohamady.setLatitude(30.1454433);
            Mohamady.setLongitude(31.3185523);

            Miswsud = new Location("");
            Miswsud.setLatitude(30.0786569);
            Miswsud.setLongitude(31.2746879);

            Pola = new Location("");
            Pola.setLatitude(30.079702);
            Pola.setLongitude(31.2817742);

            zohoor = new Location("");
            zohoor.setLatitude(30.1454433);
            zohoor.setLongitude(31.3185523);

            Horus = new Location("");
            Horus.setLatitude(30.0807093);
            Horus.setLongitude(31.2823762);

            Ainshams = new Location("");
            Ainshams.setLatitude(30.0734448);
            Ainshams.setLongitude(31.2780994);
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
// camera Permission
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.CAMERA},100);
        }
//camera open

        //getting the capture image and setting it into an image view


        carcard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                editor.remove("photonumber");
                editor.remove("maxtwo");

                editor.commit();



                dialog = new Dialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.accident_dialouge);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pic1= dialog.findViewById(R.id.pic1);
                inhu=dialog.findViewById(R.id.injury_txt);
                pic2 =dialog.findViewById(R.id.pic2);
                dialog.show();




                uploadcard=dialog.findViewById(R.id.upload_card);
                sendcard=dialog.findViewById(R.id.send_card);
                injuryEdit=dialog.findViewById(R.id.injury_edit);

                sendcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckPlayService()){
                            injuries=injuryEdit.getText().toString().trim();
                            Type="Car accident";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            editor.remove("maxtwo");

                            editor.remove("photonumber");
                            editor.commit();
                            StartLocationUpdate();}}
                });
                if(mGoogleApiCleint!=null);{
                    mGoogleApiCleint.connect();

                }
                CheckPlayService();



                final SharedPreferences storage = getContext().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                final SharedPreferences.Editor editor = storage.edit();



                uploadcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(storage.contains("maxtwo")){
                            Toast.makeText(getContext(), "Can't upload more photos", Toast.LENGTH_SHORT).show();
                        }else{
                        photonum =storage.getInt("photonumber", 0)+1;
                        editor.putInt("photonumber", photonum);
                        photonum2 =storage.getInt("photonumber2", 0)+1;
                        editor.putInt("photonumber2", photonum2);
                        editor.commit();
                        takepicture();
                    }}
                });



            }






        });



        dangercard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                editor.remove("photonumber");
                editor.remove("maxtwo");

                editor.commit();



                dialog = new Dialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.accident_dialouge);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pic1= dialog.findViewById(R.id.pic1);
                inhu=dialog.findViewById(R.id.injury_txt);
                pic2 =dialog.findViewById(R.id.pic2);
                TextView injury_txt;
                injury_txt=dialog.findViewById(R.id.injury_txt);
                injury_txt.setText("Can you add any details?");
                uploadcard=dialog.findViewById(R.id.upload_card);
                sendcard=dialog.findViewById(R.id.send_card);
                injuryEdit=dialog.findViewById(R.id.injury_edit);
                injuryEdit.setInputType(1);
                dialog.show();






                sendcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckPlayService()){
                            injuries=injuryEdit.getText().toString().trim();
                            Type="Individual harm";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            editor.remove("maxtwo");

                            editor.remove("photonumber");
                            editor.commit();
                            StartLocationUpdate();}}
                });
                if(mGoogleApiCleint!=null);{
                    mGoogleApiCleint.connect();

                }
                CheckPlayService();



                final SharedPreferences storage = getContext().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                final SharedPreferences.Editor editor = storage.edit();



                uploadcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(storage.contains("maxtwo")){
                            Toast.makeText(getContext(), "Can't upload more photos", Toast.LENGTH_SHORT).show();
                        }else{
                            photonum =storage.getInt("photonumber", 0)+1;
                            editor.putInt("photonumber", photonum);
                            photonum2 =storage.getInt("photonumber2", 0)+1;
                            editor.putInt("photonumber2", photonum2);
                            editor.commit();
                            takepicture();
                        }}
                });



            }






        });


        firecard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                editor.remove("photonumber");
                editor.remove("maxtwo");

                editor.commit();



                dialog = new Dialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.accident_dialouge);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pic1= dialog.findViewById(R.id.pic1);
                inhu=dialog.findViewById(R.id.injury_txt);
                pic2 =dialog.findViewById(R.id.pic2);
                dialog.show();




                uploadcard=dialog.findViewById(R.id.upload_card);
                sendcard=dialog.findViewById(R.id.send_card);
                injuryEdit=dialog.findViewById(R.id.injury_edit);

                sendcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckPlayService()){
                            injuries=injuryEdit.getText().toString().trim();
                            Type="Fire";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            editor.remove("maxtwo");

                            editor.remove("photonumber");
                            editor.commit();
                            StartLocationUpdate();}}
                });
                if(mGoogleApiCleint!=null);{
                    mGoogleApiCleint.connect();

                }
                CheckPlayService();



                final SharedPreferences storage = getContext().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                final SharedPreferences.Editor editor = storage.edit();



                uploadcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(storage.contains("maxtwo")){
                            Toast.makeText(getContext(), "Can't upload more photos", Toast.LENGTH_SHORT).show();
                        }else{
                            photonum =storage.getInt("photonumber", 0)+1;
                            editor.putInt("photonumber", photonum);
                            photonum2 =storage.getInt("photonumber2", 0)+1;
                            editor.putInt("photonumber2", photonum2);
                            editor.commit();
                            takepicture();
                        }}
                });


            }
        });

        ambulancecard.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                editor.remove("photonumber");
                editor.remove("maxtwo");

                editor.commit();



                dialog = new Dialog(getContext());
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.accident_dialouge);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pic1= dialog.findViewById(R.id.pic1);
                inhu=dialog.findViewById(R.id.injury_txt);


                uploadcard=dialog.findViewById(R.id.upload_card);
                sendcard=dialog.findViewById(R.id.send_card);
                injuryEdit=dialog.findViewById(R.id.injury_edit);
                pic2 =dialog.findViewById(R.id.pic2);
                TextView injury_txt;
                injury_txt=dialog.findViewById(R.id.injury_txt);
                injury_txt.setText("What's wrong?");
                injuryEdit.setInputType(1);
                dialog.show();




                uploadcard=dialog.findViewById(R.id.upload_card);
                sendcard=dialog.findViewById(R.id.send_card);
                injuryEdit=dialog.findViewById(R.id.injury_edit);

                sendcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (CheckPlayService()){
                            injuries=injuryEdit.getText().toString().trim();
                            Type="Domestic issue";
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            editor.remove("maxtwo");
                            editor.remove("photonumber");
                            editor.commit();
                            StartLocationUpdate();}}
                });
                if(mGoogleApiCleint!=null);{
                    mGoogleApiCleint.connect();

                }
                CheckPlayService();



                final SharedPreferences storage = getContext().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
                final SharedPreferences.Editor editor = storage.edit();



                uploadcard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(storage.contains("maxtwo")){
                            Toast.makeText(getContext(), "Can't upload more photos", Toast.LENGTH_SHORT).show();
                        }else{
                            photonum =storage.getInt("photonumber", 0)+1;
                            editor.putInt("photonumber", photonum);
                            photonum2 =storage.getInt("photonumber2", 0)+1;
                            editor.putInt("photonumber2", photonum2);
                            editor.commit();
                            takepicture();
                        }}
                });



            }
        });



        return v;
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr=getActivity().getApplicationContext().getContentResolver();
                MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Uploadfile() {

    }



        //create file forhte picture of hte dialoouge
    private File createphoto() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String phtoname = "JPEG_" + timestamp + "_";
        File storagedir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image= null;

        try {
            image = File.createTempFile(phtoname, ".jpg",storagedir);
        } catch (IOException e) {
            Toast.makeText(getContext()  , "Wrong TXT", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        pathtfile = image.getAbsolutePath();

        return image;
    }

    //take the picture
    private void takepicture() {
        Intent inttt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (inttt.resolveActivity(getActivity().getPackageManager()) != null ){
            File photo = null;

            try {

                photo = createphoto();

            } catch (Exception e) {
                Toast.makeText(getContext(), "WrongTXT@", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
            final SharedPreferences.Editor editor = storage.edit();

            if(storage.getInt("fromtakepic",0)==0){
                photouri = FileProvider.getUriForFile(getContext(), "com.helloworld.uifirs222.fileprovider", photo);

                editor.putInt("fromtakepic",1);
                editor.commit();
                inttt.putExtra(MediaStore.EXTRA_OUTPUT, photouri);
            }
            else if(storage.getInt("fromtakepic",0)==1){
                photouri2 = FileProvider.getUriForFile(getContext(), "com.helloworld.uifirs222.fileprovider", photo);
                inttt.putExtra(MediaStore.EXTRA_OUTPUT, photouri2);

                editor.putInt("maxtwo",1);

                editor.remove("fromtakepic");
                editor.commit();
            }

            startActivityForResult(inttt, photonum);
        }else{
            Toast.makeText(getContext(), "no photo 1 found", Toast.LENGTH_SHORT).show();
        } }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];
        if(resultCode==RESULT_OK){

            Bitmap bitmapp = BitmapFactory.decodeFile(pathtfile,options);
            if(requestCode==1) {


                ExifInterface ei = null;
                try {
                    ei = new ExifInterface(pathtfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmapp, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmapp, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmapp, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmapp;
                }

                pic1.setImageBitmap(rotatedBitmap);

                Toast.makeText(getContext(), "photo 1 set successfully", Toast.LENGTH_SHORT).show();
            } if(requestCode==2) {


                ExifInterface ei = null;
                try {
                    ei = new ExifInterface(pathtfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap rotatedBitmap = null;
                switch(orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(bitmapp, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(bitmapp, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(bitmapp, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = bitmapp;
                }

                pic2.setImageBitmap(rotatedBitmap);
            }
        }

     //   Toast.makeText(getContext(), "this from result"+ String.valueOf(photonum), Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onResume() {
        super.onResume();

        mGoogleApiCleint.connect();
        CheckPlayService();


    }

    @Override
    public void onStop() {
        super.onStop();
        if(mGoogleApiCleint.isConnected()){
            mGoogleApiCleint.disconnect();
        }
    }



    private boolean CheckPlayService(){
        int resutlcode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());

        if (resutlcode!=ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resutlcode)){
                GooglePlayServicesUtil.getErrorDialog(resutlcode,getActivity(),PLAY_SERVICE_RESOLUTION_REQUEST).show();
                Toast.makeText(getContext(), "from checkplay", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getContext(), "this device is not supported", Toast.LENGTH_LONG).show();

            }
            return false;
        }
        return true;
    }
    protected synchronized void buildGoogleApiClient(){
        mGoogleApiCleint= new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }
    protected void createLocationRequest(){
        mLocationReq=new LocationRequest();
        mLocationReq.setInterval(5000);
        mLocationReq.setFastestInterval(1000);
        mLocationReq.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationReq.setSmallestDisplacement(1);
        mLocationReq.setNumUpdates(1);

    }
    protected void StartLocationUpdate(){
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiCleint,mLocationReq,this);
        }
    }




    public static double getMin(double[] inputArray){
        double minValue = inputArray[0];
        for(int i=1;i<inputArray.length;i++){
            if(inputArray[i] < minValue){
                minValue = inputArray[i];
            }}
        return minValue;}





    @Override
    public void onConnected(Bundle bundle) {
    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(getContext(), "Suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(getContext(), "failed to connect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        final Dialog dialogup = new Dialog(getContext());
        dialogup.setCanceledOnTouchOutside(false);
        dialogup.setContentView(R.layout.upload_dialog);
        prog=dialogup.findViewById(R.id.prog);
        textView=dialogup.findViewById(R.id.uploading);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
        dialogup.getWindow().setLayout((6 * width)/7, (3 * height)/12);

        mLastLocation=location;
        final Location me
                =new Location("");
        me.setLatitude(mLastLocation.getLatitude());
        me.setLongitude(mLastLocation.getLongitude());
        final double Alfouaddis = Math.round((me.distanceTo(Alfouad)/1000)*100.0)/100.0;
        final double Rehandis =  Math.round((me.distanceTo(Rehan)/1000)*100.0)/100.0;
        final double Qasrdis = Math.round((me.distanceTo(Qasr)/1000)*100.0)/100.0;
        final double Demdis = Math.round((me.distanceTo(Demrdash)/1000)*100.0)/100.0;
        final double Salamdis = Math.round((me.distanceTo(Salam )/1000)*100.0)/100.0;
        final double MaadiMilitarydis = Math.round((me.distanceTo(MaadiMilitary)/1000)*100.0)/100.0;
        final double Dreamlanddis = Math.round((me.distanceTo(Dreamland)/1000)*100.0)/100.0;
        final double Cleopatradis = Math.round((me.distanceTo(Cleopatra)/1000)*100.0)/100.0;
        final double medicalcenteroctoberdis = Math.round((me.distanceTo(medicalcenteroctober)/1000)*100.0)/100.0;
        final double CityClinicdis = Math.round((me.distanceTo(CityClinic)/1000)*100.0)/100.0;
        final double Alwahadis = Math.round((me.distanceTo(Alwaha)/1000)*100.0)/100.0;
        final double OctoberMilitarydis = Math.round((me.distanceTo(OctoberMilitary)/1000)*100.0)/100.0;
        final double AsiaClinicsdis = Math.round((me.distanceTo(AsiaClinics)/1000)*100.0)/100.0;
        final double ElShoroukHospitaldis = Math.round((me.distanceTo(ElShoroukHospital)/1000)*100.0)/100.0;
        final double SphinxSpecializeddis = Math.round((me.distanceTo(SphinxSpecialized)/1000)*100.0)/100.0;
        final double Alharamdis = Math.round((me.distanceTo(Alharam)/1000)*100.0)/100.0;
        final double Tabarakdis = Math.round((me.distanceTo(Tabarak)/1000)*100.0)/100.0;
        final double OctoberClinicdis = Math.round((me.distanceTo(OctoberClinic)/1000)*100.0)/100.0;
        final double AlMehwardis = Math.round((me.distanceTo(AlMehwar)/1000)*100.0)/100.0;
        final double Wafadis = Math.round((me.distanceTo(Wafa)/1000)*100.0)/100.0;
        final double Sudandis = Math.round((me.distanceTo(Sudan )/1000)*100.0)/100.0;
        final double ShifaSpecialistdis = Math.round((me.distanceTo(ShifaSpecialist  )/1000)*100.0)/100.0;
        final double Mohamadydis = Math.round((me.distanceTo(Mohamady  )/1000)*100.0)/100.0;
        final double Miswsuddis = Math.round((me.distanceTo(Miswsud  )/1000)*100.0)/100.0;
        final double zohoordis = Math.round((me.distanceTo(zohoor   )/1000)*100.0)/100.0;
        final double Horusdis = Math.round((me.distanceTo(Horus   )/1000)*100.0)/100.0;
        final double Poladis = Math.round((me.distanceTo(Pola  )/1000)*100.0)/100.0;
        final double Ainshamsdis = Math.round((me.distanceTo(Ainshams  )/1000)*100.0)/100.0;
        final double DarElFouaddis = Math.round((me.distanceTo(DarElFouad   )/1000)*100.0)/100.0;
        final double MahmoudSpecialdis = Math.round((me.distanceTo(MahmoudSpecial  )/1000)*100.0)/100.0;
        final double QueenClinicdis = Math.round((me.distanceTo(QueenClinic  )/1000)*100.0)/100.0;
        final double Helaldis = Math.round((me.distanceTo(Helal  )/1000)*100.0)/100.0;

        final double Distances[]={Alfouaddis,Rehandis,Qasrdis,Demdis,Cleopatradis,DarElFouaddis,
                MahmoudSpecialdis,Dreamlanddis,medicalcenteroctoberdis,CityClinicdis,Alwahadis,OctoberMilitarydis
                ,AsiaClinicsdis,ElShoroukHospitaldis,
                SphinxSpecializeddis,Alharamdis,Tabarakdis,OctoberClinicdis,
                AlMehwardis,Wafadis,Salamdis,MaadiMilitarydis,QueenClinicdis,Sudandis,ShifaSpecialistdis,Helaldis,Mohamadydis
                ,Miswsuddis,Poladis,zohoordis,Horusdis,Ainshamsdis};

        nearest= getMin(Distances);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        int i;
        for(i=0;i<Distances.length;i++){
            if(Distances[+i]==nearest){

                // Read from the database
                final int finalI = i;
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        prevdisreportedlist.removeAll(prevdisreportedlist);

                        final Location Locations[]={
                                Alfouad,Rehan,Qasr,Demrdash,Cleopatra,DarElFouad,
                                MahmoudSpecial,Dreamland,medicalcenteroctober,CityClinic,Alwaha,OctoberMilitary,AsiaClinics,ElShoroukHospital,
                                SphinxSpecialized,Alharam,Tabarak,OctoberClinic,AlMehwar,
                                Wafa,Salam,MaadiMilitary,QueenClinic,Sudan,ShifaSpecialist,Helal,
                                Mohamady,Miswsud,Pola,zohoor,Horus,Ainshams};
                        mylistdouble.removeAll(mylistdouble);
                        mylist.removeAll(mylist);

                        editor.putString("accidentNum", String.valueOf(dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents").getChildrenCount()));
                        editor.commit();

                        int accidentNumint = Integer.parseInt(String.valueOf(dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents").getChildrenCount()));
                        final String numberofaccident=String.valueOf(dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents").getChildrenCount());

                        if (accidentNumint == 0) {

                            editor.putBoolean("Send", true);
                            editor.commit();

                            Calendar calendar =Calendar.getInstance();
                            SimpleDateFormat format = new SimpleDateFormat("hh:mm aaa");
                            String accidenttime= format.format(calendar.getTime());

                            DatabaseReference myreftime = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Time");
                            myreftime .setValue(accidenttime);
                            DatabaseReference myRefuser = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/user");
                            myRefuser.setValue(storage.getString("User number","00"));

                            DatabaseReference myRefx = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Latitude");
                            myRefx.setValue(me.getLatitude());
                            DatabaseReference myRefy = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Longitude");
                            myRefy.setValue(me.getLongitude());

                            DatabaseReference myRefinjrey = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Injuries");
                            myRefinjrey.setValue(injuries);
                            DatabaseReference myReftype = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Type");
                            myReftype .setValue(Type);
                            DatabaseReference Distance = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Distance");
                            Distance .setValue(nearest);


                            dref = FirebaseDatabase.getInstance().getReference();
                            mStorageRef = FirebaseStorage.getInstance().getReference();


                            if (photouri != null&&photouri2!=null) {

dialogup.show();
                                final StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1");
                                reference.putFile(photouri)
                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1").
                                                setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));

                                  /*      reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Uri downloadUrl = uri;
                                                //Toast.makeText(getContext(), String.valueOf(downloadUrl), Toast.LENGTH_SHORT).show();
                                            }

                                        });*/


                                        dialogup.show();
                                            StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2");
                                            reference.putFile(photouri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    prog.setProgress(0);
                                                    textView.setText("");
                                                    dialogup.dismiss();
                                                    dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2").setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));

                                                    final Handler handler = new Handler();
                                                    handler.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            dialog.dismiss();
                                                        }
                                                    }, 2000);
                                                    File mydir = getActivity().getFilesDir().getAbsoluteFile();
                                                    //get your internal directory
                                                    File myFile = new File(mydir, Environment.DIRECTORY_PICTURES);
                                                    myFile.delete();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialogup.dismiss();
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    if(taskSnapshot.getTotalByteCount()!=0){
                                                    double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                    prog.setProgress((int) progress);
                                                    textView.setText("Uploading Image 2"+" "+"("+String.valueOf((int) progress)+"%)");}

                                                }
                                            });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogup.dismiss();
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        if(taskSnapshot.getTotalByteCount()!=0){
                                        double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                        prog.setProgress((int) progress);
                                        textView.setText("Uploading Image 1"+" "+"("+String.valueOf((int) progress)+"%)");}
                                    }
                                });
                            }else if (photouri != null&&photouri2 == null) {

                                dialogup.show();
                                StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1");
                                reference.putFile(photouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        prog.setProgress(0);
                                        textView.setText("");
                                        dialogup.dismiss();
                                        Toast.makeText(getContext(), "Uploaded sucessfully", Toast.LENGTH_SHORT).show();
                                        dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1"
                                        ).setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));


                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        if(taskSnapshot.getTotalByteCount()!=0){
                                        double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                        prog.setProgress((int) progress);
                                        textView.setText("Uploading Image 1"+" "+"("+String.valueOf((int) progress)+"%)");}
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialogup.dismiss();

                                    }
                                });
                            }else if(photouri == null&&photouri2 != null){
                                dialogup.show();
                                StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2");
                                reference.putFile(photouri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        prog.setProgress(0);
                                        textView.setText("");
                                        dialogup.dismiss();
                                        Toast.makeText(getContext(), "Uploaded sucessfully", Toast.LENGTH_SHORT).show();
                                        dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2"
                                        ).setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));


                                    }
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                        if(taskSnapshot.getTotalByteCount()!=0){ double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                        prog.setProgress((int) progress);
                                        textView.setText("Uploading Image 2"+" "+"("+String.valueOf((int) progress)+"%)");}
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialogup.dismiss();

                                    }
                                });
                            }

                            for(int p=0;p<Cars.length;p++){

                                if(dataSnapshot.child("Ambulance/" + Cars[+p]+ "/Availability").exists()){
                                    if(dataSnapshot.child("Ambulance/" + Cars[+p] + "/Availability").getValue().toString().equals("True")){

                                        if (dataSnapshot.child("Ambulance/" + Cars[+p] + "/Latitude").exists() &&
                                                dataSnapshot.child("Ambulance/" + Cars[+p] + "/Longitude").exists()) {
                                            Location loc = new Location("");
                                            loc.setLatitude(Double.parseDouble(dataSnapshot.
                                                    child("Ambulance/" + Cars[+p] + "/Latitude").getValue().toString()));
                                            loc.setLongitude(Double.parseDouble(dataSnapshot.
                                                    child("Ambulance/" + Cars[+p] + "/Longitude").getValue().toString()));
                                            double distancecar = me.distanceTo(loc);
                                            mylistdouble.add(distancecar);
                                            mylist.add(Cars[+p]);

                                        }}}

                            }

                            if(mylistdouble.size()!=0){

                                target = new double[mylistdouble.size()];
                                for (int o = 0; o < target.length; o++) {
                                    target[o] = mylistdouble.get(o);
                                }

                            }


                            if(target!=null&&mylist.size()!=0){
                                double nearestCar=getMin(target);
                                for(int oo=0;oo<target.length;oo++){
                                    if(target[+oo]==nearestCar) {
                                        //  Toast.makeText(getContext(), String.valueOf(nearestCar)+"...."+mylist.get(oo), Toast.LENGTH_SHORT).show();

                                        //  Toast.makeText(getContext(), mylist.get(oo), Toast.LENGTH_SHORT).show();
                                        editor.putString("NearestCar",mylist.get(oo));
                                        editor.putString("NearestCarDistance",String.valueOf(nearestCar));

                                        DatabaseReference myRefusercar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Reporter Number");
                                        myRefusercar.setValue(storage.getString("User number", "00"));

                                        DatabaseReference myRefxcar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Latitude");
                                        myRefxcar.setValue(me.getLatitude());
                                        DatabaseReference myRefycar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Longitude");
                                        myRefycar.setValue(me.getLongitude());

                                        DatabaseReference myRefinjreycar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Injuries");
                                        myRefinjreycar.setValue(injuries);
                                        DatabaseReference myReftypecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Type");
                                        myReftypecar.setValue(Type);
                                        DatabaseReference Distancecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Distance");
                                        Distancecar.setValue(nearestCar);

                                        DatabaseReference Availablecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Availability");
                                        Location nearesthosp = Locations[+finalI];

                                        DatabaseReference NearestHospName = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Name");
                                        NearestHospName.setValue(Names[+finalI]);
                                        DatabaseReference NearestHospLocLat= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Location/Latitude");
                                        NearestHospLocLat.setValue(nearesthosp.getLatitude());
                                        DatabaseReference NearestHospLocLong= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Location/Longitude");
                                        NearestHospLocLong.setValue(nearesthosp.getLongitude());

                                        DatabaseReference NearestHospId= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Id");
                                        NearestHospId.setValue("Accident"+accidentNumint);
                                        Availablecar.setValue("False");
                                        editor.putBoolean("SendCar", false);
                                        editor.commit();


                                      /*  Toast.makeText(getContext(), storage.getString("NearestCar","pp")+".."+
                                                        storage.getString("NearestCarDistance","pp")+""+String.valueOf(storage.getBoolean("Sendcar",false))
                                                , Toast.LENGTH_LONG).show();*/
                                    }
                                }}
                        }

                        else{
                            for (int i = 0; i < accidentNumint; i++) {
                                if (accidentNumint != 0) {
                                    if (dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents/" + "Accident" + String.valueOf(i) + "/Latitude").exists()&&
                                            dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents/" + "Accident" + String.valueOf(i) + "/Longitude")
                                                    .exists()) {
                                        String Lat = dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents/" + "Accident" + String.valueOf(i) + "/Latitude")
                                                .getValue().toString();
                                        String Long = dataSnapshot.child("Hospitals/" + Names[+finalI] + "/Accidents/" + "Accident" + String.valueOf(i) + "/Longitude")
                                                .getValue().toString();
                                        double Latdouble = Double.parseDouble(Lat);
                                        double Longdouble = Double.parseDouble(Long);

                                        Location prevoius = new Location("");
                                        prevoius.setLatitude(Latdouble);
                                        prevoius.setLongitude(Longdouble);
                                        double distance = me.distanceTo(prevoius);
                                        prevdisreportedlist.add(distance);

                                        if(prevdisreportedlist.size()!=0){
                                            prevdisreportedlistarray = new double[prevdisreportedlist.size()];
                                            for (int o = 0; o < prevdisreportedlistarray.length; o++) {
                                                prevdisreportedlistarray[o] = prevdisreportedlist.get(o);}}

                                    }}}
                            if(prevdisreportedlist.size()!=0){
                                double Smallestdis=getMin(prevdisreportedlistarray);
                                if(prevdisreportedlistarray!=null&&prevdisreportedlist.size()!=0){
                                    if(Smallestdis>500) {                            editor.putBoolean("Send", true);
                                        editor.commit();

                                        Calendar calendar =Calendar.getInstance();
                                        SimpleDateFormat format = new SimpleDateFormat("hh:mm aaa");
                                        String accidenttime= format.format(calendar.getTime());
                                        DatabaseReference myreftime = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Time");
                                        myreftime .setValue(accidenttime);
                                        DatabaseReference myRefuser = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/user");
                                        myRefuser.setValue(storage.getString("User number","00"));

                                        DatabaseReference myRefx = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Latitude");
                                        myRefx.setValue(me.getLatitude());
                                        DatabaseReference myRefy = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Longitude");
                                        myRefy.setValue(me.getLongitude());

                                        DatabaseReference myRefinjrey = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Injuries");
                                        myRefinjrey.setValue(injuries);
                                        DatabaseReference myReftype = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Type");
                                        myReftype .setValue(Type);
                                        DatabaseReference Distance = database.getReference("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Distance");
                                        Distance .setValue(nearest);


                                        dref = FirebaseDatabase.getInstance().getReference();
                                        mStorageRef = FirebaseStorage.getInstance().getReference();


                                        if (photouri != null&&photouri2!=null) {

                                            dialogup.show();
                                            final StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1");
                                            reference.putFile(photouri)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                            dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1").
                                                                    setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));

                                  /*      reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Uri downloadUrl = uri;
                                                //Toast.makeText(getContext(), String.valueOf(downloadUrl), Toast.LENGTH_SHORT).show();
                                            }

                                        });*/


                                                            dialogup.show();
                                                            StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2");
                                                            reference.putFile(photouri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    prog.setProgress(0);
                                                                    textView.setText("");
                                                                    dialogup.dismiss();
                                                                    dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2").setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));

                                                                    final Handler handler = new Handler();
                                                                    handler.postDelayed(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            dialog.dismiss();
                                                                        }
                                                                    }, 2000);
                                                                    File mydir = getActivity().getFilesDir().getAbsoluteFile();
                                                                    //get your internal directory
                                                                    File myFile = new File(mydir, Environment.DIRECTORY_PICTURES);
                                                                    myFile.delete();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    dialogup.dismiss();
                                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                                    if(taskSnapshot.getTotalByteCount()!=0){
                                                                    double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                                    prog.setProgress((int) progress);
                                                                    textView.setText("Uploading Image 2"+" "+"("+String.valueOf((int) progress)+"%)");}

                                                                }
                                                            });
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialogup.dismiss();
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    if(taskSnapshot.getTotalByteCount()!=0){   double progress= (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                                                    prog.setProgress((int) progress);
                                                    textView.setText("Uploading Image 1"+" "+"("+String.valueOf((int) progress)+"%)");}
                                                }
                                            });
                                        }else if (photouri != null&&photouri2 == null) {

                                            dialogup.show();
                                            StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1");
                                            reference.putFile(photouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    prog.setProgress(0);
                                                    textView.setText("");
                                                    dialogup.dismiss();
                                                    Toast.makeText(getContext(), "Uploaded sucessfully", Toast.LENGTH_SHORT).show();
                                                    dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1"
                                                    ).setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialogup.dismiss();

                                                }
                                            });
                                        }else if(photouri == null&&photouri2 != null){
                                            dialogup.show();
                                            StorageReference reference = mStorageRef.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2");
                                            reference.putFile(photouri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    prog.setProgress(0);
                                                    textView.setText("");
                                                    dialogup.dismiss();
                                                    Toast.makeText(getContext(), "Uploaded sucessfully", Toast.LENGTH_SHORT).show();
                                                    dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image2"
                                                    ).setValue(String.valueOf(taskSnapshot.getMetadata().getReference().getDownloadUrl()));


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialogup.dismiss();

                                                }
                                            });
                                        }
                                        else if(photouri == null&&photouri2 == null){
                                            dref.child("Hospitals/"+Names[+finalI]+"/Accidents/"+"Accident"+numberofaccident+"/Images/Image1").
                                                    setValue("No image");

                                        }
                                        for(int p=0;p<Cars.length;p++){

                                            if(dataSnapshot.child("Ambulance/" + Cars[+p]+ "/Availability").exists()){
                                                if(dataSnapshot.child("Ambulance/" + Cars[+p] + "/Availability").getValue().toString().equals("True")){

                                                    if (dataSnapshot.child("Ambulance/" + Cars[+p] + "/Latitude").exists() &&
                                                            dataSnapshot.child("Ambulance/" + Cars[+p] + "/Longitude").exists()) {
                                                        Location loc = new Location("");
                                                        loc.setLatitude(Double.parseDouble(dataSnapshot.
                                                                child("Ambulance/" + Cars[+p] + "/Latitude").getValue().toString()));
                                                        loc.setLongitude(Double.parseDouble(dataSnapshot.
                                                                child("Ambulance/" + Cars[+p] + "/Longitude").getValue().toString()));
                                                        double distancecar = me.distanceTo(loc);
                                                        mylistdouble.add(distancecar);
                                                        mylist.add(Cars[+p]);

                                                    }}}

                                        }

                                        if(mylistdouble.size()!=0){

                                            target = new double[mylistdouble.size()];
                                            for (int o = 0; o < target.length; o++) {
                                                target[o] = mylistdouble.get(o);
                                            }

                                        }


                                        if(target!=null&&mylist.size()!=0){
                                            double nearestCar=getMin(target);
                                            for(int oo=0;oo<target.length;oo++){
                                                if(target[+oo]==nearestCar) {
                                                    //  Toast.makeText(getContext(), String.valueOf(nearestCar)+"...."+mylist.get(oo), Toast.LENGTH_SHORT).show();

                                                    //  Toast.makeText(getContext(), mylist.get(oo), Toast.LENGTH_SHORT).show();
                                                    editor.putString("NearestCar",mylist.get(oo));
                                                    editor.putString("NearestCarDistance",String.valueOf(nearestCar));




                                                    DatabaseReference myRefusercar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Reporter Number");
                                                    myRefusercar.setValue(storage.getString("User number", "00"));

                                                    DatabaseReference myRefxcar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Latitude");
                                                    myRefxcar.setValue(me.getLatitude());
                                                    DatabaseReference myRefycar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Longitude");
                                                    myRefycar.setValue(me.getLongitude());

                                                    DatabaseReference myRefinjreycar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Injuries");
                                                    myRefinjreycar.setValue(injuries);
                                                    DatabaseReference myReftypecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Type");
                                                    myReftypecar.setValue(Type);
                                                    DatabaseReference Distancecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Distance");
                                                    Distancecar.setValue(nearestCar);

                                                    DatabaseReference Availablecar = database.getReference("Ambulance/" + mylist.get(oo) + "/Availability");
                                                    Location nearesthosp = Locations[+finalI];

                                                    DatabaseReference NearestHospName = database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Name");
                                                    NearestHospName.setValue(Names[+finalI]);
                                                    DatabaseReference NearestHospLocLat= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Location/Latitude");
                                                    NearestHospLocLat.setValue(nearesthosp.getLatitude());
                                                    DatabaseReference NearestHospLocLong= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Nearest Hospital/Location/Longitude");
                                                    NearestHospLocLong.setValue(nearesthosp.getLongitude());

                                                    DatabaseReference NearestHospId= database.getReference("Ambulance/" + mylist.get(oo) + "/Accident/Id");
                                                    NearestHospId.setValue("Accident"+accidentNumint);
                                                    Availablecar.setValue("False");
                                                    editor.putBoolean("SendCar", false);
                                                    editor.commit();


                                      /*  Toast.makeText(getContext(), storage.getString("NearestCar","pp")+".."+
                                                        storage.getString("NearestCarDistance","pp")+""+String.valueOf(storage.getBoolean("Sendcar",false))
                                                , Toast.LENGTH_LONG).show();*/
                                                }
                                            }}
                                    }
                                    else {
                                        Toast.makeText(getContext(), "Thank you, the accident have already been Reported", Toast.LENGTH_SHORT).show();

                                    }}

                            }}

                                 /*
                                    }*/





                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                    }
                });
                String accidentNum=storage.getString("accidentNum","0");
                String nearestcardis=storage.getString("NearestCarDistance","aa");
                boolean send=storage.getBoolean("Send",false);
                boolean sendCar=storage.getBoolean("SendCar",false);
                int accidentNumint= Integer.parseInt(accidentNum);
                String carname=storage.getString("NearestCar","Car1");














            }
}



    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
