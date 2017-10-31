/*
 * Created by Talab Omar on 10/30/17 4:28 PM
 * Copyright (c) 2017. All rights reserved.
 *
 * Last modified 10/30/17 4:28 PM
 */

package com.codertal.studybook.util;

import android.os.SystemClock;
import android.util.SparseLongArray;

/**
 * This class allows a single click and prevents multiple clicks on
 * the same button in rapid succession. Setting enabled to false is inadequate
 * because click events may still be queued up.
 */
public class ClickManager {

    public SparseLongArray lastClickedTimes;
    private static final int MINIMUM_DELAY_MILLISECONDS = 1000;

    public ClickManager() {
        lastClickedTimes = new SparseLongArray();
    }

    public boolean isClickable(int viewId){
        if(lastClickedTimes.indexOfKey(viewId) < 0){
            //View does not exist

            lastClickedTimes.put(viewId, SystemClock.elapsedRealtime());
            return true;
        }else{
            long lastViewClickedTime = lastClickedTimes.get(viewId);

            if (SystemClock.elapsedRealtime() - lastViewClickedTime < MINIMUM_DELAY_MILLISECONDS){
                return false;
            }else{
                lastClickedTimes.put(viewId, SystemClock.elapsedRealtime());
                return true;
            }
        }
    }
}