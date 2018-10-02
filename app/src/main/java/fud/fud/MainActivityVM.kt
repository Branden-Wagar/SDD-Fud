package fud.fud

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable

class MainActivityVM(var Filter1: String, val Filter2: String) : BaseObservable() {

    private var _filter3 = "HI"
    var filter3 : String
        @Bindable get(){
            return _filter3
        }
        set(value) {
            if (value != _filter3){
                _filter3 = value
                //notifyPropertyChanged(BR.mainvm)
            }
        }


    @Bindable
    fun getFilterFour() : String{
        return _filter3
    }

    fun setFilterFour(value: String) {
        if (value != _filter3){
            _filter3 = value
        }
        notifyPropertyChanged(BR.filterFour)
    }

    fun onclickCreateEvent(){
       //filter3 = "Bye"
        setFilterFour("Bye")
    }




}