package com.example.legalqa

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adapters.*
import com.example.legalqa.databinding.ActivityMainBinding
import com.example.utils.exhaustive
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import com.google.gson.Gson

import com.google.gson.JsonElement

import com.google.gson.JsonParser
import kotlinx.coroutines.flow.collect
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var binding: ActivityMainBinding
    private val directionFragment = ActionCommentFragment()
    private val actionCommentRepliesFragment = ActionRepliesFragment()
    var commentsAdapter: CommentsAdapter? = null
    var commentUploadAttachmentAdapter: CommentUploadAttachmentAdapter? = null

    var commentsRepliesAdapter: CommentsRepliesAdapter? = null
    val PERMISSION_CODE = 2000
    val CAMERA_REQUEST = 0
    var listOfAttachmentUrls: List<CommentAttachment> = arrayListOf()
    var arrayListOfAttachments: ArrayList<CommentAttachment> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val json = loadJSONFromAsset()
        Log.e(TAG, "onCreate: $json")
        val parser = JsonParser()
        val mJson: JsonElement = parser.parse(json)
        val gson = Gson()
        //mainViewModel.mLawyerQueryList.value = gson.fromJson(mJson, LawyerQuery::class.java)
        val lawyerQuery = gson.fromJson(mJson, LawyerQuery::class.java)
        Log.e(TAG, "onCreate: LAWYER OBJECT:${mainViewModel.mLawyerQueryList.value}")


        val commentsReplies = lawyerQuery.comment.comments

        val commentReplies = commentsReplies.firstOrNull()?.commentReplies
        Log.e(TAG, "onCreate: commentsReplies $commentReplies")

        commentsAdapter = commentReplies?.let {
            CommentsAdapter(lawyerQuery.comment.comments,
                it,
                CommentClickListener {
                    directionFragment.show(supportFragmentManager, directionFragment.tag)
                })
        }

        val attachmentsAdapter =
            QuestionAttachmentAdapter(lawyerQuery.qDetails.questionAttachmentsList)

        Log.e(TAG, "onCreate: list of comments ${lawyerQuery.comment.comments}")
        binding.apply {
            questionDetails = lawyerQuery.qDetails
            recComments.apply {
                adapter = commentsAdapter
                layoutManager = LinearLayoutManager(applicationContext)
                setHasFixedSize(true)
            }
            recviewImages.apply {
                adapter = attachmentsAdapter
                layoutManager =
                    LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            //FOR COMMENT UPLOAD ADAPTER
            imgbtnUpload.setOnClickListener(View.OnClickListener {
                checkPermissions()
                listOfAttachmentUrls =
                    lawyerQuery.comment.comments.firstOrNull()?.commentAttachment!!
                arrayListOfAttachments = listOfAttachmentUrls as ArrayList
            })

            edtReplying.setOnClickListener(View.OnClickListener {
                txtReplytoadmin.visibility = View.VISIBLE
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
            commentsAdapter?.employeesEvent?.collect { event ->
                when (event) {
                    CommentsAdapter.CommentEvent.OpenActionForReplyingUser -> {
                        actionCommentRepliesFragment.show(
                            supportFragmentManager,
                            actionCommentRepliesFragment.tag
                        )
                    }
                }.exhaustive
            }
        }
    }

    private fun loadJSONFromAsset(): String? {
        val charset: Charset = Charsets.UTF_8
        var json: String? = null
        json = try {
            val `is`: InputStream = assets.open("lawyer_qn.json")
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    //IMAGE UPLOAD METHODS
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
        val myAlertDialog: AlertDialog.Builder = AlertDialog.Builder(
            this
        )
        myAlertDialog.setTitle("Upload Pictures Option")
        myAlertDialog.setMessage("How do you want to upload your attachment?")
        myAlertDialog.setPositiveButton("Gallery",
            DialogInterface.OnClickListener { _, _ ->
                openGallery()
            })
        myAlertDialog.setNegativeButton("Camera",
            DialogInterface.OnClickListener { _, _ ->
                openCamera()
            })
        myAlertDialog.show()
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 1)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, 2)
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
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val cmtAttachment = CommentAttachment()

        if (requestCode == 1) {
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

        if (requestCode == 2) {
            if (data?.extras?.get("data") != null) {
                val uri = data.extras?.get("data")
                val tempUri: Uri =
                    getImageUri(this, uri as Bitmap)

                val imagePath = getRealPathFromURI(tempUri)
                cmtAttachment.attachmentUrl = imagePath
                addCommentAttachment(cmtAttachment)
            }
        }
    }

    private fun addCommentAttachment(commentAttachment: CommentAttachment) {
        arrayListOfAttachments.add(commentAttachment)
        listOfAttachmentUrls = arrayListOfAttachments
        Log.e(TAG, "addCommentAttachment: ${listOfAttachmentUrls.lastIndex}")
        addAttachmentListToRecView()
    }

    private fun addAttachmentListToRecView() {
        commentUploadAttachmentAdapter = CommentUploadAttachmentAdapter(listOfAttachmentUrls)

        binding.apply {
            attachOnReplying.apply {
                Log.e(TAG, "addCommentAttachment_ADDING: $listOfAttachmentUrls")
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