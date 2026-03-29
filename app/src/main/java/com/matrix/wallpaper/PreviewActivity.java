package com.matrix.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class PreviewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ouvre directement le sélecteur de fond d'écran sur notre service
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            new ComponentName(this, MatrixWallpaperService.class)
        );
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish(); // Ferme l'activité après sélection
    }
}
