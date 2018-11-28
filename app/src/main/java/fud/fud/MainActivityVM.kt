package fud.fud

import android.arch.lifecycle.LiveData
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.Observable
import android.databinding.ObservableField
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.firestore.FirebaseFirestore
import fud.fud.Database.DatabaseManager
import fud.fud.Models.Event
import kotlin.math.roundToInt

class MainActivityVM( ct : Context) : BaseObservable() {

    var NewEventFilter = ObservableField<String>("New Events")
    var FreeFoodFilter = ObservableField<String>("Free Only")
    var EventsListAdapter = ObservableField<ArrayAdapter<String>>()
    var FoodTypeFilters = ObservableField<ArrayAdapter<String>>(ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, ct.resources.getStringArray(R.array.food_tags)))
    var parentContest = ct
    val eventString = arrayListOf<String>()
    val events = arrayListOf<Event>()

    private var maxEventPrice : Double = 0.0

    private var _freeFoodOnly = false

    @Bindable
    fun getFreeEventOnly() : Boolean{
        return _freeFoodOnly
    }
    fun setFreeEventOnly(input : Boolean){
        if (input != _freeFoodOnly){
            _freeFoodOnly = input
            if (input){
                filterPrice(0.0)
            }
            else{
                UpdateEventsList()
            }
        }
        notifyPropertyChanged(BR.freeEventOnly)
    }


    private var _priceLimit : Int = 0
    @Bindable
    fun getPriceLimit() : Int{
        return _priceLimit
    }

    fun setPriceLimit(value: Int) {
        if (value != _priceLimit){
            _priceLimit = value
            // our slider acts as a percentage value of all prices
            var x = _priceLimit / 100.0
            var k = x * maxEventPrice
            filterPrice(k)
        }
        notifyPropertyChanged(BR.priceLimit)
    }

    fun onclickCreateEvent(){

    }


    fun UpdateEventsList() {
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        //set up arrayLists for holding the information for the ListView

        events.clear()
        eventString.clear()
        maxEventPrice = 0.0
        val events = arrayListOf<String>()
        //get the information for each of the events
        dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    events.add(t)
                    eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (t.price > maxEventPrice){
                        maxEventPrice = t.price // this ensures the last value will be displayed
                    }
                    events.add(t.toString()) // then put the string rep of the object in our events
                    EventsListAdapter.notifyChange()
                }

            }
        }

        EventsListAdapter.set(ArrayAdapter(parentContest, android.R.layout.simple_list_item_1, eventString))



    }

    private fun filterPrice(lim : Double){
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        events.clear()
        eventString.clear()

        dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    if (t.price <= 0){
                        events.add(t)
                        eventString.add(t.toString()) // then put the string rep of the object in our events
                    if (t.price <= lim){
                        events.add(t.toString()) // then put the string rep of the object in our events
                        EventsListAdapter.notifyChange()
                    }
                }

            }
        }


        EventsListAdapter.set(ArrayAdapter(parentContest, android.R.layout.simple_list_item_1, eventString))
    }




}