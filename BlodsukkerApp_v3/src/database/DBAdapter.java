package database;


import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class DBAdapter {
	public static final String KEY_ROWID ="_id";
	public static final String KEY_TID="tid";
	public static final String KEY_DATO="dato";
	public static final String KEY_BLODSUKKERVAERDI="blodsukkervaerdi";
	public static final String KEY_BLODSUKKERSTATUS="blodsukkerstatus";
	public static final String KEY_NOTE="note";
	private static final String TAG="DBAdapter";


//	Calendar cal = Calendar.getInstance();
//	int year = cal.get(Calendar.YEAR);
//	String tablename = ""+year;

	public static final String DATABASE_NAME="BlodsukkerDB";
	public static final String DATABASE_TABLE="blodsukkertabel";
	public static final int DATABASE_VERSION=1;

	

	

	static final String DATABASE_CREATE = 
			"Create table blodsukkertabel (_id integer primary key autoincrement, "
		   +"tid text not null, dato text not null, blodsukkervaerdi text not null, " +
		   "blodsukkerstatus text not null, note text not null);";
	
	final Context context;
	
	
	 DatabaseHelper DBHelper;
	    SQLiteDatabase db;
	    
	    public DBAdapter(Context ctx)
	    {
	        this.context = ctx;
	        DBHelper = new DatabaseHelper(context);
	    }


		private static class DatabaseHelper extends SQLiteOpenHelper
	    {
	        DatabaseHelper(Context context)
	        {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db)
	        {
	            try {
	                db.execSQL(DATABASE_CREATE);
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	        {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");
	            db.execSQL("DROP TABLE IF EXISTS blodsukkertabel");
	            onCreate(db);
	        }
	    }

	    //---opens the database---
	    public DBAdapter open() throws SQLException 
	    {

	        db = DBHelper.getWritableDatabase();
	        return this;
	    }

	    //---closes the database---
	    public void close() 
	    {
	    	if (DBHelper != null) {
	        DBHelper.close();
	    	}
	    }

	    //---insert record into the database---
	    public long maaling(String tid, String dato, double blodsukkervaerdi, String blodsukkerstatus, String note) 
	    {
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_TID, tid);
	        initialValues.put(KEY_DATO, dato);
	        initialValues.put(KEY_BLODSUKKERVAERDI, blodsukkervaerdi);
	        initialValues.put(KEY_BLODSUKKERSTATUS, blodsukkerstatus);
	        initialValues.put(KEY_NOTE, note);
	        return db.insert(DATABASE_TABLE, null, initialValues);
	    }
	    
//	    //---insert record into the database---
//	    public long maaling(int tid, int dato, double blodsukkervaerdi, String blodsukkerstatus, String note) 
//	    {
//	        ContentValues initialValues = new ContentValues();
//	        initialValues.put(KEY_TID, tid);
//	        initialValues.put(KEY_DATO, dato);
//	        initialValues.put(KEY_BLODSUKKERVAERDI, blodsukkervaerdi);
//	        initialValues.put(KEY_BLODSUKKERSTATUS, blodsukkerstatus);
//	        initialValues.put(KEY_NOTE, note);
//	        return db.insert(DATABASE_TABLE, null, initialValues);
//	    }


	    //---deletes a particular record---
	    public boolean sletMaaling(long rowSang) 
	    {
	        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowSang, null) > 0;
	    }
	    
	    //deletes the whole db
	    public boolean deleteEntireDB (){
//	    	return db.deleteDatabase(DATABASE_NAME);
	    	return context.deleteDatabase(DATABASE_NAME);
	    	
	    }

	    //---retrieves all the records---
	    public Cursor getAlleMaalinger()
	    {
	    	return db.query(DATABASE_TABLE, new String[] {
	    			KEY_ROWID,
	    			KEY_TID,
	    			KEY_DATO,
	                KEY_BLODSUKKERVAERDI,
	                KEY_BLODSUKKERSTATUS,
	                KEY_NOTE}, 
	                null, null, null, null, KEY_ROWID+" ASC");
	    }
	    
	    public Cursor getAlleMaalingerIterated(){
	    	Cursor mCursor = 
	    			db.query(DATABASE_TABLE, new String[] {
	    					KEY_ROWID, 
	    	    			KEY_TID,
	    	    			KEY_DATO,
	    	                KEY_BLODSUKKERVAERDI,
	    	                KEY_BLODSUKKERSTATUS,
	    	                KEY_NOTE}, 
	    	                null, null, null, null,KEY_ROWID+" DESC");
	    			if (mCursor != null) {
	    				mCursor.moveToFirst();
	    			}
	    			return mCursor;
	    }

	    //---henter en måling---
	    public Cursor getMaaling(long rowId) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_TABLE, new String[] {
	                		KEY_ROWID,
	    	    			KEY_TID,
	    	    			KEY_DATO,
	    	                KEY_BLODSUKKERVAERDI,
	    	                KEY_BLODSUKKERSTATUS,
	    	                KEY_NOTE}, 
	                KEY_ROWID + "=" + rowId, null,
	                null, null, null ,KEY_ROWID+" DESC");
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }
	    
	    
	    public boolean checkForDbContent(){
	    	boolean anydata=true;
	    	Cursor cur = db.rawQuery("SELECT COUNT(*) FROM "+DATABASE_TABLE, null);
	    	if (cur != null){
	    	    cur.moveToFirst();
	    	    if (cur.getInt(0) == 0) {
	    	      anydata=false;// Empty 
	    	    }
	    	}
	    	return anydata;
	    }
	    
	    public boolean updateMaalingNote(long rowId, String note){
	    	ContentValues setNote = new ContentValues();
			setNote.put(KEY_NOTE, note);
	    	return db.update(DATABASE_TABLE, setNote, KEY_ROWID+"="+rowId, null)>0;
	    	
	    }

	}