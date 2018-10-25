package fud.fud

import android.text.Editable
import android.text.TextUtils



public class StringValidationRules {
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
            var maxPrice: String? = s.toString() ?: return false
            var mpriceDouble = maxPrice?.toDoubleOrNull() ?: return false

            return mpriceDouble >= 0
        }
    }

    interface StringRule {
        fun validate(s: Editable): Boolean
    }
}