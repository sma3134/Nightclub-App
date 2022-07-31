package group_17.nightclubapp.com.request.model


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
}
