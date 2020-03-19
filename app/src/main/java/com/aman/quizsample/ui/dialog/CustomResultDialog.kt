package com.aman.quizsample.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.aman.quizsample.R
import com.aman.quizsample.pojo.Result

class CustomResultDialog: AppCompatDialogFragment() {

    private lateinit var rootView: View

    private lateinit var result: Result

    private var tvTotalQuestion: AppCompatTextView? = null
    private var tvAttemptedQuestion: AppCompatTextView? = null
    private var tvCorrectAnswer: AppCompatTextView? = null
    private var tvWrongAnswer: AppCompatTextView? = null
    private var tvSkippedQuestion: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            result = it.getParcelable<Result>(RESULT)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.layout_custom_result, container,false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setDetails(result)
    }

    private fun setDetails(result: Result) {
        tvTotalQuestion!!.text = result.totalQuestion.toString()
        tvAttemptedQuestion!!.text = result.attemptedQuestion.toString()
        tvCorrectAnswer!!.text = result.correctAnswer.toString()
        tvWrongAnswer!!.text = result.wrongAnswer.toString()
        tvSkippedQuestion!!.text = result.skippedQuestion.toString()
    }

    private fun initView() {
        tvTotalQuestion = rootView.findViewById(R.id.tv_total_question_value)
        tvAttemptedQuestion = rootView.findViewById(R.id.tv_attempted_question_value)
        tvCorrectAnswer = rootView.findViewById(R.id.tv_correct_answer_value)
        tvWrongAnswer = rootView.findViewById(R.id.tv_wrong_answer_value)
        tvSkippedQuestion = rootView.findViewById(R.id.tv_skipped_question_value)

        val retakeBtn = rootView.findViewById<AppCompatButton>(R.id.btn_retake)
        val cancelBtn = rootView.findViewById<AppCompatButton>(R.id.btn_cancel)

        retakeBtn.setOnClickListener {
            onDialogListener.onClick()
            dialog?.dismiss()
        }
        cancelBtn.setOnClickListener { dialog?.dismiss()  }
    }

    override fun onStart() {
        super.onStart()
        val d = dialog
        if (d != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            d.window?.setLayout(width, height)
        }
    }

    companion object {
        private lateinit var onDialogListener: OnDialogListener
        private const val TAG = "CustomResultDialog"
        private const val RESULT = "ASSESSMENT_RESULT"

        fun newInstance(result: Result, onDialogListener: OnDialogListener): CustomResultDialog {
            this.onDialogListener = onDialogListener
            val customResultDialog = CustomResultDialog()

            val args = Bundle()
            args.putParcelable(RESULT, result)

            customResultDialog.arguments = args
            return customResultDialog
        }
    }
}