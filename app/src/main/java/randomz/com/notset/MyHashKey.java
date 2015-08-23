package randomz.com.notset;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nilesh Verma on 8/22/2015. $$$$ this activity is for generating the hash key
 * // cahnge the name in application in manifest
 */
public class MyHashKey extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        printHashkey();
    }

    public void printHashkey(){

        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "randomz.com.notset",  //$$$$  $$$$  //  enter your Package name
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) { //only import this package import android.content.pm.Signature;
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("HIT", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
