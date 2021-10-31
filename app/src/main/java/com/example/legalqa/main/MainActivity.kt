package com.example.legalqa.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
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

            imgbtnUpload.setOnClickListener(View.OnClickListener {
                checkPermissions()
//                attachOnReplying.visibility = View.VISIBLE
            })
//
//            imgbtnSend.setOnClickListener(View.OnClickListener {
//
//            })

            edtReplying.setOnClickListener(View.OnClickListener {
                //txtReplytoadmin.visibility = View.VISIBLE
            })

            txtReplytoadmin.setOnClickListener(View.OnClickListener {
                txtReplytoadmin.visibility = View.GONE
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

                recCommentsNorecords.visibility = View.GONE
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

                recCommentsNorecords.visibility = View.VISIBLE
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

                recCommentsNorecords.visibility = View.VISIBLE

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

                recCommentsNorecords.visibility = View.VISIBLE

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

                recCommentsNorecords.visibility = View.VISIBLE

            })


        }

        lifecycleScope.launchWhenCreated {
            commentsAdapter?.commentEventChannelFromAdapter?.collect { event ->
                when (event) {
                    CommentsAdapter.CommentEvent.OpenActionForReplyingUser -> {
                        actionCommentRepliesFragment.show(
                            supportFragmentManager,
                            actionCommentRepliesFragment.tag
                        )
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
                                }, {
                                    Log.e(TAG, "onCreate: REPLY CLICKED")
                                    //TODO HAN
                                   // DLE REPLY LOGIC HERE
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
                                }), mainViewModel.isShowClicked,this@MainActivity, mainViewModel.clickedPosition )
                            }

                        binding.questionDetails = event.questionDetail
                        binding.recComments.adapter = commentsAdapter
                        binding.recComments.layoutManager = LinearLayoutManager(applicationContext)
                        binding.recComments.setHasFixedSize(true)
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
                        Log.e(TAG, "onCreate: ON UPLOAD CLICK")
                        checkPermissions()
//                        listOfAttachmentUrls =
//                            lawyerQuery.comment.comments.firstOrNull()?.commentAttachment!!
//                        arrayListOfAttachments = listOfAttachmentUrls as ArrayList
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
            ) == PackageManager.PERMISSION_DENIED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA),
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

        val choice = arrayOf("Take a Picture", "Image", "Video", "File")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an Attachment")
        builder.setItems(choice) { dialog, which ->
            // the user clicked on colors[which]
            if ("Take a Picture" == choice[which]) {
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
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, IMAGE_CODE)
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

        if (requestCode == CAMERA_CODE) {
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
        if (requestCode == IMAGE_CODE) {
            if (data?.extras?.get("data") != null) {
                val uri = data.extras?.get("data")
                val tempUri: Uri =
                    getImageUri(this, uri as Bitmap)

                val imagePath = getRealPathFromURI(tempUri)
                cmtAttachment.attachmentUrl = imagePath
                addCommentAttachment(cmtAttachment)
            }
        }

        if (requestCode == VIDEO_CODE) {
            Log.e(TAG, "onActivityResult: VIDEO " )
            if (data?.extras?.get("data") != null) {
                binding.attachOnReplying.visibility = View.VISIBLE
                val uri = data.data
                val path = uri?.path
                Log.e(TAG, "onActivityResult: VIDEO $path" )

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
            Log.e(TAG, "onActivityResult: FILE ")
            if (data?.extras?.get("data") != null) {
                binding.attachOnReplying.visibility = View.VISIBLE
                val uri = data.data
                val path = uri?.path
                Log.e(TAG, "onActivityResult: FILE $path")


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
        Log.e(TAG, "addCommentAttachment: ${listOfAttachmentUrls.lastIndex}")
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

            txtReplytoadmin.visibility = View.VISIBLE

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

        Log.e(TAG, "getRealPathFromURI: $path", )
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