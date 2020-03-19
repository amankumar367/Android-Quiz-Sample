package com.aman.quizsample.ui.adapter

import com.aman.quizsample.room.entity.Quiz


interface OnClickListener {
    fun onItemClick(quiz: Quiz, position: Int, answerIndex: Int)
}