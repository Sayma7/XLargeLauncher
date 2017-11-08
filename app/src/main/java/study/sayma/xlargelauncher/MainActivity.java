package study.sayma.xlargelauncher;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import study.sayma.xlargelauncher.adapters.AppListAdapter;
import study.sayma.xlargelauncher.databinding.ActivityMainBinding;
import study.sayma.xlargelauncher.models.AppItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.rvApps.setLayoutManager(new GridLayoutManager(this, 3));

        AppListAdapter appListAdapter = new AppListAdapter(this, getInstalledAppList());
        binding.rvApps.setAdapter(appListAdapter);
    }

    private List<AppItem> getInstalledAppList() {
        List<AppItem> appList = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        for (ApplicationInfo app : apps) {
            //checks for flags; if flagged, check if updated system app
            if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                appList.add(
                        new AppItem(
                                (String) pm.getApplicationLabel(app),
                                app.packageName,
                                pm.getApplicationIcon(app)));
                //it's a system app, not interested
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //Discard this one
                //in this case, it should be a user-installed app
            } else {
                appList.add(
                        new AppItem(
                                (String) pm.getApplicationLabel(app),
                                app.packageName,
                                pm.getApplicationIcon(app)));
            }
        }
        return appList;
    }
}
