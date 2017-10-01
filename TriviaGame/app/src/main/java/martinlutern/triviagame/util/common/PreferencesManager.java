package martinlutern.triviagame.util.common;

import android.content.Context;
import android.content.SharedPreferences;

import martinlutern.triviagame.util.common.sqlite.SqliteManager;
import martinlutern.triviagame.util.common.sqlite.model.SqlitePreference;

/**
 * Created by martinluternainggolan on 10/1/17.
 */

public class PreferencesManager {
    private static final String TAG = "PreferencesManager";
    private static final String SAVE_TIME = "save_time";
    private static PreferencesManager instance;
    private static SqliteManager sqLiteManager;

    /**
     * Returns singleton class instance
     */
    public static PreferencesManager getInstance() {
        if (instance == null) {
            synchronized (PreferencesManager.class) {
                if (instance == null) {
                    instance = new PreferencesManager();
                }
            }
        }
        return instance;
    }

    private static boolean hasBeen24Hours(Long timestamp) {
        Long now = System.currentTimeMillis();
        long diff = now - timestamp;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays >= 1;
    }

    private static boolean hasBeenXMins(Long timestamp, int mins) {
        Long now = System.currentTimeMillis();
        long diff = now - timestamp;
        long diffTimes = diff / (mins * 60 * 1000);
        return diffTimes >= 1;
    }

    public static boolean saveAsString(Context context, String key, String value) {
        if (context == null || key == null || key.isEmpty()) {
            return false;
        }
        if (key.startsWith("me_")) {
            if (sqLiteManager == null) {
                sqLiteManager = new SqliteManager(context);
            }
            saveAsStringToSQLite(key, value);
            return true;
        } else {
            SharedPreferences prefs = android.preference.PreferenceManager
                    .getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, value);
            editor.putLong(SAVE_TIME + key, System.currentTimeMillis());
            return editor.commit();
        }
    }

    private static void saveAsStringToSQLite(String key, String value) {
        sqLiteManager.upsertPreference(new SqlitePreference(key, value));
    }

    public static boolean saveAsInt(Context context, int key, int value) {
        if (context == null || key == 0) {
            return false;
        }
        SharedPreferences prefs = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(String.valueOf(key), value);
        editor.putLong(SAVE_TIME + key, System.currentTimeMillis());
        return editor.commit();
    }

    public static int getAsInt(Context context, int key,
                               boolean oneDayExpiration) {
        if (context == null || key == 0) {
            return 0;
        }
        SharedPreferences prefs = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        if (!prefs.contains(String.valueOf(key))) {
            return 0;
        }
        long saveTime = prefs.getLong(SAVE_TIME + key, 0);
        if (oneDayExpiration && hasBeen24Hours(saveTime)) {
            // remove saved channels after one day expirations
            return 0;
        }
        return prefs.getInt(String.valueOf(key), 0);
    }

    public static String getAsString(Context context, String key,
                                     boolean oneDayExpiration) {
        if (context == null || key == null || key.isEmpty()) {
            return null;
        }
        if (key.startsWith("me_")) {
            if (sqLiteManager == null) {
                sqLiteManager = new SqliteManager(context);
            }

            SqlitePreference pref = sqLiteManager.getPreference(key);
            if (pref == null) {
                return null;
            }

            long saveTime = pref.getTimestamp();
            if (oneDayExpiration && hasBeen24Hours(saveTime)) {
                return null;
            }
            return pref.getValue();
        }
        SharedPreferences prefs = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        if (!prefs.contains(key)) {
            return null;
        }
        return prefs.getString(key, null);
    }

    public static String getAsString(Context context, String key, int mins) {
        if (context == null || key == null || key.isEmpty()) {
            return null;
        }

        if (key.startsWith("me_")) {
            if (sqLiteManager == null) {
                sqLiteManager = new SqliteManager(context);
            }

            SqlitePreference pref = sqLiteManager.getPreference(key);

            if (pref == null) {
                return null;
            }

            long saveTime = pref.getTimestamp();
            if (hasBeenXMins(saveTime, mins)) {
                return null;
            }
            return pref.getValue();
        }

        SharedPreferences prefs = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        if (!prefs.contains(key)) {
            return null;
        }
        long saveTime = prefs.getLong(SAVE_TIME + key, 0);
        if (hasBeenXMins(saveTime, mins)) {
            return null;
        }

        return prefs.getString(key, null);
    }

    public static boolean removeString(Context context, String key) {
        if (context == null || key == null || key.isEmpty()) {
            return false;
        }
        if (key.startsWith("me_")) {
            if (sqLiteManager == null) {
                sqLiteManager = new SqliteManager(context);
            }
            sqLiteManager.deletePreference(key);
            return true;
        }
        SharedPreferences prefs = android.preference.PreferenceManager
                .getDefaultSharedPreferences(context);
        if (!prefs.contains(key)) {
            return false;
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        return editor.commit();
    }
}
