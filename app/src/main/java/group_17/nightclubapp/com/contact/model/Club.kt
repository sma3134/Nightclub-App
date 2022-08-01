package group_17.nightclubapp.com.contact.model

data class Club(val clubId: String? = null,
                val phoneNumber: String? = null,
                val email: String? = null,
                val facebook: String? = null,
                val instagram: String? = null,
                val twitter: String? = null,
                val address: String? = null,
                val name: String? = null,
                val website: String? = null,
                val hours: String? = null,
                val announcement: String? = null,
                val about: String? = null,
                val faq: String? = null,
                val other: String? = null,
                val lat: Double? = null,
                val lon: Double? = null) {

    companion object {
        const val BARNONE =  "ChIJP6CK2tZzhlQRA1kN4Xazsm8"
        const val BARNONE_CAP = 10
        const val AURA = "ChIJF5OUP9RzhlQRA7EQKpS-xeE"
        const val AURA_CAP = 10
        const val CELEBRITIES = "ChIJDfEopdRzhlQRiueE4hVtcag"
        const val CELEBRITIES_CAP = 10
    }
}

