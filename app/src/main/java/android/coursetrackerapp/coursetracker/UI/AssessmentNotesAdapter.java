package android.coursetrackerapp.coursetracker.UI;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.UI
 * <p>
 * User: caymen
 * Date: 2/2/23
 * TIME: 1:49 PM
 * <p>
 * Created with Android Studio
 */

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.AssessmentNotes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** AssessmentNotesAdapter */
public class AssessmentNotesAdapter extends RecyclerView.Adapter<AssessmentNotesAdapter.AssessmentNotesViewHolder> {

    class AssessmentNotesViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteString;
        private final TextView noteDate;

        private final Button shareButton;

        private AssessmentNotesViewHolder(View itemview) {
            super(itemview);
            noteString = itemview.findViewById(R.id.noteString);
            noteDate = itemview.findViewById(R.id.noteDate);
            shareButton = itemview.findViewById(R.id.shareNote);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, noteString.getText().toString());
                    sendIntent.putExtra(Intent.EXTRA_TITLE, "Check out my note - written on "+noteDate.getText().toString());
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    context.startActivity(shareIntent);
                }
            });

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final AssessmentNotes current = mAssessmentNotes.get(position);
                    Intent intent = new Intent(context, EditAssessmentNote.class);
                    intent.putExtra("noteID", current.getNoteID());
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("note", current.getNote());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<AssessmentNotes> mAssessmentNotes;
    private final Context context;
    private final LayoutInflater mInflator;

    public AssessmentNotesAdapter(List<AssessmentNotes> mAssessmentNotes, Context context, LayoutInflater mInflator) {
        this.mAssessmentNotes = mAssessmentNotes;
        this.context = context;
        this.mInflator = mInflator;
    }

    public AssessmentNotesAdapter(Context context) {
        mInflator = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentNotesAdapter.AssessmentNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflator.inflate(R.layout.note_list_item, parent, false);
        return new AssessmentNotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentNotesAdapter.AssessmentNotesViewHolder holder, int position) {
        if (mAssessmentNotes != null) {
            AssessmentNotes current  = mAssessmentNotes.get(position);
            String note = current.getNote();
            String date = current.getDate();

            holder.noteString.setText(note);
            holder.noteDate.setText(date);
        } else {
            holder.noteString.setText("No Notes. \n Click the add button below to add a note!");
        }
    }

    @Override
    public int getItemCount() {return mAssessmentNotes.size();}

    public void setAssessmentNotes(List<AssessmentNotes> assessmentNotes) {
        mAssessmentNotes = assessmentNotes;
        notifyDataSetChanged();
    }
}
