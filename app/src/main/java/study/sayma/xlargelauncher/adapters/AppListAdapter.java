package study.sayma.xlargelauncher.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import study.sayma.xlargelauncher.R;
import study.sayma.xlargelauncher.models.AppItem;
import study.sayma.xlargelauncher.viewholder.AppViewHolder;

/**
 * Created by Touhid's Guest on 11/9/2017.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppViewHolder> {

    private Context context;
    private List<AppItem> appList;

    public AppListAdapter(Context context, List<AppItem> appList) {
        this.context = context;
        this.appList = appList;
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.app_item, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        final AppItem appItem = appList.get(position);
        holder.binding.tvNameApp.setText(appItem.getName());
        holder.binding.ivIconApp.setImageDrawable(appItem.getIcon());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = context.getPackageManager()
                        .getLaunchIntentForPackage(appItem.getPkg());
                if (launchIntent != null)
                    context.startActivity(launchIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }
}
