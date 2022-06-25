package com.sdk.planetnew.domain.manager

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sdk.planetnew.data.util.Constants
import java.lang.reflect.Type

class MySharedPref(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(Constants.MY_PREF, Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveArrayList(list: java.util.ArrayList<String?>?) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(Constants.MY_KEY, json)
        editor.apply()
    }

    fun getArrayList(): java.util.ArrayList<String?>? {
        val gson = Gson()
        val json: String? = sharedPreferences.getString(Constants.MY_KEY, null)
        val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun removeNum() {
        val editor = sharedPreferences.edit()
        editor.remove(Constants.MY_KEY)
        editor.apply()
    }

    fun clearAll() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}