package study.sayma.xlargelauncher.services

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import study.sayma.xlargelauncher.R
import study.sayma.xlargelauncher.activity.EmergencySendSmsActivity
import study.sayma.xlargelauncher.utils.C


/**
 * Created by Touhid's Guest on 11/17/2017.
 */
class EmergencyWidgetService : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val intent = Intent(context, EmergencySendSmsActivity::class.java).setAction(C.KEY_EMERGENCY)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val rv = RemoteViews(context?.packageName, R.layout.widget_emergency_button)
        rv.setOnClickPendingIntent(R.id.flEmergencyClicker, pendingIntent)
        appWidgetManager?.updateAppWidget(appWidgetIds, rv)
    }
}