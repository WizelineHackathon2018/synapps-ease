package com.System.SqLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper
{
	  private static final String DATABASE_NAME = "bdRMX.db";
	  private static final int DATABASE_VERSION = 1;

	  //public static final String TABLE_COMMENTS = "comments";
	  //public static final String COLUMN_ID = "_id";
	  //public static final String COLUMN_COMMENT = "comment";
	  // Database creation sql statement
	  //private static final String DATABASE_CREATE = "create table " + TABLE_COMMENTS + "(" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_COMMENT + " text not null);";

	  public SQLiteManager(Context context)
	  {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database)
	  {
	    //database.execSQL(DATABASE_CREATE);
		  //alguna validacion por aca
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	  {
	    //db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	    //onCreate(db);
		//alguna validacion por aca
	  }
	  
	  //metodos de control de la bd, sql builder
	  
	  //verificar y crear tabla
	  
	  //select
	  
	  //insert
	  
	  //update
	  
	  //delete
	  
	  //delete all
	  
	  
	  
}

