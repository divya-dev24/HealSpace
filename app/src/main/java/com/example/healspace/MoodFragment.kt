package com.example.healspace

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MoodFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    private val moodMap = mapOf(
        R.id.mood_joy to Color.YELLOW,
        R.id.mood_content to Color.CYAN,
        R.id.mood_sad to Color.BLUE,
        R.id.mood_anxiety to Color.DKGRAY,
        R.id.mood_anger to Color.RED
    )

    private var selectedMoodColor: Int? = null
    private var selectedMoodView: ImageView? = null
    private val notesList = mutableListOf<Pair<String, Int>>() // Stores notes with their mood color
    private lateinit var notesAdapter: NotesAdapter


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("mood_notes_prefs",
            Context.MODE_PRIVATE)

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
        return inflater.inflate(R.layout.fragment_mood, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val moodImageViews = listOf(
            view.findViewById<ImageView>(R.id.mood_joy),
            view.findViewById<ImageView>(R.id.mood_content),
            view.findViewById<ImageView>(R.id.mood_sad),
            view.findViewById<ImageView>(R.id.mood_anxiety),
            view.findViewById<ImageView>(R.id.mood_anger)
        )

        moodImageViews.forEach { imageView ->
            imageView.setOnClickListener {
                highlightSelectedMood(imageView)
                selectedMoodColor = moodMap[imageView.id]
            }
        }

        val noteInput = view.findViewById<EditText>(R.id.mood_note_input)
        val saveButton = view.findViewById<Button>(R.id.save_note_button)
        val submitButton = view.findViewById<Button>(R.id.mood_submit)

        submitButton.setOnClickListener {
            if (selectedMoodColor != null) {
                noteInput.visibility = View.VISIBLE
                saveButton.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Please select a mood", Toast.LENGTH_SHORT).show()
            }
        }

        saveButton.setOnClickListener {
            saveNote()
        }

        val notesRecyclerView = view.findViewById<RecyclerView>(R.id.notes_recycler_view)
        notesAdapter = NotesAdapter(notesList)
        notesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerView.adapter = notesAdapter

        // Load saved notes
        loadNotes()

        val showOnlyNotes = arguments?.getBoolean("show_only_notes", false) ?: false

        if (showOnlyNotes) {
            view.findViewById<TextView>(R.id.tv_notes_col).visibility = View.VISIBLE
            view.findViewById<TextView>(R.id.textView8).visibility = View.GONE
            view.findViewById<ImageView>(R.id.mood_joy).visibility = View.GONE
            view.findViewById<ImageView>(R.id.mood_content).visibility = View.GONE
            view.findViewById<ImageView>(R.id.mood_sad).visibility = View.GONE
            view.findViewById<ImageView>(R.id.mood_anxiety).visibility = View.GONE
            view.findViewById<ImageView>(R.id.mood_anger).visibility = View.GONE
            view.findViewById<TextView>(R.id.textView13).visibility = View.GONE
            view.findViewById<TextView>(R.id.textView14).visibility = View.GONE
            view.findViewById<TextView>(R.id.textView15).visibility = View.GONE
            view.findViewById<TextView>(R.id.textView16).visibility = View.GONE
            view.findViewById<TextView>(R.id.textView17).visibility = View.GONE
            view.findViewById<Button>(R.id.mood_submit).visibility = View.GONE
            view.findViewById<EditText>(R.id.mood_note_input).visibility = View.GONE
            view.findViewById<Button>(R.id.save_note_button).visibility = View.GONE
        }

    }

    private fun highlightSelectedMood(selectedView: ImageView) {
        selectedMoodView?.alpha = 1.0f
        selectedMoodView = selectedView
        selectedView.alpha = 1.0f

        listOf(R.id.mood_joy, R.id.mood_content, R.id.mood_sad, R.id.mood_anxiety, R.id.mood_anger)
            .forEach { id ->
                if (id != selectedView.id) view?.findViewById<ImageView>(id)?.alpha = 0.5f
            }
    }

    private fun saveNote() {
        val noteInput = view?.findViewById<EditText>(R.id.mood_note_input)
        val noteText = noteInput?.text.toString().trim()
        if (noteText.isNotEmpty() && selectedMoodColor != null) {
            // Add the new note to the list
            notesList.add(Pair(noteText, selectedMoodColor!!))
            // Save the updated list to SharedPreferences
            val editor = sharedPreferences.edit()
            val json = gson.toJson(notesList)
            editor.putString("notes_list", json)
            editor.apply()

            // Update the RecyclerView
            notesAdapter.notifyDataSetChanged()

            // Clear and hide the input fields
            noteInput?.text?.clear()
            noteInput?.visibility = View.GONE
            view?.findViewById<Button>(R.id.save_note_button)?.visibility = View.GONE
        } else {
            Toast.makeText(requireContext(), "Please enter a note", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadNotes() {
        val json = sharedPreferences.getString("notes_list", null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Pair<String, Int>>>() {}.type
            val loadedNotes: MutableList<Pair<String, Int>> = gson.fromJson(json, type)
            notesList.clear()
            notesList.addAll(loadedNotes)
            notesAdapter.notifyDataSetChanged()
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}