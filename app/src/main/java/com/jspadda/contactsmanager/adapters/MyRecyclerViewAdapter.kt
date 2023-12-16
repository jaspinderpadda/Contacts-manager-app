package com.jspadda.contactsmanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jspadda.contactsmanager.R
import com.jspadda.contactsmanager.databinding.CardItemBinding
import com.jspadda.contactsmanager.model.User

class MyRecyclerViewAdapter(
    private val userList:List<User>,
    private val clickListener: (User) ->Unit
) : RecyclerView.Adapter<MyViewModel>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.
                inflate(layoutInflater, R.layout.card_item, parent, false)
        return MyViewModel(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        holder.bind(userList[position], clickListener)
    }


}

class MyViewModel(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(user: User, clickListener: (User) -> Unit){
        binding.nameTxt.text = user.name
        binding.emailTxt.text = user.email

        binding.listItemView.setOnClickListener{
            clickListener(user)
        }
    }

}