package org.example.myapplication.presintation.fragment

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorPickerViewListener
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.example.myapplication.R
import org.example.myapplication.databinding.FragmentTopBinding
import org.example.myapplication.presintation.viewmodel.ThemeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class TopFragment : Fragment() {

    private lateinit var bindings: FragmentTopBinding
    private val viewModel: ThemeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindings = FragmentTopBinding.inflate(inflater, container, false)

        bindings.isDynamicColorEnabled
            .setOnCheckedChangeListener { view, checked ->
                val btnHeight = bindings.chooseColorBtn.height
                val startHeight = bindings.container.height
                val targetHeight = if (checked) startHeight + btnHeight + 10 else startHeight - btnHeight - 10 // Укажите нужный прирост

                // Анимация изменения высоты контейнера
                ValueAnimator.ofInt(startHeight, targetHeight).apply {
                    duration = 600 // Длительность анимации
                    addUpdateListener { animator ->
                        val value = animator.animatedValue as Int
                        bindings.container.minimumHeight = value // Меняем minHeight, а не layoutParams
                    }
                    start()
                }

                if (checked) {
                    bindings.chooseColorBtn.apply {
                        visibility = View.VISIBLE
                        alpha = 0f // Начинаем с прозрачности
                        scaleX = 0f
                        scaleY = 0f
                        animate()
                            .alpha(1f) // Плавное появление
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(300)
                            .start()
                    }
                }
                else {
                    // Уменьшаем кнопку перед скрытием
                    bindings.chooseColorBtn.animate()
                        .alpha(0f) // Исчезновение
                        .scaleX(0f)
                        .scaleY(0f)
                        .setDuration(300)
                        .withEndAction {
                            bindings.chooseColorBtn.visibility = View.GONE
                        }
                        .start()
                }
            }

        bindings.chooseColorBtn.setOnClickListener { _ ->
            val cpd = ColorPickerDialog.Builder(requireActivity())
                .attachBrightnessSlideBar(true)
                .attachAlphaSlideBar(false)
                .setPositiveButton(getString(R.string.color_picker_positive_btn),
                    object : ColorPickerViewListener {

                    })
                .create()

            cpd.show()
        }

        return bindings.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TopFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TopFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}