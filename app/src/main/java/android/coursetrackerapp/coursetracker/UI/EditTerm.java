package android.coursetrackerapp.coursetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.Database.Repository;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditTerm extends AppCompatActivity {

    EditText editTermTitle;
    Button editTermStart;
    Button editTermEnd;

    int id;
    String termName;
    String startDate;
    String endDate;

    Repository repo;

    Term term;


    //Date Picker Initialization


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_term);


        editTermTitle = findViewById(R.id.editTermTitle);
        editTermStart = findViewById(R.id.editTermStart);
        editTermEnd = findViewById(R.id.editTermEnd);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        editTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditTerm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editTermStart.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });

        editTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(EditTerm.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = month+"/"+dayOfMonth+"/"+year;
                        editTermEnd.setText(date);
                    }
                }, year, month, day);
                dialog.show();
            }
        });


        id = getIntent().getIntExtra("id", -1);
        termName = getIntent().getStringExtra("termTitle");
        startDate = getIntent().getStringExtra("termStart");
        endDate = getIntent().getStringExtra("termEnd");

        //If editing Term - prepopulate fields with saved data
        editTermTitle.setText(termName);
        editTermStart.setText(startDate);
        editTermEnd.setText(endDate);

        repo = new Repository(getApplication());

        Button button = findViewById(R.id.saveTerm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == -1) {
                    term = new Term(0, editTermTitle.getText().toString(),
                            editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repo.insert(term);
                   finish();

                } else if (!(id == -1)) {
                    term = new Term(id, editTermTitle.getText().toString(),
                            editTermStart.getText().toString(), editTermEnd.getText().toString());
                    repo.update(term);
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