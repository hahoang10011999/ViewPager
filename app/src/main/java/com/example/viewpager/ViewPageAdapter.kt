package com.example.viewpager

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.viewpager.adapter.CalendarAdapter
import kotlinx.android.synthetic.main.item_page.view.*
import java.time.LocalDate
import androidx.viewpager2.widget.ViewPager2
import java.time.YearMonth

class ViewPageAdapter(
    var context: Context,
    var array: ArrayList<CalendarAdapter>,
    var arrayLocalDate: ArrayList<LocalDate>,
    var startOfWeek: Int,
    var arratText: ArrayList<String>,
    var viewPager: ViewPager2
) :RecyclerView.Adapter<ViewPageAdapter.ViewPagerHolder>(){
    class ViewPagerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerView = itemView.calendarRecyclerView
        val monthYearTV = itemView.monthYearTV
        val tvDay = itemView.tvDay
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_page, parent, false)
        val viewHolder = ViewPagerHolder(view)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return array.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        var layoutManager = GridLayoutManager(context, 7)
        holder.recyclerView.layoutManager = layoutManager
        holder.recyclerView.adapter = array[position]
        var divider = DividerItemDecoration(context, layoutManager.orientation)
        holder.recyclerView.addItemDecoration(divider)
        var divider1 = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
        holder.recyclerView.addItemDecoration(divider1)
        var d: String = ""
        for (i in 0 until 7) {
            d += arratText[i] + "      "
        }
        holder.tvDay.text = d

        holder.monthYearTV.text =
            arrayLocalDate[position].month.toString() + " " + arrayLocalDate[position].year
        if (position == array.size - 1) {
            viewPager.post(runnable)
        }
        if (viewPager.getChildAt(0).isFocused) {
            viewPager.post(run)
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView(day: LocalDate, start: Int): ArrayList<ItemCalendar> {
        var arrayList: ArrayList<ItemCalendar> = arrayListOf()
        var arrayListPrevious: ArrayList<ItemCalendar> = arrayListOf()
        var arrayListNext: ArrayList<ItemCalendar> = arrayListOf()


        val yearMonth = YearMonth.from(day)

        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = day.withDayOfMonth(1)

        var dayOfWeek = firstOfMonth.dayOfWeek.value + start
        if (dayOfWeek >= 7) {
            dayOfWeek = dayOfWeek - 7
        }
        var previous = previousMonth(day)
        var next: Int = 1
        for (i in 1..42) {
            if (i <= dayOfWeek) {
                arrayListPrevious.add(ItemCalendar(1, previous.toString()))
                previous--
            } else if (i > daysInMonth + dayOfWeek) {
                arrayListNext.add(ItemCalendar(1, next.toString()))
                next++
            } else {
                arrayList.add(ItemCalendar(0, (i - dayOfWeek).toString()))
            }
        }
        for (i in 0 until arrayListPrevious.size) {
            arrayList.add(0, arrayListPrevious[i])
        }
        for (i in 0 until arrayListNext.size) {
            arrayList.add(arrayList.size, arrayListNext[i])
        }
        return arrayList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun previousMonth(date: LocalDate): Int {
        var d = date.minusMonths(1)
        val yearMonth = YearMonth.from(d)
        val daysInMonth = yearMonth.lengthOfMonth()
        return daysInMonth
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var runnable = Runnable {
        var calendar = setMonthView(arrayLocalDate[array.size - 1].plusMonths(1), startOfWeek)
        var calendarAdapter = CalendarAdapter(calendar, context)
        array.add(calendarAdapter)
        arrayLocalDate.add(arrayLocalDate[array.size - 2].plusMonths(1))
        notifyItemInserted(array.size - 1)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    var run = Runnable {
        var calendar = setMonthView(arrayLocalDate[0].minusMonths(1), startOfWeek)
        var calendarAdapter = CalendarAdapter(calendar, context)
        array.add(0, calendarAdapter)
        arrayLocalDate.add(0, arrayLocalDate[0].minusMonths(1))
        notifyItemInserted(0)
    }
}