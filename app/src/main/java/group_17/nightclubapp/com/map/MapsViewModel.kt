package group_17.nightclubapp.com.map

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import group_17.nightclubapp.com.BuildConfig
import kotlinx.coroutines.delay
import java.lang.Thread.sleep

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var placesClient: PlacesClient
    lateinit var data: LiveData<Place>
    val context = getApplication<Application>().applicationContext

    //place IDs will be obtained from firebase DB later
    val placeID_BarNone = "ChIJP6CK2tZzhlQRA1kN4Xazsm8"
    val placeID_Aura = "ChIJF5OUP9RzhlQRA7EQKpS-xeE"
    val placeID_celebrities = "ChIJDfEopdRzhlQRiueE4hVtcag"

    //required data fields to take from place API
    val placeFields = listOf(
        Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.OPENING_HOURS, Place.Field.BUSINESS_STATUS,
        Place.Field.RATING, Place.Field.PHONE_NUMBER, Place.Field.PHOTO_METADATAS)

    val request = FetchPlaceRequest.newInstance(placeID_BarNone, placeFields)

    //API call function
    fun getPlaceData(){
        val _data = MutableLiveData<Place>().apply {
            Places.initialize(context, BuildConfig.MAPS_API_KEY)
            placesClient = Places.createClient(context)
            placesClient.fetchPlace(request)
                .addOnSuccessListener { response: FetchPlaceResponse ->
                    value = response.place
                }.addOnFailureListener { exception: Exception ->
                    if (exception is ApiException) {
                        val statusCode = exception.statusCode
                        value = null
                    }
                }
        }
        data = _data
    }
}