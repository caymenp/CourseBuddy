package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    EditText editName;
    EditText editStartDate;
    EditText editEndDate;
    String name;
    String startDate;
    String endDate;
    int id;
    Term term;
    Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        editName= findViewById(R.id.termName);
        editStartDate= findViewById(R.id.termStartDate);
        editEndDate= findViewById(R.id.termEndDate);
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("termTitle");
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");
        editName.setText(name);
        editStartDate.setText(startDate);
        editEndDate.setText(endDate);
        repo = new Repository(getApplication());

        //Adding Courses to Related Terms
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        repo = new Repository(getApplication());
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Course> filteredCourses = new ArrayList<>();
        for (Course c : repo.getAllCourses()) {
            if (c.getTermID() == id) filteredCourses.add(c);
        }
        courseAdapter.setCourse(filteredCourses);


        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(id == -1) {
                    term = new Term(0, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repo.insert(term);
                } else {
                    term = new Term(id, editName.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                    repo.update(term);
                }
            }
        });


        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermDetails.this, CourseDetails.class);
                intent.putExtra("prodID", id);
                startActivity(intent);
            }
        });
    }
}