package com.System.Sonidos;

import com.comynt.launa.R;

import android.content.Context;
import android.media.MediaPlayer;

public class Sounds 
{
	boolean silent = false;
	
	public static void PlayFailed(Context context, boolean silent) 
	{
		try
		{
			if(!silent) 
			{
			       MediaPlayer failedPlayer = MediaPlayer.create(context, R.raw.failed);
		
			        failedPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
			        {
						public void onCompletion(MediaPlayer mp)
						{
							mp.release();
						}
					});
				if(failedPlayer != null)
					failedPlayer.start();
			}
		}
		catch(Exception ex){}
	}
	
	public static void PlaySuccess(Context context, boolean silent)
	{
		try
		{
			if(!silent)
			{
			       MediaPlayer player = MediaPlayer.create(context, R.raw.beep);

			        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
			        {
						public void onCompletion(MediaPlayer mp)
						{
							mp.release();
						}
					});
				if(player != null) player.start();
			}
		}
		catch(Exception ex){}
	}
}
