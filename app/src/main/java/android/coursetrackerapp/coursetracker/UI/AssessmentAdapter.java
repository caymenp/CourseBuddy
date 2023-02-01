package android.coursetrackerapp.coursetracker.UI;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.UI
 * <p>
 * User: caymen
 * Date: 1/31/23
 * TIME: 5:56 PM
 * <p>
 * Created with Android Studio
 */

import android.content.Context;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** AssessmentAdapter */
public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewAssessmentName;
        private final TextView textViewAssessmentType;

        private AssessmentViewHolder(View itemview) {
            super(itemview);
            textViewAssessmentName = itemview.findViewById(R.id.textViewAssessmentName);
            textViewAssessmentType = itemview.findViewById(R.id.textViewAssessmentType);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }


    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;

    public AssessmentAdapter(List<Assessment> mAssessment, Context context, LayoutInflater mInflater) {
        this.mAssessment = mAssessment;
        this.context = context;
        this.mInflater = mInflater;
    }

    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessment != null) {
            Assessment current  = mAssessment.get(position);
            int courseID = current.getCourseID();
            String assessmentName = current.getAssessmentName();
            String assessmentType = current.getAssessmentType();
            String startDate = current.getAssessmentStartDate();
            String endDate = current.getAssessmentEndDate();

            holder.textViewAssessmentName.setText(assessmentName);
            holder.textViewAssessmentType.setText(assessmentType);
        } else {
            holder.textViewAssessmentName.setText("No Assessment Name");
            holder.textViewAssessmentType.setText("No Assessment Type");

        }
    }

    @Override
    public int getItemCount() {return mAssessment.size();}

    public void setAssessment(List<Assessment> assessment) {
        mAssessment = assessment;
        notifyDataSetChanged();
    }
}
