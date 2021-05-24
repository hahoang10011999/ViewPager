package com.example.viewpager.adapter

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager.ItemCalendar
import com.example.viewpager.R
import kotlinx.android.synthetic.main.calendar_cell.view.*

private const val THIS_MONTH = 0

class CalendarAdapter(var arrayList: ArrayList<ItemCalendar>, var context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var row_index: Int = -1
    var row_index_click: Int = -1
    var sharedPreferences = context.getSharedPreferences("SHARED_PERF", Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()
    var month: Int = 0
    var year: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == THIS_MONTH) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
            val viewHolder = MonthHolder(view)
            return viewHolder
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell_2, parent, false)
            val viewHolder = NextMonthHolder(view)
            return viewHolder
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return arrayList[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (arrayList[position].type == THIS_MONTH) {
            (holder as MonthHolder).bind(position)
        } else {
            (holder as NextMonthHolder).bind(position)
        }
    }

    inner class MonthHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellText = itemView.cellDayText
        fun bind(position: Int) {
            var item = arrayList[position]
            cellText.text = item.date
            cellText.setOnClickListener(object : DoubleClickListener() {
                override fun onDoubleClick() {
                    row_index = position
                    editor.putString("day", position.toString())
                    editor.putInt("month", month)
                    editor.putInt("year", year)
                    editor.putString("click", "double")
                    editor.commit()
                    notifyDataSetChanged()
                }

                override fun onSingleClick() {
                    row_index_click = position
                    editor.putString("day", position.toString())
                    editor.putInt("month", month)
                    editor.putInt("year", year)
                    editor.putString("click", "click")
                    editor.commit()
                    notifyDataSetChanged()
                }

            })
            if (row_index == position) {
                cellText.setBackgroundResource(R.drawable.background_doubleclick)
                row_index = -1
            } else {
                cellText.setBackgroundResource(R.drawable.background_item)
            }
            if (row_index_click == position) {
                cellText.setBackgroundResource(R.drawable.background)
                row_index_click = -1
            }
        }
    }

    fun setMonthYear(month: Int, year: Int) {
        this.month = month
        this.year = year
        notifyDataSetChanged()
    }

    fun dayChosed(click: Int, double: Int) {
        row_index = double
        row_index_click = click
        notifyDataSetChanged()
    }

    inner class NextMonthHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cellText = itemView.cellDayText
        fun bind(position: Int) {
            var item = arrayList[position]
            cellText.text = item.date
        }
    }


    abstract class DoubleClickListener : View.OnClickListener {
        private val delta: Long
        private var deltaClick: Long
        var han = Handler()
        override fun onClick(v: View) {
            han.removeCallbacksAndMessages(null)
            han.postDelayed({ onSingleClick() }, delta)
            if (SystemClock.elapsedRealtime() - deltaClick < delta) {
                han.removeCallbacksAndMessages(null)
                onDoubleClick()
            }
            deltaClick = SystemClock.elapsedRealtime()
        }

        abstract fun onDoubleClick()
        abstract fun onSingleClick()

        companion object {
            private const val DEFAULT_QUALIFICATION_SPAN: Long = 200
        }

        init {
            delta = DEFAULT_QUALIFICATION_SPAN
            deltaClick = 0
        }
    }
}