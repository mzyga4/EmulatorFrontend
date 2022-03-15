package pl.mzyga4.emulatorfrontend.game_system_list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import pl.mzyga4.emulatorfrontend.game_list.GameListFragment;
import pl.mzyga4.emulatorfrontend.util.JsonParser;
import pl.mzyga4.emulatorfrontend.R;

/**
 * A fragment representing a list of Items.
 */
public class GameSystemFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";

    private RecyclerView mRecyclerView;
    private List<GameSystem> mData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameSystemFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_system_list, container, false);
        setupAdapter(view);

        return view;
    }

    @SuppressWarnings("unchecked")
    private void setupAdapter(View view){
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;

            LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);

            mRecyclerView.setLayoutManager(layoutManager);

            mData = (List<GameSystem>) JsonParser.getListOfTypeFromJson(
                    requireContext(),
                    new TypeToken<List<GameSystem>>() {}.getType(),
                    "gs.json"
            );

            mRecyclerView.setAdapter(new GameSystemRecyclerViewAdapter(requireActivity(), mData));
        }
    }

    public void onKeyDown(int keyCode) {
        if(mRecyclerView == null) return;
        Log.i("GSF", "setupAdapter - onKeyDown - " + keyCode);
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

        if(layoutManager != null && mRecyclerView.getAdapter() != null){
            int lastElementPos = mRecyclerView.getAdapter().getItemCount() - 1;
            int currentPos = layoutManager.findFirstVisibleItemPosition();

            switch(keyCode){
                case KeyEvent.KEYCODE_DPAD_CENTER:
                    String alias = mData.get(currentPos).emulatorAlias;
                    if(alias != null && !alias.isEmpty())
                        goToGameList(currentPos);
                    else
                        GameSystemRecyclerViewAdapter.startApp(requireActivity(), mData.get(currentPos).emulatorPackage);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(currentPos == lastElementPos) {
                        mRecyclerView.scrollToPosition(0);
                        mRecyclerView.invalidate();
                    }else
                        mRecyclerView.smoothScrollToPosition(currentPos + 1);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(currentPos == 0) {
                        mRecyclerView.scrollToPosition(lastElementPos);
                    }else
                        mRecyclerView.smoothScrollToPosition(currentPos -1);
                        break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    goToGameList(currentPos);
                    break;
            }
        }
    }

    private void goToGameList(int currentPos){
        Bundle bundle = new Bundle();
        bundle.putSerializable(GameListFragment.ARG_GAME_SYSTEM_OBJ,
                mData.get(currentPos));

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_gameSystemFragment_to_gameListFragment, bundle);
    }
}