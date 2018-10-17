package fud.fud

import android.databinding.BaseObservable
import android.databinding.Bindable
import java.sql.Time
import android.widget.TextView
import android.databinding.InverseBindingAdapter
import android.databinding.BindingAdapter



class CreateEventVM(var Name : String? = null) : BaseObservable() {


    private var _eventName : String? = null;
    private var _maxPrice : Double = 0.0;
    private var _endTime : Time? = null;
    private var _eventDesc : String? = null;

    @Bindable
    fun getEventName() : String?{
        return _eventName
    }

    fun setEventName(value: String?) {
        if (value != _eventName){
            _eventName = value
        }
        notifyPropertyChanged(BR.eventName)
    }

    @Bindable
    fun getMaxPrice() : Double{
        return _maxPrice
    }
    fun setMaxPrice(value : Double){
        if (value != _maxPrice){
            _maxPrice = value
        }
        notifyPropertyChanged(BR.maxPrice)
    }

    @Bindable
    fun getEndTime() : Time?{
        return _endTime
    }
    fun setEndTime(value: Time?){
        if (value != _endTime){
            _endTime = value
        }
        notifyPropertyChanged(BR.endTime)
    }

    @Bindable
    fun getEventDesc() : String?{
        return _eventDesc
    }
    fun setEventDesc(value : String?){
        if (value != _eventDesc){
            _eventDesc = value
        }
        notifyPropertyChanged(BR.eventDesc)
    }


    @BindingAdapter("android:text")
    fun setText(view: TextView, value: Double) {
        view.text = String.format("%d", value)
    }

    @InverseBindingAdapter(attribute = "android:text")
    fun getText(view: TextView): Double {
        return view.text.toString().toDouble()
    }


}