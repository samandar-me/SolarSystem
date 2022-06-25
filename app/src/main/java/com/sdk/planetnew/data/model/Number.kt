package com.sdk.planetnew.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.planetnew.data.util.Constants

@Entity(tableName = Constants.NUMBER)
data class Number(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val num: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Number) return false

        return (other.num == this.num)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + num
        return result
    }


}
