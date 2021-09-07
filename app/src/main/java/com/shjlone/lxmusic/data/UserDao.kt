package com.shjlone.lxmusic.data

import androidx.room.*

/**
 * @author: a564
 * @Date 2021/5/19
 */
@Dao
interface UserDao {

    @Query("SELECT COUNT(*) FROM " + "user")
    fun count(): Int

    @Insert
    fun insert(user: User?): Long

    @Delete
    fun delete(user:User)

    /**
     * Update the Music. The Music is identified by the row ID.
     *
     * @param Music The Music to update.
     * @return A number of Musics updated. This should always be `1`.
     */
    @Update
    fun update(user: User?): Int
}