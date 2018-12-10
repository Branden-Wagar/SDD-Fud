package fud.fud

import android.location.Location
import fud.fud.Models.EventLocation
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityVMTest{

    /*
        Tests when l2 > l1
    */
    @Test
    fun calculateDistanceNormal() {
        var l1 = Location("test")
        var l2 = EventLocation()
        l1.longitude = 0.0
        l1.latitude = 0.0
        l2.longitude = 1.0
        l2.latitude = 1.0
        var d = StringValidationRules.calculateDistance(l1, l2)
        print(d)
        assert(d in 157.2..157.3)
    }

    /*
        Tests when l2 < l1
     */
    @Test
    fun calculateDistanceBackwards() {
        var l1 = Location("test")
        var l2 = EventLocation()
        l1.longitude = 2.0
        l1.latitude = 2.0
        l2.longitude = 1.0
        l2.latitude = 1.0
        var d = StringValidationRules.calculateDistance(l1, l2)
        print(d)
        assert(d in 157.2..157.3)
    }

    /*
        Tests when l2 = l1
     */
    @Test
    fun calculateDistanceSame() {
        var l1: Location? = Location("")
        var l2 = EventLocation()
        l1?.longitude = 0.0
        l1?.latitude = 0.0
        l2.longitude = 0.0
        l2.latitude = 0.0
        var d = StringValidationRules.calculateDistance(l1, l2)
        print(d)
        assert(d == 0.0)
    }
}