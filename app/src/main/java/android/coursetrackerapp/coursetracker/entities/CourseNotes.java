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

/** AssessmentNotes */
@Entity(tableName = "courseNotes")
public class CourseNotes {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private int courseID;
    private String date;
    private String note;

    // Constructor

    public CourseNotes() {
    }

    public CourseNotes(int noteID, int courseID, String date, String note) {
        this.noteID = noteID;
        this.courseID = courseID;
        this.date = date;
        this.note = note;
    }

    //Getters

    public int getNoteID() {
        return noteID;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getNote() {
        return note;
    }

    //Setters

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
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
