package com.shjlone.lxmusic.data

import android.database.Cursor
import androidx.room.*

/**
 * 音乐信息表，表名：music
 *
 * @author: a564
 * @Date 2021/5/19
 */
@Dao
interface MusicDao {

    /**
     * Counts the number of Musics in the table.
     *
     * @return The number of Musics.
     */
    @Query("SELECT COUNT(*) FROM " + "music")
    fun count(): Int

    /**
     * Inserts a Music into the table.
     *
     * @param Music A new Music.
     * @return The row ID of the newly inserted Music.
     */
    @Insert
    fun insert(Music: Music?): Long

    /**
     * Inserts multiple Musics into the database
     *
     * @param Musics An array of new Musics.
     * @return The row IDs of the newly inserted Musics.
     */
    @Insert
    fun insertAll(Musics: Array<Music?>?): LongArray?

    /**
     * Select all Musics.
     *
     * @return A [Cursor] of all the Musics in the table.
     */
    @Query("SELECT * FROM " + "music")
    fun selectAll(): List<Music>

    @Query("SELECT * FROM music WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Music>


    /**
     * Delete a Music by the ID.
     *
     * @param id The row ID.
     * @return A number of Musics deleted. This should always be `1`.
     */
    @Query("DELETE FROM " + "music" + " WHERE uid" + " = :id")
    fun deleteById(id: Long): Int

    @Delete
    fun delete(music: Music)

    /**
     * 删除所有数据
     */
    @Query("delete from " + "music")
    fun deleteAll()

    /**
     * Update the Music. The Music is identified by the row ID.
     *
     * @param Music The Music to update.
     * @return A number of Musics updated. This should always be `1`.
     */
    @Update
    fun update(Music: Music?): Int
}