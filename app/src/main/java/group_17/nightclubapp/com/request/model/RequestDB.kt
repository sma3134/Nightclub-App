package group_17.nightclubapp.com.request.model

import java.text.SimpleDateFormat
import java.util.*


data class RequestDB(val request: String? = null,
                   val type: String? = null,
                   val clubID: String? = null,
                   val date: Long? = null) {

    companion object {
        const val SONG_REQUEST = "SONG_REQUEST"
        const val ANNOUNCEMENT_REQUEST = "ANNOUNCEMENT_REQUEST"
    }

    fun isValid(): Boolean {
        return request != null && type != null && clubID != null && date != null
    }

    fun getRequestText(): String {
        if (!isValid()) return ""
        return if (type == SONG_REQUEST) {
            "Song Request: $request"
        } else {
            "Announcement Request: $request"
        }
    }

    fun getDateText(): String {
        if (date == null) return ""
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val format = SimpleDateFormat("MMM d h:mm aa")

        return format.format(calendar.time).removePrefix("0")
    }
}
