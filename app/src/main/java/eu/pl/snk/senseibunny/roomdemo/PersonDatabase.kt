package eu.pl.snk.senseibunny.roomdemo

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PersonEntity::class], version=1) // if we change fe. properties we must change the version
abstract class PersonDatabase:RoomDatabase() {
    abstract fun personDao():PersonDao

    companion object{
        @Volatile
        private  var INSTANCE: PersonDatabase?=null

        fun getInstance(context: Context):PersonDatabase{
            synchronized(this){
                var instance= INSTANCE

                if(instance==null){ //create instance to database only if it wasnt created already
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        "person_database"
                    ).fallbackToDestructiveMigration().build()

                    INSTANCE=instance
                }
                return instance
            }
        }
    }
}