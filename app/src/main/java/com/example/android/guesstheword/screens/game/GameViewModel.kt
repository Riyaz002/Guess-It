package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber


class GameViewModel: ViewModel() {

    companion object Timer{
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val TOTAL_TIME = 60000L
    }

    // The current word
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() =  _word
    // The current score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _eventGameFinished = MutableLiveData<Boolean>()
    val eventGameFinished: LiveData<Boolean> get() = _eventGameFinished

    private val _timeLeft = MutableLiveData<String>()
    val timeLeft: LiveData<String> get() = _timeLeft

    private var timer: CountDownTimer

    // The list of words - the front of the list is the next word to guess
    lateinit var wordList: MutableList<String>

    /**
     * Resets the list of words and randomizes the order
     */
    fun resetList() {
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
//
//    private fun startTimer(){
//        _timeLeft.value = 10
//        while(_timeLeft.value != 0){
//            _timeLeft.value?.minus(1)
//            Thread.sleep(1000)
//        }
//        if(_timeLeft.value == 0){
//            nextWord()
//        }
//    }

    /**
     * Moves to the next word in the list
     */
    fun nextWord() {
        //Select and remove a word from the list
        if (wordList.isEmpty()) {
            resetList()
        }
        _word.value = wordList.removeAt(0)
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
        Log.i("GameViewModel", "onSkip() called; score = ${score.value}")
    }

    fun onCorrect() {
        _score.setValue(_score.value?.plus(1))
        nextWord()
        Log.i("GameViewModel", "onCorrect() called; score = ${score.value}")
    }

    init {
        Log.i("GameViewModel", "GameViewModel created!")
        Timber.tag("Timber: GameViewModel Created!")
        _score.value = 0
        _word.value = ""
        _eventGameFinished.value = false
        resetList()
        nextWord()
        timer = object: CountDownTimer(TOTAL_TIME, ONE_SECOND){

            override fun onTick(p0: Long) {
                _timeLeft.value = DateUtils.formatElapsedTime(p0/1000)
            }

            override fun onFinish() {
                _eventGameFinished.value = true
            }
        }.start()
    }

    fun onGameComplete(){
        _eventGameFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
        Log.i("GameViewModel", "onCleared() is called")
    }
}