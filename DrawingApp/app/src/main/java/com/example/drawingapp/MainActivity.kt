package com.example.drawingapp

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private var drawingView:DrawingView?=null
    private var btnBrush:ImageButton?=null
    private var linearLayoutPaintColor:LinearLayout?=null

    private var imageButtonCurrentPaint:ImageButton?=null
    private var canvasBackgroundImage:ImageView?=null
    private var eraserButton:ImageButton?=null
    private var colorPicker:ImageButton?=null
    private var colorFromPicker:Int=0
    private var customProgressDialog:Dialog?=null

    val openGalleryLauncher:ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
            if(result.resultCode== RESULT_OK && result.data!=null){
                canvasBackgroundImage?.setImageURI(result.data?.data)
            }
        }

    val requestPermission:ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
             permission->
            permission.entries.forEach{
                val permissionName=it.key
                val permissionGranted=it.value

                if(permissionGranted){
                    Toast.makeText(this,"Permission granted for $permissionName",Toast.LENGTH_SHORT).show()

                    val pickIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    openGalleryLauncher.launch(pickIntent)

                }else{

                        Toast.makeText(this,"Permission Not granted for $permissionName",Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setId()
        drawingView?.setSizeOfBrush(20.toFloat())

        imageButtonCurrentPaint= linearLayoutPaintColor?.get(3) as ImageButton

        imageButtonCurrentPaint!!.setImageDrawable(
            ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
        )

        btnBrush?.setOnClickListener{
            showBrushSizeChoserDialog()
        }
        //Eraser button
        eraserButton?.setOnClickListener {
            drawingView?.selectColor2(Color.WHITE)
            drawingView?.setSizeOfBrush(30.toFloat())
            imageButtonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this,R.drawable.pallet_normal)
            )

        }

        //file_button
        val selectImageFromFile=findViewById<ImageButton>(R.id.select_image_form_file)
        selectImageFromFile.setOnClickListener {
            requestStorageFunctionality()
        }

        //undo activity
        val undobtn=findViewById<ImageButton>(R.id.undo_btn)
        undobtn.setOnClickListener {
            drawingView?.onClickUndo()
        }
        //redo
         val redoBtn=findViewById<ImageButton>(R.id.Redo_btn)
        redoBtn.setOnClickListener {
            drawingView?.onClickRedo()
        }
        val saveBtn=findViewById<ImageButton>(R.id.save_btn)
        saveBtn.setOnClickListener {
            if(isReadStorageAllowed()){
                showProgressdialog()
                lifecycleScope.launch{
                    val fldrawingView=findViewById<FrameLayout>(R.id.frame_layout)
                    saveBitmapFile(getBitmapImage(fldrawingView))
                }
            }
        }

     colorPicker()



    }

    private fun showBrushSizeChoserDialog(){
        val brushDialog=Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("Brush size :")
        val smallBtn=brushDialog.findViewById<ImageButton>(R.id.ib_small_brush)
        val mediumBtn=brushDialog.findViewById<ImageButton>(R.id.ib_medium_brush)
        val largeBtn=brushDialog.findViewById<ImageButton>(R.id.ib_large_brush)
        val veryLargeBtn=brushDialog.findViewById<ImageButton>(R.id.ib_very_large_brush)
        val veryUltraLargeBtn=brushDialog.findViewById<ImageButton>(R.id.ib_very_ultra_large_brush)
        val customInputsize=brushDialog.findViewById<EditText>(R.id.custom_size)
        smallBtn.setOnClickListener{
            drawingView?.setSizeOfBrush(10.toFloat())
            brushDialog.dismiss()
        }
            mediumBtn.setOnClickListener{
            drawingView?.setSizeOfBrush(20.toFloat())
            brushDialog.dismiss()
        }
        largeBtn.setOnClickListener{
            drawingView?.setSizeOfBrush(30.toFloat())
            brushDialog.dismiss()
        }
       veryLargeBtn.setOnClickListener{
            drawingView?.setSizeOfBrush(40.toFloat())
            brushDialog.dismiss()
        }
       veryUltraLargeBtn.setOnClickListener{
            drawingView?.setSizeOfBrush(50.toFloat())
            brushDialog.dismiss()
        }
        customInputsize.setOnClickListener{
            val input=customInputsize.text.toString()
            if(input.toFloat()<100)
                drawingView?.setSizeOfBrush(input.toFloat())
            else
                drawingView?.setSizeOfBrush(10.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()

    }
    fun paintClicked(view: View){
        if(view!=imageButtonCurrentPaint) {
            val imageButton = view as ImageButton
            if (imageButton.tag.toString() != "") {
                val colorTag = imageButton.tag.toString()
                drawingView?.selectColor(colorTag)

                imageButton.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
                )

                imageButtonCurrentPaint!!.setImageDrawable(
                    ContextCompat.getDrawable(this, R.drawable.pallet_normal)
                )
                imageButtonCurrentPaint = view

            }else{

            }
        }
    }

fun colorPicker(){
colorPicker?.setOnClickListener{
    val colorPickerDialogue = AmbilWarnaDialog(this, colorFromPicker,
        object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {

               Toast.makeText(this@MainActivity,color.toString(),Toast.LENGTH_LONG).show()
                drawingView?.selectColor2(color)
            }

        })
    colorPickerDialogue.show()

    }
}
    private fun isReadStorageAllowed():Boolean{
        val result=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)

        return result== PackageManager.PERMISSION_GRANTED
     }

    private fun requestStorageFunctionality(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,Manifest.permission.READ_EXTERNAL_STORAGE)
        ){
            showRationaleDialog("DrawingApp","DrawingApp need to access your External Storage")

        }else{
            requestPermission.launch(arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE

            ))
        }

    }
    private fun showRationaleDialog(
        title: String,
        message: String,
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun getBitmapImage(view:View):Bitmap{
        val returnBitmap=Bitmap.createBitmap(view.width,view.height,
            Bitmap.Config.ARGB_8888)
        val canvas=Canvas(returnBitmap)
        val bgDrawable=view.background
        if(bgDrawable!=null)
            bgDrawable.draw(canvas)
        else
            canvas.drawColor(Color.WHITE)

        view.draw(canvas)
        return returnBitmap

    }

    private suspend fun saveBitmapFile(mBitmap:Bitmap?):String{
        var result=""
        withContext(Dispatchers.IO){
            if(mBitmap!=null){
                try {
                    val bytes=ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG,90,bytes)

                    val f=File(externalCacheDir?.absoluteFile.toString() +
                    File.separator + "DrawingApp_" + System.currentTimeMillis()/1000 + ".png")
                    val fo=FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                    fo.close()

                    result =f.absolutePath

                    runOnUiThread{
                        cancelProgressDialog()
                        if(result.isNotEmpty()) {
                            Toast.makeText(
                                this@MainActivity,
                                "File Save successfully  : $result",
                                Toast.LENGTH_SHORT
                            ).show()
                            shareImg(result)
                        }else
                            Toast.makeText(this@MainActivity,"Something went wrong saving the file",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:Exception){
                    result=""
                    e.printStackTrace()
                }
            }
        }
        return result
    }
private fun showProgressdialog(){
    customProgressDialog=Dialog(this@MainActivity)
    customProgressDialog?.setContentView(R.layout.custom_progress_dialog)
    customProgressDialog?.show()
}
    private fun cancelProgressDialog(){
        if(customProgressDialog!=null){
            customProgressDialog?.dismiss()
            customProgressDialog=null
        }

    }
    private fun shareImg(result: String){
        MediaScannerConnection.scanFile(this, arrayOf(result),null){
            path,uri->
            val shareIntent=Intent()
            shareIntent.action=Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_STREAM,uri)
            shareIntent.type="image/png"
            startActivity(Intent.createChooser(shareIntent,"share"))
        }
    }
    private fun setId(){
        drawingView=findViewById(R.id.drawing_view)
        btnBrush=findViewById(R.id.brush_btn)
        linearLayoutPaintColor=findViewById(R.id.linear_layout)
        eraserButton=findViewById(R.id.eraser_button)
        colorPicker=findViewById(R.id.color_picker)
        canvasBackgroundImage=findViewById(R.id.image)

    }
}

