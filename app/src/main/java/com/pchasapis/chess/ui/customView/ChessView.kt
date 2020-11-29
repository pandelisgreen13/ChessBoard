package com.pchasapis.chess.ui.customView

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.pchasapis.chess.R
import com.pchasapis.chess.model.Position
import java.util.*

class ChessView : GridLayout {
    private var piecesMatrix: Array<Array<View?>>

    /**
     * Tile background.
     */
    private var lightTile: Drawable? = null
    private var darkTile: Drawable? = null
    private var dimension: Int? = null
    private var dimm = -1

    /**
     * Marking tile animation.
     */
    private var anim = 0

    /**
     * Piece's moviment anim duration.
     */
    private val animMovListDur = 2000

    /**
     * Listener.
     */
    private var boardListener: BoardListener? = null

    constructor(context: Context?) : super(context) {
        piecesMatrix =
            Array(getBoardDimension()) { arrayOfNulls(getBoardDimension()) }
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        piecesMatrix = Array(getBoardDimension()) { arrayOfNulls(getBoardDimension()) }
        initAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        piecesMatrix = Array(getBoardDimension()) { arrayOfNulls(getBoardDimension()) }
        initAttributes(context, attrs)
    }

    public override fun onMeasure(width: Int, height: Int) {
        val parentWidth = MeasureSpec.getSize(width)
        val parentHeight = MeasureSpec.getSize(height)
        setMeasuredDimension(parentWidth, parentHeight)
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        )
        dimm = parentWidth
    }

    private fun initAttributes(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BoardView,
            0, 0
        )
        try {
            darkTile = a.getDrawable(R.styleable.BoardView_darkTileImage)
            lightTile = a.getDrawable(R.styleable.BoardView_lightTileImage)
            anim = a.getInt(R.styleable.BoardView_tileMarkingAnimation, 0)
        } finally {
            a.recycle()
        }

        val arrayOfNulls: Array<View?> = arrayOfNulls(getBoardDimension())
        piecesMatrix = Array(getBoardDimension()) { arrayOfNulls }
    }
    
    fun removePiece(i: Int, j: Int) {
        require(isPosValid(i, j)) { "Invalid position: [$i;$j]." }
        for (i in piecesMatrix.indices) {
            for (j in piecesMatrix.indices) {
                val view = piecesMatrix[i][j]
                view?.let {
                    it.visibility = GONE
                }
                piecesMatrix[i][j] = null
            }
        }
    }

    fun setDimension(dimension: Int) {
        this.dimension = dimension
        val arrayOfNulls: Array<View?> = arrayOfNulls(dimension)
        piecesMatrix = Array(dimension) { arrayOfNulls }
        requestLayout()
    }

    /**
     * Create board background.
     */
    private fun setBoardBackground(dimm: Int) {
        val boardDimension = getBoardDimension()
        for (i in 0 until boardDimension) {
            for (j in 0 until boardDimension) {
                val tile: View = createTile(chooseTileColor(i, j), dimm)
                addViewToGrid(tile, i, j)
            }
        }
    }

    /**
     * Create a piece and set it on te given position.
     *
     * @param i position coord i.
     * @param j position coord j.
     */
    fun setPiece(i: Int, j: Int, imageid: Int, pieceId: Int = 0) {
        val view: View = createPiece(imageid, pieceId)
        require(isPosValid(i, j)) { "Invalid position: [$i;$j]." }
        piecesMatrix[i][j] = view
        addViewToGrid(view, i, j)
    }

    /**
     * Aux for background intercalation.
     *
     * @param i tile coord i.
     * @param j tile coord j.
     * @return image id.
     */
    private fun chooseTileColor(i: Int, j: Int): Drawable? {
        return if (i % 2 == 0) {
            if (j % 2 == 0) {
                lightTile
            } else {
                darkTile
            }
        } else {
            if (j % 2 == 0) {
                darkTile
            } else {
                lightTile
            }
        }
    }

    /**
     * Get tile position.
     *
     * @param tile tile being searched.
     * @return Pos instance.
     */
    private fun getTilePos(tile: View): Position {
        val index = indexOfChild(tile)
        val boardDimension = getBoardDimension()
        val linha = index / boardDimension
        val coluna = index % boardDimension
        return Position(linha, coluna)
    }

    /**
     * @param view View being added.
     * @param i    i coord.
     * @param j    j coord.
     */
    private fun addViewToGrid(view: View, i: Int, j: Int) {
        val indexI = spec(i)
        val indexJ = spec(j)
        val gridParam = LayoutParams(indexI, indexJ)
        this.addView(view, gridParam)
    }

    fun unmarkTile(i: Int, j: Int) {
        val boardDimension = getBoardDimension()
        val pos = i * boardDimension + j
        val view = getChildAt(pos) as FrameLayout
        val image = view.getChildAt(1)
        image.visibility = GONE
    }

    private fun createTile(imageId: Drawable?, dimm: Int): FrameLayout {
        val boardDimension = getBoardDimension()
        val size = dimm / boardDimension
        val frameLayout = FrameLayout(context)
        val params = FrameLayout.LayoutParams(size, size)
        val piece = ImageView(context)
        piece.layoutParams = params
        piece.background = imageId
        piece.scaleType = ImageView.ScaleType.CENTER_CROP
        val marked = FrameLayout(context)
        marked.layoutParams = params
        marked.alpha = 0.5.toFloat()
        marked.visibility = GONE
        frameLayout.setOnClickListener(onClickTile())
        frameLayout.addView(piece)
        frameLayout.addView(marked)
        return frameLayout
    }

    private fun createPiece(imageId: Int, pieceId: Int): LinearLayout {
        val boardDimension = getBoardDimension()
        val size = dimm / boardDimension
        val linearLayout = LinearLayout(context)
        val params = LinearLayout.LayoutParams(size, size, Gravity.CENTER.toFloat())
        val piece = ImageView(context)
        val padding = pxFromDp(context, 5f).toInt()
        piece.setPadding(padding, padding, padding, padding)
        piece.layoutParams = params
        piece.setImageResource(imageId)
        piece.scaleType = ImageView.ScaleType.CENTER_CROP
        linearLayout.id = pieceId
        linearLayout.addView(piece)
        return linearLayout
    }

    private fun pxFromDp(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun getPiece(position: Position): View? {
        return piecesMatrix[position.i][position.j]
    }

    private fun getTile(position: Position): View {
        val size = getBoardDimension()
        return getChildAt(position.i * size + position.j)
    }

    private fun onClickTile(): OnClickListener {
        return OnClickListener { v ->
            if (boardListener != null) {
                val pos = getTilePos(v)
                boardListener!!.onTileClicked(pos)
            }
        }
    }

    fun movePiece(positions: List<Position>) {
        val piece = getPiece(positions[0])
        val eachAnimDur = animMovListDur / positions.size
        for (i in 1 until positions.size) {
            val pos = positions[i]
            val tile = getTile(pos)
            val finalX = tile.x
            val finalY = tile.y
            val animX = ObjectAnimator.ofFloat(piece, "x", finalX)
            val animY = ObjectAnimator.ofFloat(piece, "y", finalY)
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(animX, animY)
            animatorSet.duration = eachAnimDur.toLong()
            animatorSet.startDelay = (eachAnimDur * i).toLong()
            animatorSet.start()
        }
    }

    private fun isPosValid(i: Int, j: Int): Boolean {
        val boardDimension = getBoardDimension()
        val max = boardDimension - 1
        return !(i < 0 || i > max || j < 0 || j > max)
    }

    fun setBoardListener(boardListener: BoardListener?) {
        this.boardListener = boardListener
    }

    interface BoardListener {
        fun onTileClicked(positionTile: Position)
    }

    private fun getBoardDimension(): Int {
        return dimension ?: BOARD_DIMENSION
    }

    private inner class PieceQueue(var position: Position, var imageId: Int)
    companion object {
        /**
         * Board dimension.
         * Default 6x6.
         */
        private const val BOARD_DIMENSION = 6
    }
}