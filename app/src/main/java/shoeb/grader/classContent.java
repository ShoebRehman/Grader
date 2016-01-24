package shoeb.grader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class classContent {
    public static final List<classInfo> ITEMS = new ArrayList<classInfo>();

    public static final Map<String, classInfo> ITEM_MAP = new HashMap<String, classInfo>();


    private static void addItem(classInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getName(), item);
    }


    private static String makeDetails(classInfo cl) {
        StringBuilder builder = new StringBuilder();
        //for (int i = 0; i < cl.getTestCount(); i++) {
        builder.append(cl.getName() + cl.getHomeworkCount() + cl.getTestCount());
        //}
        return builder.toString();
    }

}

class classInfo implements Serializable {
    private int testCount, homeworkCount, projectCount;

    //grade array stores all respective grades in an array
    private grade test[] = new grade[testCount];
    private grade homework[] = new grade[homeworkCount];
    private grade projects[] = new grade[projectCount];

    private String gpa;
    private String name;

    public classInfo(){
        testCount = 0;
        homeworkCount = 0;
        projectCount = 0;
        gpa = "";
        name = "";
    }

    public classInfo(int test, int hw, int proj, String name){
        testCount = test;
        homeworkCount = hw;
        projectCount = proj;
        this.name = name;

    }

    public void setHomeworkCount(int c){
        this.homeworkCount = c;
    }

    public void setProjectCount(int c){
        this.projectCount = c;
    }

    public void setTestCount(int c){
        this.testCount = c;
    }


    public void setGpa(String gpa){

        this.gpa = gpa;
    }

    public void setHomework(grade[] homework) {
        this.homework = homework;
    }

    public void setProjects(grade[] projects) {
        this.projects = projects;
    }

    public void setTest(grade[] test) {
        this.test = test;
    }

    public void setAll(grade[] test,grade[] homework,grade[] projects){
        this.homework = homework;
        this.projects = projects;
        this.test = test;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getTestCount() {
        return testCount;
    }

    public int getHomeworkCount() {
        return homeworkCount;
    }

    public int getProjectCount() {
        return projectCount;
    }

    public String getName() {
        return name;
    }

    public grade[] getHomework() {
        return homework;
    }

    public grade[] getProjects() {
        return projects;
    }

    public grade[] getTest() {
        return test;
    }
}

class grade implements Serializable{
    int grade;
    int percentage;

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public grade(){
        grade = 0;
        percentage = 0;
    }

    public grade(int g, int p){
        grade = g;
        percentage = p;
    }

    public int getGrade() {
        return grade;
    }

    public int getPercentage() {
        return percentage;
    }
}