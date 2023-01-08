package com.example.drawingapp
//drawing custom view
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import java.security.KeyStore.Entry.Attribute

class DrawingView(context:Context,attri:AttributeSet): View(context,attri) {
   private var drawPath:CustomPath?=null
    private var bitmapCanvas:Bitmap?=null
    private var drawPaint:Paint?=null
    private var canvasPaint:Paint?=null
    private var brushThickness:Float=0.toFloat()
    private var color=Color.BLACK
    private var canvas:Canvas?=null
    private var mpath=ArrayList<CustomPath>()
    private var mUndoPath=ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }
/*to undo using onClickUndo
    removing the path from the mpath and adding that removed path to mUndoPath(storing the path so that i can recover it later)
    invalidate() function calls the onDraw function as it is a function parameter inside graphics
    */
    fun onClickUndo(){
        if(mpath.size > 0 ){
            mUndoPath.add(mpath.removeAt(mpath.size -1))
            invalidate()
        }
    }
    fun onClickRedo(){
        if(mUndoPath.size > 0 ){
            mpath.add(mUndoPath.removeAt(mUndoPath.size -1))
            invalidate()
        }
    }
/* it bassically setup the pencil color with stroke etc
* drawPaint is a object of function paint() of class paint
* and drawpath is a object of customePath(which set color of brush/pencil and it's thickness)
* */
    private fun setUpDrawing(){
        drawPaint=Paint()
        drawPath=CustomPath(color,brushThickness)
        drawPaint!!.color=color
        drawPaint!!.style=Paint.Style.STROKE
        drawPaint!!.strokeJoin=Paint.Join.ROUND
        drawPaint!!.strokeCap=Paint.Cap.SQUARE
        canvasPaint=Paint(Paint.DITHER_FLAG)
//        brushThickness=20.toFloat()
    }
//it bassically change the size of the pen or brush
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmapCanvas=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas= Canvas(bitmapCanvas!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmapCanvas!!,0f,0f,canvasPaint)

        for(path in mpath){
            drawPaint!!.strokeWidth=path.brushThickness
            drawPaint!!.color=path.color
            canvas.drawPath(path,drawPaint!!)
        }

        if(!drawPath!!.isEmpty){
            drawPaint!!.strokeWidth=drawPath!!.brushThickness
            drawPaint!!.color=drawPath!!.color
            canvas.drawPath(drawPath!!,drawPaint!!)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchx=event?.x
        val touchy=event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                drawPath!!.color=color
                drawPath!!.brushThickness=brushThickness

                drawPath!!.reset()
                if (touchx != null) {
                    if (touchy != null) {
                        drawPath!!.moveTo(touchx,touchy)
                    }
                }
            }
            MotionEvent.ACTION_MOVE->{
                if (touchy != null) {
                    if (touchx != null) {
                        drawPath!!.lineTo(touchx,touchy)
                    }
                }
            }
            MotionEvent.ACTION_UP->{
                mpath.add(drawPath!!)
                drawPath=CustomPath(color,brushThickness)
            }
            else->return false
        }
        invalidate()
        return true
    }

    fun setSizeOfBrush(newSize:Float){
        brushThickness=TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
        newSize,resources.displayMetrics
            )
        drawPaint!!.strokeWidth=brushThickness
    }

    fun selectColor(newColor:String){
        color=Color.parseColor(newColor)
        drawPaint!!.color=color
    }
    fun selectColor2(newColor:Int){
        color=newColor
        drawPaint!!.color=color
    }

   internal inner class CustomPath(var color:Int,var brushThickness:Float) : Path() {

    }

}