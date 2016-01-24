package shoeb.grader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shoeb on 1/18/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    //Database Name
    private static final String DB_NAME = "grader.db";

    //Table Name
    private static final String DB_TABLE_CLASS = "classDetails";
    private static final String DB_TABLE_HOMEWORK = "hw_info";
    private static final String DB_TABLE_TEST = "test_info";
    private static final String DB_TABLE_PROJECT = "proj_info";

    @Override
    public void onCreate(SQLiteDatabase db) {
        //oncreate of the database, create or set db to grader.db
        db.execSQL("CREATE TABLE IF NOT EXISTS classDetails ( class_name VARCHAR PRIMARY KEY, homework_count INT, test_count INT, project_count INT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS hw_info ( class_name VARCHAR, grade INT NOT NULL, percentage INT NOT NULL, number INT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS test_info ( class_name VARCHAR, grade INT NOT NULL, percentage INT NOT NULL, number INT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS proj_info ( class_name VARCHAR, grade INT NOT NULL, percentage INT NOT NULL, number INT NOT NULL)");
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_TEST);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_HOMEWORK);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_PROJECT);
        onCreate(db);
    }

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
    }

    public void open(){
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public void insertClass(classInfo curr){
        SQLiteDatabase db = this.getWritableDatabase();

        int hwTotal = curr.getHomeworkCount();
        int testTotal = curr.getTestCount();
        int projTotal = curr.getProjectCount();

        grade[] test = curr.getTest();
        grade[] hw = curr.getHomework();
        grade[] proj = curr.getProjects();

        ContentValues classValues= new ContentValues();
        classValues.put("class_name",curr.getName());
        classValues.put("homework_count",curr.getHomeworkCount());
        classValues.put("test_count",curr.getTestCount());
        classValues.put("project_count", curr.getProjectCount());
        //db.insert(DB_TABLE_CLASS, null, classValues);
        db.execSQL("insert into classDetails(class_name,homework_count,test_count,project_count) values(?,?,?,?)", new Object[]{curr.getName(),hwTotal,testTotal,projTotal});

        for(int i = 0; i < testTotal;i++){
            if(curr.getTest()[i] != null) {
                db.execSQL("insert into test_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), test[i].getGrade(), test[i].getPercentage(), i});
            }
            else{
                db.execSQL("insert into test_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), 0, test[i].getPercentage(), i});
            }
        }

        for(int i = 0; i < hwTotal;i++){
            if(curr.getHomework()[i] != null){
                db.execSQL("insert into hw_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), hw[i].getGrade(), hw[i].getPercentage(),i});
            }
            else{
                db.execSQL("insert into hw_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), 0, hw[i].getPercentage(),i});
            }
        }

        for(int i = 0; i < projTotal;i++){
            if(curr.getProjects()[i] != null) {
                db.execSQL("insert into proj_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), proj[i].getGrade(), proj[i].getPercentage(), i});
            }
            else{
                db.execSQL("insert into proj_info(class_name,grade,percentage,number) values(?,?,?,?)", new Object[]{curr.getName(), 0, proj[i].getPercentage(), i});
            }
        }
    }

    public void removeClassByName(String curr){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE_CLASS,"class_name = '"+curr + "'",null);
        db.delete(DB_TABLE_TEST,"class_name = '"+curr + "'",null);
        db.delete(DB_TABLE_HOMEWORK,"class_name = '"+curr + "'",null);
        db.delete(DB_TABLE_PROJECT,"class_name = '"+curr + "'",null);
    }

    public void updateClass(classInfo curr){
        SQLiteDatabase db = this.getWritableDatabase();
        int hwTotal = curr.getHomeworkCount();
        int testTotal = curr.getTestCount();
        int projTotal = curr.getProjectCount();

        grade[] test = curr.getTest();
        grade[] hw = curr.getHomework();
        grade[] proj = curr.getProjects();


        ContentValues classValues= new ContentValues();
        classValues.put("class_name",curr.getName());
        classValues.put("homework_count", curr.getHomeworkCount());
        classValues.put("test_count", curr.getTestCount());
        classValues.put("project_count", curr.getProjectCount());

        db.update(DB_TABLE_CLASS, classValues, "class_name = '" + curr.getName() + "'", null);


        for(int i = 0; i < testTotal;i++){
            ContentValues testValues = new ContentValues();
            testValues.put("class_name",curr.getName());
            testValues.put("grade",test[i].getGrade());
            testValues.put("percentage",test[i].getPercentage());
            testValues.put("number",i);
            db.update(DB_TABLE_TEST, testValues, "class_name = '" + curr.getName() + "'", null);
        }

        for(int i = 0; i < hwTotal;i++){
            ContentValues hwValues = new ContentValues();
            hwValues.put("class_name",curr.getName());
            hwValues.put("grade",hw[i].getGrade());
            hwValues.put("percentage",hw[i].getPercentage());
            hwValues.put("number",i);
            db.update(DB_TABLE_HOMEWORK, hwValues, "class_name = '" + curr.getName() + "'", null);
        }

        for(int i = 0; i < projTotal;i++){
            ContentValues projValues = new ContentValues();
            projValues.put("class_name",curr.getName());
            projValues.put("grade",proj[i].getGrade());
            projValues.put("percentage",proj[i].getPercentage());
            projValues.put("number",i);
            db.update(DB_TABLE_PROJECT, projValues, "class_name = '" + curr.getName() + "'", null);
        }
    }

    public classInfo getClassItem(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        classInfo myClass = new classInfo();
        Cursor cursor = db.rawQuery("SELECT class_name,homework_count,test_count,project_count FROM " + DB_TABLE_CLASS + " WHERE class_name = '" + name + "'",null);
        while(cursor.moveToNext()) {
            myClass.setName(cursor.getString(0));
            myClass.setHomeworkCount(cursor.getInt(1));
            myClass.setTestCount(cursor.getInt(2));
            myClass.setProjectCount(cursor.getInt(3));
        }

        cursor = db.rawQuery("SELECT class_name,grade,percentage,number FROM " + DB_TABLE_TEST + " WHERE class_name = '" + name + "'",null);
        grade[]test = new grade[myClass.getTestCount()];
        int g = 0;
        int p = 0;
        while (cursor.moveToNext()) {
            Log.i("Grader",cursor.getString(1));
            g = cursor.getInt(1);
            p = cursor.getInt(2);
            grade newGrade = new grade(g,p);
            test[cursor.getInt(3)] = newGrade;
        }

        grade[]hw = new grade[myClass.getHomeworkCount()];
        cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_HOMEWORK + " WHERE class_name = '" + name + "'",null);

        while(cursor.moveToNext()){
            Log.i("Grader",cursor.getString(1));
            g = cursor.getInt(1);
            p = cursor.getInt(2);
            grade newGrade = new grade(g,p);
            hw[cursor.getInt(3)] = newGrade;
        }

        grade[] proj = new grade[myClass.getProjectCount()];
        cursor = db.rawQuery("SELECT * FROM " + DB_TABLE_PROJECT + " WHERE class_name = '" + name + "'",null);

        while (cursor.moveToNext()) {
            Log.i("Grader",cursor.getString(1));
            g = cursor.getInt(1);
            p = cursor.getInt(2);
            grade newGrade = new grade(g,p);
            proj[cursor.getInt(3)] = newGrade;
        }

        myClass.setAll(test,hw,proj);

        return myClass;
    }

    public String[] getAllClasses(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT class_name FROM " + DB_TABLE_CLASS, null);
        String[] classes = new String[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()){
            classes[i] = cursor.getString(0);
            i++;
            cursor.moveToNext();
        }
        return classes;
    }

};
