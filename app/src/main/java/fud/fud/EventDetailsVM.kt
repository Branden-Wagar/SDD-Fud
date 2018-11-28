package fud.fud

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import fud.fud.Models.Event


class EventDetailsVM (event:Event): ViewModel() {


    var eventName = MutableLiveData<String>()
    val hold = event.getEventName()
    var filter1 = ObservableField<String>(hold)
    var filter4 = ObservableField<String>("Location")

    fun eventDetailsClick(){
        if (eventName.value == null){
            eventName.setValue("Taco Party!")
        }
        else{
            if (eventName.value == "Taco Party!"){
                eventName.setValue("PIZZA!")
            }
            else{
                eventName.setValue("Taco Party!")
            }
        }
        filter4 = ObservableField("Hello Kimosabe")

    }




}