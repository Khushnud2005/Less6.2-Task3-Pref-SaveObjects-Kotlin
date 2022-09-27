package uz.exemple.less62_task3_pref_saveobjects_kotlin

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import uz.exemple.less62_task3_pref_saveobjects_kotlin.manager.PrefsManager
import uz.exemple.less62_task3_pref_saveobjects_kotlin.model.MemberModel

class MainActivity : AppCompatActivity() {

    lateinit var member:MemberModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    fun initViews() {
        val et_memberName = findViewById<EditText>(R.id.et_userName)
        val et_memberAge = findViewById<EditText>(R.id.et_userAge)
        val et_memberEmail = findViewById<EditText>(R.id.et_userEmail)
        val b_save = findViewById<Button>(R.id.b_save)
        val b_load = findViewById<Button>(R.id.b_load)
        val tv_resObj = findViewById<TextView>(R.id.tv_resObj)
        val tv_res = findViewById<TextView>(R.id.tv_res)
        val context: Context = this


            b_save.setOnClickListener {
                if (!et_memberAge.text.isEmpty()){
                    val name = et_memberName.text.toString().trim { it <= ' ' }
                    val age = et_memberAge.text.toString().trim { it <= ' ' }.toInt()
                    val email = et_memberEmail.text.toString().trim { it <= ' ' }
                    member = MemberModel(name, age, email)
                    val gson = Gson()
                    val json: String = gson.toJson(member)
                    PrefsManager.getInstance(context)!!.saveData("member", json)
                    et_memberName.setText("")
                    et_memberAge.setText("")
                    et_memberEmail.setText("")
                }
            }


        b_load.setOnClickListener { v ->
            val res: String = PrefsManager.getInstance(v.context)!!.loadData("member")!!
            tv_resObj.text = res
            tv_resObj.visibility = View.VISIBLE
            val gson = Gson()
            val obj: MemberModel = gson.fromJson(res, MemberModel::class.java)
            tv_res.text = "Name - ${obj.fullName},\nEmail ${obj.email},\nAge - ${obj.age}"
            tv_res.visibility = View.VISIBLE
        }
    }
}