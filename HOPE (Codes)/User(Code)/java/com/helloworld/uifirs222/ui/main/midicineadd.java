package com.helloworld.uifirs222.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.helloworld.uifirs222.MainActivity;
import com.helloworld.uifirs222.OffsetItemDecoration;
import com.helloworld.uifirs222.R;
import com.helloworld.uifirs222.RecyclerViewAdapter2;
import com.helloworld.uifirs222.RecyclerViewAdapter;
import com.helloworld.uifirs222.RecyclerViewAdapter3;

import java.util.ArrayList;

public class midicineadd extends AppCompatActivity {
    public static final String PREFS_NAME = "30";
    TextView time1, time2, time3;
    LinearLayout once, twice, three;
    GridView gridView;
    RadioButton Once, Twice, Three;
    RadioGroup Group;
    TimePicker timePicker;
    TextInputLayout dose, Name;
    int mHour, mMin, pos, pos3;
    Button button;
    CardView save;
    GridAdapter gridAdapter;
    ScrollView scr;
    private View background;
    public int[] mColors = {R.color.red, R.color.lightred, R.color.babyred, R.color.redpill, R.color.Tanred,
            R.color.orange, R.color.lightorange, R.color.babyorange, R.color.orangepill, R.color.Tanorange,
            R.color.yellow, R.color.lightyellow, R.color.babyyellow, R.color.yellowpill, R.color.Tanyellow,
            R.color.green, R.color.lightgreen, R.color.babygreen, R.color.greenpill, R.color.Tangreen,
            R.color.purple, R.color.lightpurple, R.color.babypurple, R.color.purplepill, R.color.Tanpurple
            , R.color.Blue, R.color.lightblue, R.color.babyblue, R.color.bluepill, R.color.Tanblue, R.color.white};

    LayerDrawable finalDrawable;

    int targetPosition;
    int targetPosition2;

    RecyclerView recyclerView, recyclerView2, recyclerView3, recyclerView4;

    LayerDrawable not_rounded_pill;
    ArrayList<Drawable> Number;
    ArrayList<Drawable> Number2;
    ArrayList<Drawable> Number3;
    RecyclerViewAdapter RecyclerViewHorizontalAdapter;
    RecyclerViewAdapter2 RecyclerViewHorizontalAdapter2;
    RecyclerViewAdapter3 RecyclerViewHorizontalAdapter3;
    LinearLayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager RecyclerViewLayoutManager2;
    LinearLayoutManager RecyclerViewLayoutManager3;
    View ChildView;
    View ChildView2;
    int RecyclerViewItemPosition;
    int RecyclerViewItemPosition2;
    View centerView;
ImageView rightarrow, leftarrow;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midicineadd);
leftarrow= findViewById(R.id.arrow_3_left);
rightarrow= findViewById(R.id.arrow_3_right);
        Once = findViewById(R.id.radioonce);
        Twice = findViewById(R.id.radiotwice);
        Three = findViewById(R.id.radiothree);
        Group = findViewById(R.id.group);
        time1 = findViewById(R.id.text1time);
        time2 = findViewById(R.id.text2time);
        time3 = findViewById(R.id.text3time);
        save = findViewById(R.id.save);
        Name = findViewById(R.id.name);
        dose = findViewById(R.id.dose_Edit);


        scr = findViewById(R.id.scroll);

        if (savedInstanceState == null) {


            final ViewTreeObserver viewTreeObserver = scr.getViewTreeObserver();


        }


        background = findViewById(R.id.scroll);

        if (savedInstanceState == null) {
            background.setVisibility(View.INVISIBLE);

            final ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();

            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }


