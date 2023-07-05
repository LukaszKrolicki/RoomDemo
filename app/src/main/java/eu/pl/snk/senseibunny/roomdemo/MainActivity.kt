package eu.pl.snk.senseibunny.roomdemo

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Delete
import eu.pl.snk.senseibunny.roomdemo.databinding.ActivityMainBinding
import eu.pl.snk.senseibunny.roomdemo.databinding.DeleteDialogBinding
import eu.pl.snk.senseibunny.roomdemo.databinding.UpdateDialogBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
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

           val itemAdapter= ItemAdapter(personList, {updateId->updateRecordDialog(updateId,personDao)}, {deleteId->deleteRecordDialog(deleteId,personDao)})

            binding?.rvData?.layoutManager=LinearLayoutManager(this)
            binding?.rvData?.adapter=itemAdapter
            binding?.rvData?.visibility= View.VISIBLE


        }
    }

    private fun updateRecordDialog(id:Int, personDao:PersonDao){
        val updateDialog= Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog)
        val binding=UpdateDialogBinding.inflate(layoutInflater)
        updateDialog.setContentView(binding.root)

        lifecycleScope.launch{
            personDao.fetchAllEmployessById(id).firstOrNull().also{
                binding.nameInputUpdate.setText(it?.name)
                binding.emailInputUpdate.setText(it?.email)
            }
        }

        binding.finishbutton1.setOnClickListener{
            val name = binding.nameInputUpdate.text.toString()
            val email = binding.emailInputUpdate.text.toString()

            if(name.isNotEmpty() and email.isNotEmpty()){
                lifecycleScope.launch{
                    personDao.update(PersonEntity(id,name,email))
                    updateDialog.dismiss()
                }
            }
        }

        binding.finishbutton2.setOnClickListener{
            updateDialog.dismiss()
        }

        updateDialog.show()
    }

    private fun deleteRecordDialog(id:Int, personDao:PersonDao){
        val deleteDialog= Dialog(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog)
        val binding=DeleteDialogBinding.inflate(layoutInflater)
        deleteDialog.setContentView(binding.root)

        binding.finishbutton1.setOnClickListener{
                lifecycleScope.launch{
                    personDao.delete(PersonEntity(id))

                    deleteDialog.dismiss()

                }

        }

        binding.finishbutton2.setOnClickListener{
            deleteDialog.dismiss()
        }

        deleteDialog.show()


    }




}