package android.coursetrackerapp.coursetracker.UI;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.UI
 * <p>
 * User: caymen
 * Date: 1/31/23
 * TIME: 2:56 PM
 * <p>
 * Created with Android Studio
 */

import android.content.Context;
import android.content.Intent;
import android.coursetrackerapp.coursetracker.R;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/** CourseAdapter */
public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {


    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewCourseName;
        private final TextView textViewCourseStatus;
        private CourseViewHolder(View itemview){
            super(itemview);
            textViewCourseName=itemview.findViewById(R.id.textViewCourseName);
            textViewCourseStatus=itemview.findViewById(R.id.textViewCourseStatus);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourse.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseID", current.getCourseID());
                    intent.putExtra("termID", current.getTermID());
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("instructorPhone", current.getInstructorPhone());
                    intent.putExtra("instructorEmail", current.getInstructorEmail());
                    context.startActivity(intent);
                }
            });
        }

    }

    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (mCourse != null) {
            Course current  = mCourse.get(position);
            String courseTitle = current.getCourseTitle();
            int termID = current.getTermID();
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            String status = current.getStatus();
            String instrucName = current.getInstructorName();
            String instrucEmail = current.getInstructorEmail();
            String instrucPhone = current.getInstructorPhone();
            holder.textViewCourseName.setText(courseTitle);
            holder.textViewCourseStatus.setText(status);
        } else {
            holder.textViewCourseStatus.setText("No Course Name");
            holder.textViewCourseStatus.setText("No Course Status");

        }
    }

    @Override
    public int getItemCount() {
        return mCourse.size();
    }

    public void setCourse(List<Course> courses) {
        mCourse = courses;
        notifyDataSetChanged();
    }
}
