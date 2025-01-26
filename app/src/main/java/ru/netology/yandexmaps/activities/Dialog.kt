package ru.netology.yandexmaps.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.dto.Point
import ru.netology.yandexmaps.viewmodel.YandexMapViewModel

class Dialog : DialogFragment() {

    companion object{
        private const val ID_KEY = "ID_KEY"
        private const val LAT_KEY = "LAT_KEY"
        private const val LONG_KEY = "LONG_KEY"
        fun newInstance(lat: Double, long: Double, id: Long? = null) = Dialog().apply {
            arguments = bundleOf(LAT_KEY to lat, LONG_KEY to long, ID_KEY to id)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): android.app.Dialog {

        val viewModel by viewModels<YandexMapViewModel>()
        val view = AppCompatEditText(requireContext())
        return AlertDialog.Builder(requireContext())
            .setView(view)
            .setTitle(getString(R.string.name_point))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val text = view.text?.toString()?.takeIf { it.isNotBlank() } ?: run {
                    Toast.makeText(requireContext(), "Enter the name!", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.addPoint(
                    Point(id =  requireArguments().getLong(ID_KEY),
                        lat = requireArguments().getDouble(LAT_KEY),
                        long = requireArguments().getDouble(LONG_KEY),
                        title = text)
                )
            }.create()
    }
}