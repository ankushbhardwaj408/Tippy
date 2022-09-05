package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
private lateinit var base:TextView
   private lateinit var seekbar:SeekBar
    private lateinit var tip:TextView
    private lateinit var total:TextView
    private lateinit var percent:TextView
    private lateinit var textview:TextView
   private var initial=15
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        base=findViewById(R.id.etBase)
        seekbar=findViewById(R.id.seekBarTip)
        percent=findViewById(R.id.tvTipPercent)
        tip=findViewById(R.id.tvTipAmount)
        total=findViewById(R.id.tvTotalAmount)
        textview=findViewById(R.id.textView)
      percent.text="$initial%"
       seekbar.progress=initial
       colorchange(initial)
        seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

        percent.text="$p1%"
        compute()
       colorchange(p1)
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}

})
        base.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                compute()
            }

        })


    }

    private fun colorchange(p1:Int) {
        val text=when(p1){
            in 0..9->"Poor"
            in 10..14->"Acceptable"
            in 15..19->"Good"
            in 20..24->"Great"
            else->"Amazing"
        }
        textview.text=text
val color=ArgbEvaluator().evaluate(
    p1.toFloat()/seekbar.max,
    ContextCompat.getColor(this,R.color.worst_tip),
    ContextCompat.getColor(this,R.color.best_tip)
) as Int
textview.setTextColor(color)
    }

    private fun compute()
    {
        if(base.text.isEmpty()) {
            tip.text=""
            total.text=""
            return
        }
        val x=base.text.toString().toDouble()
        val p=seekbar.progress
        val ans=x*p/100
        val totalans=ans+x
        tip.text="%.2f".format(ans)
        total.text="%.2f".format(totalans)

    }
}