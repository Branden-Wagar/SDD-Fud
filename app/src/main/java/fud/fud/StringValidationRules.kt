package fud.fud

import android.location.Location
import android.text.Editable
import android.text.TextUtils
import fud.fud.Models.Event
import fud.fud.Models.EventLocation


class StringValidationRules {
    var NOT_EMPTY: StringRule = object : StringRule {
        override fun validate(s: Editable): Boolean {
            return TextUtils.isEmpty(s.toString())
        }
    }

    var EMAIL: StringRule = object : StringRule {
        override fun validate(s: Editable): Boolean {
            return !android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()

        }
    }

    var PASSWORD: StringRule = object : StringRule {
        override fun validate(s: Editable): Boolean {
            return s.length < 8
        }
    }

    var PRICE: StringRule = object : StringRule {
        override fun validate(s: Editable): Boolean {
            var maxPrice: String? = s.toString()
            var mpriceDouble = maxPrice?.toDoubleOrNull() ?: return false

            return mpriceDouble >= 0
        }
    }

    companion object {
        public fun calculateDistance(l1 : Location?, l2 : EventLocation?) : Double{

            if (l1 == null || l2 == null){
                return 0.0
            }

            var R = 6371 // Radius of the earth in km
            var dLat = deg2rad(l2.latitude-l1.latitude)  // deg2rad below
            var dLon = deg2rad(l2.longitude-l1.longitude)
            var a =
                    Math.sin(dLat/2) * Math.sin(dLat/2) +
                            Math.cos(deg2rad(l1.latitude)) * Math.cos(deg2rad(l2.latitude)) *
                            Math.sin(dLon/2) * Math.sin(dLon/2)
            ;
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
            var d = R * c // Distance in km
            return d
        }

        private fun deg2rad(deg : Double) : Double {
            return deg * (Math.PI/180)
        }
    }

    interface StringRule {
        fun validate(s: Editable): Boolean
    }
}