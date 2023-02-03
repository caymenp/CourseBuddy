package android.coursetrackerapp.coursetracker.dao;

import android.coursetrackerapp.coursetracker.entities.AssessmentNotes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentNotesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(AssessmentNotes assessmentNotes);

    @Update
    void update(AssessmentNotes assessmentNotes);

    @Delete
    void delete(AssessmentNotes assessmentNotes);

    @Query("SELECT * FROM assessmentNotes ORDER BY noteID ASC")
    List<AssessmentNotes> getAllNotes();

    @Query("SELECT * FROM assessmentNotes WHERE assessmentID= :assessmentID ORDER BY noteID ASC")
    List<AssessmentNotes> getAllNotesByAssessment(int assessmentID);
}
