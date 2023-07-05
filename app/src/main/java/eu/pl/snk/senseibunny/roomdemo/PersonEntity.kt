package eu.pl.snk.senseibunny.roomdemo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person-table")
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) //it makes sure every entry is unique
    var id: Int=0,

    var name: String="",
    @ColumnInfo(name="email-id")//specify the name of column, if not it will be named after val..
    var email: String=""
)

