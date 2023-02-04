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
import android.coursetrackerapp.coursetracker.dao.CourseNotesDAO;
import android.coursetrackerapp.coursetracker.dao.CourseDAO;
import android.coursetrackerapp.coursetracker.dao.TermDAO;
import android.coursetrackerapp.coursetracker.entities.Assessment;
import android.coursetrackerapp.coursetracker.entities.CourseNotes;
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
    private CourseNotesDAO mCourseNotesDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Course> mAllCourseByTerm;
    private List<Assessment> mAllAssessments;
    private List<Assessment> mAllAsssessmentsByCourse;
    private List<CourseNotes> mAllCourseNotes;
    private List<CourseNotes> mAllCourseNotesByCourse;

    private static int NUMBER_OF_THREADS = 20;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        CourseTrackerDatabaseBuilder db = CourseTrackerDatabaseBuilder.getDatabase(application);
        mTermDAO = db.termDAO();
        mCourseDAO = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mCourseNotesDAO = db.courseNotesDAO();
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

    //-----------------------Course Notes---------------------------//

    public List<CourseNotes> getAllCourseNotes() {
        databaseExecutor.execute(()->{
            mAllCourseNotes = mCourseNotesDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourseNotes;
    }
    public List<CourseNotes> getAllCourseNotesByCourse(int courseID) {
        databaseExecutor.execute(()->{
            mAllCourseNotesByCourse = mCourseNotesDAO.getAllNotesByCourse(courseID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourseNotesByCourse;
    }
    public void insert(CourseNotes courseNotes) {
        databaseExecutor.execute(()->{
            mCourseNotesDAO.insert(courseNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void update(CourseNotes courseNotes) {
        databaseExecutor.execute(()->{
            mCourseNotesDAO.update(courseNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void delete(CourseNotes courseNotes) {
        databaseExecutor.execute(()->{
            mCourseNotesDAO.delete(courseNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
