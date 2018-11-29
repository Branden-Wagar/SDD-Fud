package fud.fud.Models

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle

public class LocationManagerLocal : LocationListener {
    private var lastLoc : Location? = null

    private object Holder { val INSTANCE = LocationManagerLocal() }

    companion object {
        val instance: LocationManagerLocal by lazy { Holder.INSTANCE }
    }

    fun GetLocation() : Location?{

        return lastLoc
    }
    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLocationChanged(location: Location?) {
        if (location != lastLoc){
            lastLoc = location
        }
    }
}