package com.kaibltdinc.melancholian

import android.content.Context
import android.content.SharedPreferences

class Saving(context: Context) {
    val bundle = "com.kaibltdinc.melancholian"
    val HOS = "is_HOS _$bundle"
    val ADIKI = "is_ADIKI_$bundle"
    val FINALE_L = "is_FINALE _$bundle"
    val CHECKER_LINKA = "is_LINKA _$bundle"
    val AFI = "is_AFI _$bundle"
    val DOMI= "is_DOMI _$bundle"
    val OSI= "is_OSI _$bundle"
    val CHECK = "is_checker_chek _$bundle"



    private val preferences: SharedPreferences =
        context.getSharedPreferences(bundle, Context.MODE_PRIVATE)


    var checkLocas: Boolean
        get() = preferences.getBoolean(CHECK, false)
        set(value) = preferences.edit().putBoolean(CHECK, value).apply()

    var adiki_id: String
        get() = preferences.getString(ADIKI, null) ?: ""
        set(value) = preferences.edit().putString(ADIKI,(value)).apply()

    var checkerHost: Boolean
        get() = preferences.getBoolean(HOS, false)
        set(value) = preferences.edit().putBoolean(HOS, value).apply()

    var lk: String
        get() = preferences.getString(CHECKER_LINKA, null) ?: ""
        set(value) = preferences.edit()
            .putString(CHECKER_LINKA, (value)).apply()

    var isAdIdSaved: Boolean
        get() = preferences.contains(ADIKI)
        set(value) = preferences.edit().putBoolean(ADIKI, value).apply()

    var finale_l: String
        get() = preferences.getString(FINALE_L,null) ?:""
        set(value) = preferences.edit().putString(FINALE_L,(value)).apply()

    var af: String
        get() = preferences.getString(AFI,null) ?:""
        set(value) = preferences.edit().putString(AFI,(value)).apply()

    var domik: String
        get() = preferences.getString(DOMI,null) ?:""
        set(value) = preferences.edit().putString(DOMI,(value)).apply()

    var osi: String
        get() = preferences.getString(OSI,null) ?:""
        set(value) = preferences.edit().putString(OSI,(value)).apply()




}