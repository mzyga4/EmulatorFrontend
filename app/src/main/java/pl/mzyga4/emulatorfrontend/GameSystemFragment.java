package pl.mzyga4.emulatorfrontend;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment representing a list of Items.
 */
public class GameSystemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameSystemFragment() { }

    public static GameSystemFragment newInstance(int columnCount) {
        GameSystemFragment fragment = new GameSystemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_system_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;

            LinearLayoutManager layoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);

            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setAdapter(
                    new GameSystemRecyclerViewAdapter( requireActivity(),
                            JsonParser.getSampleData(requireContext()) )
            );
        }
        return view;
    }

    public void onKeyDown(int keyCode, KeyEvent event) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

        if(layoutManager != null && mRecyclerView.getAdapter() != null){
            Log.i("GSF", "layoutManager - "
                    + layoutManager.findFirstVisibleItemPosition() + "\n" +
                    + layoutManager.findFirstCompletelyVisibleItemPosition() + "\n" +
                    + layoutManager.findLastVisibleItemPosition() + "\n" +
                    + layoutManager.findLastCompletelyVisibleItemPosition() + "\n"
            );

            int lastElementPos = mRecyclerView.getAdapter().getItemCount() - 1;
            int currentPos = layoutManager.findFirstVisibleItemPosition();

            switch(keyCode){
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if(currentPos == lastElementPos) {
                        Log.i("GSF", "layoutManager - KEYCODE_DPAD_RIGHT");
                        mRecyclerView.scrollToPosition(0);
                        mRecyclerView.invalidate();
                    }else
                        mRecyclerView.smoothScrollToPosition(currentPos + 1);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if(currentPos == 0) {
                        Log.i("GSF", "layoutManager - KEYCODE_DPAD_LEFT");
                        mRecyclerView.scrollToPosition(lastElementPos);
                    }else
                        mRecyclerView.smoothScrollToPosition(currentPos -1);
                        break;
            }
        }
    }
}