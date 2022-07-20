package group_17.nightclubapp.com.request.model


data class Request(val request: String? = null,
                   val type: String? = null,
                   val clubID: Long? = null,
                   val date: Long? = null) {

    companion object {
        const val SONG_REQUEST = "SONG_REQUEST"
        const val ANNOUNCEMENT_REQUEST = "ANNOUNCEMENT_REQUEST"
    }

    fun isValid(): Boolean {
        return request != null && type != null && clubID != null && date != null
    }
}
