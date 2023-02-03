package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.AssessmentNotes;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class AssessmentDetails extends AppCompatActivity {

    TextView assessmentTitleView;
    TextView assessmentStartDateView;
    TextView assessmentEndDateView;
    TextView assessmentTypeView;

    int assessmentID;
    int courseID;
    String assessmentName;
    String assessmentType;
    String startDate;
    String endDate;

    Repository repo;
    Assessment assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        assessmentTitleView = findViewById(R.id.assessmentTitleView);
        assessmentStartDateView = findViewById(R.id.assessmentStartDateView);
        assessmentEndDateView = findViewById(R.id.assessmentEndDateView);
        assessmentTypeView = findViewById(R.id.assessmentTypeView);

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType = getIntent().getStringExtra("assessmentType");
        startDate = getIntent().getStringExtra("assessmentStartDate");
        endDate = getIntent().getStringExtra("assessmentEndDate");
        assessmentTitleView.setText(assessmentName);
        assessmentTypeView.setText(assessmentType);
        assessmentStartDateView.setText(startDate);
        assessmentEndDateView.setText(endDate);

        repo = new Repository(getApplication());

        //Adding Assessments to Related Courses
        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        repo = new Repository(getApplication());
        final AssessmentNotesAdapter assessmentNotesAdapter = new AssessmentNotesAdapter(this);
        recyclerView.setAdapter(assessmentNotesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<AssessmentNotes> filteredAssessmentNotes = new ArrayList<>();
        for (AssessmentNotes a : repo.getAllAssessmentNotes()) {
            if (a.getAssessmentID() == assessmentID) filteredAssessmentNotes.add(a);
        }
        assessmentNotesAdapter.setAssessmentNotes(filteredAssessmentNotes);

        FloatingActionButton fab=findViewById(R.id.addAssessmentNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssessmentDetails.this, EditAssessmentNote.class);
                intent.putExtra("assessmentID", assessmentID);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate options menu
        getMenuInflater().inflate(R.menu.assessmentdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.editAssessment:
                Intent intent = new Intent(AssessmentDetails.this, EditAssessment.class);
                intent.putExtra("assessmentID", assessmentID);
                intent.putExtra("courseID", courseID);
                intent.putExtra("assessmentName", assessmentName);
                intent.putExtra("assessmentType", assessmentType);
                intent.putExtra("assessmentStartDate", startDate);
                intent.putExtra("assessmentEndDate", endDate);
                startActivity(intent);

                return true;
            case R.id.deleteAssessment:
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<AssessmentNotes> allAssessmentNotes = repo.getAllAssessmentNotesByAssessment(assessmentID);
        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);
        final AssessmentNotesAdapter assessmentNotesAdapter = new AssessmentNotesAdapter(this);
        recyclerView.setAdapter(assessmentNotesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        assessmentNotesAdapter.setAssessmentNotes(allAssessmentNotes);
    }
}