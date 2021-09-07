package com.shjlone.lxmusic.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author: a564
 * @Date 2021/5/19
 */
@Database(entities = arrayOf(Music::class, User::class), version = 1)
abstract class DatabaseDao : RoomDatabase() {
    abstract fun musicDao(): MusicDao
    abstract fun userDao(): UserDao

}