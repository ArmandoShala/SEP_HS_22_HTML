package ch.zhaw.shalaar3.sep_hs_22_html

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var linearLayoutLetters: Array<Button?>;
    lateinit var linearLayoutNumbers: Array<Button?>;
    lateinit var linearLayoutFunctional: Array<Button?>;
    lateinit var textViewInput: TextView;
    val API_ENDPOINT = "https://transport.opendata.ch/v1/stationboard?station=Winterthur";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewInput = findViewById(R.id.textViewInput);
        linearLayoutLetters = loadButtons(R.id.linearLayoutLetters);
        linearLayoutNumbers = loadButtons(R.id.linearLayoutNumbers);
        linearLayoutFunctional = loadButtons(R.id.linearLayoutFunctonal);
        init();
    }

    private fun loadButtons(layoutId: Int): Array<Button?> {
        val linearLayout = findViewById<LinearLayout>(layoutId)
        val buttons = arrayOfNulls<Button>(linearLayout.childCount)
        for (i in 0 until linearLayout.childCount) {
            buttons[i] = linearLayout.getChildAt(i) as Button
        }
        return buttons
    }

    private fun init() {
        textViewInput.text = "";

        setEnableStateOfButtons(linearLayoutLetters);
        setEnableStateOfButtons(linearLayoutNumbers, false);
        setEnableStateOfButtons(linearLayoutFunctional, false);

        addEventListenerToButtons(linearLayoutLetters) { button -> handleButtonPressed(button) }
        addEventListenerToButtons(linearLayoutNumbers) { button -> handleButtonPressed(button) }
        addEventListenerToButtons(linearLayoutFunctional) { button -> handleSpecialButtonPressed(button) }

        // This is special, because it is the only button that is enabled at the beginning
        setEnableStateOfButton(findViewById(R.id.buttonClear));
    }

    private fun handleButtonPressed(button: Button) {
        textViewInput.text = "${textViewInput.text}${button.text}";

        if (textViewInput.text.length == 3) {
            textViewInput.text = "${textViewInput.text}-";
            setEnableStateOfButtons(linearLayoutLetters, false);
            setEnableStateOfButtons(linearLayoutNumbers);
        }
        if (textViewInput.text.length > 4) {
            setEnableStateOfButton(findViewById(R.id.buttonCheck));
        }
        if (textViewInput.text.length >= 8) {
            setEnableStateOfButtons(linearLayoutNumbers, false);
        }
    }

    private fun handleSpecialButtonPressed(button: Button) {
        if (button.text == "CLEAR") {
            init()
        } else if (button.text == "CHECK") {
            sendAPIRequest(textViewInput.text.toString());
        }
    }

    private fun sendAPIRequest(text: String) {
        val requestQueue = Volley.newRequestQueue(this)

        val request = StringRequest(Request.Method.GET, API_ENDPOINT, { response ->
            var adsf = response.length;
            println(adsf)
        }, {
            println("Error: ${it.message}")
        })

        requestQueue.add(request)
    }

    private fun addEventListenerToButtons(buttons: Array<Button?>, event: (Button) -> Unit) {
        for (button in buttons) {
            addEventListenerToButton(button, event)
        }
    }

    private fun addEventListenerToButton(button: Button?, event: (Button) -> Unit) {
        button?.setOnClickListener { event(button) }
    }

    private fun setEnableStateOfButtons(buttons: Array<Button?>, state: Boolean = true) {
        for (button in buttons) {
            setEnableStateOfButton(button, state)
        }
    }

    private fun setEnableStateOfButton(button: Button?, state: Boolean = true) {
        button?.isEnabled = state
    }

}
