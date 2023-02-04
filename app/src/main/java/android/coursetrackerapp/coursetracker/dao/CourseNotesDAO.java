package android.coursetrackerapp.coursetracker.dao;

import android.coursetrackerapp.coursetracker.entities.CourseNotes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseNotesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CourseNotes courseNotes);

    @Update
    void update(CourseNotes courseNotes);

    @Delete
    void delete(CourseNotes courseNotes);

    @Query("SELECT * FROM courseNotes ORDER BY noteID ASC")
    List<CourseNotes> getAllNotes();

    @Query("SELECT * FROM courseNotes WHERE courseID = :courseID ORDER BY noteID ASC")
    List<CourseNotes> getAllNotesByCourse(int courseID);
}
