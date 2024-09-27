package com.example.privatevpn

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class BottomsheetFragment : BottomSheetDialogFragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private var isShowing: Boolean = false // Track if BottomSheet is showing

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("app_language", Context.MODE_PRIVATE)

        // Load and set the current selected language
        loadSelectedLanguage()

        // Set up click listeners for each language TextView
        setupLanguageClickListeners(view)
    }

    private fun setupLanguageClickListeners(view: View) {
        val textEnglish: TextView = view.findViewById(R.id.textEnglish)
        val textAfrikaans: TextView = view.findViewById(R.id.textAfrikaans)
        val textArabic: TextView = view.findViewById(R.id.textArabic)
        val textChinese: TextView = view.findViewById(R.id.textChinese)
        val textCzech: TextView = view.findViewById(R.id.textCzech)
        val textDanish: TextView = view.findViewById(R.id.textDanish)
        val textDutch: TextView = view.findViewById(R.id.textDutch)
        val textFrench: TextView = view.findViewById(R.id.textFrench)
        val textGerman: TextView = view.findViewById(R.id.textGerman)
        val textGreek: TextView = view.findViewById(R.id.textGreek)
        val textHindi: TextView = view.findViewById(R.id.textHindi)
        val textIndonesian: TextView = view.findViewById(R.id.textIndonesian)
        val textItalian: TextView = view.findViewById(R.id.textItalian)
        val textJapanese: TextView = view.findViewById(R.id.textJapanese)
        val textMalay: TextView = view.findViewById(R.id.textMalay)
        val textKorean: TextView = view.findViewById(R.id.textKorean)
        val textNorwegian: TextView = view.findViewById(R.id.textNorwegian)
        val textPersian: TextView = view.findViewById(R.id.textPersian)
        val textPortuguese: TextView = view.findViewById(R.id.textPortuguese)
        val textRussian: TextView = view.findViewById(R.id.textRussian)
        val textSpanish: TextView = view.findViewById(R.id.textSpanish)
        val textThai: TextView = view.findViewById(R.id.textThai)
        val textTurkish: TextView = view.findViewById(R.id.textTurkish)
        val textVietnamese: TextView = view.findViewById(R.id.textVietnamese)

        // Set click listeners for each language option
        textEnglish.setOnClickListener { changeLanguage("en", "English") }
        textAfrikaans.setOnClickListener { changeLanguage("af", "Afrikaans") }
        textArabic.setOnClickListener { changeLanguage("ar", "Arabic") }
        textChinese.setOnClickListener { changeLanguage("zh", "Chinese") }
        textCzech.setOnClickListener { changeLanguage("cs", "Czech") }
        textDanish.setOnClickListener { changeLanguage("da", "Danish") }
        textDutch.setOnClickListener { changeLanguage("nl", "Dutch") }
        textFrench.setOnClickListener { changeLanguage("fr", "French") }
        textGerman.setOnClickListener { changeLanguage("de", "German") }
        textGreek.setOnClickListener { changeLanguage("el", "Greek") }
        textHindi.setOnClickListener { changeLanguage("hi", "Hindi") }
        textIndonesian.setOnClickListener { changeLanguage("in", "Indonesian") }
        textItalian.setOnClickListener { changeLanguage("it", "Italian") }
        textJapanese.setOnClickListener { changeLanguage("ja", "Japanese") }
        textMalay.setOnClickListener { changeLanguage("ms", "Malay") }
        textKorean.setOnClickListener { changeLanguage("ko", "Korean") }
        textNorwegian.setOnClickListener { changeLanguage("no", "Norwegian") }
        textPersian.setOnClickListener { changeLanguage("fa", "Persian") }
        textPortuguese.setOnClickListener { changeLanguage("pt", "Portuguese") }
        textRussian.setOnClickListener { changeLanguage("ru", "Russian") }
        textSpanish.setOnClickListener { changeLanguage("es", "Spanish") }
        textThai.setOnClickListener { changeLanguage("th", "Thai") }
        textTurkish.setOnClickListener { changeLanguage("tr", "Turkish") }
        textVietnamese.setOnClickListener { changeLanguage("vi", "Vietnamese") }
    }

    private fun loadSelectedLanguage() {
        // Load the selected language from SharedPreferences
        val selectedLanguage = sharedPreferences.getString("selected_language", "en") // Default to English

        // Set the locale based on the selected language
        setLocale(selectedLanguage ?: "en")
    }

    private fun changeLanguage(languageCode: String, languageName: String) {
        if (isShowing) return // Prevent multiple clicks

        isShowing = true // Set to true when the language is changing

        // Save the selected language in SharedPreferences
        sharedPreferences.edit().putString("selected_language", languageCode).apply()

        // Set the locale for the app
        setLocale(languageCode)

        // Show a toast message
        Toast.makeText(requireContext(), "Language changed to $languageName", Toast.LENGTH_SHORT).show()

        // Notify the activity to recreate itself to apply the new language
        requireActivity().recreate()

        // Close the BottomSheetFragment
        dismiss()

        // Reset isShowing to false after a brief delay
        view?.postDelayed({ isShowing = false }, 300) // Delay for 300ms
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = requireContext().resources.configuration
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)
    }
}
