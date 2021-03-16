package stan.grobwt.schdlvw.sample

import android.app.Activity
import android.os.Bundle
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = TextView(this).also {
        	it.text = "stan.grobwt.schdlvw"
        }
        setContentView(contentView)
        val foo = stan.grobwt.schdlvw.Test()
    }
}
