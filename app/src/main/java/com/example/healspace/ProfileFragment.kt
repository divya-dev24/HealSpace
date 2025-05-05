package com.example.healspace

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

class ProfileFragment : Fragment() {

    private val PREFS_NAME = "HealSpacePrefs"
    private val KEY_PROFILE_AVATAR = "profile_avatar"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogOut = view.findViewById<Button>(R.id.btn_log_out)
        btnLogOut.setOnClickListener {
            startActivity(Intent(requireActivity(), WelcomeActivity::class.java))
            requireActivity().finish() // Closes current activity
        }

        val profileAvatar = view.findViewById<ImageView>(R.id.profile_avatar)
        val editProfileAvatar = view.findViewById<Button>(R.id.edit_profile_avatar)
        val profileAvatarsLayout = view.findViewById<View>(R.id.profile_avatars_layout)
        val closeIcon = view.findViewById<ImageView>(R.id.close_icon)
        val chooseButton = view.findViewById<Button>(R.id.profile_choose)

        val avatarOptions = listOf(
            view.findViewById<ImageView>(R.id.profile_pic_1),
            view.findViewById<ImageView>(R.id.profile_pic_2),
            view.findViewById<ImageView>(R.id.profile_pic_3),
            view.findViewById<ImageView>(R.id.profile_pic_4),
            view.findViewById<ImageView>(R.id.profile_pic_5)
        )

        val sharedPrefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val savedAvatarId = sharedPrefs.getInt(KEY_PROFILE_AVATAR, R.id.profile_pic_1)

        // Set saved avatar as profile pic
        avatarOptions.find { it.id == savedAvatarId }?.drawable?.let {
            profileAvatar.setImageDrawable(it)
        }

        editProfileAvatar.setOnClickListener {
            profileAvatarsLayout.visibility = View.VISIBLE
        }

        closeIcon.setOnClickListener {
            profileAvatarsLayout.visibility = View.GONE
        }

        var selectedAvatarId = savedAvatarId

        avatarOptions.forEach { imageView ->
            imageView.setOnClickListener {
                selectedAvatarId = imageView.id // Store the selected avatar's ID
                avatarOptions.forEach { it.alpha = 0.5f } // Dim all images
                imageView.alpha = 1.0f // Highlight selected image
            }
        }

        chooseButton.setOnClickListener {
            val selectedImageView = avatarOptions.find { it.id == selectedAvatarId }
            selectedImageView?.drawable?.let { profileAvatar.setImageDrawable(it) }

            // Save selected avatar ID
            sharedPrefs.edit().putInt(KEY_PROFILE_AVATAR, selectedAvatarId).apply()

            profileAvatarsLayout.visibility = View.GONE
        }
    }
}
