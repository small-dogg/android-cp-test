package com.smalldogg.study.cpapp2

import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smalldogg.study.cpapp2.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var b : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        b = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.s.setOnClickListener {
            //Content Provider의 이름을 가지고 있는 uri 객체를 생성한다.
            val uri = Uri.parse("content://com.smalldogg.dbprovider")

            // 1 : 접속할 프로바이더 uri
            // 2 : 가져올 컬럼 목록 배열, null이면 모두 가져옴
            // 3 : 조건절
            // 4 : 조건절 ?에 바인딩될 값
            // 5 : 정렬 기준
            val c1 = contentResolver.query(uri, null,null,null,null)

            while (c1?.moveToNext()!!) {
                // 가져올 컬럼의 인덱스 번호를 추출한다.
                val idx1 = c1.getColumnIndex("idx")
                val idx2 = c1.getColumnIndex("textData")
                val idx3 = c1.getColumnIndex("intData")
                val idx4 = c1.getColumnIndex("floatData")
                val idx5 = c1.getColumnIndex("dateData")

                //데이터를 추출한다.
                val idx = c1?.getInt(idx1)
                val textData = c1?.getString(idx2)
                val intData = c1?.getInt(idx3)
                val floatData = c1?.getFloat(idx4)
                val dateData = c1?.getString(idx5)
                b.textView.append("idx : $idx\n")
                b.textView.append("textData : $textData\n")
                b.textView.append("intData : $intData\n")
                b.textView.append("floatData : $floatData\n")
                b.textView.append("dateData : $dateData\n")
            }
        }

        b.i.setOnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val now = sdf.format(Date())

            val cv1 = ContentValues()
            cv1.put("textData", "문자열1")
            cv1.put("intData", 11)
            cv1.put("floatData", 11.11)
            cv1.put("dateData", now)

            val cv2 = ContentValues()
            cv2.put("textData", "문자열2")
            cv2.put("intData", 22)
            cv2.put("floatData", 22.22)
            cv2.put("dateData", now)

            val uri = Uri.parse("content://com.smalldogg.dbprovider")
            contentResolver.insert(uri, cv1)
            contentResolver.insert(uri, cv2)

            b.textView.text = "저장 완료"
        }

        b.u.setOnClickListener {
            val cv = ContentValues()

            cv.put("textData", "문자열100")
            val where = "idx = ?"
            val args = arrayOf("1")

            val uri = Uri.parse("content://com.smalldogg.dbprovider")
            contentResolver.update(uri, cv, where, args)

            b.textView.text = "수정 완료"

        }

        b.d.setOnClickListener {
            val where = "idx = ?"
            val args = arrayOf("1")

            val uri = Uri.parse("content://com.smalldogg.dbprovider")
            contentResolver.delete(uri, where, args)
            b.textView.text = "삭제 완료"
        }
    }
}