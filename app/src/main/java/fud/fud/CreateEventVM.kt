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
import android.support.v4.content.ContextCompat.startActivity
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager


class CreateEventVM(var Name : String? = null) : ViewModel() {


    var EndTime = ObservableField<String>("1:30")
    var EventName = ObservableField<String>("")
    var MaxPrice = ObservableField<String>("0.0")
    var EventDesc = ObservableField<String>("")

    val foodTag = ObservableField<String>()

    fun onClickCreateButton(){
        var toSubmit = Event()
        //toSubmit.cuisineType = foodTag.toString()
        toSubmit.date = Date.from(Instant.now())
        toSubmit.description = EventDesc.get()
        toSubmit.eventName = EventName.get()
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

    @BindingAdapter("app:validation", "app:errorMsg")
    fun setErrorEnable(editText: EditText, stringRule: StringValidationRules.StringRule, errorMsg: String) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (stringRule.validate(editText.text)) {
                    editText.error = errorMsg
                } else {
                    editText.error = null
                }
            }
        })
    }



    /*


    @BindingAdapter("android:text")
    fun setText(view: TextView, value: Double) {
        view.text = String.format("%d", value)
    }

    @InverseBindingAdapter(attribute = "android:text")
    fun getText(view: TextView): Double {
        return view.text.toString().toDouble()
    }
    */

}