package com.example.myfavyoutubechannel.handlers

import com.example.myfavyoutubechannel.models.TopChannels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TopChannelHandlers {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var channelRef: DatabaseReference

    init {
        channelRef = database.getReference("channels")
    }

    fun create(channel: TopChannels): Boolean {
        val id = channelRef.push().key
        channel.id = id

        channelRef.child(id!!).setValue(channel)
        return true
    }

    fun update(channel: TopChannels): Boolean {
        channelRef.child(channel.id!!).setValue(channel)
        return true
    }

    fun delete(channel: TopChannels): Boolean {
        channelRef.child(channel.id!!).removeValue()
        return true
    }
}