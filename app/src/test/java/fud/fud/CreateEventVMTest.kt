package fud.fud

import android.content.Context
import android.content.res.Resources
import android.location.Location
import android.widget.ArrayAdapter
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock

class CreateEventVMTest {

    lateinit var createEventVM : CreateEventVM

    @Mock
    private lateinit var mockContext: Context


    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        var t = ArrayList<String>()
        val adapter = ArrayAdapter<String>(mockContext, android.R.layout.simple_spinner_item, t)
        var bestLocation = Location("test")

        createEventVM = CreateEventVM(mockContext, t, "Chinese", adapter, bestLocation)
    }

    /*
        Tests for an invalid event.
     */
    @Test
    fun invalidTest() {
        var testBool = createEventVM.isValid()
        assertFalse(testBool)
    }

    /*
        Tests for a valid event.
     */
    @Test
    fun validTest() {
        createEventVM.EndTime.set("08:00")
        createEventVM.EventName.set("Philip's Test Event")
        createEventVM.MaxPrice.set("1.00")
        createEventVM.EventDesc.set("Thi is a description.")

        var testBool = createEventVM.isValid()
        assert(testBool)
    }



}