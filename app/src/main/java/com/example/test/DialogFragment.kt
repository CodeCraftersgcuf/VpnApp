package com.example.test

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment

class SelectLanguageDialogFragment : DialogFragment() {

    interface LanguageSelectionListener {
        fun onLanguageSelected(languageCode: String)
    }

    private var listener: LanguageSelectionListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? LanguageSelectionListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_language, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val radioButtonEn: RadioButton = view.findViewById(R.id.radio_button_en)
        val radioButtonEs: RadioButton = view.findViewById(R.id.radio_button_es)
        val radioButtonFr: RadioButton = view.findViewById(R.id.radio_button_fr)
        val radioButtonDe: RadioButton = view.findViewById(R.id.radio_button_de)
        val radioButtonNl: RadioButton = view.findViewById(R.id.radio_button_nl)
        val radioButtonIt: RadioButton = view.findViewById(R.id.radio_button_it)
        val radioButtonPt: RadioButton = view.findViewById(R.id.radio_button_pt)
        val radioButtonRu: RadioButton = view.findViewById(R.id.radio_button_ru)

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

                // Notify the listener (MainActivity) about the selected language
                listener?.onLanguageSelected(selectedLanguageCode)

                // Dismiss the dialog
                dismiss()
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
