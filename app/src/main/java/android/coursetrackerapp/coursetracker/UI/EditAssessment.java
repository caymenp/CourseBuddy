package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;

public class EditAssessment extends AppCompatActivity {
    EditText editName;
    Button editStartDate;
    Button editEndDate;
    Spinner typeSpinner;

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
        setContentView(R.layout.activity_edit_assessment);


        editName = findViewById(R.id.assessmentName);
        editStartDate = findViewById(R.id.assessmentStartDate);
        editEndDate = findViewById(R.id.assessmentEndDate);
        typeSpinner = findViewById(R.id.typeSpinner);

        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        assessmentName = getIntent().getStringExtra("assessmentName");
        assessmentType = getIntent().getStringExtra("assessmentType");
        startDate = getIntent().getStringExtra("assessmentStartDate");
        endDate = getIntent().getStringExtra("assessmentEndDate");


        initializeAssessmentType();

        editName.setText(assessmentName);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditAssessment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editStartDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditAssessment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editEndDate.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        repo = new Repository(getApplication());

        Button button = findViewById(R.id.saveAssessment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (assessmentID == -1) {
                    assessment = new Assessment(0, courseID, typeSpinner.getSelectedItem().toString(),
                            editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repo.insert(assessment);
                    updateUI();
                } else {
                    assessment = new Assessment(assessmentID, courseID, typeSpinner.getSelectedItem().toString(),
                            editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repo.update(assessment);
                    updateUI();
                }
            }
        });
    }

    private void initializeAssessmentType() {
        typeSpinner = findViewById(R.id.typeSpinner);
        ArrayList<String> assessmentType = new ArrayList<>();
        assessmentType.add("Objective");
        assessmentType.add("Performance");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, assessmentType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(adapter);
    }

    private void updateUI() {
        Intent intent = new Intent();
        intent.putExtra("assessmentTitle", editName.getText().toString());
        intent.putExtra("startDate", editStartDate.getText().toString());
        intent.putExtra("endDate", editEndDate.getText().toString());
        intent.putExtra("assessmentType", typeSpinner.getSelectedItem().toString());
        setResult(92, intent);
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