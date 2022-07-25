package group_17.nightclubapp.com.map

import com.google.android.libraries.places.api.model.Place

class place(place: Place){
    var ID = place.id
    var Name = place.name
    var Address = place.address
    var LatLng = place.latLng
    var OpeningH = place.openingHours
    var BusinessSTAT = place.businessStatus
    var Rating = place.rating
    var Phone = place.phoneNumber
    var photos = place.photoMetadatas
}