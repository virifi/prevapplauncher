package net.virifi.android.prevapplauncher;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class OptionalButtonClickedReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RecentTaskInfo> recentTaskList = am.getRecentTasks(5, ActivityManager.RECENT_WITH_EXCLUDED);
		
		for (int j = 1; j < recentTaskList.size(); j++) {
			RecentTaskInfo info = recentTaskList.get(j);
			Intent i = info.baseIntent;
			String packageName = i.getComponent().getPackageName();
			if (!"com.android.launcher".equals(packageName)) {
				try {
					context.startActivity(info.baseIntent);
					break;
				} catch (ActivityNotFoundException e) {
					;
				} catch (SecurityException e) {
					// Superuserを開こうとすると落ちるため
					;
				}
			}
		}
	}
}
