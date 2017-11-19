package study.sayma.xlauncher.medalert

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.med_alert_item.view.*
import study.sayma.xlauncher.medalert.db.MedAlertTable

/**
 * Created by Touhid on 11/20/2017.
 *
 */
class MedAlertAdapter(private val ctx: Context, private var itemList: ArrayList<MedAlert>,
                      private val db: MedAlertTable = MedAlertTable(ctx))
    : RecyclerView.Adapter<MedAlertViewHolder>() {

    override fun getItemCount(): Int = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MedAlertViewHolder =
            MedAlertViewHolder(
                    LayoutInflater.from(parent?.context).inflate(R.layout.med_alert_item, parent, false)
            )

    override fun onBindViewHolder(h: MedAlertViewHolder, position: Int) {
        val m = itemList[position]

        h.tvTitle.text = m.medicineName
        h.tvTime.text = String.format("%d : %d", m.hour, m.minute)
        h.switch.isChecked = m.on
        h.switch.setOnCheckedChangeListener { _, isChecked ->
            m.on = isChecked
            if (db.update(m) > -1) {
                // TODO Reset & unset (possible in this case?) alarm
                toast(String.format("Medicine Alert made %s", if (m.on) "on" else "off"))
            }
        }
        h.ivDelete.setOnClickListener({ _ ->
            db.delete(m)
            itemList.removeAt(h.adapterPosition)
            notifyItemRemoved(h.adapterPosition)
        })

        val sz = if (h.tvDays.size < m.dayOn.size) h.tvDays.size else m.dayOn.size
        for (i in 0 until sz)
            h.tvDays[i].visibility = if (m.dayOn[i]) View.VISIBLE else View.GONE
    }

    private fun toast(msg: String) = Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()

    fun addUniquely(newList: ArrayList<MedAlert>) {
        for (i in 0 until newList.size) {
            val nm = newList[i]
            val exists = (0 until itemCount)
                    .map { itemList[it] }
                    .any { nm == it }
            if (!exists) {
                itemList.add(nm)
                notifyItemInserted(itemList.size - 1)
            }
        }
    }

}

class MedAlertViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.tvMedicineName!!
    val tvTime = itemView.tvTimeMedAlert!!
    val switch = itemView.switchMedAlertItem!!
    val ivDelete = itemView.ivDeleteMedAlert!!
    val tvDays = arrayOf(itemView.tvSaturday!!, itemView.tvSunday!!, itemView.tvMonday!!,
            itemView.tvTuesday!!, itemView.tvWednesday!!, itemView.tvThursday!!, itemView.tvFriday!!)
}