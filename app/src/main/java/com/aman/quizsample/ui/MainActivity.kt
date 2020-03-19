package com.aman.quizsample.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aman.quizsample.R
import com.aman.quizsample.databinding.ActivityMainBinding
import com.aman.quizsample.extension.createFactory
import com.aman.quizsample.repo.QuizRepoI
import com.aman.quizsample.room.entity.Quiz
import com.aman.quizsample.ui.adapter.QuizAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: QuizAdapter

    private var quizList: List<Quiz> = listOf()

    @Inject
    lateinit var quizRepo: QuizRepoI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        init()
        getAllList()
        setObserver()
        setRecyclerView()
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
        adapter = QuizAdapter(quizList)
        rv_answers.adapter = adapter
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
