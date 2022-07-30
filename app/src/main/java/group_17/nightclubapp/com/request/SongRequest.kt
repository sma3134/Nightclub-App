package group_17.nightclubapp.com.request

data class SongRequest (
    var data: List<Song>? = null,
    var total: Int = 0,
    var next: String = ""
)

data class Song (
    var id: Int = 0,
    var readable: Boolean = false,
    var title: String = "",
    var title_short: String = "",
    var link: String = "",
    var artist: Artist? = null,
    var album: Album? = null
)

data class Artist (
    var name: String = ""
)

data class Album (
    var title: String = "",
    var cover: String = ""
)