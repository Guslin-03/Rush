package com.example.rush.ui.profile.info

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.rush.R
import com.example.rush.databinding.InfoActivityBinding
import com.example.rush.databinding.ProfileActivityBinding
import com.example.rush.ui.profile.ProfileAdapter
import com.example.rush.ui.restaurant.RestaurantActivity
import com.example.rush.utils.MyApp
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.ByteArrayOutputStream

class InfoActivity : AppCompatActivity(){
    private lateinit var binding: InfoActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InfoActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        binding.profilePicture.setOnClickListener { pickPhoto() }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.orders -> {
                    //FUNCIONALIDAD ORDERS
                    true
                }

                R.id.start -> {
                    showStart()
                    true
                }

                R.id.profile -> {
                    showInfo()
                    true
                }

                else -> false // Manejo predeterminado para otros elementos
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == AppCompatActivity.RESULT_OK) {

            if (requestCode == 1) {
                if (data != null && data.extras != null) {
                    val bitmap = data.extras!!.get("data") as Bitmap
                    val uri = getImageUri(bitmap)
                    MyApp.userPreferences.removeProfilePictureUri()
                    MyApp.userPreferences.saveProfilePictureCamera(uri)
                    val roundedBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(resources, bitmap)
                    roundedBitmapDrawable.isCircular = true
                    binding.profilePicture.setImageDrawable(roundedBitmapDrawable)
                }
            } else if (requestCode == 2) {
                if (data != null && data.data != null) {
                    MyApp.userPreferences.removeProfilePictureUriCamera()
                    MyApp.userPreferences.saveProfilePicture(data.data!!)
                    Glide.with(this)
                        .load(data.data)
                        .apply(RequestOptions().transform(CircleCrop()))
                        .into(binding.profilePicture)
                }
            }


        }

    }
    private fun pickPhoto() {
        val options = arrayOf<CharSequence>(
            getString(R.string.take_picture),
            getString(R.string.get_picture),
            getString(R.string.cancel)
        )
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.choose)
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> takePhotoFromCamera()
                1 -> pickPhotoFromGallery()
                2 -> dialog.dismiss()
            }
        }
        builder.show()
    }

    private fun takePhotoFromCamera() {
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 1)
        } else {
            Toast.makeText(this, R.string.toast_no_camera, Toast.LENGTH_SHORT).show()
        }
    }

    private fun pickPhotoFromGallery() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {

                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    startActivityForResult(intent, 2)
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                }

            }).check()
    }
    private fun getImageUri(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            applicationContext.contentResolver,
            bitmap,
            "Title",
            null
        )
        return Uri.parse(path)
    }
    private fun setData(){
        val user = MyApp.userPreferences.getUser()
        if (user != null) {
            binding.name.setText(user.name)
            binding.surname.setText(user.surname)
            binding.phone.setText(user.phoneNumber.toString())
            binding.email.setText(user.email)
        }

    }
    private fun showStart(){
        val intent = Intent(this, RestaurantActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun showInfo(){
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
        finish()
    }
}