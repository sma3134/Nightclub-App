package group_17.nightclubapp.com.map

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*
import group_17.nightclubapp.com.BuildConfig
import group_17.nightclubapp.com.contact.model.Club


class MapsViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var placesClient: PlacesClient
    val data = ArrayList<LiveData<Place>>()
    val context = getApplication<Application>().applicationContext
    val IDList = ArrayList<String>()


    val placeID_BarNone = Club.BARNONE
    val placeID_Aura = Club.AURA
    val placeID_celebrities = Club.CELEBRITIES

    private val photoBitmapsCopy = ArrayList<Bitmap>()
    val photoBitMaps = MutableLiveData<List<Bitmap>>()

    //required data fields to take from place API
    val placeFields = listOf(
        Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.OPENING_HOURS, Place.Field.BUSINESS_STATUS,
        Place.Field.RATING, Place.Field.PHONE_NUMBER, Place.Field.PHOTO_METADATAS)



    //API call function
    fun getPlaceData() {
        IDList.add(placeID_BarNone)
        IDList.add(placeID_Aura)
        IDList.add(placeID_celebrities)
        for (id in IDList) {
            val _data = MutableLiveData<Place>().apply {
                Places.initialize(context, BuildConfig.MAPS_API_KEY)
                placesClient = Places.createClient(context)
                val request = FetchPlaceRequest.newInstance(id, placeFields)
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
            data.add(_data)
        }
    }

    fun getPhotoBitMap(photoMetadataList: List<PhotoMetadata>) {
        //empty any old club images
        photoBitmapsCopy.clear()
        photoBitMaps.value = listOf()
        for (photoMetaData in photoMetadataList) {
            val photoRequest = FetchPhotoRequest.builder(photoMetaData)
                .build()
            placesClient.fetchPhoto(photoRequest)
                .addOnSuccessListener { fetchPhotoResponse: FetchPhotoResponse ->
                        photoBitmapsCopy.add(fetchPhotoResponse.bitmap)
                        photoBitMaps.value = photoBitmapsCopy
                }.addOnFailureListener { exception: java.lang.Exception ->
                    if (exception is ApiException) {
                        val apiException = exception
                        Log.e("MAP", "Place not found: " + exception.message)
                        }
                }
        }
    }
}