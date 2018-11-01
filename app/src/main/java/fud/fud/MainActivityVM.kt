package fud.fud

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event

class MainActivityVM(var Filter1: String, val Filter2: String) : BaseObservable() {

    private var _filter4 = "HI"

    private var _eventList : MutableList<Event> = mutableListOf()

    @Bindable
    fun getFilterFour() : String{
        return _filter4
    }

    fun setFilterFour(value: String) {
        if (value != _filter4){
            _filter4 = value
        }
        notifyPropertyChanged(BR.filterFour)
    }


    @Bindable
    fun getEventList() : MutableList<Event>{
        return _eventList
    }
    fun setEventList(value: MutableList<Event>){
        if (value != _eventList){
            _eventList = value
        }
        notifyPropertyChanged(BR.eventList)
    }

    fun onclickCreateEvent(){

    }

    fun updateEventList(){
        var tlist : MutableList<Event> = mutableListOf()
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        dbManager.allEvents.addOnCompleteListener {
            task -> if (task.isSuccessful)
            task.result!!.forEach {
                it ->
                tlist.add(it.data as Event)

            }
        }
        _eventList = tlist
    }




}