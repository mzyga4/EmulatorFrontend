package pl.mzyga4.emulatorfrontend.game_system_list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.mzyga4.emulatorfrontend.databinding.FragmentGameSystemItemBinding;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GameSystem}.
 */
public class GameSystemRecyclerViewAdapter extends RecyclerView.Adapter<GameSystemRecyclerViewAdapter.ViewHolder> {
    private final Activity mActivity;
    private final List<GameSystem> mValues;

    public GameSystemRecyclerViewAdapter(Activity activity, List<GameSystem> items) {
        mActivity = activity;
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentGameSystemItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mTitleView.setText(holder.mItem.title);
        holder.mContentView.setText(holder.mItem.content);

        int logoBgColor = Color.parseColor(holder.mItem.imgSystemLogoBackground);
        holder.mSystemLogo.setBackgroundColor(logoBgColor);

        int logoResId = getResourceId(holder.mItem.imgSystemLogo);
        int hardwareResId = getResourceId(holder.mItem.imgSystemHardware);
        int softwareResId = getResourceId(holder.mItem.imgSystemSoftware);
        int manufacturerResId = getResourceId(holder.mItem.imgSystemManufacturer);

        holder.mSystemLogo.setImageResource(logoResId);
        holder.mSystemHardwareImg.setImageResource(hardwareResId);
        holder.mSystemSoftwareImg.setImageResource(softwareResId);
        holder.mSystemManufacturerImg.setImageResource(manufacturerResId);

        holder.itemView.setOnClickListener(view -> startApp(holder.mItem.emulatorPackage));
    }

    private int getResourceId(String drawableName) {
        if(drawableName == null || drawableName.isEmpty()) return 0;

        try{
            return mActivity.getResources().getIdentifier(drawableName, "drawable", mActivity.getPackageName());
        }catch (Exception e){
            return 0;
        }
    }

    private void startApp(String packageName){
        Intent launchIntent = mActivity.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) { //null pointer check in case package name was not found
            mActivity.startActivity(launchIntent);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public GameSystem mItem;

        public final TextView mTitleView;
        public final TextView mContentView;
        public final ImageView mSystemLogo;
        public final ImageView mSystemHardwareImg;
        public final ImageView mSystemSoftwareImg;
        public final ImageView mSystemManufacturerImg;

        public ViewHolder(FragmentGameSystemItemBinding binding) {
            super(binding.getRoot());
            mTitleView = binding.title;
            mContentView = binding.content;
            mSystemLogo = binding.ivSystemLogo;
            mSystemHardwareImg = binding.ivSystemHardware;
            mSystemSoftwareImg = binding.ivSystemSoftware;
            mSystemManufacturerImg = binding.ivManufacturer;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}