package fud.fud

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import fud.fud.Models.Event


public class EventDetailsVM : ViewModel() {


    var eventName = MutableLiveData<String>()

    var filter4 = ObservableField<String>("goodBye")




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