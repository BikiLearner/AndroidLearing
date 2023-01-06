package com.example.drawingapp

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

    init {
        setUpDrawing()
    }
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

   internal inner class CustomPath(var color:Int,var brushThickness:Float) : Path() {

    }

}