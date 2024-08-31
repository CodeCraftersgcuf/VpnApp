package com.example.test

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment

class SelectLanguageDialogFragment : DialogFragment() {

    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_select_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Directly handle the checked state of RadioButtons without adding them to a RadioGroup
        val radioButtonEn: RadioButton = view.findViewById(R.id.radio_button_en)
        val radioButtonEs: RadioButton = view.findViewById(R.id.radio_button_es)
        val radioButtonFr: RadioButton = view.findViewById(R.id.radio_button_fr)
        val radioButtonDe: RadioButton = view.findViewById(R.id.radio_button_de)
        val radioButtonNl: RadioButton = view.findViewById(R.id.radio_button_nl)
        val radioButtonIt: RadioButton = view.findViewById(R.id.radio_button_it)
        val radioButtonPt: RadioButton = view.findViewById(R.id.radio_button_pt)
        val radioButtonRu: RadioButton = view.findViewById(R.id.radio_button_ru)

        // Handle RadioButton clicks
        val radioButtons = listOf(
            radioButtonEn, radioButtonEs, radioButtonFr, radioButtonDe,
            radioButtonNl, radioButtonIt, radioButtonPt, radioButtonRu
        )

        radioButtons.forEach { radioButton ->
            radioButton.setOnClickListener {
                // Uncheck all RadioButtons
                radioButtons.forEach { it.isChecked = false }
                // Check the selected RadioButton
                radioButton.isChecked = true

                // You can add logic here to handle language selection based on the selected RadioButton
                val selectedLanguageCode = when (radioButton.id) {
                    R.id.radio_button_en -> "en"
                    R.id.radio_button_es -> "es"
                    R.id.radio_button_fr -> "fr"
                    R.id.radio_button_de -> "de"
                    R.id.radio_button_nl -> "nl"
                    R.id.radio_button_it -> "it"
                    R.id.radio_button_pt -> "pt"
                    R.id.radio_button_ru -> "ru"
                    else -> "en"
                }

                // Dismiss the dialog after selection (optional)
                dismiss()

                // Implement your logic for changing the app's language based on selectedLanguageCode
                // Example: updateLanguage(selectedLanguageCode)
            }
        }

        // Optionally pre-select a default language
        radioButtonEn.isChecked = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialog.setOnShowListener {
            // Set the dialog to a larger size
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return dialog
    }
}
