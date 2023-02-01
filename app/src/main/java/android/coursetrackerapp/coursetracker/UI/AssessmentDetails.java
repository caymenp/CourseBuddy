package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.os.Bundle;
import android.widget.EditText;


public class AssessmentDetails extends AppCompatActivity {

    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    EditText editAssessmentType;

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

        editName = findViewById(R.id.assessmentName);
        editStartDate = findViewById(R.id.assessmentStartDate);
        editEndDate = findViewById(R.id.assessmentEndDate);
        editAssessmentType = findViewById(R.id.assessmentType);

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType = getIntent().getStringExtra("assessmentType");
        startDate = getIntent().getStringExtra("assessmentStartDate");
        endDate = getIntent().getStringExtra("assessmentEndDate");
        editName.setText(assessmentName);
        editAssessmentType.setText(assessmentType);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        repo = new Repository(getApplication());


    }
}