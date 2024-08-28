import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.test.R

class RateUsDialogFragment : DialogFragment() {

    private var userRating: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_rate_us_dialog, container, false)

        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val btnCancel: Button = view.findViewById(R.id.btnCancel)
        val btnSubmit: Button = view.findViewById(R.id.btnSubmit)

        // Set the initial state for the submit button (disabled if no rating is given)
        btnSubmit.isEnabled = false

        // Handle rating bar changes
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            userRating = rating
            btnSubmit.isEnabled = rating > 0
        }

        btnCancel.setOnClickListener {
            dismiss() // Close the dialog
        }

        btnSubmit.setOnClickListener {
            Toast.makeText(context, "Thank you for your feedback!", Toast.LENGTH_SHORT).show()
            dismiss() // Close the dialog after submitting
            // Optionally, you can handle the rating value here
            // sendRatingToServer(userRating)
        }

        return view
    }
}