//THE CODE FOR THE MEDICINE PICKER
        // the LayerDrawable  that contain two sided pill

        not_rounded_pill = (LayerDrawable) getResources().getDrawable(R.drawable.not_rounded_pill);

        //two halfs of the not_rounded_pill
        Drawable half11 = not_rounded_pill.findDrawableByLayerId(R.id.half_11);
        Drawable half12 = not_rounded_pill.findDrawableByLayerId(R.id.half_12);

        //*the code that bring the two halfs side by side and not on top of each other
        // int not_rounded_pill_offset = (half11.getIntrinsicWidth() + half12.getIntrinsicWidth()) / 2;
        int not_rounded_wpill_offset = half11.getIntrinsicWidth();
        not_rounded_pill.setLayerInset(0, not_rounded_wpill_offset, 0, 0, 0);
        not_rounded_pill.setLayerInset(1, 0, 0, 0, 0);


        // initializing the recyclerviews
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView3 = (RecyclerView) findViewById(R.id.recyclerview3);
        recyclerView4 = (RecyclerView) findViewById(R.id.recyclerView4);


        // Adding items to RecyclerView.
        AddItemsToRecyclerViewArrayList();
        AddItemsToRecyclerViewArrayList2();
        AddItemsToRecyclerViewArrayList3();

        // setting the array containing items to each recycelrview
        RecyclerViewHorizontalAdapter = new RecyclerViewAdapter(Number);
        RecyclerViewHorizontalAdapter2 = new RecyclerViewAdapter2(Number2);
        RecyclerViewHorizontalAdapter3 = new RecyclerViewAdapter3(Number3);
// initializing the layoutmanger *responsible for making hte scrolling horizontal* to recyclerviews
        RecyclerViewLayoutManager = new LinearLayoutManager(midicineadd.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewLayoutManager2 = new LinearLayoutManager(midicineadd.this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewLayoutManager3 = new LinearLayoutManager(midicineadd.this, LinearLayoutManager.HORIZONTAL, false);
//setting the layoutmanger to the recycler views
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        recyclerView2.setLayoutManager(RecyclerViewLayoutManager2);
        recyclerView3.setLayoutManager(RecyclerViewLayoutManager3);

// controlling recyclerview1 items size
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener so we don't get called again.
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Capture the height or the width of the parent view group.
                int height = ((ViewGroup) recyclerView.getParent()).getHeight();
                int width = ((ViewGroup) recyclerView4.getParent()).getWidth();
                // And let the adapter know the height or width.
                RecyclerViewHorizontalAdapter.setItemWidth(width);
                //   RecyclerViewHorizontalAdapter.setItemWidth(width);
                // Now that we know the height of the RecyclerView and its items, we
                // can set the adapter so the items can be created with the proper height.
                recyclerView.setAdapter(RecyclerViewHorizontalAdapter);
            }
        });
        // setting the adapter to recycler1
        recyclerView.setAdapter(RecyclerViewHorizontalAdapter);
