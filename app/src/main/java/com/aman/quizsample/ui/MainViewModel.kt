package com.aman.quizsample.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aman.quizsample.repo.QuizRepoI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val quizRepoI: QuizRepoI): ViewModel() {

    var observableState: MutableLiveData<MainState> = MutableLiveData()

    private val compositeDisposable = CompositeDisposable()

    private var state = MainState()
        set(value) {
            field = value
            publishState(value)
        }

    fun getAllQuestion() {
        Log.d(TAG, " >>> Received call to get question list")
        state = state.copy(loading = true, success = false, failure = false, list = null)
        compositeDisposable.add(
            quizRepoI.getAllItem()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, " >>> List: $it")
                    state = state.copy(loading = false, success = true, failure = false, list = it)
                },{
                    state = state.copy(
                        loading = false,
                        failure = true,
                        success = false,
                        message = it.localizedMessage)
                })
        )
    }

    private fun publishState(state: MainState) {
        Log.d(TAG," >>> Publish State : $state")
        observableState.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, " >>> Clearing compositeDisposable")
        compositeDisposable.clear()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}