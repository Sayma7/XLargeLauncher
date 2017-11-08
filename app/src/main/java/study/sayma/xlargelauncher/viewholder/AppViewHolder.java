package study.sayma.xlargelauncher.viewholder;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import study.sayma.xlargelauncher.databinding.AppItemBinding;

/**
 * Created by Touhid's Guest on 11/9/2017.
 */

public class AppViewHolder extends RecyclerView.ViewHolder {

    public AppItemBinding binding;

    public AppViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
