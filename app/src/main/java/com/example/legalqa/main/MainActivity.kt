package com.example.legalqa.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Display
import android.view.View
import android.widget.Switch
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapters.*
import com.example.legalqa.*
import com.example.legalqa.databinding.ActivityMainBinding
import com.example.utils.exhaustive
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

import kotlinx.coroutines.flow.collect
import java.io.ByteArrayOutputStream
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.view.marginTop
import androidx.lifecycle.MutableLiveData
import androidx.core.app.ActivityCompat.startActivityForResult
import android.os.Environment
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var binding: ActivityMainBinding
    private val directionFragment = ActionCommentFragment()
    private val actionCommentRepliesFragment = ActionRepliesFragment()
    var commentsAdapter: CommentsAdapter? = null
    var commentUploadAttachmentAdapter: CommentUploadAttachmentAdapter? = null

    var commentsRepliesAdapter: CommentsRepliesAdapter? = null
    private val PERMISSION_CODE = 2000
    private val CAMERA_REQUEST = 0
    var mCurrentPhotoPath: String? = null

    //REQUEST CODES
    private val CAMERA_CODE = 101
    private val IMAGE_CODE = 102
    private val VIDEO_CODE = 103
    private val FILE_CODE = 104
    private var flag: Int = 0

    var listOfAttachmentUrls: List<CommentAttachment> = arrayListOf()
    var arrayListOfAttachments: ArrayList<CommentAttachment> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.mainViewModel = mainViewModel
        setContentView(binding.root)

        mainViewModel.getDataFromJson(applicationContext)

