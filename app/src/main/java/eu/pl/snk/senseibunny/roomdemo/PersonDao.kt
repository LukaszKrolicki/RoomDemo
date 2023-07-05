package eu.pl.snk.senseibunny.roomdemo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao { //funtions used to query the dataBase

    @Insert //we create coorutine becasue it takes time, and should be done on a background
    suspend fun insert(personEntity: PersonEntity)

    @Update
    suspend fun update(personEntity: PersonEntity)

    @Delete
    suspend fun delete(personEntity: PersonEntity)

    @Query("SELECT * FROM `person-table`") //you can retrieve all data too
    fun fetchAllEmployess():Flow<List<PersonEntity>> //flow is part of coroutines that store data that can be changed on runtime, you dont have to notify that it changed

    @Query("SELECT * FROM `person-table` WHERE id=:id") //returns one row
    fun fetchAllEmployessById(id:Int):Flow<PersonEntity>
}