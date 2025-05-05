package com.example.healspace

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MedFragment : Fragment() {

    private var mediaPlayer: MediaPlayer? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_med, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //med 10
        val med10Recom = view.findViewById<View>(R.id.med_10_recom)
        val playButton = view.findViewById<Button>(R.id.play_med_10)
        val med10 = view.findViewById<View>(R.id.med_10)
        val med10View = view.findViewById<View>(R.id.med_10_view)
        val closeButton = view.findViewById<View>(R.id.close_med_10)

        med10Recom.setOnClickListener {
            med10View.visibility = View.VISIBLE
        }

        playButton.setOnClickListener {
            playAudio(R.raw.med_10_min)
        }

        med10.setOnClickListener {
            med10View.visibility = View.VISIBLE
        }

        closeButton.setOnClickListener {
            stopAudio()
            med10View.visibility = View.GONE
        }
        //med5
        val med5 = view.findViewById<View>(R.id.med_5)
        val playButton2 = view.findViewById<Button>(R.id.play_med_5)
        val med5View = view.findViewById<View>(R.id.med_5_view)
        val closeButton2 = view.findViewById<View>(R.id.close_med_5)

        med5.setOnClickListener {
            med5View.visibility = View.VISIBLE
        }

        playButton2.setOnClickListener {
            playAudio(R.raw.med_5_min)
        }

        closeButton2.setOnClickListener {
            stopAudio()
            med5View.visibility = View.GONE
        }

        //med15
        val med15 = view.findViewById<View>(R.id.med_15)
        val playButton3 = view.findViewById<Button>(R.id.play_med_15)
        val med15View = view.findViewById<View>(R.id.med_15_view)
        val closeButton3 = view.findViewById<View>(R.id.close_med_15)

        med15.setOnClickListener {
            med15View.visibility = View.VISIBLE
        }

        playButton3.setOnClickListener {
            playAudio(R.raw.med_15_min)
        }

        closeButton3.setOnClickListener {
            stopAudio()
            med15View.visibility = View.GONE
        }

        //med anxiety
        val medanxiety = view.findViewById<View>(R.id.med_anxiety)
        val playButton4 = view.findViewById<Button>(R.id.play_med_anxiety)
        val medAnxietyView = view.findViewById<View>(R.id.med_anxiety_view)
        val closeButton4 = view.findViewById<View>(R.id.close_med_anxiety)

        medanxiety.setOnClickListener {
            medAnxietyView.visibility = View.VISIBLE
        }

        playButton4.setOnClickListener {
            playAudio(R.raw.med_anxiety)
        }

        closeButton4.setOnClickListener {
            stopAudio()
            medAnxietyView.visibility = View.GONE
        }

        //med stress
        val medstress = view.findViewById<View>(R.id.med_stress)
        val playButton5 = view.findViewById<Button>(R.id.play_med_stress)
        val medStressView = view.findViewById<View>(R.id.med_stress_view)
        val closeButton5 = view.findViewById<View>(R.id.close_med_stress)

        medstress.setOnClickListener {
            medStressView.visibility = View.VISIBLE
        }

        playButton5.setOnClickListener {
            playAudio(R.raw.med_destress)
        }

        closeButton5.setOnClickListener {
            stopAudio()
            medStressView.visibility = View.GONE
        }
    }

    private fun playAudio(audioResId: Int) {
        stopAudio() // Stop any existing audio before playing new one
        mediaPlayer = MediaPlayer.create(requireContext(), audioResId).apply {
            start()
        }
    }

    private fun stopAudio() {
        mediaPlayer?.apply {
            if (isPlaying) stop()
            release()
        }
        mediaPlayer = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAudio() // Stop audio when leaving the fragment
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SleepFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MedFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}