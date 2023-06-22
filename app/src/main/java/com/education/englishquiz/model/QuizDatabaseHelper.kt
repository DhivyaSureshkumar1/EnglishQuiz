package com.education.englishquiz.model

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class QuizDatabaseHelper(context: Context) : SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "ques.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_QUESTIONS = "Questions"
        const val COLUMN_QUESTION_ID = "QuesNo"
        const val COLUMN_QUESTION_TEXT = "Question"
        const val COLUMN_OPTION1 = "Option1"
        const val COLUMN_OPTION2 = "Option2"
        const val COLUMN_OPTION3 = "Option3"
        const val COLUMN_OPTION4 = "Option4"
        const val COLUMN_CORRECT_OPTION = "CorrectOpt"
        const val COLUMN_TOPIC = "Topic"
        const val COLUMN_GRADE = "Grade"
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUESTIONS")
        onCreate(db)
    }

    // Method to fetch all questions from the database
    @SuppressLint("Range")
    fun getAllQuestions(grade: Int?): List<Questions> {
        val questionList = mutableListOf<Questions>()
        val selectQuery = "SELECT * FROM $TABLE_QUESTIONS WHERE $COLUMN_GRADE =$grade"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val questionId = cursor.getInt(cursor.getColumnIndex(COLUMN_QUESTION_ID))
                val questionText = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION_TEXT))
                val optionA = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1))
                val optionB = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2))
                val optionC = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3))
                val optionD = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION4))
                val correctOpt = cursor.getInt(cursor.getColumnIndex(COLUMN_CORRECT_OPTION))
                val topic = cursor.getString(cursor.getColumnIndex(COLUMN_TOPIC))

                val question = Questions(questionId, questionText, optionA, optionB, optionC, optionD, correctOpt, topic)
                questionList.add(question)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return questionList
    }
}
