package group_17.nightclubapp.com.contact.model

data class Club(val clubId: Long? = null,
                val phoneNumber: String? = null,
                val email: String? = null,
                val facebook: String? = null,
                val instagram: String? = null,
                val twitter: String? = null,
                val address: String? = null,
                val lat: Double? = null,
                val lon: Double? = null) {

    companion object {
        val BARNONE =  9548L
    }
}

