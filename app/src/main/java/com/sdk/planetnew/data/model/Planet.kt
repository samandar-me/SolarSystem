package com.sdk.planetnew.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sdk.planetnew.data.util.Constants.TABLE_NAME
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class Planet(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val num: Int = 0,
    val title: String = "",
    val desc: String = "",
    val image: String = "",
    val backImage: String = ""
) : Parcelable