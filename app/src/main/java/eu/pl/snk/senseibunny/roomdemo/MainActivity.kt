package eu.pl.snk.senseibunny.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import eu.pl.snk.senseibunny.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val personDao = (application as PersonApp).db.personDao()

        binding?.addRecord?.setOnClickListener{
            addRecord(personDao)
        }
    }

    fun addRecord(personDao: PersonDao){
        val name= binding?.nameInput?.text.toString()
        val email = binding?.emailInput?.text.toString()

        if(email.isNotEmpty() and name.isNotEmpty()){
            lifecycleScope.launch{
                personDao.insert(PersonEntity(name=name,email=email))

                Toast.makeText(applicationContext,"Record Saved", Toast.LENGTH_LONG).show()

                binding?.nameInput?.text?.clear()
                binding?.emailInput?.text?.clear()
            }
        }
        else{
            Toast.makeText(applicationContext,"Record NOT saved", Toast.LENGTH_LONG).show()
        }
    }
}