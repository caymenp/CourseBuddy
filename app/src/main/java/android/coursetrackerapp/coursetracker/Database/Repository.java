package android.coursetrackerapp.coursetracker.Database;/**
 * Project: Course Tracker
 * Package: android.coursetrackerapp.coursetracker.Database
 * <p>
 * User: caymen
 * Date: 1/29/23
 * TIME: 3:30 PM
 * <p>
 * Created with Android Studio
 */

import android.app.Application;
import android.coursetrackerapp.coursetracker.dao.AssessmentDAO;
import android.coursetrackerapp.coursetracker.dao.AssessmentNotesDAO;
import android.coursetrackerapp.coursetracker.dao.CourseDAO;
import android.coursetrackerapp.coursetracker.dao.TermDAO;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.AssessmentNotes;
import android.coursetrackerapp.coursetracker.entities.Course;
import android.coursetrackerapp.coursetracker.entities.Term;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Repository */
public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private AssessmentNotesDAO mAssessmentNotesDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Course> mAllCourseByTerm;
    private List<Assessment> mAllAssessments;
    private List<Assessment> mAllAsssessmentsByCourse;
    private List<AssessmentNotes> mAllAssessmentNotes;
    private List<AssessmentNotes> mAllAssessmentNotesByAssessment;

    private static int NUMBER_OF_THREADS = 20;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        CourseTrackerDatabaseBuilder db = CourseTrackerDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mAssessmentNotesDAO = db.assessmentNotesDAO();
    }

    //-----------------------TERMS---------------------------//

    public List<Term> getAllTerms() {
        databaseExecutor.execute(()->{
            mAllTerms = mTermDAO.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;
    }
    public void insert(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Term term) {
        databaseExecutor.execute(()->{
            mTermDAO.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //-----------------------COURSES---------------------------//

    public List<Course> getAllCourses() {
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDAO.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;
    }
    public List<Course> getAllCoursesByTerm(int termID) {
        databaseExecutor.execute(()->{
            mAllCourseByTerm = mCourseDAO.getAllCoursesByTerm(termID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourseByTerm;
    }
    public void insert(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.insert(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Course course) {
        databaseExecutor.execute(()->{
            mCourseDAO.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //-----------------------Assessments---------------------------//

    public List<Assessment> getAllAssessments() {
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    public List<Assessment> getAllAssessmentsByCourseByTerm(int courseID) {
        databaseExecutor.execute(()->{
            mAllAsssessmentsByCourse = mAssessmentDAO.getAllAssessmentsByCourse(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAsssessmentsByCourse;
    }
    public void insert(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(Assessment assessment) {
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //-----------------------Assessment Notes---------------------------//

    public List<AssessmentNotes> getAllAssessmentNotes() {
        databaseExecutor.execute(()->{
            mAllAssessmentNotes = mAssessmentNotesDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessmentNotes;
    }
    public List<AssessmentNotes> getAllAssessmentNotesByAssessment(int assessmentID) {
        databaseExecutor.execute(()->{
            mAllAssessmentNotesByAssessment = mAssessmentNotesDAO.getAllNotesByAssessment(assessmentID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessmentNotesByAssessment;
    }
    public void insert(AssessmentNotes assessmentNotes) {
        databaseExecutor.execute(()->{
            mAssessmentNotesDAO.insert(assessmentNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(AssessmentNotes assessmentNotes) {
        databaseExecutor.execute(()->{
            mAssessmentNotesDAO.update(assessmentNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(AssessmentNotes assessmentNotes) {
        databaseExecutor.execute(()->{
            mAssessmentNotesDAO.delete(assessmentNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
