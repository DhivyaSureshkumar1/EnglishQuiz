package com.education.englishquiz.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "QUESTIONS")
data class Questions(@PrimaryKey(autoGenerate = true)
                     val ques_no: Int,
                     val ques: String,
                     val opt1: String,
                     val opt2: String,
                     val opt3: String,
                     val opt4: String,
                     val correct_opt: Int,
                     val topic: String):Parcelable
