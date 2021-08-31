import java.util.ArrayList;


public class Student {
    private String lastname;
    private ArrayList<Subject> subjects;
    private double average;

    public Student(String lastname) {
        this.lastname = lastname;
        this.subjects = null;
        this.average = 0;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<Subject> subjects) {
        this.subjects = subjects;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public void checkAverage()
    {
        double avg = 0;
        for (int i = 0; i < getSubjects().size(); i++) {
            avg += getSubjects().get(i).getMark();
        }
        avg /=(double) getSubjects().size();
        this.setAverage(avg);
    }
}
