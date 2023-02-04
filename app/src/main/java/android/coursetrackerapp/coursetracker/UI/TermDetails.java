package android.coursetrackerapp.coursetracker.UI;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    ImageView arrow;
    TextView emptyNote;
    
    ActivityResultLauncher<Intent> activityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 78) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            String termName = intent.getStringExtra("termTitle");
                            String startDate = intent.getStringExtra("startDate");
                            String endDate = intent.getStringExtra("endDate");
                            termTitleView.setText(termName);
                            startDateView.setText(startDate);
                            endDateView.setText(endDate);
                        }
                    }
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        termTitleView= findViewById(R.id.termTitleView);
        startDateView= findViewById(R.id.startDateView);
        endDateView= findViewById(R.id.endDateView);
        emptyNote = findViewById(R.id.emptyNote);
        arrow = findViewById(R.id.emptyArrow);
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

        if (filteredCourses.isEmpty()) {
            emptyNote.setText("No courses, yet! \n \nAdd courses, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        }



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
                    activityLauncher.launch(intent);
                    return true;
                case R.id.deleteTerm:
                    deleteTerm();
                    return true;

            }
            return super.onOptionsItemSelected(menuItem);
    }

    private void deleteTerm() {
        Term currentTerm = null;
        //Getting this Term Object
        for (Term t : repo.getAllTerms()) {
            if (t.getTermID() == id) {
                currentTerm = t;
            }
        }

        //Checking if Courses are Assigned
        List<Course> courses = repo.getAllCoursesByTerm(id);
        int numberOfCoursesAssigned = courses.size();

        if (numberOfCoursesAssigned == 0) {
            repo.delete(currentTerm);
            Toast.makeText(TermDetails.this, termTitleView.getText().toString() + " Successfully Deleted",
                    Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(TermDetails.this, termTitleView.getText().toString() + " has " + numberOfCoursesAssigned + "" +
                            " courses assigned.\n Cannot Delete Term",
                    Toast.LENGTH_LONG).show();
        }
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

        if (allCourses.isEmpty()) {
            emptyNote.setText("No courses, yet! \n \nAdd courses, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        } else {
            emptyNote.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
        }
    }
}