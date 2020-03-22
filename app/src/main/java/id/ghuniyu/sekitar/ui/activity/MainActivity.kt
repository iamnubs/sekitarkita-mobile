package id.ghuniyu.sekitar.ui.activity

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.orhanobut.hawk.Hawk
import id.ghuniyu.sekitar.R
import id.ghuniyu.sekitar.service.ScanService
import id.ghuniyu.sekitar.utils.Constant
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startService
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    private var btAdapter: BluetoothAdapter? = null

    override fun getLayout() = R.layout.activity_main

    companion object {
        private const val EXTRA_ADDRESS = "Device_Address"
        private const val TAG = "MainActivityTag"
        private const val REQUEST_COARSE = 1
        private const val REQUEST_BLUETOOTH = 2
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_COARSE -> {
                if (resultCode != Activity.RESULT_OK) {
                    forcePermission()
                }
            }
        }

        setStatus()
    }

    private fun setStatus() {
        // TODO : Call After Report
        if (Hawk.contains(Constant.STORAGE_STATUS)) {
            when (Hawk.get<String>(Constant.STORAGE_STATUS)) {
                "healthy" -> {
                    status.text = getString(R.string.status, "Sehat")
                }

                "pdp" -> {
                    status.text = getString(R.string.status, "Pasien Dalam Pengawasan")
                }

                "odp" -> {
                    status.text = getString(R.string.status, "Orang Dalam Pengawasan")
                }
            }
        } else {
            //TODO : fetch Status Here
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startActivity<ReportActivity>()
            finish()
        } else {
            btAdapter = BluetoothAdapter.getDefaultAdapter()
            if (btAdapter == null) {
                toast(getString(R.string.bluetooth_unavailable))
                finish()
                return
            }
            forcePermission()
            bluetoothOn()
        }
    }

    private fun bluetoothOn() {
        if (btAdapter!!.isEnabled) {
            toast(getString(R.string.bluetooth_active))
            startService<ScanService>()
        } else {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_BLUETOOTH)
        }
    }

    private fun forcePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_COARSE
            )
        }
    }

    fun share(view: View) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_content))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share))
        startActivity(shareIntent)
    }
}
