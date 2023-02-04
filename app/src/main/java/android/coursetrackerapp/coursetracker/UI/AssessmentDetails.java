package android.coursetrackerapp.coursetracker.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class AssessmentDetails extends AppCompatActivity {

    TextView assessmentTitleView;
    TextView assessmentStartDateView;
    TextView assessmentEndDateView;
    TextView assessmentTypeView;
    Button scheduleReminder;

    int assessmentID;
    int courseID;
    String assessmentName;
    String assessmentType;
    String startDate;
    String endDate;

    Repository repo;
    Assessment assessment;

    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 92) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String assessmentTitle = intent.getStringExtra("assessmentTitle");
                            String startDate = intent.getStringExtra("startDate");
                            String endDate = intent.getStringExtra("endDate");
                            String type = intent.getStringExtra("assessmentType");
                            assessmentTitleView.setText(assessmentTitle);
                            assessmentStartDateView.setText(startDate);
                            assessmentEndDateView.setText(endDate);
                            assessmentTypeView.setText(type);
                        }
                    }
                }
            }
    );

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


        scheduleReminder = findViewById(R.id.assessmentReminder);
        scheduleReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetails.this, NotificationDetails.class);
                intent.putExtra("title", assessmentName);
                intent.putExtra("message", "assessment");
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
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
                activityLauncher.launch(intent);

                return true;
            case R.id.deleteAssessment:
                deleteAssessment();
                return true;

        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void deleteAssessment() {
        Assessment currentAssessment = null;
        for (Assessment a : repo.getAllAssessmentsByCourseByTerm(courseID)) {
            if (a.getAssessmentID() == assessmentID) {
                currentAssessment = a;
            }
        }

        // Delete Assessment
        repo.delete(currentAssessment);
        Toast.makeText(AssessmentDetails.this, assessmentTitleView.getText().toString() + ", Successfully Deleted",
                Toast.LENGTH_LONG).show();
        finish();
    }

}