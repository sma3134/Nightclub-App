package group_17.nightclubapp.com.request.model


data class BookDB(val name: String? = null,
                     val email: String? = null,
                     val phone: String? = null,
                     val date: String? = null,
                     val time: String? = null,
                     val table: Int? = null,
                     val people: Int? = null,
                     val clubID: String? = null) {

    fun isValid(): Boolean {
        return name != null && email != null && phone != null && date != null && time != null && table != null && people != null && clubID != null
    }
}
