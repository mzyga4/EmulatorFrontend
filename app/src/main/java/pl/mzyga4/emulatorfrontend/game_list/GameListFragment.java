package pl.mzyga4.emulatorfrontend.game_list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pl.mzyga4.emulatorfrontend.R;
import pl.mzyga4.emulatorfrontend.game_system_list.GameSystem;

/**
 * A fragment representing a list of Items.
 */
public class GameListFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_GAME_SYSTEM_OBJ = "game-system-obj";

    private int mColumnCount = 1;
    private GameSystem mGameSystem;

    private RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GameListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mGameSystem = (GameSystem) getArguments().getSerializable(ARG_GAME_SYSTEM_OBJ);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<Game> contentList = new ArrayList<>();
            File[] fileList = new File(mGameSystem.gamesListSource).listFiles();

            setupFileList(fileList, contentList, false);
            if(mGameSystem.gamesListSource2 != null && !mGameSystem.gamesListSource2.isEmpty()) {
                File[] fileList2 = new File(mGameSystem.gamesListSource2).listFiles();
                setupFileList(fileList2, contentList, true);
            }

            contentList.sort(Comparator.comparing(Game::getContent));
            mRecyclerView.setAdapter(new GameRecyclerViewAdapter(requireActivity(), contentList, mGameSystem));
        }
        return view;
    }

    private void setupFileList(File[] fileList, List<Game> contentList, boolean bAddPrefix){
        int i = 0;
        if(fileList != null)
            for(File file : fileList) {
                i++;

                String fullFileName = file.getName();
                if(file.isDirectory()) {
                    String orgFileName = file.getName();
                    File[] filesInDir = new File(file.getPath()).listFiles(
                            (file1, s) ->
                                    s.contains(".cue") || s.contains(".gdi") || s.contains(".cdi")
                    );

                    if(filesInDir != null && filesInDir.length > 0) {
                        file = filesInDir[0];
                        fullFileName = orgFileName + "/" + file.getName();
                    }
                }

                String filename = file.getName().replace("vs.", "vs");
                String[] fn = filename.split("\\.");
                String title = clearFromDummies(fn[0]);
                boolean noDummies = !title.contains("vmu ") && !title.equals("emu") && !title.equals("data")
                        && !file.getName().endsWith(".ncq");
                boolean ifSegaMS = !mGameSystem.imgSystemLogo.equals("system_sms") || filename.endsWith(".sms");
                boolean ifSegaMD = !mGameSystem.imgSystemLogo.equals("system_smd") || !filename.endsWith(".sms");

                if(bAddPrefix)
                    fullFileName = "_use_src_2_" + fullFileName;

                if(noDummies && ifSegaMS && ifSegaMD) {
                    Log.i("GLF", file.getName());
                    contentList.add(new Game(String.valueOf(i), title, fullFileName));
                }
            }
    }

    private String clearFromDummies(String text){
        return text.replaceAll("_", " ")
                .replaceAll("\\(Beta\\)", "")
                .replaceAll("\\(World\\)", "")
                .replaceAll("\\(Japan, Europe\\)", "")
                .replaceAll("\\(USA, Europe\\)", "")
                .replaceAll("\\(USA, Korea\\)", "")
                .replaceAll("\\(Europe\\)", "")
                .replaceAll("\\(English v1", "")
                .replaceAll("\\(Japan\\)", "")
                .replaceAll("\\(China\\)", "")
                .replaceAll("\\(UE\\)", "")
                .replaceAll("\\(US\\)", "")
                .replaceAll("\\(PAL\\)", "")
                .replaceAll("\\(En,Es\\)", "")
                .replaceAll("\\(USA\\)", "")
                .replaceAll("\\(E\\)", "")
                .replaceAll("\\(M3\\)", "")
                .replaceAll("\\(U\\)", "")
                .replaceAll("\\(JU\\)", "")
                .replaceAll("\\[!]", "")
                .replaceAll("\\[2P]", "")
                .replaceAll("\\[b1]", "")
                .replaceAll("\\[GDI] ", "")
                .replaceAll("\\[GDI]", "")
                .replaceAll("\\[hl]", "")
                .replaceAll("\\[hI]", "")
                .replaceAll("\\[10S 51035]", "")
                .replaceAll("\\[1S T-8109N]", "")
                .replaceAll("\\(V1", "")
                .replaceAll("\\(J\\)", "")
                .replaceAll("\\(54227\\)", "")
                .replaceAll(" \\(RE\\)", "")
                .replaceAll("GDI v1 0 0", "")
                .replaceAll("\\(En,Ja,Zh\\)", "")

                // Translate weird titles
                .replace("Akumajou Dracula X - Chi No Rondo", "Castlevania: Rondo of Blood")
                .replace("Daimakaimura", "Daimakaimura (Ghouls 'N Ghosts)")
                .replace("NewZealand", "New Zealand");
    }
}