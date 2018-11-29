package fud.fud

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import fud.fud.Models.Event


class EventDetailsVM (event:Event): ViewModel() {

    //get the values from the event
    val name = event.getEventName()
    val distance = event.getDistanceToUser()
    val discription = event.getDescription()
    val price = event.getPrice()
    //bind these values to the page
    var filter1 = ObservableField<String>(name)
    var filter2= ObservableField<String>(distance.toString()+" miles")
    var filter3=ObservableField<String>(discription)
    var filter5 = ObservableField<String>("$"+price.toString())






}