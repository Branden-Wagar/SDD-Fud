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

    @Test
    fun invalidDescTest() {
        createEventVM.EventDesc.set("")
        assertFalse(createEventVM.isEventDescValid())
    }

    @Test
    fun validDescTest() {
        createEventVM.EventDesc.set("Valid description.")
        assertTrue(createEventVM.isEventDescValid())
    }

    @Test
    fun invalidNameTest() {
        createEventVM.EventName.set("short")
        assertFalse(createEventVM.isEventNameValid())
    }

    @Test
    fun validNameTest() {
        createEventVM.EventName.set("long name")
        assertTrue(createEventVM.isEventNameValid())
    }

    @Test
    fun invalidPriceTest() {
        createEventVM.MaxPrice.set("")
        assertFalse(createEventVM.isPriceValid())

        createEventVM.MaxPrice.set("-1.99")
        assertFalse(createEventVM.isPriceValid())
    }

    @Test
    fun validPriceTest() {
        createEventVM.MaxPrice.set("1.00")
        assertTrue(createEventVM.isPriceValid())
    }

    @Test
    fun invalidTimeTest() {
        createEventVM.EndTime.set("")
        assertFalse(createEventVM.isTimeValid())
    }

    @Test
    fun validTimeText() {
        createEventVM.EndTime.set("09:00")
        assertTrue(createEventVM.isTimeValid())
    }
}