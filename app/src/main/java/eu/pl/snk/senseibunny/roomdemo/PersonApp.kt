package eu.pl.snk.senseibunny.roomdemo

import android.app.Application

class PersonApp:Application() {
    val db by lazy{
        PersonDatabase.getInstance(this)
    }
}