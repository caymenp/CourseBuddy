package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TermList extends AppCompatActivity {
    private Repository repository;
    ImageView arrow;
    TextView emptyNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        emptyNote = findViewById(R.id.emptyNote);
        arrow = findViewById(R.id.emptyArrow);
        RecyclerView recyclerView = findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Term> allTerms = repository.getAllTerms();
        termAdapter.setTerms(allTerms);

        if (allTerms.isEmpty()) {
            emptyNote.setText("No Terms, yet! \n \nAdd terms, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        }


        FloatingActionButton fab=findViewById(R.id.floatingActionButton4);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TermList.this, EditTerm.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        List<Term> allTerms = repository.getAllTerms();
        RecyclerView recyclerView = findViewById(R.id.termrecyclerview);
        final TermAdapter termAdapter = new TermAdapter(this);
        recyclerView.setAdapter(termAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        termAdapter.setTerms(allTerms);

        if (allTerms.isEmpty()) {
            emptyNote.setText("No Terms, yet! \n \nAdd terms, by clicking the green button in the bottom right of your screen!");
            emptyNote.setVisibility(View.VISIBLE);
            arrow.setVisibility(View.VISIBLE);
        } else {
            emptyNote.setVisibility(View.GONE);
            arrow.setVisibility(View.GONE);
        }
    }
}