package fud.fud

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable

class MainActivityVM(var Filter1: String, val Filter2: String) : BaseObservable() {

    private var _filter4 = "New Events"



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

    fun onclickCreateEvent(){

    }




}