//        val display: Display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//        val width: Int = size.x
//        val height: Int = size.y

        binding.apply {

            //SETTING MARGINS PROGRAMMATICALLY
//            val margins = (edtReplying.layoutParams as ConstraintLayout.LayoutParams).apply {
//                topMargin = height - 500
//                rightMargin = 45
//            }
//            edtReplying.layoutParams = margins

            edtReplying.setOnClickListener(View.OnClickListener {
                //txtReplytoadmin.visibility = View.VISIBLE
            })

            txtCommentAll.setOnClickListener(View.OnClickListener {
                //TODO
                txtCommentToday.setBackgroundResource(R.drawable.round_white)
                txtCommentToday.setTextColor(Color.BLACK)

                txtCommentYesterday.setBackgroundResource(R.drawable.round_white)
                txtCommentYesterday.setTextColor(Color.BLACK)

                txtCommentLastweek.setBackgroundResource(R.drawable.round_white)
                txtCommentLastweek.setTextColor(Color.BLACK)

                txtCommentLastmonth.setBackgroundResource(R.drawable.round_white)
                txtCommentLastmonth.setTextColor(Color.BLACK)

                it.setBackgroundResource(R.drawable.round)
                txtCommentAll.setTextColor(Color.WHITE)

                txtNoRecords.visibility = View.GONE
                recComments.visibility = View.VISIBLE

            })

            txtCommentToday.setOnClickListener(View.OnClickListener {
                //TODO
                txtCommentAll.setBackgroundResource(R.drawable.round_white)
                txtCommentAll.setTextColor(Color.BLACK)

                txtCommentYesterday.setBackgroundResource(R.drawable.round_white)
                txtCommentYesterday.setTextColor(Color.BLACK)

                txtCommentLastweek.setBackgroundResource(R.drawable.round_white)
                txtCommentLastweek.setTextColor(Color.BLACK)

                txtCommentLastmonth.setBackgroundResource(R.drawable.round_white)
                txtCommentLastmonth.setTextColor(Color.BLACK)

                it.setBackgroundResource(R.drawable.round)
                txtCommentToday.setTextColor(Color.WHITE)

                txtNoRecords.visibility = View.VISIBLE
                recComments.visibility = View.GONE

            })

            txtCommentYesterday.setOnClickListener(View.OnClickListener {
                //TODO
                txtCommentAll.setBackgroundResource(R.drawable.round_white)
                txtCommentAll.setTextColor(Color.BLACK)

                txtCommentToday.setBackgroundResource(R.drawable.round_white)
                txtCommentToday.setTextColor(Color.BLACK)

                txtCommentLastweek.setBackgroundResource(R.drawable.round_white)
                txtCommentLastweek.setTextColor(Color.BLACK)

                txtCommentLastmonth.setBackgroundResource(R.drawable.round_white)
                txtCommentLastmonth.setTextColor(Color.BLACK)

                it.setBackgroundResource(R.drawable.round)
                txtCommentYesterday.setTextColor(Color.WHITE)

                txtNoRecords.visibility = View.VISIBLE
                recComments.visibility = View.GONE

            })

            txtCommentLastweek.setOnClickListener(View.OnClickListener {
                //TODO

                txtCommentAll.setBackgroundResource(R.drawable.round_white)
                txtCommentAll.setTextColor(Color.BLACK)

                txtCommentToday.setBackgroundResource(R.drawable.round_white)
                txtCommentToday.setTextColor(Color.BLACK)

                txtCommentYesterday.setBackgroundResource(R.drawable.round_white)
                txtCommentYesterday.setTextColor(Color.BLACK)

                txtCommentLastmonth.setBackgroundResource(R.drawable.round_white)
                txtCommentLastmonth.setTextColor(Color.BLACK)

                it.setBackgroundResource(R.drawable.round)
                txtCommentLastweek.setTextColor(Color.WHITE)

                txtNoRecords.visibility = View.VISIBLE
                recComments.visibility = View.GONE

            })

            txtCommentLastmonth.setOnClickListener(View.OnClickListener {
                //TODO
                txtCommentAll.setBackgroundResource(R.drawable.round_white)
                txtCommentAll.setTextColor(Color.BLACK)

                txtCommentToday.setBackgroundResource(R.drawable.round_white)
                txtCommentToday.setTextColor(Color.BLACK)

                txtCommentYesterday.setBackgroundResource(R.drawable.round_white)
                txtCommentYesterday.setTextColor(Color.BLACK)

                txtCommentLastweek.setBackgroundResource(R.drawable.round_white)
                txtCommentLastweek.setTextColor(Color.BLACK)

                it.setBackgroundResource(R.drawable.round)
                txtCommentLastmonth.setTextColor(Color.WHITE)

                txtNoRecords.visibility = View.VISIBLE
                recComments.visibility = View.GONE

            })


        }

        //OBSERVING CLICKS ON SUB REPLIES THREE DOTS
        mainViewModel.observerForSubRepliesThreedots.observe(this){
            if(it == true){
                //Log.e(TAG, "bind: ACCTION REPLYINNG USER", )
                actionCommentRepliesFragment.show(
                    supportFragmentManager,
                    actionCommentRepliesFragment.tag
                )
                mainViewModel.observerForSubRepliesThreedots.value = false
            }
        }

        //OVERSERVING CLICKS ON SUB REPIES
        mainViewModel.observerForSubReplies.observe(this){
                binding.txtReplytoadmin.visibility = View.VISIBLE
                binding.txtReplytoadmin.text = "Replying to $it"
                mainViewModel.observerForSubReplies1.value = true
        }

        lifecycleScope.launchWhenCreated {
            commentsAdapter?.commentEventChannelFromAdapter?.collect { event ->
                when (event) {
                    CommentsAdapter.CommentEvent.OpenActionForReplyingUser -> {
                        //NOT USING THIS
                    }
                }.exhaustive
            }

            mainViewModel.mainActivityChannel?.collect { event ->
                when (event) {
                    //MAIN FOR MAIN ACTIVITY
                    is MainViewModel.MainActivityEvent.AddingCommentsAndRepliesToAdapter -> {
                        commentsAdapter =
                            event.commentsReplies?.let {
                                CommentsAdapter(event.comments, it, CommentClickListener ({
                                    //HANDLE CLICK EVENT HERE
                                    directionFragment.show(
                                        supportFragmentManager,
                                        directionFragment.tag
                                    )
                                }, { comment ->
//                                    Log.e(TAG, "onCreate: REPLY CLICKED")
                                    binding.txtReplytoadmin.visibility = View.VISIBLE
                                    binding.txtReplytoadmin.text = "Replying to ${comment.commentBy}"
                                    mainViewModel.isReplying.value = true
                                    mainViewModel.observeIsplaying.value = comment
                                }, {

                                    if(flag == 0){
                                        mainViewModel.isShowClicked.value = true
                                        mainViewModel.clickedPosition.value = it
                                        flag = 1
                                    }else{
                                        mainViewModel.isShowClicked.value = false
                                        mainViewModel.clickedPosition.value = it
                                        flag = 0
                                    }
                                }), mainViewModel.isShowClicked,
                                    this@MainActivity,
                                    mainViewModel.clickedPosition,
                                    mainViewModel.observerForSubRepliesThreedots,
                                    mainViewModel.observerForSubReplies,

                                )
                            }

                        binding.questionDetails = event.questionDetail
                        binding.recComments.adapter = commentsAdapter
                        binding.recComments.layoutManager = LinearLayoutManager(applicationContext)
                        binding.recComments.setHasFixedSize(true)

                        binding.edtReplying.text = null
                    }

                    //QUESTIONATTACHMENT
                    is MainViewModel.MainActivityEvent.AddingAttachmentToAdapter -> {
                        val attachmentsAdapter =
                            QuestionAttachmentAdapter(event.attachmentList)
                        binding.recviewImages.adapter = attachmentsAdapter
                        binding.recviewImages.layoutManager =
                            LinearLayoutManager(
                                applicationContext,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        binding.recviewImages.setHasFixedSize(true)
                    }

                    //ON UPLOAD BTNCLICK
                    MainViewModel.MainActivityEvent.OnUploadBtnClicked -> {
//                        Log.e(TAG, "onCreate: ON UPLOAD CLICK")
                        checkPermissions()
                    }

                    //VALIDATION
                    MainViewModel.MainActivityEvent.ShowValidationMessage -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Please type the comment!",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("OK") {

                            }
                            .setBackgroundTint(Color.rgb(222,171,82))
                            .setTextColor(Color.BLACK)
                            .show()
                    }

                    //SUCCES SENDING COMMENT
                    MainViewModel.MainActivityEvent.ShowSuccessComment -> {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            "Comment posted!",
                            Snackbar.LENGTH_SHORT
                        )
                            .setAction("Cancel") {
                            }
                            .setBackgroundTint(Color.rgb(222,171,82))
                            .setTextColor(Color.BLACK)
                            .show()
                    }

                    //ON CANCEL REPLYING TO
                    MainViewModel.MainActivityEvent.OnCancelReplyingTo -> {
                        binding.txtReplytoadmin.visibility = View.GONE
                    }
                }.exhaustive
            }
        }

    }


    //MEDIA UPLOAD METHODS
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_CODE
            )
        } else {
            startDialog()
        }
    }

    private fun startDialog() {
//        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(
//            this
//        )
//        myAlertDialog.setTitle("Upload Pictures Option")
//        myAlertDialog.setMessage("How do you want to upload your attachment?")
//        myAlertDialog.setPositiveButton("Gallery",
//            DialogInterface.OnClickListener { _, _ ->
//                openGallery()
//            })
//        myAlertDialog.setNegativeButton("Camera",
//            DialogInterface.OnClickListener { _, _ ->
//                openCamera()
//            })
//        myAlertDialog.show()

        val choice = arrayOf("Camera", "Image", "Video", "File")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an Attachment")
        builder.setItems(choice) { dialog, which ->
            // the user clicked on colors[which]
            if ("Camera" == choice[which]) {
                openCamera()
            } else if ("Image" == choice[which]) {
                openGallery()
                //openImage()
            } else if ("Video" == choice[which]) {
                openVideo()
            } else if ("File" == choice[which]) {
                openFile()
            } else {
                //
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_CODE)

//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if (cameraIntent.resolveActivity(packageManager) != null) {
//            Log.e(TAG, "openCamera: NOT NULL", )
//            // Create the File where the photo should go
//            var photoFile: File? = null
//            try {
//                photoFile = createImageFile()
//            } catch (ex: IOException) {
//                // Error occurred while creating the File
//                Log.i(TAG, "EXEPTION: "+ex.message)
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Log.e(TAG, "openCamera: NOT NULL", )
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
//                startActivityForResult(cameraIntent, CAMERA_CODE)
//            }
//        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val image: File = File.createTempFile(
            imageFileName,  // prefix
            ".jpg",  // suffix
            storageDir // directory
        )

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.absolutePath
//        Log.e(TAG, "createImageFile: FILE $mCurrentPhotoPath", )
        return image
    }

