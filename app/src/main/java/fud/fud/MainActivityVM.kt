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

class MainActivityVM( ct : Context) : BaseObservable() {

    private var _filter4 = "New Events"
    var Filter1 = ObservableField<String>("Free Only")
    var EventsListAdapter = ObservableField<ArrayAdapter<String>>()
    var FoodTypeFilters = ObservableField<ArrayAdapter<String>>(ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, ct.resources.getStringArray(R.array.food_tags)))
    var parentContest = ct

    private var _NewEventOnly = false

    @Bindable
    fun getNewEventOnly() : Boolean{
        return _NewEventOnly
    }
    fun setNewEventOnly(input : Boolean){
        if (input != _NewEventOnly){
            _NewEventOnly = input
            if (input){
                filterNew()
            }
            else{
                UpdateEventsList()
            }
        }
        notifyPropertyChanged(BR.newEventOnly)
    }


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


    fun UpdateEventsList() {
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        //set up arrayLists for holding the information for the ListView

        val events = arrayListOf<String>()
        //get the information for each of the events
        var t = dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    events.add(t.toString()) // then put the string rep of the object in our events
                    EventsListAdapter.notifyChange()
                }

            }
        }

        EventsListAdapter.set(ArrayAdapter(parentContest, android.R.layout.simple_list_item_1, events))



    }

    fun filterNew(){
        var dbInstance = FirebaseFirestore.getInstance()
        var dbManager = DatabaseManager(dbInstance)
        val events = arrayListOf<String>()

        var t = dbManager.allEvents.addOnCompleteListener { task ->
            if (task.isSuccessful()) {
                var temp = task.getResult()
                temp!!.forEach {
                    // foreach document we get from allEvents convert it to an Event
                    val t = it.toObject(Event::class.java)
                    if (t.price <= 0){
                        events.add(t.toString()) // then put the string rep of the object in our events
                        EventsListAdapter.notifyChange()
                    }
                }

            }
        }


        EventsListAdapter.set(ArrayAdapter(parentContest, android.R.layout.simple_list_item_1, events))
    }




}