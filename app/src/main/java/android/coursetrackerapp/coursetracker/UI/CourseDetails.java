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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseDetails extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editStatus;
    EditText editInstrucName;
    EditText editInstrucPhone;
    EditText editInstrucEmail;

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

        editName= findViewById(R.id.courseName);
        editStartDate= findViewById(R.id.courseStartDate);
        editEndDate= findViewById(R.id.courseEndDate);
        editStatus = findViewById(R.id.courseStatus);
        editInstrucName = findViewById(R.id.instructorName);
        editInstrucPhone = findViewById(R.id.instructorPhone);
        editInstrucEmail = findViewById(R.id.instructorEmail);


        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseTitle = getIntent().getStringExtra("termTitle");
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");
        status = getIntent().getStringExtra("status");
        instructorName = getIntent().getStringExtra("instructorName");
        instructorPhone = getIntent().getStringExtra("instructorPhone");
        instructorEmail = getIntent().getStringExtra("instructorEmail");
        editName.setText(courseTitle);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        editStatus.setText(status);
        editInstrucName.setText(instructorName);
        editInstrucPhone.setText(instructorPhone);
        editInstrucEmail.setText(instructorEmail);
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


        Button button = findViewById(R.id.saveCourse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(courseID == -1) {
                    course = new Course(0, termID, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),
                            editStatus.getText().toString(), editInstrucName.getText().toString(), editInstrucPhone.getText().toString(), editInstrucEmail.getText().toString());
                    repo.insert(course);
                } else {
                    course = new Course(courseID, termID, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString(),
                            editStatus.getText().toString(), editInstrucName.getText().toString(), editInstrucPhone.getText().toString(), editInstrucEmail.getText().toString());
                    repo.update(course);
                }
            }
        });

        FloatingActionButton fab=findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseDetails.this, AssessmentDetails.class);
                startActivity(intent);
            }
        });

    }

}