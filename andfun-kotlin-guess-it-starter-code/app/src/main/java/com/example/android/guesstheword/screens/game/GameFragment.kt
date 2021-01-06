
package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.i(Companion.TAG, "onCreateView: Called viewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)

        binding.correctButton.setOnClickListener {
            viewModel.onCorrect()
        }
        binding.skipButton.setOnClickListener {
            viewModel.onSkip()
        }

        viewModel.score.observe(this, Observer { newScore ->
            binding.scoreText.text = newScore.toString()
        })
        viewModel.word.observe(this, Observer { newWord ->
            binding.wordText.text = newWord.toString()
        })

        viewModel.isGameFinished.observe(this, Observer { hasGameFinished ->
            if (hasGameFinished == true)
            {
                gameFinished()
            }
        })
        return binding.root

    }

    private fun gameFinished() {
        val action = GameFragmentDirections.actionGameToScore(viewModel.score.value ?: 0)
        findNavController(this).navigate(action)
    }

    companion object {
        private const val TAG = "GameFragment"
    }
}
