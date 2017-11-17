package study.sayma.xlargelauncher.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_emergency_add.*
import kotlinx.android.synthetic.main.number_item.view.*
import study.sayma.xlargelauncher.R
import study.sayma.xlargelauncher.utils.P
import study.sayma.xlargelauncher.utils.U

/**
 * Created by Touhid's Guest on 11/11/2017.
 */
class AddEmgNumberActivity : AppCompatActivity() {

    private var numberAdapter = EmgNumberAdapter(this, ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_add)

        rvEmergencyNumbers.layoutManager = LinearLayoutManager(this)
        numberAdapter = EmgNumberAdapter(this, P.getEmergencyNumberList(this))
        rvEmergencyNumbers.adapter = numberAdapter

        btnAdd.setOnClickListener({ saveNumber() })
    }

    private fun saveNumber() {
        val num = etNewEmergencyNumber.text.toString()
        if (num.isNotEmpty() && num.length > 10 && U.isPhoneNumberValid(num)) {
            if (P.addEmergencyNumber(this, num)) {
                etNewEmergencyNumber.setText("")
                numberAdapter.add(num)
            } else
                etNewEmergencyNumber.error = "Number already exists"
        } else
            etNewEmergencyNumber.error = "Not a valid Phone Number"
    }

    class EmgNumberAdapter(private val context: Context, private val numberList: ArrayList<String>)
        : RecyclerView.Adapter<NumberViewHolder>() {

        override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
            val number = numberList[position]
            holder.itemView.tvNumberItem.text = number

            holder.itemView.setOnClickListener({ _ ->
                if (P.removeNumber(context, number)) {
                    toast("Removed number: " + number)
                    remove(number)
                }
            })
        }

        fun toast(s: String) {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show()
        }

        override fun getItemCount(): Int = numberList.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): NumberViewHolder {
            return NumberViewHolder(LayoutInflater.from(parent?.context).inflate(
                    R.layout.number_item, parent, false
            ))
        }

        fun add(num: String) {
            val size = numberList.size
            numberList.add(num)
            notifyItemInserted(size)
        }

        fun remove(num: String) {
            val sz = itemCount
            for (i in 0 until sz)
                if (numberList[i] == num) {
                    remove(i)
                    return
                }
        }

        fun remove(position: Int) {
            if (position > -1 && position < itemCount) {
                numberList.removeAt(position)
                notifyItemRemoved(position)
                return
            }
        }

    }

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun toast(s: String) = Toast.makeText(this, s, Toast.LENGTH_LONG).show()

}