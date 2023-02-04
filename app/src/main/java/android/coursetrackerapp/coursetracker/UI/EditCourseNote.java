package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.CourseNotes;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;

public class EditCourseNote extends AppCompatActivity {

    EditText editAssessmentNote;

    int noteID;
    int courseID;
    String date;
    String note;
    Button deleteNote;

    Repository repo;
    CourseNotes courseNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_note);

        editAssessmentNote = findViewById(R.id.editAssessmentNote);

        // If note already exists, get note object info from parent activity
        noteID = getIntent().getIntExtra("noteID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        note = getIntent().getStringExtra("note");

        //If note already exists, prefill the note for editing
        editAssessmentNote.setText(note);

        deleteNote = findViewById(R.id.deleteNote);
        //Setting button as not visible, if its a new note.
        if (noteID == -1) {
            deleteNote.setVisibility(View.GONE);
        }

        repo = new Repository(getApplication());

        Button button = findViewById(R.id.saveNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noteID == -1) {
                    date = LocalDate.now().toString();
                    courseNotes = new CourseNotes(0, courseID, date, editAssessmentNote.getText().toString());
                    repo.insert(courseNotes);
                    finish();
                } else {
                    date = LocalDate.now().toString();
                    courseNotes = new CourseNotes(noteID, courseID, date, editAssessmentNote.getText().toString());
                    repo.update(courseNotes);
                    finish();
                }
            }
        });

        deleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteNote();
            }
        });
    }

    private void deleteNote() {
        for (CourseNotes cn : repo.getAllCourseNotesByCourse(courseID)) {
            if (cn.getNoteID() == noteID) {
                repo.delete(cn);
            }
        }
        Toast.makeText(EditCourseNote.this, "Note: " + noteID + ", Successfully Deleted",
                Toast.LENGTH_LONG).show();
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