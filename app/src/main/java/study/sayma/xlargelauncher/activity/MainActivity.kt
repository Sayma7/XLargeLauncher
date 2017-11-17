package study.sayma.xlargelauncher.activity
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import study.sayma.xlargelauncher.R
import study.sayma.xlargelauncher.adapters.AppListAdapter
import study.sayma.xlargelauncher.models.AppItem
import java.util.*

class MainActivity : AppCompatActivity() {
    private//checks for flags; if flagged, check if updated system app
            //it's a system app, not interested
            //Discard this one
            //in this case, it should be a user-installed app
    val installedAppList: List<AppItem>
        get() {
            val appList = ArrayList<AppItem>()

            val pm = packageManager
            val apps = pm.getInstalledApplications(0)

            for (app in apps) {
                if (app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0) {
                    appList.add(
                            AppItem(
                                    pm.getApplicationLabel(app) as String,
                                    app.packageName,
                                    pm.getApplicationIcon(app)))
                } else if (app.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
                } else {
                    appList.add(
                            AppItem(
                                    pm.getApplicationLabel(app) as String,
                                    app.packageName,
                                    pm.getApplicationIcon(app)))
                }
            }
            return appList
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvApps.layoutManager = GridLayoutManager(this, 3)

        val appListAdapter = AppListAdapter(this, installedAppList)
        rvApps.adapter = appListAdapter

        btnAddEmNumbers.setOnClickListener({
            startActivity(
                    Intent(this@MainActivity, AddEmgNumberActivity::class.java))
        })
    }
}
