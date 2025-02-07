package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val nameInput = findViewById<EditText>(R.id.text_name)
        val checkbox1 = findViewById<CheckBox>(R.id.checkbox_course1)
        val checkbox2 = findViewById<CheckBox>(R.id.checkbox_course2)
        val checkbox3 = findViewById<CheckBox>(R.id.checkbox_course3)
        val checkbox4 = findViewById<CheckBox>(R.id.checkbox_course4)
        val checkbox5 = findViewById<CheckBox>(R.id.checkbox_course5)
        val totalAmount = findViewById<TextView>(R.id.total_amount)
        val confirmButton = findViewById<Button>(R.id.pay_button)
        val confirmationSection = findViewById<LinearLayout>(R.id.confirmation_section)

        val nameDisplay = findViewById<TextView>(R.id.name_display)
        val coursesDisplay = findViewById<TextView>(R.id.courses_display)
        val totalDisplay = findViewById<TextView>(R.id.total_display)

        val coursePrice = 2500
        val checkboxes = listOf(checkbox1, checkbox2, checkbox3, checkbox4, checkbox5)

        // Update total dynamically
        checkboxes.forEach { checkbox ->
            checkbox.setOnCheckedChangeListener { _, _ ->
                val selectedCourses = checkboxes.count { it.isChecked }
                totalAmount.text = (selectedCourses * coursePrice).toString()

                // Disable unchecked checkboxes if the maximum is reached
                if (selectedCourses >= 3) {
                    checkboxes.forEach { cb ->
                        if (!cb.isChecked) cb.isEnabled = false
                    }
                } else {
                    // Re-enable all checkboxes when under the limit
                    checkboxes.forEach { cb -> cb.isEnabled = true }
                }
            }
        }

        confirmButton.setOnClickListener {
            val name = nameInput.text.toString()
            val selectedCourses = checkboxes.filter { it.isChecked }.map { it.text.toString() }

            if (name.isBlank()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedCourses.isEmpty() || selectedCourses.size > 3) {
                Toast.makeText(this, "Please select between 1 and 3 courses", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val total = selectedCourses.size * coursePrice

            // Hide course selection views
            nameInput.visibility = EditText.GONE
            checkboxes.forEach { it.visibility = CheckBox.GONE }
            totalAmount.visibility = TextView.GONE
            confirmButton.visibility = Button.GONE

            // Show confirmation section
            confirmationSection.visibility = LinearLayout.VISIBLE
            nameDisplay.text = "Name: $name"
            coursesDisplay.text = "Courses: ${selectedCourses.joinToString(", ")}"
            totalDisplay.text = "Total Paid: $$total"
        }
    }
}
