package com.shjlone.lxmusic.util.db

import android.content.Context
import androidx.room.Room
import com.shjlone.lxmusic.data.DatabaseDao

/**
 * 数据库工具类
 * @author: a564
 * @Date 2021/5/19
 */
class DatabaseUtil {


    companion object {
        var db: DatabaseDao? = null

        fun getInstance(context: Context): DatabaseDao {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    DatabaseDao::class.java, "music.db"
                ).build()
            }
            return db as DatabaseDao
        }

    }
}