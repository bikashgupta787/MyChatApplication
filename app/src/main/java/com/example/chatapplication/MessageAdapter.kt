package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context : Context, val messageList:ArrayList<MessageModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1;
    val ITEM_SENT = 2;

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.msg_receive,parent,false)
            return ReceiveViewHolder(view)
        } else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.msg_sent,parent,false)
            return SentViewHolder(view)

        }    }

    override fun getItemViewType(position: Int): Int {
        val currentMsg = messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMsg.senderId)){
            return ITEM_SENT
        }else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMsg = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.sentMsg.text = currentMsg.message

        } else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMsg.text = currentMsg.message
        }

    }


    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMsg = itemView.findViewById<TextView>(R.id.txt_sent_msg)

    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val receiveMsg = itemView.findViewById<TextView>(R.id.txt_receive_msg)
    }
}