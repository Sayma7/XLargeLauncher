package study.sayma.xlauncher.medalert

import java.util.*

/**
 * Created by Touhid on 11/20/2017.
 *
 */
data class MedAlert(var medicineName: String,
                    var hour: Int, var minute: Int, var second: Int,
                    var on: Boolean, var dayOn: BooleanArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MedAlert

        if (medicineName != other.medicineName) return false
        if (hour != other.hour) return false
        if (minute != other.minute) return false
        if (second != other.second) return false
        if (on != other.on) return false

        val sz = dayOn.size
        if (sz != other.dayOn.size) return false

        return (0 until sz)
                .none { dayOn[it] != other.dayOn[it] }
    }

    override fun hashCode(): Int {
        var result = medicineName.hashCode()
        result = 31 * result + hour
        result = 31 * result + minute
        result = 31 * result + second
        result = 31 * result + Arrays.hashCode(dayOn)
        return result
    }
}