//    private fun openImage() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, 2)
////        val intent = Intent()
////        intent.type = "image/*"
////        intent.action = Intent.ACTION_GET_CONTENT
////        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_CODE)
//
//    }


    private fun openGallery() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, IMAGE_CODE)

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,IMAGE_CODE)
    }

    private fun openVideo() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, 3)

        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,VIDEO_CODE)
    }

    private fun openFile() {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.F)
//        startActivityForResult(galleryIntent, IMAGE_CODE)
        val intent = Intent(Intent.ACTION_GET_CONTENT);
        intent.type = "application/pdf";
        startActivityForResult(intent, FILE_CODE);
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //openImage()
                openGallery()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val cmtAttachment = CommentAttachment()

        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
//            Log.e(TAG, "onActivityResult: IMAGE PATH $mCurrentPhotoPath", )

            if (data?.data != null) {
                val uri = data.data
                Log.e("Gallery Image Uri: ", uri.toString() + "")

                val bitmap = MediaStore.Images.Media.getBitmap(
                    applicationContext?.contentResolver,
                    uri
                )
                val imagePath = uri?.let { getRealPathFromURI(it) }
                if (imagePath != null) {
                    cmtAttachment.attachmentUrl = imagePath
                }
                addCommentAttachment(cmtAttachment)
            }

        }


        if (requestCode == IMAGE_CODE && resultCode != RESULT_CANCELED) {
            if (data?.extras?.get("data") != null) {
                val uri = data?.extras?.get("data")
                val tempUri: Uri =
                    getImageUri(this, uri as Bitmap)

                val imagePath = getRealPathFromURI(tempUri)
                cmtAttachment.attachmentUrl = imagePath
                addCommentAttachment(cmtAttachment)
            }
        }

        if (requestCode == VIDEO_CODE) {
//            Log.e(TAG, "onActivityResult: VIDEO " )
            if (data?.extras?.get("data") != null) {
                binding.attachOnReplying.visibility = View.VISIBLE
                val uri = data.data
                val path = uri?.path
//                Log.e(TAG, "onActivityResult: VIDEO $path" )

//                val uri = data.extras?.get("data")
//                val tempUri: Uri =
//                    getImageUri(this, uri as Bitmap)
//
//                val imagePath = getRealPathFromURI(tempUri)
//                cmtAttachment.attachmentUrl = imagePath
//                addCommentAttachment(cmtAttachment)
            }
        }
