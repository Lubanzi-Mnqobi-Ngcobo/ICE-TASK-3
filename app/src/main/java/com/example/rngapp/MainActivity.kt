package com.example.rngapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.contracts.SimpleEffect
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Setup Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Declaration
        val edtBoxes: EditText = findViewById(R.id.edtBoxes)
        val switchGM: Switch = findViewById(R.id.switchGM)
        val btnSim: Button = findViewById(R.id.btnSim)
        val tvBoxList: TextView = findViewById(R.id.tvBoxList)

        val scale = resources.displayMetrics.density
        tvBoxList.layoutParams.height = (400 * scale).toInt()
        tvBoxList.layoutParams.width =
            android.view.ViewGroup.LayoutParams.MATCH_PARENT

        // scrolling
        tvBoxList.movementMethod =
            android.text.method.ScrollingMovementMethod()

        tvBoxList.requestLayout()

        btnSim.setOnClickListener {
            tvBoxList.text = ""
            val output = StringBuilder()
            if (switchGM.isChecked) {
                // Grind Mode
                var attempts = 0
                var foundLegendary = false

                while (!foundLegendary) {
                    attempts++
                    val roll = Random.nextInt(1, 101)

                    if (roll >= 97) { // 97..100 is Legendary
                        foundLegendary = true
                        output.append("Legendary found after $attempts attempts!\n")
                        output.append("Item: The King's Throne")
                    }

                    // break if infinite loop logic fails
                    if (attempts > 10000) break
                }
            } else {
                // GRIND MODE: Roll a specific amount of times
                val input = edtBoxes.text.toString()

                if (input.isEmpty()) {
                    Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show()
                } else {
                    val amount = input.toIntOrNull() ?: 0

                    if (amount <= 0) {
                        Toast.makeText(this, "Enter a number above 0", Toast.LENGTH_SHORT).show()
                    } else {
                        for (i in 1..amount) {
                            val roll = Random.nextInt(1, 101)
                            val result = when (roll) {
                                in 1..60 -> "Common Item - Wet Flip-Flops"
                                in 61..85 -> "Rare - Steel Dagger"
                                in 86..96 -> "Epic - Animal Transference Spell"
                                in 97..100 -> "LEGENDARY - The King's Throne Is Yours"
                                else -> "Unknown Item"
                            }
                            output.append("Box $i: $result\n")
                        }
                    }
                }
            }
            tvBoxList.text = output.toString()
        }
    }
}