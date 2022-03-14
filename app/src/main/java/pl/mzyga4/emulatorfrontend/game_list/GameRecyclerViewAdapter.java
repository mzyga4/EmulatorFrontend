package pl.mzyga4.emulatorfrontend.game_list;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.mzyga4.emulatorfrontend.databinding.FragmentGameListItemBinding;
import pl.mzyga4.emulatorfrontend.game_system_list.GameSystem;

import java.io.File;
import java.util.List;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
    private final Activity mActivity;
    private final List<Game> mItems;
    private final GameSystem mGameSystem;

    public GameRecyclerViewAdapter(Activity activity, List<Game> items, GameSystem gameSystem) {
        mActivity = activity;
        mItems = items;
        mGameSystem = gameSystem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentGameListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mItems.get(position);
        holder.mIdView.setText(mItems.get(position).id);
        holder.mContentView.setText(mItems.get(position).content);

        holder.itemView.setOnClickListener(view -> openFile(mItems.get(position)));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Game mItem;

        public ViewHolder(FragmentGameListItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void openFile(Game game) {
        try{
            File file = new File(mGameSystem.gamesListSource, game.details);
            Log.i("GCRVA/", mGameSystem.gamesListSource + " --- " + game.details);

            //Uri uri = FileProvider.getUriForFile(mActivity, mGameSystem.emulatorPackage + ".fileprovider", file);
            Uri uri = Uri.parse(file.getPath());

            //String mime = mActivity.getContentResolver().getType(uri);

            // Open file with user selected app
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "*/*");
            Intent chooser = Intent.createChooser(intent, "Wybierz odpowiedni emulator");
            mActivity.startActivity(chooser);
        }catch (Exception e){
            Log.w("GRVA/", e.getMessage());
        }
    }
}