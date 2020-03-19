package com.aman.quizsample.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.aman.quizsample.R
import com.aman.quizsample.databinding.ActivityMainBinding
import com.aman.quizsample.extension.createFactory
import com.aman.quizsample.extension.mapInPlace
import com.aman.quizsample.repo.QuizRepoI
import com.aman.quizsample.room.entity.Answer
import com.aman.quizsample.room.entity.Quiz
import com.aman.quizsample.ui.adapter.OnClickListener
import com.aman.quizsample.ui.helper.SnapOnScrollListener.Companion.NOTIFY_ON_SCROLL
import com.aman.quizsample.ui.adapter.QuizAdapter
import com.aman.quizsample.ui.helper.SnapHelperByOne
import com.aman.quizsample.ui.helper.SnapOnScrollListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: QuizAdapter

    private var quizList: List<Quiz> = listOf()

    private var answeredList: List<Answer> = mutableListOf()

    private val snapHelper = SnapHelperByOne()

    @Inject
    lateinit var quizRepo: QuizRepoI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
        getAllList()
        setObserver()
        setRecyclerView()
        setRecyclerOnScrollListener()
    }

    private fun init() {
        Log.d(TAG, " >>> Initializing viewModel")

        val factory = MainViewModel(quizRepo).createFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun getAllList() {
        viewModel.getAllQuestion()
    }

    private fun setObserver() {
        viewModel.observableState.observe(this, Observer {
            when {
                it.success -> {
                    Handler().postDelayed({
                        binding.state = it
                    }, 700)

                    quizList = it.list!!
                    adapter.quizList = it.list!!
                    adapter.notifyDataSetChanged()
                }
                else -> binding.state = it
            }
        })
    }

    private fun setRecyclerView() {
        rv_answers.layoutManager = LinearLayoutManager(
            this, RecyclerView.HORIZONTAL, false)
        adapter = QuizAdapter(quizList, answeredList, object : OnClickListener{
            override fun onItemClick(quiz: Quiz, position: Int, answerIndex: Int) {
                if (answeredList.isEmpty()) {
                    answeredList =
                        answeredList + Answer(quiz.question, position, answerIndex, quiz.correctIndex)
                } else {
                    var isItemFound = false
                    answeredList.find {
                        if (it.selectedPosition == position) {
                            it.answerIndex = answerIndex
                            isItemFound = true
                            true
                        } else {
                            false
                        }
                    }

                    if (!isItemFound) {
                        answeredList = answeredList + Answer(quiz.question, position, answerIndex, quiz.correctIndex)
                    }
                }

                adapter.answerList = answeredList
                adapter.notifyItemChanged(position)
            }
        })
        rv_answers.adapter = adapter
        rv_answers.itemAnimator = null

        snapHelper.attachToRecyclerView(rv_answers)
    }

    private fun setRecyclerOnScrollListener() {
        rv_answers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
            }

            override fun onScrolled(recyclerView: RecyclerView, i: Int, i2: Int) {
                val childCount: Int = recyclerView.childCount
                val width: Int = recyclerView.getChildAt(0).width
                val padding: Int = (recyclerView.width - width) / 2
                for (j in 0 until childCount) {
                    val v: View = recyclerView.getChildAt(j)

                    var rate = 0f
                    if (v.left <= padding) {
                        rate = if (v.left >= padding - v.width) {
                            (padding - v.left) * 1f / v.width
                        } else {
                            1f
                        }
                        v.scaleY = 1 - rate * 0.1f
                        v.scaleX = 1 - rate * 0.1f
                    } else {
                        if (v.left <= recyclerView.width - padding) {
                            rate = (recyclerView.width - padding - v.left) * 1f / v.width
                        }
                        v.scaleY = 0.9f + rate * 0.1f
                        v.scaleX = 0.9f + rate * 0.1f
                    }
                }
            }
        })

        rv_answers.addOnScrollListener(SnapOnScrollListener(snapHelper, NOTIFY_ON_SCROLL) { position ->
                binding.selectedQuestion = position
                binding.shouldShowSubmitBtn = (position + 1) == quizList.size
                adapter.notifyItemChanged(position)

                Log.d(TAG, "Scroll Position : $position List Size : ${quizList.size}")
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
