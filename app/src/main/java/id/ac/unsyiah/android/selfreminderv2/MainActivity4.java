package id.ac.unsyiah.android.selfreminderv2;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import id.ac.unsyiah.android.selfreminderv2.databinding.ActivityMain4Binding;

public class MainActivity4 extends AppCompatActivity {


    private EditText name1, time1;
    private ImageButton add1;
    private DBHelper dbHelper;

    private ActivityMain4Binding binding;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        /*binding = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        //tombol back
        ImageButton back;
        back = findViewById(R.id.ib_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                overridePendingTransition(0,0);
                startActivity(i);
            }
        });

        //get string dari activity 2
        String data1 = getIntent().getStringExtra("key");
        String data2 = getIntent().getStringExtra("key1");

        //set string activity 2
        EditText et1;
        et1 = findViewById(R.id.editTextTextPersonName);
        et1.setText(data1);

        //time picker
        EditText time;
        time = findViewById(R.id.set_time);
        time.setInputType(InputType.TYPE_NULL);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimedialog(time);
            }

            private void showTimedialog(EditText time) {
                Calendar calendar = Calendar.getInstance();
                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        calendar.set(Calendar.HOUR_OF_DAY, i);
                        calendar.set(Calendar.MINUTE, i1);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");
                        time.setText(simpleDateFormat.format(calendar.getTime()));
                        startAlarm(calendar);
                        notification(calendar);
                    }
                    private void startAlarm(Calendar calendar){
                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
                        final int id = (int) System.currentTimeMillis();
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), id,intent,PendingIntent.FLAG_ONE_SHOT);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    }

                    private void notification(Calendar calendar){
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            CharSequence name = "Notification";
                            String description = "Your Notification";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;

                            NotificationChannel channel = new NotificationChannel("Reminder", name, importance);
                            channel.setDescription(description);

                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                        }
                    }
                };
                new TimePickerDialog(MainActivity4.this,  timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        });


        // initializing all our variables.
        name1 = findViewById(R.id.editTextTextPersonName);
        time1 = findViewById(R.id.set_time);
        add1 = findViewById(R.id.imageButton10);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHelper = new DBHelper(MainActivity4.this);

        // below line is to add on click listener for our add course button.
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String name = name1.getText().toString();
                String time = time1.getText().toString();
                String icon = data2;

                Log.d("onClick: ",data2);

                // validating if the text fields are empty or not.
                if (name.isEmpty() || time.isEmpty()) {
                    Toast.makeText(MainActivity4.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHelper.addNewReminder(name, time, icon);

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity4.this, "Reminder has been added.", Toast.LENGTH_SHORT).show();
                name1.setText("");
                time1.setText("");
            }
        });

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
        overridePendingTransition(0,0);
    }
}