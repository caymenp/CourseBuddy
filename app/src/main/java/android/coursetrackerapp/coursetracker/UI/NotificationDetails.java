package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.databinding.ActivityMainBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;

public class NotificationDetails extends AppCompatActivity {

    Button scheduleNotification;
    Button changeStartReminder;
    Button changeEndReminder;
    Button confirmStartDate;
    Button confirmEndDate;
    Switch startReminder;
    Switch endReminder;
    TextInputEditText notificationTitle;
    TextInputEditText notificationMessage;
    TextView startDateView;
    TextView endDateView;
    LinearLayout endColumn;
    LinearLayout startColumn;
    LinearLayout dateTimeChanger;
    DatePicker datePicker;

    String startDate;
    String endDate;

    String title;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_reminder);

        scheduleNotification = findViewById(R.id.submitNotification);
        changeStartReminder = findViewById(R.id.changeStartReminder);
        changeEndReminder = findViewById(R.id.changeEndReminder);
        startReminder = findViewById(R.id.startReminder);
        endReminder = findViewById(R.id.endReminder);
        notificationTitle = findViewById(R.id.notificationTitle);
        notificationMessage = findViewById(R.id.notificationMessage);
        startDateView = findViewById(R.id.startDateTimeView);
        endDateView = findViewById(R.id.endDateTimeView);
        endColumn = findViewById(R.id.endColumn);
        startColumn = findViewById(R.id.startColumn);
        dateTimeChanger = findViewById(R.id.dateTimeChanger);
        datePicker = findViewById(R.id.datePicker);
        confirmStartDate = findViewById(R.id.changeDefaultStart);
        confirmEndDate = findViewById(R.id.changeDefaultEnd);

        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        title = getIntent().getStringExtra("title");
        message = getIntent().getStringExtra("message");

        notificationTitle.setText(title);
        notificationMessage.setText(message);
        endDateView.setText(endDate);
        startDateView.setText(startDate);


        changeStartReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeChanger.setVisibility(View.VISIBLE);
                scheduleNotification.setVisibility(View.GONE);
                confirmStartDate.setVisibility(View.VISIBLE);
            }
        });

        confirmStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();

                startDateView.setText(month+"/"+day+"/"+year);

                dateTimeChanger.setVisibility(View.GONE);
                confirmStartDate.setVisibility(View.GONE);
                scheduleNotification.setVisibility(View.VISIBLE);
            }
        });

        changeEndReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeChanger.setVisibility(View.VISIBLE);
                scheduleNotification.setVisibility(View.GONE);
                confirmEndDate.setVisibility(View.VISIBLE);
            }
        });

        confirmEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int month = datePicker.getMonth() + 1;
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();

                endDateView.setText(month+"/"+day+"/"+year);

                dateTimeChanger.setVisibility(View.GONE);
                confirmEndDate.setVisibility(View.GONE);
                scheduleNotification.setVisibility(View.VISIBLE);
            }
        });



        startReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startReminder.isChecked()) {
                    startColumn.setVisibility(View.VISIBLE);
                } else {
                    startColumn.setVisibility(View.INVISIBLE);
                    endReminder.setChecked(true);
                    endColumn.setVisibility(View.VISIBLE);
                }
            }
        });

        endReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (endReminder.isChecked()) {
                    endColumn.setVisibility(View.VISIBLE);
                } else {
                    endColumn.setVisibility(View.INVISIBLE);
                    startReminder.setChecked(true);
                    startColumn.setVisibility(View.VISIBLE);
                }
            }
        });

    }

}