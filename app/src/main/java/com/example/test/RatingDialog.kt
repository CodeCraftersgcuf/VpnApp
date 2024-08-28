package com.example.test

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast

class RatingDialog(private val context: Context) {

    private val TAG = "RatingDialog"

    fun show() {
        Log.d(TAG, "show: Dialog is being created.")

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.rating_dialog)

        Log.d(TAG, "show: Layout set for dialog.")

        val ratingBar = dialog.findViewById<RatingBar>(R.id.ratingBar)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnSubmit = dialog.findViewById<Button>(R.id.btnSubmit)

        // Handle cancel button click
        btnCancel.setOnClickListener {
            Log.d(TAG, "btnCancel: Cancel button clicked.")
            dialog.dismiss()
        }

        // Handle submit button click
        btnSubmit.setOnClickListener {
            val rating = ratingBar.rating
            Log.d(TAG, "btnSubmit: Submit button clicked. Rating: $rating")
            Toast.makeText(context, "Thank you for rating $rating stars!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        Log.d(TAG, "show: Dialog is about to be shown.")
        dialog.show()
    }
}