//
        if (requestCode == FILE_CODE) {
//            Log.e(TAG, "onActivityResult: FILE ")
            if (data?.extras?.get("data") != null) {
                binding.attachOnReplying.visibility = View.VISIBLE
                val uri = data.data
                val path = uri?.path
//                Log.e(TAG, "onActivityResult: FILE $path")


//                val uri = data.extras?.get("data")
//
//                val uri = data.extras?.get("data")
//                val tempUri: Uri =
//                    getImageUri(this, uri as Bitmap)
//
//                val imagePath = getRealPathFromURI(tempUri)
//                cmtAttachment.attachmentUrl = imagePath
//                addCommentAttachment(cmtAttachment)
            }
        }
    }

    private fun addCommentAttachment(commentAttachment: CommentAttachment) {
        arrayListOfAttachments.add(commentAttachment)
        //listOfAttachmentUrls = arrayListOfAttachments
        mainViewModel.listOfCommentsAttachments.value = arrayListOfAttachments
//        Log.e(TAG, "addCommentAttachment: ${listOfAttachmentUrls.lastIndex}")
        addAttachmentListToRecView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addAttachmentListToRecView() {
        commentUploadAttachmentAdapter = mainViewModel.listOfCommentsAttachments.value?.let {
            CommentUploadAttachmentAdapter(
                it, AttachmentListener { pos ->
                    var mutableList = mainViewModel.listOfCommentsAttachments.value?.toMutableList()
                    mutableList?.removeAt(pos)
                    mainViewModel.listOfCommentsAttachments.value = mutableList
                    binding.attachOnReplying.adapter?.notifyDataSetChanged()
                })
        }

        binding.apply {
            attachOnReplying.apply {
                adapter = commentUploadAttachmentAdapter
                layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
            attachOnReplying.visibility = View.VISIBLE
        }

    }

    private fun getRealPathFromURI(uri: Uri): String {
        var path = ""
        if (applicationContext?.contentResolver != null) {
            val cursor: Cursor? = applicationContext?.contentResolver
                ?.query(uri, null, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                path = cursor.getString(idx)
                cursor.close()
            }
        }

//        Log.e(TAG, "getRealPathFromURI: $path")
        return path
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Image", null)
        return Uri.parse(path)
    }
}