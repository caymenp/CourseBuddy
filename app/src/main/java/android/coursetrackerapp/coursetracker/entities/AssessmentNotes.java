package android.coursetrackerapp.coursetracker.entities;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.entities
 * <p>
 * User: caymen
 * Date: 2/2/23
 * TIME: 12:53 PM
 * <p>
 * Created with Android Studio
 */

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/** AssessmentNotes */
@Entity(tableName = "assessmentNotes")
public class AssessmentNotes {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private int assessmentID;
    private String date;
    private String note;

    // Constructor

    public AssessmentNotes() {
    }

    public AssessmentNotes(int noteID, int assessmentID, String date, String note) {
        this.noteID = noteID;
        this.assessmentID = assessmentID;
        this.date = date;
        this.note = note;
    }

    //Getters

    public int getNoteID() {
        return noteID;
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public String getNote() {
        return note;
    }

    //Setters

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
