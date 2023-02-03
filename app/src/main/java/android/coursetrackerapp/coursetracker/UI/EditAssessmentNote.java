package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.AssessmentNotes;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;

public class EditAssessmentNote extends AppCompatActivity {

    EditText editAssessmentNote;

    int noteID;
    int assessmentID;
    String date;
    String note;

    Repository repo;
    AssessmentNotes assessmentNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assessment_note);

        editAssessmentNote = findViewById(R.id.editAssessmentNote);

        // If note already exists, get note object info from parent activity
        noteID = getIntent().getIntExtra("noteID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        note = getIntent().getStringExtra("note");

        //If note already exists, prefill the note for editing
        editAssessmentNote.setText(note);

        repo = new Repository(getApplication());

        Button button = findViewById(R.id.saveNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteID == -1) {
                    date = LocalDate.now().toString();
                    assessmentNotes = new AssessmentNotes(0, assessmentID, date, editAssessmentNote.getText().toString());
                    repo.insert(assessmentNotes);
                    finish();
                } else {
                    date = LocalDate.now().toString();
                    assessmentNotes = new AssessmentNotes(noteID, assessmentID, date, editAssessmentNote.getText().toString());
                    repo.update(assessmentNotes);
                    finish();
                }
            }
        });
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