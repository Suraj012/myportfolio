package com.example.user.myapps1st.Database;

/**
 * Created by Suraj on 7/5/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.user.myapps1st.Model.CategoryInfo;
import com.example.user.myapps1st.Model.ContactInfo;
import com.example.user.myapps1st.Model.ExperienceInfo;
import com.example.user.myapps1st.Model.PortfolioInfo;
import com.example.user.myapps1st.Model.ProfileInfo;
import com.example.user.myapps1st.Model.SkillInfo;
import com.example.user.myapps1st.Model.UserInfo;
import com.example.user.myapps1st.Model.WorkInfo;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String dbname = "db_myapp1st";
    static int version = 1;

    String createUserTable = "CREATE TABLE IF NOT EXISTS `tbl_user` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `name`	TEXT,"
            +" `email`	TEXT,"
            +" `gender`	TEXT,"
            +" `username`	TEXT,"
            +" `password`	TEXT,"
            +" `contact`	NUMERIC)";

    String createProfileTable = "CREATE TABLE IF NOT EXISTS `tbl_profile` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `name`	TEXT,"
            +" `designation`	TEXT,"
            +" `image`	BLOB,"
            +" `facebook`	TEXT,"
            +" `google`	TEXT,"
            +" `twitter`	TEXT,"
            +" `instagram`	TEXT,"
            +" `description`	TEXT)";

    String createExperienceTable = "CREATE TABLE IF NOT EXISTS `tbl_experience` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `title`	TEXT,"
            +" `company`	TEXT,"
            +" `dateFrom`	TEXT,"
            +" `dateTo`	TEXT,"
            +" `description`	TEXT)";

    String createSkillTable = "CREATE TABLE IF NOT EXISTS `tbl_skill` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `rate`	TEXT,"
            +" `skill`	TEXT)";

    String createPortfolioTable = "CREATE TABLE IF NOT EXISTS `tbl_portfolio` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `category` TEXT,"
            +" `cid` TEXT,"
            +" `image` BLOB,"
            +" `title` TEXT,"
            +" `description` TEXT)";

    String createCategoryTable = "CREATE TABLE IF NOT EXISTS `tbl_category` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `category` TEXT)";

    String createContactTable = "CREATE TABLE IF NOT EXISTS `tbl_contact` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `address` TEXT,"
            +" `city` TEXT,"
            +" `country` TEXT,"
            +" `phone` BLOB,"
            +" `primary_email` TEXT,"
            +" `secondary_email` TEXT,"
            +" `gps` TEXT)";

    String createWorkTable = "CREATE TABLE IF NOT EXISTS `tbl_work` ("
            +" `id`	INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" `title`	TEXT,"
            +" `cid`	INTEGER,"
            +" `description`	TEXT,"
            +" `technology`	TEXT,"
            +" `category`	TEXT)";

    public DatabaseHelper(Context context) {
        super(context, dbname, null, version);
        // TODO Auto-generated constructor stub

        getWritableDatabase().execSQL(createUserTable);
        getWritableDatabase().execSQL(createProfileTable);
        getWritableDatabase().execSQL(createExperienceTable);
        getWritableDatabase().execSQL(createSkillTable);
        getWritableDatabase().execSQL(createPortfolioTable);
        getWritableDatabase().execSQL(createContactTable);
        getWritableDatabase().execSQL(createCategoryTable);
        getWritableDatabase().execSQL(createWorkTable);
    }
    public boolean insertUserInfo(String name, String email, String gender, String username, String password, String contact){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("email", email);
        cv.put("gender", gender);
        cv.put("username", username);
        cv.put("password", password);
        cv.put("contact", contact);
        long result = getWritableDatabase().insert("tbl_user", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean isValidLoggedIn(String email, String password){
        String sql = "Select count(*) from tbl_user where email='" +email+ "'and password='"+password+"'";
        SQLiteStatement stm = getReadableDatabase().compileStatement(sql);
        long rows = stm.simpleQueryForLong();
        if(rows>0) return true;
        else return false;
    }

    public ArrayList<UserInfo> selectUserInfo(){
        ArrayList<UserInfo>list = new ArrayList<UserInfo>();
        String select = "SELECT * FROM tbl_user";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            UserInfo info = new UserInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.email = cursor.getString(cursor.getColumnIndex("email"));
            info.username = cursor.getString(cursor.getColumnIndex("username"));
            info.password = cursor.getString(cursor.getColumnIndex("password"));
            info.contact = cursor.getString(cursor.getColumnIndex("contact"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    //Insert
    public boolean insertProfileInfo(String name, String designation, String description, String facebook, String google, String twitter, String instagram){
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("designation", designation);
        cv.put("description", description);
        cv.put("facebook", facebook);
        cv.put("google", google);
        cv.put("twitter", twitter);
        cv.put("instagram", instagram);
        long result = getWritableDatabase().insert("tbl_profile", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<ProfileInfo> selectProfileInfo(){
        ArrayList<ProfileInfo>list = new ArrayList<ProfileInfo>();
        String select = "SELECT * FROM tbl_profile";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            ProfileInfo info = new ProfileInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.designation = cursor.getString(cursor.getColumnIndex("designation"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.facebook = cursor.getString(cursor.getColumnIndex("facebook"));
            info.google = cursor.getString(cursor.getColumnIndex("google"));
            info.twitter = cursor.getString(cursor.getColumnIndex("twitter"));
            info.instagram = cursor.getString(cursor.getColumnIndex("instagram"));

            list.add(info);
        }
        cursor.close();
        return list;
    }

    public ProfileInfo getProfileInfo(String id){

        String select = "SELECT * FROM tbl_profile where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        ProfileInfo info = new ProfileInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.name = cursor.getString(cursor.getColumnIndex("name"));
            info.designation = cursor.getString(cursor.getColumnIndex("designation"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            info.facebook = cursor.getString(cursor.getColumnIndex("facebook"));
            info.google = cursor.getString(cursor.getColumnIndex("google"));
            info.twitter = cursor.getString(cursor.getColumnIndex("twitter"));
            info.instagram = cursor.getString(cursor.getColumnIndex("instagram"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editProfileInfo(String id, String name, String designation, String description, String facebook, String google, String twitter, String instagram){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("name", name);
        cv.put("designation", designation);
        cv.put("description", description);
        cv.put("facebook", facebook);
        cv.put("google", google);
        cv.put("twitter", twitter);
        cv.put("instagram", instagram);
        long result = getWritableDatabase().update("tbl_profile",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteProfileInfo(String id){
        getWritableDatabase().delete("tbl_profile", "id="+id, null);
    }

    //Skill database

    //Insert
    public boolean insertSkillInfo(String rate, String skill){
        ContentValues cv = new ContentValues();
        cv.put("rate", rate);
        cv.put("skill", skill);
        long result = getWritableDatabase().insert("tbl_skill", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<SkillInfo> selectSkillInfo(){
        ArrayList<SkillInfo>list = new ArrayList<SkillInfo>();
        String select = "SELECT * FROM tbl_skill";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            SkillInfo info = new SkillInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.rate = cursor.getString(cursor.getColumnIndex("rate"));
            info.skill = cursor.getString(cursor.getColumnIndex("skill"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public SkillInfo getSkillInfo(String id){

        String select = "SELECT * FROM tbl_skill where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        SkillInfo info = new SkillInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.rate = cursor.getString(cursor.getColumnIndex("rate"));
            info.skill = cursor.getString(cursor.getColumnIndex("skill"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editSkillInfo(String id, String rate, String skill){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("rate", rate);
        cv.put("skill", skill);
        long result = getWritableDatabase().update("tbl_skill",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteSkillInfo(String id){
        getWritableDatabase().delete("tbl_skill", "id="+id, null);
    }


    //Experience database

    //Insert
    public boolean insertExperienceInfo(String title, String company, String dateFrom, String dateTo, String description){
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("company", company);
        cv.put("dateFrom", dateFrom);
        cv.put("dateTo", dateTo);
        cv.put("description", description);
        long result = getWritableDatabase().insert("tbl_experience", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<ExperienceInfo> selectExperienceInfo(){
        ArrayList<ExperienceInfo>list = new ArrayList<ExperienceInfo>();
        String select = "SELECT * FROM tbl_experience";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            ExperienceInfo info = new ExperienceInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.company = cursor.getString(cursor.getColumnIndex("company"));
            info.dateFrom = cursor.getString(cursor.getColumnIndex("dateFrom"));
            info.dateTo = cursor.getString(cursor.getColumnIndex("dateTo"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public ExperienceInfo getExperienceInfo(String id){

        String select = "SELECT * FROM tbl_experience where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        ExperienceInfo info = new ExperienceInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.company = cursor.getString(cursor.getColumnIndex("company"));
            info.dateFrom = cursor.getString(cursor.getColumnIndex("dateFrom"));
            info.dateTo = cursor.getString(cursor.getColumnIndex("dateTo"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editExperienceInfo(String id, String title, String company, String dateFrom, String dateTo, String description){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("title", title);
        cv.put("company", company);
        cv.put("dateFrom", dateFrom);
        cv.put("dateTo", dateTo);
        cv.put("description", description);
        long result = getWritableDatabase().update("tbl_experience",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteExperienceInfo(String id){
        getWritableDatabase().delete("tbl_experience", "id="+id, null);
    }

    //Category database

    //Insert
    public boolean insertCategoryInfo(String category){
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        long result = getWritableDatabase().insert("tbl_category", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<CategoryInfo> selectCategoryInfo(){
        ArrayList<CategoryInfo>list = new ArrayList<CategoryInfo>();
        String select = "SELECT * FROM tbl_category";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            CategoryInfo info = new CategoryInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
            list.add(info);
        }
        cursor.close();
        return list;
    }
    public List<String> getCategoryList(){
        List<String> list = new ArrayList<String>();
        String select = "SELECT * FROM tbl_category";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            list.add(cursor.getString(1));
        }
        cursor.close();
        return list;
    }
    public CategoryInfo getCategoryInfo(String id){

        String select = "SELECT * FROM tbl_category where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        CategoryInfo info = new CategoryInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editCategoryInfo(String id, String category){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("category", category);
        long result = getWritableDatabase().update("tbl_category",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteCategoryInfo(String id){
        getWritableDatabase().delete("tbl_category", "id="+id, null);
    }


    //Portfolio database

    //Insert
    public boolean insertPortfolioInfo(String category, String cid ,String title, String description){
        ContentValues cv = new ContentValues();
        cv.put("category", category);
        cv.put("cid", cid);
        cv.put("title", title);
        cv.put("description", description);
        long result = getWritableDatabase().insert("tbl_portfolio", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<PortfolioInfo> selectPortfolioInfo(){
        ArrayList<PortfolioInfo>list = new ArrayList<PortfolioInfo>();
        String select = "SELECT * FROM tbl_portfolio";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            PortfolioInfo info = new PortfolioInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
            info.cid = cursor.getString(cursor.getColumnIndex("cid"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public PortfolioInfo getPortfolioInfo(String id){

        String select = "SELECT * FROM tbl_portfolio where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        PortfolioInfo info = new PortfolioInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
            info.cid = cursor.getString(cursor.getColumnIndex("cid"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editPortfolioInfo(String id, String category, String cid ,String title, String description){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("category", category);
        cv.put("cid", cid);
        cv.put("title", title);
        cv.put("description", description);
        long result = getWritableDatabase().update("tbl_portfolio",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deletePortfolioInfo(String id){
        getWritableDatabase().delete("tbl_portfolio", "id="+id, null);
    }


    //Contact database

    //Insert
    public boolean insertContactInfo(String address, String city ,String country, String phone, String primary_email, String secondary_email){
        ContentValues cv = new ContentValues();
        cv.put("address", address);
        cv.put("city", city);
        cv.put("country", country);
        cv.put("phone", phone);
        cv.put("primary_email", primary_email);
        cv.put("secondary_email", secondary_email);
        long result = getWritableDatabase().insert("tbl_contact", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<ContactInfo> selectContactInfo(){
        ArrayList<ContactInfo>list = new ArrayList<ContactInfo>();
        String select = "SELECT * FROM tbl_contact";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            ContactInfo info = new ContactInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.city = cursor.getString(cursor.getColumnIndex("city"));
            info.country = cursor.getString(cursor.getColumnIndex("country"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.primary_email = cursor.getString(cursor.getColumnIndex("primary_email"));
            info.secondary_email = cursor.getString(cursor.getColumnIndex("secondary_email"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public ContactInfo getContactInfo(String id){

        String select = "SELECT * FROM tbl_contact where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        ContactInfo info = new ContactInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.address = cursor.getString(cursor.getColumnIndex("address"));
            info.city = cursor.getString(cursor.getColumnIndex("city"));
            info.country = cursor.getString(cursor.getColumnIndex("country"));
            info.phone = cursor.getString(cursor.getColumnIndex("phone"));
            info.primary_email = cursor.getString(cursor.getColumnIndex("primary_email"));
            info.secondary_email = cursor.getString(cursor.getColumnIndex("secondary_email"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editContactInfo(String id, String address, String city ,String country, String phone, String primary_email, String secondary_email){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("address", address);
        cv.put("city", city);
        cv.put("country", country);
        cv.put("phone", phone);
        cv.put("primary_email", primary_email);
        cv.put("secondary_email", secondary_email);
        long result = getWritableDatabase().update("tbl_contact",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteContactInfo(String id){
        getWritableDatabase().delete("tbl_contact", "id="+id, null);
    }

    //Work database

    //Insert
    public boolean insertWorkInfo(String cid, String title, String description, String technology, String category){
        ContentValues cv = new ContentValues();
        cv.put("cid", cid);
        cv.put("title", title);
        cv.put("description", description);
        cv.put("technology", technology);
        cv.put("category", category);
        long result = getWritableDatabase().insert("tbl_work", "", cv);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Select
    public ArrayList<WorkInfo> selectWorkInfo(){
        ArrayList<WorkInfo>list = new ArrayList<WorkInfo>();
        String select = "SELECT * FROM tbl_work";

        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        while (cursor.moveToNext()){
            WorkInfo info = new WorkInfo();
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.cid = cursor.getString(cursor.getColumnIndex("cid"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
            info.technology = cursor.getString(cursor.getColumnIndex("technology"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public WorkInfo getWorkInfo(String id){

        String select = "SELECT * FROM tbl_work where id="+id ;
        Cursor cursor = getWritableDatabase().rawQuery(select, null);
        WorkInfo info = new WorkInfo();
        while (cursor.moveToNext()){
            info.id = cursor.getString(cursor.getColumnIndex("id"));
            info.cid = cursor.getString(cursor.getColumnIndex("cid"));
            info.title = cursor.getString(cursor.getColumnIndex("title"));
            info.category = cursor.getString(cursor.getColumnIndex("category"));
            info.technology = cursor.getString(cursor.getColumnIndex("technology"));
            info.description = cursor.getString(cursor.getColumnIndex("description"));
        }
        cursor.close();
        return info;
    }

    //Update
    public boolean editWorkInfo(String id, String cid,  String title, String description, String technology, String category){
        ContentValues cv = new ContentValues();
        cv.put("id", id);
        cv.put("cid", cid);
        cv.put("title", title);
        cv.put("description", description);
        cv.put("technology", technology);
        cv.put("category", category);
        long result = getWritableDatabase().update("tbl_work",cv,"id="+id,null);
        if(result == -1)
            return false;
        else
            return true;
    }

    //Delete
    public void deleteWorkInfo(String id){
        getWritableDatabase().delete("tbl_work", "id="+id, null);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}

