package eu.pl.snk.senseibunny.roomdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import eu.pl.snk.senseibunny.roomdemo.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
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

        lifecycleScope.launch{//it works in background
            personDao.fetchAllEmployess().collect(){
                val list = ArrayList(it)//we create ArrayList from it
                setupListofDataIntoRecyclerView(list,personDao)
            }
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

    private fun setupListofDataIntoRecyclerView(personList: ArrayList<PersonEntity>, personDao: PersonDao){
        if(personList.isNotEmpty()){
            val itemAdapter= ItemAdapter(personList)

            binding?.rvData?.layoutManager=LinearLayoutManager(this)
            binding?.rvData?.adapter=itemAdapter
            binding?.rvData?.visibility= View.VISIBLE
        }
        else{

        }

    }
}