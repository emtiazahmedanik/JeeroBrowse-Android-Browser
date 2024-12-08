package com.andev.JeeroBrowse;

import android.os.StatFs;
import android.os.Environment;

import java.io.File;

public class StorageUtils {

    // Method to get available space in bytes
    public static long getAvailableSpaceInBytes() {
        // Check the storage directory (e.g., external storage)
        File storageDir = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(storageDir.getPath());

        // Get the available blocks and block size in bytes
        long availableBlocks = statFs.getAvailableBlocksLong();
        long blockSize = statFs.getBlockSizeLong();

        // Return the available space in bytes
        return availableBlocks * blockSize;
    }

    // Method to check if there's enough space for the file
    public static boolean isEnoughSpaceForDownload(long fileSizeBytes) {
        long availableSpace = getAvailableSpaceInBytes();
        return availableSpace > fileSizeBytes;  // True if there's enough space
    }
}
