package pl.mzyga4.emulatorfrontend;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import pl.mzyga4.emulatorfrontend.game_system_list.GameSystemFragment;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    final int NAV_HOST_FRAGMENT = R.id.nav_host_fragment_content_main;
    final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public final int REQUEST_PERMISSIONS = 138;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Navigation.findNavController(this, NAV_HOST_FRAGMENT);

        requestPermissions();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (getForegroundFragment() instanceof GameSystemFragment)
            ((GameSystemFragment) getForegroundFragment()).onKeyDown(keyCode);

        return super.onKeyDown(keyCode, event);
    }

    public Fragment getForegroundFragment() {
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(NAV_HOST_FRAGMENT);
        return navHostFragment == null
                ? null
                : navHostFragment.getChildFragmentManager().getFragments().get(0);
    }

    public boolean requestPermissions() {
        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            if (!hasPermissions()) {
                isPermissionOn = false;
                requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            }
        }

        return isPermissionOn;
    }

    public boolean hasPermissions() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String permission) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, permission));
    }

}