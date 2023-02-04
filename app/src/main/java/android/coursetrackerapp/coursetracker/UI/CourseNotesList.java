package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.CourseNotes;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CourseNotesList extends AppCompatActivity {
    int courseID;
    TextView emptyNote;
    ImageView arrow;

    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_notes_list);

        courseID = getIntent().getIntExtra("courseID", -1);
        emptyNote = findViewById(R.id.emptyNote);
        emptyNote.setVisibility(View.GONE);
        arrow = findViewById(R.id.emptyArrow);
        arrow.setVisibility(View.GONE);

        //Adding Assessments to Related Courses
        RecyclerView recyclerView = findViewById(R.id.courseNotesRecyclerView);
        repo = new Repository(getApplication());
        final CourseNotesAdapter courseNotesAdapter = new CourseNotesAdapter(this);
        recyclerView.setAdapter(courseNotesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<CourseNotes> filteredCourseNotes = new ArrayList<>();
        for (CourseNotes a : repo.getAllCourseNotes()) {
            if (a.getCourseID() == courseID) filteredCourseNotes.add(a);
        }
        courseNotesAdapter.setmCourseNotes(filteredCourseNotes);


        if (filteredCourseNotes.isEmpty()) {
            emptyNote.setText("No notes, yet! \n \nAdd some notes, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        }



        FloatingActionButton fab=findViewById(R.id.addCourseNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CourseNotesList.this, EditCourseNote.class);
                intent.putExtra("courseID", courseID);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();
        List<CourseNotes> allNotes = repo.getAllCourseNotesByCourse(courseID);
        RecyclerView recyclerView = findViewById(R.id.courseNotesRecyclerView);
        final CourseNotesAdapter courseNotesAdapter = new CourseNotesAdapter(this);
        recyclerView.setAdapter(courseNotesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseNotesAdapter.setmCourseNotes(allNotes);

        if (allNotes.isEmpty()) {
            emptyNote.setText("No notes, yet! \n \nAdd some notes, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        } else {
            emptyNote.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
        }
    }
}