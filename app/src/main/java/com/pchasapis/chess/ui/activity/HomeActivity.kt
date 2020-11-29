package com.pchasapis.chess.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pchasapis.chess.common.utils.SharePreferencesUtil
import com.pchasapis.chess.databinding.ActivityHomeBinding
import com.pchasapis.chess.model.Position
import com.pchasapis.chess.mvp.presenter.home.HomePresenter
import com.pchasapis.chess.mvp.presenter.home.HomePresenterImpl
import com.pchasapis.chess.mvp.view.home.HomeView
import com.pchasapis.chess.ui.activity.SplashActivity.Companion.BOARD_SIZE
import com.pchasapis.chess.ui.activity.SplashActivity.Companion.MAX_MOVE
import com.pchasapis.chess.ui.customView.ChessView.BoardListener
import java.util.*

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HomePresenterImpl(
            this,
            getMoveArgument(),
            getBoardArgument(),
            SharePreferencesUtil.getBoardSize(this),
            SharePreferencesUtil.getKnightPosition(this),
            SharePreferencesUtil.getTargetPosition(this),
            SharePreferencesUtil.getMaxMoves(this)
        )
        binding.chessView.setDimension(presenter.getBoardSize())
        presenter.setBoard()
        initLayout()
    }

    override fun showPosition(piecePosition: Position, drawableIcon: Int, i: Int) {
        binding.chessView.setPiece(
            piecePosition.i,
            piecePosition.j,
            drawableIcon,
            i
        )
    }

    override fun moviePiece(path: ArrayList<Position>) {
        binding.chessView.movePiece(path.reversed())
    }

    override fun showError(errorMessage: Int, count: Int?) {
        Toast.makeText(this, getString(errorMessage, count), Toast.LENGTH_SHORT).show()
    }

    override fun removePiece(it: Position) {
        binding.chessView.removePiece(it.i, it.j)
    }

    override fun handleLoadingView(visibility: Boolean) {
        binding.loadingView.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    override fun savePositions(knightPosition: Position,
                               targetPosition: Position,
                               boardSize: Int,
                               maxMoves: Int) {
        SharePreferencesUtil.setKnightPosition(knightPosition.i, knightPosition.j, this)
        SharePreferencesUtil.setTargetPosition(targetPosition.i, targetPosition.j, this)
        SharePreferencesUtil.setBoardSize(boardSize, this)
        SharePreferencesUtil.setMaxMoves(maxMoves, this)
    }

    override fun isAttached(): Boolean {
        return !isFinishing
    }

    private fun initLayout() {
        binding.resetButton.setOnClickListener {
            presenter.clearChess()
        }

        binding.toolbar.backButtonImageView.setOnClickListener {
            finish()
        }

        binding.chessView.setBoardListener(object : BoardListener {
            override fun onTileClicked(piecePosition: Position?, positionTile: Position) {
                presenter.calculateTile(positionTile)
            }
        })
    }

    private fun getBoardArgument(): Int {
        return intent.getIntExtra(BOARD_SIZE, BOARD_DEFAULT_DIMENSION)
    }

    private fun getMoveArgument(): Int {
        return intent.getIntExtra(MAX_MOVE, MAX_DEFAULT_MOVES)
    }

    companion object {
        private const val BOARD_DEFAULT_DIMENSION = 6
        private const val MAX_DEFAULT_MOVES = 3
    }
}
