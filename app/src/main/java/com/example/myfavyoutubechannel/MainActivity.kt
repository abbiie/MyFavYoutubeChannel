package com.example.myfavyoutubechannel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.myfavyoutubechannel.handlers.TopChannelHandlers
import com.example.myfavyoutubechannel.models.TopChannels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var rankEdit: EditText
    lateinit var title: EditText
    lateinit var link: EditText
    lateinit var reason: EditText
    lateinit var addButton: Button
    lateinit var TopChannelHandlers: TopChannelHandlers
    lateinit var arrList: ArrayList<TopChannels>
    lateinit var ListView: ListView
    lateinit var EditChannels: TopChannels
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rankEdit = findViewById(R.id.rrank)
        title = findViewById(R.id.TitleName)
        link = findViewById(R.id.Link)
        reason = findViewById(R.id.Reason)
        addButton = findViewById(R.id.AddBtn)
        TopChannelHandlers = TopChannelHandlers()
        arrList = ArrayList()
        ListView = findViewById(R.id.TopList)
        addButton.setOnClickListener{
            val rank = rankEdit.text.toString()
            val rankconv:Int = rank.toInt()
            val title = title.text.toString()
            val link = link.text.toString()
            val reason =  reason.text.toString()
            if(addButton.text.toString()== "Add"){
                val restaurant = TopChannels(rank = rankconv, name = title, link = link, reason = reason)
                if(TopChannelHandlers.create(restaurant)){
                    Toast.makeText(applicationContext, "the channel has been added", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
            else if(addButton.text.toString() == "Update"){
                val video = TopChannels(id = EditChannels.id, rank = rankconv, name = title,  link = link, reason = reason)
                if(TopChannelHandlers.update(video)){
                    Toast.makeText(applicationContext, "Channel updated", Toast.LENGTH_SHORT).show()
                    clearFields()
                }
            }
        }
        registerForContextMenu(ListView)
    }
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.channel_options, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when(item.itemId){
            R.id.edit ->{
                EditChannels = arrList[info.position]
                val nullableInt = EditChannels.rank
                val nonnullableInt = nullableInt!!
                rankEdit.setText(""+nonnullableInt)
                title.setText(EditChannels.name)
                link.setText(EditChannels.link)
                reason.setText(EditChannels.reason)
                addButton.setText("Update")
                true
            }
            R.id.delete ->{
                if(TopChannelHandlers.delete(arrList[info.position])){
                    Toast.makeText(applicationContext, "You deleted the video from List", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else ->return super.onContextItemSelected(item) }
    }
    override fun onStart() {
        super.onStart()
        TopChannelHandlers.channelRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                arrList.clear()

                snapshot.children.forEach{
                        it -> val video = it.getValue(TopChannels::class.java)
                    arrList.add(video!!)
                }
                arrList.sortBy { it.rank }
                val adapter = ArrayAdapter<TopChannels>(applicationContext, android.R.layout.simple_list_item_1, arrList)
                ListView.adapter = adapter
            }
            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    fun clearFields(){
        rankEdit.text.clear()
        title.text.clear()
        link.text.clear()
        reason.text.clear()
        addButton.setText("Add")
    }
}