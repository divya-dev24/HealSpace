package com.example.healspace

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val notesList: List<Pair<String, Int>>) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTextView: TextView = itemView.findViewById(R.id.note_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val (note, color) = notesList[position]
        holder.noteTextView.text = note

        // Dynamically update the outline color
        val background = holder.noteTextView.background.mutate() as android.graphics.drawable.GradientDrawable
        background.setStroke(3, color) // Change border color to mood color
    }


    override fun getItemCount(): Int = notesList.size
}
