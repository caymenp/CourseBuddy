package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class EditCourse extends AppCompatActivity {

    EditText editCourseTitle;
    Button editCourseStart;
    Button editCourseEnd;
    EditText editCourseStatus;
    EditText editInstructorName;
    EditText editInstructorPhone;
    EditText editInstructorEmail;

    Spinner statusSpinner;

    int courseID;
    int termID;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String instructorName;
    String instructorPhone;
    String instructorEmail;

    Repository repo;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        editCourseTitle = findViewById(R.id.editCourseTitle);
        editCourseStart = findViewById(R.id.editCourseStart);
        editCourseEnd = findViewById(R.id.editCourseEnd);
        editInstructorName = findViewById(R.id.editInstructorName);
        editInstructorPhone = findViewById(R.id.editInstructorPhone);
        editInstructorEmail = findViewById(R.id.editInstructorEmail);

        //If editing and data is passed - get data from intent
        termID = getIntent().getIntExtra("termID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        courseStart = getIntent().getStringExtra("courseStart");
        courseEnd = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");

        //If Data is passed, set fields with pre-populated data.
        editCourseTitle.setText(courseTitle);
        editCourseStart.setText(courseStart);
        editCourseEnd.setText(courseEnd);
        editInstructorName.setText(instructorName);
        editInstructorPhone.setText(instructorPhone);
        editInstructorEmail.setText(instructorEmail);

        initializeCourseStatus();

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editCourseStart.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        editCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditCourse.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editCourseEnd.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        //Start new repo
        repo = new Repository(getApplication());

        //Save Button
        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(courseID == -1) {
                    course = new Course(0, termID, editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(),
                            statusSpinner.getSelectedItem().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString());
                    repo.insert(course);
                    updateUI();
                } else {
                    course = new Course(courseID, termID, editCourseTitle.getText().toString(), editCourseStart.getText().toString(), editCourseEnd.getText().toString(),
                            statusSpinner.getSelectedItem().toString(), editInstructorName.getText().toString(), editInstructorPhone.getText().toString(), editInstructorEmail.getText().toString());
                    repo.update(course);
                    updateUI();
                }
            }
        });
    }

    private void initializeCourseStatus() {
        statusSpinner = findViewById(R.id.statusSpinner);
        ArrayList<String> courseStatus = new ArrayList<>();
        courseStatus.add("In Progress");
        courseStatus.add("Completed");
        courseStatus.add("Dropped");
        courseStatus.add("Plan to Take");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, courseStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        statusSpinner.setAdapter(adapter);
    }
    private void updateUI() {
        Intent intent = new Intent();
        intent.putExtra("courseTitle", editCourseTitle.getText().toString());
        intent.putExtra("startDate", editCourseStart.getText().toString());
        intent.putExtra("endDate", editCourseEnd.getText().toString());
        intent.putExtra("status", statusSpinner.getSelectedItem().toString());
        intent.putExtra("instructorName", editInstructorName.getText().toString());
        intent.putExtra("instructorPhone", editInstructorPhone.getText().toString());
        intent.putExtra("instructorEmail", editInstructorEmail.getText().toString());
        setResult(80, intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}