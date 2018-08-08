package nextline.com.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import kotlinx.android.synthetic.main.activity_main.*
import yogesh.firzen.filelister.FileListerDialog
import yogesh.firzen.filelister.OnFileSelectedListener
import java.io.File

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_library.setOnClickListener {
            val fileListerDialog = FileListerDialog.createFileListerDialog(this@MainActivity)

            fileListerDialog.setOnFileSelectedListener(object : OnFileSelectedListener {
                override fun onFileSelected(file: File?, path: String?) {
                    println("===============FILE " + file)
                    println("===============PATH " + path)

                }
            })

            fileListerDialog.setDefaultDir(Environment.getExternalStorageDirectory().path)

            fileListerDialog.setFileFilter(FileListerDialog.FILE_FILTER.ALL_FILES)

            fileListerDialog.show()

            // /Tarjeta de memoria/HTC_MakeMoreSpace/Gallery

        }

    }
}
