package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {


    // The current word
    var _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    private var _score = MutableLiveData<Int>()
    val score : LiveData<Int>
    get() = _score

    var isGameFinished = MutableLiveData<Boolean>()

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>

    init {
        Log.i(TAG, ": Game view Model created!")
        _word.value = ""
        _score.value = 0
        isGameFinished.value = false
        resetList()
        nextWord()
    }

    override fun onCleared() {
        Log.i(TAG, "onCleared: Game View Model destroyed!")
        super.onCleared()
    }

    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    private fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            isGameFinished.value = true
            gameHasCompleted()
        } else {
            _word.value = wordList.removeAt(0)
        }

    }

    private fun gameHasCompleted() {
         isGameFinished.value = false
    }

    fun onSkip() {
        _score.value = score.value?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        _score.value = score.value?.plus(1)
        nextWord()
    }

    companion object {
        private const val TAG = "GameViewModel"
    }

}