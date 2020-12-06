package com.example.myfavyoutubechannel.models
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class TopChannels (var id: String? = "",var rank: Int? = 0, var name: String? = "", var link: String? = "", var reason: String? = "") {
    override fun toString(): String {
        return  "Rank: $rank - Title: $name\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n" +
                "Link: $link \n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \n" +
                "Reason: $reason"

    }
}