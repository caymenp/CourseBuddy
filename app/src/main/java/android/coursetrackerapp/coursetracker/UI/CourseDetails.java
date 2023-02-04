package android.coursetrackerapp.coursetracker.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.CourseNotes;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    Button courseNotes;

    int courseID;
    int termID;
    String courseTitle;
    String startDate;
    String endDate;
    String status;
    String instructorName;
    String instructorPhone;
    String instructorEmail;
    ImageView arrow;
    TextView emptyNote;

    Repository repo;
    Course course;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 80) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String courseTitle = intent.getStringExtra("courseTitle");
                            String startDate = intent.getStringExtra("startDate");
                            String endDate = intent.getStringExtra("endDate");
                            String status = intent.getStringExtra("status");
                            String instructorName = intent.getStringExtra("instructorName");
                            String instructorPhone = intent.getStringExtra("instructorPhone");
                            String instructorEmail = intent.getStringExtra("instructorEmail");
                            courseName.setText(courseTitle);
                            courseStartDateView.setText(startDate);
                            courseEndDateView.setText(endDate);
                            courseStatusView.setText(status);
                            instructorNameView.setText(instructorName);
                            instructorPhoneView.setText(instructorPhone);
                            instructorEmailView.setText(instructorEmail);
                        }
                    }
                }
            }
    );

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

        emptyNote = findViewById(R.id.emptyNote);
        arrow = findViewById(R.id.emptyArrow);


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

        if (filteredAssessments.isEmpty()) {
            emptyNote.setText("No assessments, yet! \n \nAdd assessments, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        }


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
                intent.putExtra("title", courseTitle);
                intent.putExtra("message", "course");
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                startActivity(intent);
            }
        });

        courseNotes = findViewById(R.id.courseNotes);
        courseNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetails.this, CourseNotesList.class);
                intent.putExtra("courseID", courseID);
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
                activityLauncher.launch(intent);

                return true;
            case R.id.deleteCourse:
                deleteCourse();
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void deleteCourse() {
        Course currentCourse = null;
        for (Course c : repo.getAllCourses()) {
            if (courseID == c.getCourseID()) {
                currentCourse = c;
            }
        }

        if (!(repo.getAllAssessmentsByCourseByTerm(courseID).isEmpty())) {
            Toast.makeText(CourseDetails.this, "This course has " + repo.getAllAssessmentsByCourseByTerm(courseID).size() + " " +
                            "assessments. \n Cannot delete course, until assessments are deleted.",
                    Toast.LENGTH_LONG).show();
        } else {
            repo.delete(currentCourse);
            Toast.makeText(CourseDetails.this, courseName.getText().toString() + " Successfully Deleted",
                    Toast.LENGTH_LONG).show();
            finish();
        }
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

        if (allAssessments.isEmpty()) {
            emptyNote.setText("No assessments, yet! \n \nAdd assessments, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        } else {
            emptyNote.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
        }

    }

}