package com.purplepath.purplepath.document.adapter;

import androidx.annotation.NonNull;

/**
 * Created by pravinr on 8/24/17.
 */

interface DownloadInterface {
    void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
