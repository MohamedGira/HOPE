package com.helloworld.hosfis.ui.main;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.helloworld.hosfis.MainActivity;
import com.helloworld.hosfis.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ACCDIENT_farg extends Fragment {
    Button btnnn;
    TextInputLayout num1,num2,num3;
    ListView listaccident,ImageList;
    DatabaseReference dref;
    TextView DialogType,DialogDistance,Dialogtime,DialogInjuries;
    String Image1,Image2;
    ImageView DialogPic1,Dialogpic2;
    Bitmap bitmapp;

    private StorageReference mStorageRef;
    public ACCDIENT_farg() {
        // Required empty public constructor
    }

    TextInputLayout numm[] = {num1,num2,num3};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_accdient_farg, container, false);
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();
        final String Hospitalname=storage.getString("HospitalName","Zembaboy");
        final String Hospitallocation=storage.getString("HospitalLocation","30,30");
        final Dialog dialogddetail = new Dialog(getContext());
        dialogddetail.setContentView(R.layout.accidente_detail_dialouge);
        DialogDistance=dialogddetail.findViewById(R.id.Distance);
        DialogInjuries=dialogddetail.findViewById(R.id.number);
        Dialogtime=dialogddetail.findViewById(R.id.time);
        DialogType=dialogddetail.findViewById(R.id.type);
        ImageList=dialogddetail.findViewById(R.id.imglist);
        final ArrayList<imagemodel> imagemodels= new ArrayList<imagemodel>();

        final ACCDIENT_farg.MyCustomAdapterImage imgadapter = new MyCustomAdapterImage(imagemodels);
        ImageList.setAdapter(imgadapter);


      /*  DialogPic1=dialogddetail.findViewById(R.id.pic1);
        Dialogpic2=dialogddetail.findViewById(R.id.pic2);
*/
        mStorageRef = FirebaseStorage.getInstance().getReference();
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
        dialogddetail.getWindow().setLayout((6 * width)/7, (8 * height)/12);
        dialogddetail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        dref = FirebaseDatabase.getInstance().getReference("Hospitals/"+Hospitalname+"/Accidents");

        listaccident=v.findViewById(R.id.listaccident);
        final ArrayList<accidentmodel> accident= new ArrayList<accidentmodel>();
        final ACCDIENT_farg.MyCustomAdapter medadpter= new ACCDIENT_farg.MyCustomAdapter(accident);
        listaccident.setAdapter(medadpter);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[16 * 1024];

        listaccident.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                imagemodels.removeAll(imagemodels);
                DialogType.setText(accident.get(position).getType());
                DialogDistance.setText(accident.get(position).getDistance()+" "+"Km");
                Dialogtime.setText(accident.get(position).getTime());
                DialogInjuries.setText(accident.get(position).getInjuries());

              if(accident.get(position).getMp1()==null){
                  imagemodels.add(new imagemodel(accident.get(position).getMp2()));
              } else if (accident.get(position).getMp2()==null) {
                  imagemodels.add(new imagemodel(accident.get(position).getMp1()));
              }
              else if(accident.get(position).getMp2()!=null&&accident.get(position).getMp2()!=null) {
                  imagemodels.add(new imagemodel(accident.get(position).getMp1()));
                  imagemodels.add(new imagemodel(accident.get(position).getMp2()));
                  }
              else{imagemodels.removeAll(imagemodels);}

                CardView map=dialogddetail.findViewById(R.id.Map_card);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?saddr="+Hospitallocation+"&daddr="+accident.get(position).getLocation());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
                dialogddetail.show();

            }
        });

        ImageList.setOnTouchListener(new ListView.OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;


                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        final Dialog dialogf =new ProgressDialog(getContext());

        dialogf.setTitle("Downloading Accidents information");
        ((ProgressDialog) dialogf).setMessage("test");
        dialogf.setCanceledOnTouchOutside(false);


        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                medadpter.notifyDataSetChanged();

                for(int i=0; i<10;i++) {
                    final String istr = String.valueOf(i);
                    if (dataSnapshot.child("Accident" + istr + "/Type").exists() &&
                            dataSnapshot.child("Accident" + istr + "/Latitude").exists() &&
                            dataSnapshot.child("Accident" + istr + "/Longitude").exists() &&
                            dataSnapshot.child("Accident" + istr + "/Injuries").exists() &&
                            dataSnapshot.child("Accident" + istr + "/Distance").exists()&&
                            dataSnapshot.child("Accident" + istr + "/Time").exists()) {

                        final String Type = dataSnapshot.child("Accident" + istr + "/Type").getValue().toString();
                        final String Location = dataSnapshot.child("Accident" + istr + "/Latitude").getValue().toString()
                                + "," + dataSnapshot.child("Accident" + istr + "/Longitude").getValue().toString();
                        final String Injuries = dataSnapshot.child("Accident" + istr + "/Injuries").getValue().toString();
                        final String Distance = dataSnapshot.child("Accident" + istr + "/Distance").getValue().toString();
                        final String Time= dataSnapshot.child("Accident" + istr + "/Time").getValue().toString();


//if no images exist at all
                        if (!dataSnapshot.child("Accident" + istr + "/Images/Image1").exists() && !dataSnapshot.child("Accident" + istr + "/Images/Image2").exists()){
                            accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                            medadpter.notifyDataSetChanged();
                        }else

                            // if Image number 1 exists

                            if (dataSnapshot.child("Accident" + istr + "/Images/Image1").exists() && !dataSnapshot.child("Accident" + istr + "/Images/Image2").exists()) {
                                if (accident.size() > i) {
                                    if (accident.get(i).getMp1() == null) {
                                        accident.remove(i);
                                        medadpter.notifyDataSetChanged();
                                        FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                        StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image1");
                                        dialogf.show();

                                        //Downloading Image1
                                        try {
                                            final File file = File.createTempFile("1image", ".jpg");
                                            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    dialogf.dismiss();


                                                    //Adjust Image oreintation to be vertical

                                                    bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                    ExifInterface ei = null;
                                                    try {
                                                        ei = new ExifInterface(file.getAbsolutePath());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                            ExifInterface.ORIENTATION_UNDEFINED);

                                                    Bitmap rotatedBitmap = null;
                                                    switch (orientation) {

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

                                                    Bitmap b1 = rotatedBitmap;

                                                    accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, b1, null));


                                                    medadpter.notifyDataSetChanged();
                                                    dialogf.dismiss();


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    //Toast why download failed, and adding the accident with no images to thelist
                                                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                    dialogf.dismiss();
                                                    accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                                    medadpter.notifyDataSetChanged();



                                                }
                                            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                }
                                            });

                                        } catch (IOException e) {
                                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                            accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                        }

                                    }
                                }
                                //if there is no prev 1 child
                                else{ FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                    StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image1");
                                    dialogf.show();

                                    //Downloading Image1
                                    try {
                                        final File file = File.createTempFile("1image", ".jpg");
                                        reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                dialogf.dismiss();


                                                //Adjust Image oreintation to be vertical

                                                bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                ExifInterface ei = null;
                                                try {
                                                    ei = new ExifInterface(file.getAbsolutePath());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                        ExifInterface.ORIENTATION_UNDEFINED);

                                                Bitmap rotatedBitmap = null;
                                                switch (orientation) {

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

                                                Bitmap b1 = rotatedBitmap;

                                                accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, b1, null));


                                                medadpter.notifyDataSetChanged();
                                                dialogf.dismiss();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                //Toast why download failed, and adding the accident with no images to thelist
                                                Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                dialogf.dismiss();
                                                accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                                medadpter.notifyDataSetChanged();



                                            }
                                        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            }
                                        });

                                    } catch (IOException e) {
                                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                        accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                    }
                                }
                            } else
                                // if Image number 2 exists and there is a previuos child
                                if (!dataSnapshot.child("Accident" + istr + "/Images/Image1").exists() && dataSnapshot.child("Accident" + istr + "/Images/Image2").exists()) {

                                    FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                    StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image2");
                                    dialogf.show();
                                    if (accident.size() > i) {
                                        if (accident.get(i).getMp2() == null) {
                                            accident.remove(i);
                                            medadpter.notifyDataSetChanged();


                                            //Downloading Image2
                                            try {
                                                final File file = File.createTempFile("1image", ".jpg");
                                                reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                        dialogf.dismiss();


                                                        //Adjust Image oreintation to be vertical

                                                        bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                        ExifInterface ei = null;
                                                        try {
                                                            ei = new ExifInterface(file.getAbsolutePath());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                                ExifInterface.ORIENTATION_UNDEFINED);

                                                        Bitmap rotatedBitmap = null;
                                                        switch (orientation) {

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

                                                        Bitmap b2 = rotatedBitmap;

                                                        accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, b2));

                                                        medadpter.notifyDataSetChanged();
                                                        dialogf.dismiss();


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {

                                                        //Toast why download failed, and adding the accident with no imagesto thelist
                                                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                        dialogf.dismiss();
                                                        accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                                        medadpter.notifyDataSetChanged();



                                                    }
                                                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    }
                                                });

                                            } catch (IOException e) {
                                                Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                                medadpter.notifyDataSetChanged();

                                            }

                                        }
                                    }else
                                    //If image 2 exist and there is no prev child
                                    { try {
                                        final File file = File.createTempFile("1image", ".jpg");
                                        reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                dialogf.dismiss();


                                                //Adjust Image oreintation to be vertical

                                                bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                ExifInterface ei = null;
                                                try {
                                                    ei = new ExifInterface(file.getAbsolutePath());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                        ExifInterface.ORIENTATION_UNDEFINED);

                                                Bitmap rotatedBitmap = null;
                                                switch (orientation) {

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

                                                Bitmap b2 = rotatedBitmap;

                                                accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, b2));

                                                medadpter.notifyDataSetChanged();
                                                dialogf.dismiss();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                                //Toast why download failed, and adding the accident with no imagesto thelist
                                                Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                dialogf.dismiss();
                                                accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                                medadpter.notifyDataSetChanged();



                                            }
                                        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            }
                                        });

                                    } catch (IOException e) {
                                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                        accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, null, null));
                                        medadpter.notifyDataSetChanged();

                                    }}




                                  /*  else {FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                        StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image2");
                                        dialogf.show();

                                        //Downloading Image2
                                        try {
                                            final File file = File.createTempFile("1image", ".jpg");
                                            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                    dialogf.dismiss();


                                                    //Adjust Image oreintation to be vertical

                                                    bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                    ExifInterface ei = null;
                                                    try {
                                                        ei = new ExifInterface(file.getAbsolutePath());
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                            ExifInterface.ORIENTATION_UNDEFINED);

                                                    Bitmap rotatedBitmap = null;
                                                    switch (orientation) {

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

                                                    Bitmap b2 = rotatedBitmap;

                                                    accident.add(new accidentmodel(Type, Location, Injuries, Distance, null, b2));

                                                    medadpter.notifyDataSetChanged();
                                                    dialogf.dismiss();


                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    //Toast why download failed, and adding the accident with no imagesto thelist
                                                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                    dialogf.dismiss();
                                                    accident.add(new accidentmodel(Type, Location, Injuries, Distance, null, null));
                                                    medadpter.notifyDataSetChanged();



                                                }
                                            }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                }
                                            });

                                        } catch (IOException e) {
                                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                            accident.add(new accidentmodel(Type, Location, Injuries, Distance, null, null));
                                            medadpter.notifyDataSetChanged();

                                        }}*/
                                } else

                                //if Both images exist

                                {

                                    FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                    StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image1");

                                    if (accident.size() > i) {
                                        if (accident.get(i).getMp1() == null || accident.get(i).getMp2() == null) {
                                            accident.remove(i);
                                            dialogf.show();

                                            //try and catch for both
                                            try {
                                                final File file = File.createTempFile("image", ".jpg");
                                                reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    // On first image download success
                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                        bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                        // Adjust Orientation
                                                        ExifInterface ei = null;
                                                        try {
                                                            ei = new ExifInterface(file.getAbsolutePath());
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                                ExifInterface.ORIENTATION_UNDEFINED);

                                                        Bitmap rotatedBitmap = null;
                                                        switch (orientation) {

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
                                                        final Bitmap b1 = rotatedBitmap;


                                                        FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                                        StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image2");

                                                        try {
                                                            final File file = File.createTempFile("1image", ".jpg");
                                                            reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                                    dialogf.dismiss();

                                                                    bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                                    ExifInterface ei = null;
                                                                    try {
                                                                        ei = new ExifInterface(file.getAbsolutePath());
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                                            ExifInterface.ORIENTATION_UNDEFINED);

                                                                    Bitmap rotatedBitmap = null;
                                                                    switch (orientation) {

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

                                                                    Bitmap b2 = rotatedBitmap;

                                                                    accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, b1, b2));

                                                                    medadpter.notifyDataSetChanged();
                                                                    dialogf.dismiss();


                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    else{
// if there is no prev childs
                                        dialogf.show();
                                        //try and catch for both
                                        try {
                                        final File file = File.createTempFile("image", ".jpg");
                                        reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                            @Override
                                            // On first image download success
                                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                // Adjust Orientation
                                                ExifInterface ei = null;
                                                try {
                                                    ei = new ExifInterface(file.getAbsolutePath());
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                        ExifInterface.ORIENTATION_UNDEFINED);

                                                Bitmap rotatedBitmap = null;
                                                switch (orientation) {

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
                                                final Bitmap b1 = rotatedBitmap;


                                                FirebaseStorage storage1 = FirebaseStorage.getInstance();
                                                StorageReference reference = storage1.getReferenceFromUrl("gs://hope-75dfa.appspot.com/Hospitals/" + Hospitalname + "/Accidents/Accident" + istr + "/Images/Image2");

                                                try {
                                                    final File file = File.createTempFile("1image", ".jpg");
                                                    reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                                            dialogf.dismiss();

                                                            bitmapp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                                                            ExifInterface ei = null;
                                                            try {
                                                                ei = new ExifInterface(file.getAbsolutePath());
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                                                    ExifInterface.ORIENTATION_UNDEFINED);

                                                            Bitmap rotatedBitmap = null;
                                                            switch (orientation) {

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

                                                            Bitmap b2 = rotatedBitmap;

                                                            accident.add(new accidentmodel(Type, Location, Injuries, Distance,Time, b1, b2));

                                                            medadpter.notifyDataSetChanged();
                                                            dialogf.dismiss();


                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getContext(), String.valueOf(e), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }




                                    }
                                }

                    }
                    else if(accident.size()>i){
                        accident.remove(i);
                        medadpter.notifyDataSetChanged();
                    }




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return v;
    }

    private class MyCustomAdapter extends BaseAdapter {
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        ArrayList<accidentmodel> accident= new ArrayList<accidentmodel>();
        MyCustomAdapter(ArrayList<accidentmodel> Items){
            this.accident= Items;
        }

        @Override
        public int getCount() {return accident.size();}

        @Override
        public Object getItem(int position) {
            return accident.get(position).getLocation();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.accident_item,null);
            TextView textView=(TextView) view1.findViewById(R.id.title_txt);
            TextView textView2=(TextView) view1.findViewById(R.id.location);
            TextView textView3=(TextView) view1.findViewById(R.id.injury);
            textView.setText(accident.get(i).getType());
            textView2.setText(accident.get(i).getTime());
            textView3.setText(accident.get(i).getInjuries());
            return view1;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    private class MyCustomAdapterImage extends BaseAdapter {
        final SharedPreferences storage = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();

        ArrayList<imagemodel> imagemodels= new ArrayList<imagemodel>();
        MyCustomAdapterImage(ArrayList<imagemodel> Items){
            this.imagemodels= Items;
        }

        @Override
        public int getCount() {return imagemodels.size();}

        @Override
        public Object getItem(int position) {
            return imagemodels.get(position).mp1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LayoutInflater inflater=getLayoutInflater();
            View view1= inflater.inflate(R.layout.bitmapitem,null);

            ImageView bm= view1.findViewById(R.id.bitp);
            bm.setImageBitmap(imagemodels.get(i).getMp1());

            return view1;
        }
    }


}
