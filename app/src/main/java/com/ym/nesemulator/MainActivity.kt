package com.ym.nesemulator

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ym.library.sdk.EmulatorManager
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private val btnStart: Button by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        findViewById(R.id.btnStart)
    }

    private val btnLoad: Button by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        findViewById(R.id.btnLoad)
    }

    private val listView: ListView by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        findViewById(R.id.list)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoad.setOnClickListener {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                ), 1000
            )
            copyAssetsToInternalStorage(this.applicationContext)
            loadGameList()
        }

        btnStart.setOnClickListener {
            reqPermission()
        }
    }

    private fun loadGameList() {
        var path = this.applicationContext.filesDir.absolutePath
        Toast.makeText(this, "dir000: $path", Toast.LENGTH_SHORT).show()

        val dir = File(path);
        if(!dir.isDirectory()) return;
        val fileList = mutableListOf<String>()
        val files = dir.listFiles(){ file -> file.extension.equals("nes", ignoreCase = true) }

        for (file in files){
            fileList.add(file.name)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileList)
        listView.adapter = adapter

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE

        // 设置点击事件监听器
        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            // 当项被点击时，会调用这个方法
            val itemClicked = parent.getItemAtPosition(position) as String
            Toast.makeText(this, "Clicked on $itemClicked", Toast.LENGTH_SHORT).show()

            // 在这里添加你的点击事件处理逻辑
        }
    }
    /**
     * 请求权限
     */
    private fun reqPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startGame()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1000
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != 1000)
            return
        if (grantResults.isEmpty() || grantResults.size < 3) {
            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
        } else {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //startGame()
                //loadGameList()

            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * 启动游戏
     */
    private fun startGame() {
        //Toast.makeText(this, "iiiiiiiiiiiiii:", Toast.LENGTH_SHORT).show()

        var i = listView.checkedItemPosition
        val checkedItem = listView.getItemAtPosition(i)
        val path = this.applicationContext.filesDir.absolutePath+"/"+checkedItem.toString()
        //Toast.makeText(this, "path:"+path, Toast.LENGTH_SHORT).show()


        val romFile = File(path)
        if (!romFile.exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
            return
        }
        // 默认游戏界面
//        EmulatorManager.getInstance().startGame(this, romFile)
        //自定义游戏界面
        EmulatorManager.getInstance().startGame(this, MyEmulatorActivity::class.java, romFile)
        finish()
    }
}

fun copyAssetsToInternalStorage(context: Context) {
    val assetManager = context.assets
    val files = assetManager.list("") // 获取assets目录下的所有文件和文件夹

    if (files != null) {
        for (filename in files) {
            if(!filename.endsWith("nes")) continue
            //Toast.makeText(context, ""+filename.toString(), Toast.LENGTH_SHORT).show()
            val aa :java.io.InputStream = assetManager.open(filename.toString())
            // 构建目标文件路径
            val destPath = "${context.filesDir.absolutePath}/$filename"

            // 检查目标路径是否为目录，如果是目录则创建目录
            val destFile = File(destPath)
            if (!destFile.exists() && filename.endsWith("/")) {
                destFile.mkdirs()
                continue
            }

            // 创建目标文件的输出流
            val os = FileOutputStream(destFile)

            var length: Int=0
            val buffer = ByteArray(1024)

            while ({
                    length = aa.read(buffer)
                    -1 != length
                }()) {
                os.write(buffer, 0, length)
            }
            System.out.println(destPath);

            // 关闭流
            os.close()
            aa.close()
        }
    }
}