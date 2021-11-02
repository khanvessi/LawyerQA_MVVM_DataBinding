package com.example.legalqa

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActionRepliesFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action_replies, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return Dialog(requireActivity()).apply {
            setContentView(R.layout.fragment_action_replies)
            window!!.setBackgroundDrawableResource(R.drawable.rounded_dialog)
            window!!.setGravity(Gravity.BOTTOM)
            window!!.setLayout(800,600)
        }

    }

}