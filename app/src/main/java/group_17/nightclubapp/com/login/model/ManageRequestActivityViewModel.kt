package group_17.nightclubapp.com.login.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import group_17.nightclubapp.com.request.model.RequestDB

class ManageRequestActivityViewModel: ViewModel() {
    var clubID = ""
    var isListening = false
    var requestList = MutableLiveData<ArrayList<RequestDB>>()
    private val requestListCopy: ArrayList<RequestDB> = arrayListOf()

    fun addRequest(request: RequestDB) {
        if (request.clubID.equals(clubID)) {
            requestListCopy.add(request)
            requestList.value = requestListCopy
        }
    }

    fun clearList() {
        requestListCopy.clear()
    }
}