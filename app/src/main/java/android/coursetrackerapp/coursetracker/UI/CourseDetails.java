package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {
    TextView courseName;
    TextView courseStartDateView;
    TextView courseEndDateView;
    TextView courseStatusView;
    TextView instructorNameView;
    TextView instructorPhoneView;
    TextView instructorEmailView;
    Button scheduleReminder;

    int courseID;
    int termID;
    String courseTitle;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;

    Repository repo;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        courseName= findViewById(R.id.courseName);
        courseStartDateView= findViewById(R.id.courseStartDateView);
        courseEndDateView= findViewById(R.id.courseEndDateView);
        courseStatusView = findViewById(R.id.courseStatusView);
        instructorNameView = findViewById(R.id.instructorNameView);
        instructorPhoneView = findViewById(R.id.instructorPhoneView);
        instructorEmailView = findViewById(R.id.instructorEmailView);


        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        courseName.setText(courseTitle);
        courseStartDateView.setText(startDate);
        courseEndDateView.setText(endDate);
        courseStatusView.setText(status);
        instructorNameView.setText(instructorName);
        instructorPhoneView.setText(instructorPhone);
        instructorEmailView.setText(instructorEmail);
        repo = new Repository(getApplication());

        //Adding Assessments to Related Courses
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        repo = new Repository(getApplication());
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Assessment> filteredAssessments = new ArrayList<>();
        for (Assessment a : repo.getAllAssessments()) {
            if (a.getCourseID() == courseID) filteredAssessments.add(a);
        }
        assessmentAdapter.setAssessment(filteredAssessments);


        FloatingActionButton fab=findViewById(R.id.addAssessment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, EditAssessment.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
            }
        });

        scheduleReminder = findViewById(R.id.courseReminder);
        scheduleReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, NotificationDetails.class);
                intent.putExtra("title", "⏰ "+ courseTitle + " -- Course Reminder from Course Buddy!");
                intent.putExtra("message", "Reminder for your course! \n" + courseTitle + " Starts: " + startDate +
                        "\n" + courseTitle + " Ends: "+endDate);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate options menu
        getMenuInflater().inflate(R.menu.coursedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.editCourse:
                Intent intent = new Intent(CourseDetails.this, EditCourse.class);
                intent.putExtra("termID", termID);
                intent.putExtra("courseID", courseID);
                intent.putExtra("courseTitle", courseTitle);
                intent.putExtra("courseStart", startDate);
                intent.putExtra("courseEnd", endDate);
                intent.putExtra("status", status);
                intent.putExtra("instructorName", instructorName);
                intent.putExtra("instructorPhone", instructorPhone);
                intent.putExtra("instructorEmail", instructorEmail);
                startActivity(intent);

                return true;
            case R.id.deleteCourse:
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Assessment> allAssessments = repo.getAllAssessmentsByCourseByTerm(courseID);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        final AssessmentAdapter assessmentAdapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(assessmentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentAdapter.setAssessment(allAssessments);
    }

}