// controlling recyclerview1 items size
        recyclerView2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener so we don't get called again.
                recyclerView2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Capture the height of the parent view group.
                int height = ((ViewGroup) recyclerView2.getParent()).getHeight();
                int width = ((ViewGroup) recyclerView4.getParent()).getWidth();
                // And let the adapter know the height.
                RecyclerViewHorizontalAdapter2.setItemWidth(width);
                //   RecyclerViewHorizontalAdapter.setItemWidth(width);
                // Now that we know the height of the RecyclerView and its items, we
                // can set the adapter so the items can be created with the proper height.
                recyclerView2.setAdapter(RecyclerViewHorizontalAdapter2);
            }
        });
        recyclerView2.setAdapter(RecyclerViewHorizontalAdapter2);
        recyclerView3.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Remove the listener so we don't get called again.
                recyclerView3.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                // Capture the height of the parent view group.
                //      int height = ((ViewGroup) recyclerView2.getParent()).getHeight();
                int width = ((ViewGroup) recyclerView4.getParent()).getWidth();
                // And let the adapter know the height.
                RecyclerViewHorizontalAdapter3.setItemWidth(width);
                //   RecyclerViewHorizontalAdapter.setItemWidth(width);
                // Now that we know the height of the RecyclerView and its items, we
                // can set the adapter so the items can be created with the proper height.
                recyclerView3.setAdapter(RecyclerViewHorizontalAdapter3);
            }
        });
        recyclerView3.setAdapter(RecyclerViewHorizontalAdapter3);


        final SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        final SnapHelper snapHelper2 = new LinearSnapHelper();
        snapHelper2.attachToRecyclerView(recyclerView2);
        final SnapHelper snapHelper3 = new LinearSnapHelper();
        snapHelper3.attachToRecyclerView(recyclerView3);


        OffsetItemDecoration offssss = new OffsetItemDecoration(midicineadd.this);
        recyclerView.addItemDecoration(offssss);
        recyclerView2.addItemDecoration(offssss);
        recyclerView3.addItemDecoration(offssss);


        int num2_last = Number2.size() - 1;
        int num3_last = Number3.size() - 1;
        int Transparent_color_position = mColors.length - 1;
        // Number2.get(num2_last).setColorFilter(getResources().getColor((mColors[Transparent_color_position])), PorterDuff.Mode.SRC_IN);
        Number3.get(num3_last).setColorFilter(getResources().getColor((mColors[Transparent_color_position])), PorterDuff.Mode.SRC_IN);

        not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[0])), PorterDuff.Mode.SRC_IN);
        not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[0])), PorterDuff.Mode.MULTIPLY);
        not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[0])), PorterDuff.Mode.SRC_IN);
        not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[0])), PorterDuff.Mode.MULTIPLY);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerViewkkk, int newState) {
                super.onScrollStateChanged(recyclerViewkkk, newState);
                View centerview = snapHelper.findSnapView(recyclerView.getLayoutManager());
                int pos = recyclerView.getLayoutManager().getPosition(centerview);
                Log.e("snapped Item Position2", "" + pos);
                View centerview2 = snapHelper2.findSnapView(recyclerView2.getLayoutManager());
                int pos2 = recyclerView2.getLayoutManager().getPosition(centerview2);
                Log.e("snapped Item Position2", "" + pos2);

                if (pos != 0) {
                    recyclerView3.setVisibility(View.GONE);
                    leftarrow.setVisibility(View.GONE);
                    rightarrow.setVisibility(View.GONE);
                } else {
                    leftarrow.setVisibility(View.VISIBLE);
                    rightarrow.setVisibility(View.VISIBLE);
                    recyclerView3.setVisibility(View.VISIBLE);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    for (int i = 0; i < Number.size(); i++) {

                        Number.get(i).clearColorFilter();
                    }
                    Number.get(pos).setColorFilter(getResources().getColor((mColors[pos2])), PorterDuff.Mode.SRC_IN);
                    Number.get(pos).setColorFilter(getResources().getColor((mColors[pos2])), PorterDuff.Mode.MULTIPLY);
                }

               // Toast.makeText(midicineadd.this, "dsa" + pos, Toast.LENGTH_SHORT).show();


            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(midicineadd.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView22, int newState) {
                super.onScrollStateChanged(recyclerView22, newState);
                View mLastSnappedView2 = snapHelper2.findSnapView(recyclerView2.getLayoutManager());
                int pos11 = recyclerView2.getLayoutManager().getPosition(mLastSnappedView2);
                Log.e("Snapped Item Position:", "" + pos11);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    final View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                    int pos12 = recyclerView.getLayoutManager().getPosition(view);
                    Log.e("Snapped Item Position:", "" + pos12);
                    if (pos12 == 0) {
                        not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[pos11])), PorterDuff.Mode.SRC_IN);
                        not_rounded_pill.getDrawable(0).setColorFilter(getResources().getColor((mColors[pos11])), PorterDuff.Mode.MULTIPLY);
                    } else if (pos12 != 0) {
                        Number.get(pos12).setColorFilter(getResources().getColor((mColors[pos11])), PorterDuff.Mode.SRC_IN);
                        Number.get(pos12).setColorFilter(getResources().getColor((mColors[pos11])), PorterDuff.Mode.MULTIPLY);
                    }

          /*   for (int i = 0 ; i < Number.size();i++){
                       if(i != pos12){
                           Number.get(pos12).clearColorFilter();

                       }

                   } */


             //       Toast.makeText(midicineadd.this, "sda" + String.valueOf(pos11), Toast.LENGTH_SHORT).show();

                } else if (mLastSnappedView2 != null) {
                    final View view = snapHelper2.findSnapView(recyclerView2.getLayoutManager());
                    int pos2 = recyclerView2.getLayoutManager().getPosition(view);


                }
            }
        });

        recyclerView2.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(midicineadd.this, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview2, MotionEvent motionEvent) {
                ChildView2 = Recyclerview2.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (ChildView2 != null && gestureDetector.onTouchEvent(motionEvent)) {
                    //Getting clicked value.
                    RecyclerViewItemPosition2 = Recyclerview2.getChildAdapterPosition(ChildView2);
                    // Showing clicked item value on screen using toast message.

                    int y = RecyclerViewItemPosition2;
                    RecyclerViewLayoutManager2.scrollToPositionWithOffset(y, targetPosition2);
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview2, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
        recyclerView3.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView22, int newState) {
                super.onScrollStateChanged(recyclerView22, newState);
                View mLastSnappedView2 = snapHelper3.findSnapView(RecyclerViewLayoutManager3);
                int pos21 = RecyclerViewLayoutManager3.getPosition(mLastSnappedView2);
                Log.e("Snapped Item Position:", "" + pos21);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    final View view = snapHelper.findSnapView(RecyclerViewLayoutManager);
                    int pos12 = RecyclerViewLayoutManager.getPosition(view);
                    Log.e("Snapped Item Position:", "" + pos12);


                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[pos21])), PorterDuff.Mode.SRC_IN);
                    not_rounded_pill.getDrawable(1).setColorFilter(getResources().getColor((mColors[pos21])), PorterDuff.Mode.MULTIPLY);

          /*   for (int i = 0 ; i < Number.size();i++){
                       if(i != pos12){
                           Number.get(pos12).clearColorFilter();

                       }

                   } */


                 //   Toast.makeText(midicineadd.this, "sda" + String.valueOf(pos21), Toast.LENGTH_SHORT).show();

                } else if (mLastSnappedView2 != null) {
                    final View view = snapHelper2.findSnapView(recyclerView2.getLayoutManager());
                    int pos2 = recyclerView2.getLayoutManager().getPosition(view);


                }
            }
        });


        final SharedPreferences storage = getSharedPreferences(midicineadd.PREFS_NAME, 0); // 0 - for private mode
        final SharedPreferences.Editor editor = storage.edit();


        final Context context = this;

        final Intent intent = getIntent();
        final String exist = intent.getStringExtra("exist");
        final int exisit = Integer.parseInt(exist);


        Group.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override


                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId) {
                        RadioButton
                                radioButton
                                = (RadioButton) group
                                .findViewById(checkedId);


                        if (radioButton == Once) {
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.INVISIBLE);
                            time3.setVisibility(View.INVISIBLE);
                        } else if (radioButton == Twice) {
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.INVISIBLE);
                        } else if (radioButton == Three) {
                            time1.setVisibility(View.VISIBLE);
                            time2.setVisibility(View.VISIBLE);
                            time3.setVisibility(View.VISIBLE);
                        }
                    }
                });
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        final int width = metrics.widthPixels;
        final int height = metrics.heightPixels;
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);


                timePicker = dialog.findViewById(R.id.timepicker);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMin = minute;
                    }
                });

                button = dialog.findViewById(R.id.dialogButtonOK);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        time1.setText(mHour + ":" + mMin);
                        editor.putInt("Timer1Hour" + exist, mHour);
                        editor.putInt("Timer1Min" + exist, mMin);
                        editor.commit();
                        dialog.dismiss();
                    }


                });

                dialog.show();
                dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 6);

            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);

                timePicker = dialog.findViewById(R.id.timepicker);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMin = minute;
                    }
                });
                button = dialog.findViewById(R.id.dialogButtonOK);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        editor.putInt("Timer2Hour" + exist, mHour);
                        editor.putInt("Timer2Min" + exist, mMin);
                        time2.setText(mHour + ":" + mMin);
                        editor.commit();
                        dialog.dismiss();
                        Toast.makeText(midicineadd.this, exist, Toast.LENGTH_SHORT).show();

                    }

                });
                dialog.show();
                dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 6);
            }
        });
        time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_dialog);
                timePicker = dialog.findViewById(R.id.timepicker);

                timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMin = minute;
                    }
                });
                button = dialog.findViewById(R.id.dialogButtonOK);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        time3.setText(mHour + ":" + mMin);

                        editor.putInt("Timer3Hour" + exist, mHour);
                        editor.putInt("Timer3Min" + exist, mMin);
                        editor.commit();
                        dialog.dismiss();
                    }


                });
                dialog.show();
                dialog.getWindow().setLayout((6 * width) / 7, (4 * height) / 6);


            }
        });


        final Spinner spinner = (Spinner) findViewById(R.id.spinner_dose_unit);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Dose, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if(Name.getText().toString().isEmpty()) {Name.setError("Enter your medication name");}
                else if(dose.getText().toString().isEmpty()) {Toast.makeText(context, "Enter a dose", Toast.LENGTH_SHORT).show();}
                else{}*/
                String unit = spinner.getSelectedItem().toString();
                String name = Name.getEditText().getText().toString().trim();
                String Dose = dose.getEditText().getText().toString().trim() + " " + unit;
                View icon=snapHelper.findSnapView(recyclerView.getLayoutManager());
                int iconid=recyclerView.getLayoutManager().getPosition(icon);
                View color1=snapHelper.findSnapView(recyclerView2.getLayoutManager());
                int color1id=recyclerView2.getLayoutManager().getPosition(color1);
                View color2=snapHelper.findSnapView(recyclerView3.getLayoutManager());
                int color2id=recyclerView2.getLayoutManager().getPosition(color2);

                editor.putString("NAME" + exist, name);
                editor.putString("DOSE" + exist, Dose);
                editor.putInt("ICON" + exist, iconid);
                editor.putInt("Color1" + exist, color1id);
                editor.putInt("Color2" + exist, color2id);
                editor.putString("CODE" + exist, exist);
                editor.putInt("Exist" + exist, exisit);

                editor.commit();
                Toast.makeText(context, "Done" + name, Toast.LENGTH_SHORT).show();
                Intent intentback = new Intent(midicineadd.this, MainActivity.class);
                intentback.putExtra("exist", exisit);
                intentback.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentback);

            }
        });


    }

    private class GridAdapter extends BaseAdapter {

        Context context;
        private final int[] Vectors;
        View view;
        LayoutInflater layoutInflater;

        public GridAdapter(Context context, int[] vectors) {
            this.context = context;
            Vectors = vectors;

        }


        @Override
        public int getCount() {
            return Vectors.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return Vectors[+position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                view = new View(context);
                view = layoutInflater.inflate(R.layout.single_item, null);
                ImageView imageView = view.findViewById(R.id.vectorview);
                imageView.setImageResource(Vectors[position]);
            } else {
                view = new View(context);
                view = layoutInflater.inflate(R.layout.single_item, null);
                ImageView imageView = view.findViewById(R.id.vectorview);
                imageView.setImageResource(Vectors[position]);
            }
            return view;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void circularRevealActivity() {
        int cx = background.getRight() - getDips(44);
        int cy = background.getBottom() - getDips(44);

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                background,
                cx,
                cy,
                0,
                finalRadius);

        circularReveal.setDuration(500);
        background.setVisibility(View.VISIBLE);
        circularReveal.start();

    }

    private int getDips(int dps) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dps,
                resources.getDisplayMetrics());
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int cx = background.getWidth() - getDips(44);
            int cy = background.getBottom() - getDips(44);

            float finalRadius = Math.max(background.getWidth(), background.getHeight());
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, finalRadius, 0);

            circularReveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    background.setVisibility(View.INVISIBLE);
                    finish();
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            circularReveal.setDuration(500);
            circularReveal.start();
        } else {
            super.onBackPressed();
        }
    }


    public void AddItemsToRecyclerViewArrayList() {

        Number = new ArrayList<>();

        Number.add(not_rounded_pill);
        Number.add(getResources().getDrawable(R.drawable.ic_rounded_pill));
        Number.add(getResources().getDrawable(R.drawable.ic_oval_pill));
        Number.add(getResources().getDrawable(R.drawable.ic_pill_tape));
        Number.add(getResources().getDrawable(R.drawable.ic_pill_jar));
        Number.add(getResources().getDrawable(R.drawable.ic_medicine_jar));
        Number.add(getResources().getDrawable(R.drawable.ic_drinkable_med));
        Number.add(getResources().getDrawable(R.drawable.ic_syrenge));
        Number.add(getResources().getDrawable(R.drawable.ic_droplet_eye));
        Number.add(getResources().getDrawable(R.drawable.ic_marham));
        Number.add(getResources().getDrawable(R.drawable.ic_tuberclouses));
        Number.add(getResources().getDrawable(R.drawable.ic_pipette));
        Number.add(getResources().getDrawable(R.drawable.ic_pharma_jar));



    }

    public void AddItemsToRecyclerViewArrayList2() {

        Number2 = new ArrayList<>();


        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
        Number2.add(getResources().getDrawable(R.drawable.ic_circle));
    }

    public void AddItemsToRecyclerViewArrayList3() {

        Number3 = new ArrayList<>();


        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));
        Number3.add(getResources().getDrawable(R.drawable.ic_circle));

    }
}