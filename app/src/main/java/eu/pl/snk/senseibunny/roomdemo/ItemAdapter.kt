package eu.pl.snk.senseibunny.roomdemo

import android.animation.ValueAnimator.AnimatorUpdateListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import eu.pl.snk.senseibunny.roomdemo.databinding.DataItemBinding

class ItemAdapter(private val items: ArrayList<PersonEntity>)://,private val updateListener:(id:Int)->Unit,
                  //private val deleteListner:(id:Int)->Unit
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(binding: DataItemBinding): RecyclerView.ViewHolder(binding.root){
        val llMain=binding.main
        val name=binding.nameText
        val email=binding.emailText
        val update=binding.edit
        val delete=binding.bin
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) { // what should do for every item when view is bind
        val context = holder.itemView.context
        val item=items[position]

        holder.name.text=item.name
        holder.email.text=item.email

        if(position%2!=0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        }

        holder.update.setOnClickListener{
            //updateListener.invoke(item.id)
        }

        holder.delete.setOnClickListener(){
            //deleteListner.invoke(item.id)
        }
    }
}