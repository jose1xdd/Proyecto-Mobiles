package com.ufps.proyectomobile.storage

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        const val PREFS_NAME = "user_prefs"
        const val KEY_user = "user"
        const val KEY_token = "token"
        const val KEY_task_id = "taskId"
        const val KEY_task_desc = "taskdesc"
        const val KEY_task_title = "tasktittle"
        const val KEY_task_prio = "taskprio"
    }

    fun saveLoginData(username: String, token: String) {
        prefs.edit().putString(KEY_user, username).apply()
        prefs.edit().putString(KEY_token, token).apply()
    }

    fun saveTaskId(id: Int) {
        return prefs.edit().putInt(KEY_task_id, id).apply()
    }

    fun saveTaskTitle(title: String) {
        return prefs.edit().putString(KEY_task_title, title).apply()
    }

    fun saveTaskDesc(desc: String) {
        return prefs.edit().putString(KEY_task_desc, desc).apply()
    }

    fun saveTaskPrio(prio: Int) {
        return prefs.edit().putInt(KEY_task_prio, prio).apply()
    }

    fun getTaskPrio(): Int {
        return prefs.getInt(KEY_task_prio, 0)
    }

    fun getTaskTitle(): String? {
        return prefs.getString(KEY_task_title, null)
    }

    fun getTaskDesc(): String? {
        return prefs.getString(KEY_task_desc, null)
    }

    fun getTaskId(): Int {
        return prefs.getInt(KEY_task_id, 0)
    }

    fun getUser(): String? {
        return prefs.getString(KEY_user, null)
    }

    fun getToken(): String? {
        return prefs.getString(KEY_token, null)
    }


    fun clear() {
        prefs.edit().clear().apply()
    }
}
