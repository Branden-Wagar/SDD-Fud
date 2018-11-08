package fud.fud

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.databinding.ObservableField
import fud.fud.Models.Event
import java.sql.Date
import java.time.Instant
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.databinding.BindingAdapter
import android.databinding.InverseBindingMethod
import android.databinding.InverseBindingMethods
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager




class CreateEventVM(options: List<String>, curr: String, adapter: ArrayAdapter<String>) : ViewModel() {


    var EndTime = ObservableField<String>("1:30")
    var EventName = ObservableField<String>("")
    var MaxPrice = ObservableField<String>("0.0")
    var EventDesc = ObservableField<String>("")
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

        val db = DatabaseManager(FirebaseFirestore.getInstance())
        db.add(toSubmit)
        //need to add return to main activity here
    }

    fun validatePrice() : Boolean{
        var maxPrice: String? = MaxPrice.get() ?: return false
        var mpriceDouble = maxPrice?.toDoubleOrNull() ?: return false

        return mpriceDouble >= 0

    }



}