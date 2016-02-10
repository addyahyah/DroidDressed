package edu.rosehulman.harrislb.droiddressed.ImgurStorage;

/**
 * Created by harrislb on 2/9/2016.
 */
import android.util.Log;

import edu.rosehulman.harrislb.droiddressed.Constants;


/**
 * Created by AKiniyalocts on 1/16/15.
 *
 * Basic logger bound to a flag in Constants.java
 */
public class aLog {
    public static void w (String TAG, String msg){
        if(Constants.LOGGING) {
            if (TAG != null && msg != null)
                Log.w(TAG, msg);
        }
    }

}