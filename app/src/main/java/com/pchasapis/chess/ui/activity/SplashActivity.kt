package com.pchasapis.chess.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pchasapis.chess.databinding.ActivitySplashBinding
import com.pchasapis.chess.mvp.presenter.splash.SplashPresenter
import com.pchasapis.chess.mvp.presenter.splash.SplashPresenterImpl
import com.pchasapis.chess.mvp.view.splash.SplashView

class SplashActivity : AppCompatActivity(), SplashView {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = SplashPresenterImpl(this)
        initLayout()
    }

    override fun proceedToHome(maxMoves: String, boardSize: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra(MAX_MOVE,maxMoves.toInt())
        intent.putExtra(BOARD_SIZE,boardSize.toInt())
        startActivity(intent)
        finish()
    }

    override fun showError(splashBoardError: Int) {
        Toast.makeText(this, getString(splashBoardError), Toast.LENGTH_SHORT).show()
    }

    override fun isAttached(): Boolean {
        return !isFinishing
    }

    private fun initLayout() {
        binding.startButton.setOnClickListener {
            presenter.validateFields(
                binding.boardSizeEditText.text?.toString() ?: "",
                binding.moveEditText.text?.toString() ?: "")

        }
    }

    companion object{
        const val MAX_MOVE= "maxMoves"
        const val BOARD_SIZE= "boardSize"
    }
}