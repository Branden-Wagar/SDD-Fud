package fud.fud

import android.os.Bundle
import android.app.Activity

import kotlinx.android.synthetic.main.activity_fake_event.*

class FakeEvent : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake_event)
    }

}
