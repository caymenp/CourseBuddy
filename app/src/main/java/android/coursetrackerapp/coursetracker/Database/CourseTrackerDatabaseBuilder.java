package android.coursetrackerapp.coursetracker.Database;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.Database
 * <p>
 * User: caymen
 * Date: 1/29/23
 * TIME: 3:14 PM
 * <p>
 * Created with Android Studio
 */

import android.content.Context;
import android.coursetrackerapp.coursetracker.dao.AssessmentDAO;
import android.coursetrackerapp.coursetracker.dao.CourseDAO;
import android.coursetrackerapp.coursetracker.dao.TermDAO;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/** CourseTrackerDatabaseBuilder */
@Database(entities = {Term.class, Course.class, Assessment.class}, version = 1, exportSchema = false)
public abstract class CourseTrackerDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    private static volatile CourseTrackerDatabaseBuilder INSTANCE;

    static CourseTrackerDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CourseTrackerDatabaseBuilder.class) {
                if (INSTANCE==null) {
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            CourseTrackerDatabaseBuilder.class, "CourseTrackedDatabase.db")
                            .fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
