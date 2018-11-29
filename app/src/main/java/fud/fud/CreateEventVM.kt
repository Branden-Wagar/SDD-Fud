package fud.fud

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import fud.fud.Models.Event
import java.sql.Date
import java.time.Instant
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager


class CreateEventVM(options: List<String>, curr: String, adapter: ArrayAdapter<String>) : ViewModel() {


    var EndTime = ObservableField<String>("1:30")
    var EndTimeError = ObservableField<String>()

    var EventName = ObservableField<String>("")
    var EventNameError = ObservableField<String>()

    var MaxPrice = ObservableField<String>("0.00")
    var MaxPriceError = ObservableField<String>()

    var EventDesc = ObservableField<String>("")
    var EventDescError = ObservableField<String>()

    val foodTagOptions : List<String> = options
    var foodTag : ObservableField<String> = ObservableField(curr)
    var adapter : ObservableField<ArrayAdapter<String>> = ObservableField(adapter)
    var foodTagIndex : ObservableField<Int> = ObservableField(0)

    fun onClickCreateButton(){
        var toSubmit = Event()
        toSubmit.date = Date.from(Instant.now())
        toSubmit.description = EventDesc.get()
        toSubmit.eventName = EventName.get()
        var index : Int = foodTagIndex.get()!!
        toSubmit.cuisineType = foodTagOptions[index]

        val tprice: Double? = MaxPrice.get()!!.toDoubleOrNull()
        if (tprice == null){
            throw error("Invalid price")
        }
        else{
            toSubmit.price = tprice.toDouble()
        }

        if (isValid()) {
            val db = DatabaseManager(FirebaseFirestore.getInstance())
            db.add(toSubmit)
        }
    }

    fun isValid() : Boolean {
        var isValid = isTimeValid()
        isValid = isEventDescValid() && isValid
        isValid = isPriceValid() && isValid
        isValid = isEventNameValid() && isValid

        return isValid
    }

    private fun isEventDescValid(): Boolean {
        return if (EventDesc.get()!!.isNotEmpty()) {
            EventDescError.set(null)
            true
        } else {
            EventDescError.set("Please enter a description for the event...")
            false
        }
    }

    private fun isEventNameValid(): Boolean {
        return if (EventName.get() != null && EventName.get()!!.length > 5) {
            EventNameError.set(null)
            true
        } else {
            EventNameError.set("Please enter a valid name for the event...")
            false

        }
    }

    private fun isPriceValid(): Boolean {
        var pricePattern = "^([0-9]*)(\\.[0-9][0-9])?\$".toRegex()
        return if (MaxPrice.get()!!.isNotEmpty() && MaxPrice.get()!!.toDouble() >= 0
                && pricePattern.matches(MaxPrice.get()!!)) {
            MaxPriceError.set(null)
            true
        } else {
            MaxPriceError.set("Please enter a valid maximum price...")
            false
        }
    }

    private fun isTimeValid(): Boolean {
        var timePattern = "^(0?[1-9]|1[0-2]):[0-5][0-9]\$".toRegex()

        return if (EndTime.get()!!.isNotEmpty() && timePattern.matches(EndTime.get()!!)) {
            EndTimeError.set(null)
            true
        } else {
            EndTimeError.set("Please enter a valid end time for the event...")
            false
        }
    }
}