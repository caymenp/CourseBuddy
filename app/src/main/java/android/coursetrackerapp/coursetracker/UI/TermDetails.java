package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.dao.TermDAO;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TermDetails extends AppCompatActivity {
    TextView termTitleView;
    TextView startDateView;
    TextView endDateView;
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
        termTitleView= findViewById(R.id.termTitleView);
        startDateView= findViewById(R.id.startDateView);
        endDateView= findViewById(R.id.endDateView);
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("termTitle");
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");
        termTitleView.setText(name);
        startDateView.setText(startDate);
        endDateView.setText(endDate);
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



        FloatingActionButton fab=findViewById(R.id.addNewCourse);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermDetails.this, EditCourse.class);
                intent.putExtra("termID", id);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate options menu
        getMenuInflater().inflate(R.menu.termdetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
                case R.id.editTerm:
                    Intent intent = new Intent(TermDetails.this, EditTerm.class);
                    intent.putExtra("id", id);
                    intent.putExtra("termTitle", termTitleView.getText().toString());
                    intent.putExtra("termStart", startDateView.getText().toString());
                    intent.putExtra("termEnd", endDateView.getText().toString());
                    startActivity(intent);
                    return true;
                case R.id.deleteTerm:
                    return true;

            }
            return super.onOptionsItemSelected(menuItem);
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Course> allCourses = repo.getAllCoursesByTerm(id);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        final CourseAdapter courseAdapter = new CourseAdapter(this);
        recyclerView.setAdapter(courseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseAdapter.setCourse(allCourses);
    }
}