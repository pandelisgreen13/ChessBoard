package com.pchasapis.chess.ui.activity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pchasapis.chess.model.Position
import com.pchasapis.chess.databinding.ActivityHomeBinding
import com.pchasapis.chess.mvp.presenter.HomePresenter
import com.pchasapis.chess.mvp.presenter.HomePresenterImpl
import com.pchasapis.chess.mvp.view.HomeView
import com.pchasapis.chess.ui.customView.ChessView.BoardListener
import java.util.*

class HomeActivity : AppCompatActivity(), HomeView {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.chessView.setDimension(BOARD_DEFAULT_DIMENSION)
        presenter = HomePresenterImpl(this, MAX_MOVES)
        presenter.setBoard(BOARD_DEFAULT_DIMENSION)

        binding.resetButton.setOnClickListener {
            presenter.clearChess()
        }

        binding.chessView.setBoardListener(object : BoardListener {
            override fun onTileClicked(piecePosition: Position?, positionTile: Position) {
                presenter.calculateTile(piecePosition, positionTile)
            }
        })
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
        Handler().postDelayed(
            { binding.chessView.movePiece(path.reversed()) },
            POST_DELAY_ANIMATION)
    }

    override fun showError(errorMessage: Int, count: Int?) {
        Toast.makeText(this, getString(errorMessage, count), Toast.LENGTH_SHORT).show()
    }

    override fun removePiece(it: Position) {
        binding.chessView.removePiece(it.i, it.j)
    }

    companion object {
        const val BOARD_DEFAULT_DIMENSION = 6
        const val POST_DELAY_ANIMATION = 200L
        const val MAX_MOVES = 3
    }
}
