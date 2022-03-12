package pl.mzyga4.emulatorfrontend;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.fragment.app.FragmentActivity;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    final GameSystemFragment gameSystemFragment = GameSystemFragment.newInstance(1);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, gameSystemFragment)
                    .commitNow();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(gameSystemFragment != null)
            gameSystemFragment.onKeyDown(keyCode, event);

        return super.onKeyDown(keyCode, event);
    }
}