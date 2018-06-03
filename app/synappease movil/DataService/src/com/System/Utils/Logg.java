package com.System.Utils;

import android.util.Log;

public final class Logg
{
  private static final String LOG_ID_STRING = "com.comynt";
    
  public static final boolean logToSystemOut;

  static 
  {
    logToSystemOut = false; // needs to be false for deployment to blackberry device and true for debuging on simulator.
  }

  private Logg() {
  }

  /**
   * Logs the given message at the debug level.
   */
  public static void debug(String message)
  {
	  Log.d(LOG_ID_STRING, message);
  }

  /**
   * Logs the given message at the info level.
   */
  public static void info(String message)
  {
      Log.i(LOG_ID_STRING, message);  
  }

  /**
   * Logs the given message at the error level.
   */
  public static void error(String message) 
  {
      Log.e(LOG_ID_STRING, message);  
  }

} 

