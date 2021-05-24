package com.example.viewpager

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.viewpager.adapter.CalendarAdapter
import kotlinx.android.synthetic.main.item_page.*
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment(
    var date: LocalDate,
    var dayStart: Int
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.item_page, container, false)
        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        var sharedPreferences = context?.getSharedPreferences("SHARED_PERF", Context.MODE_PRIVATE)
        var array = setMonthView(date, dayStart)
        var adapter = CalendarAdapter(array, requireContext())
        adapter.notifyDataSetChanged()
        adapter.setMonthYear(date.monthValue, date.year)
        var month = sharedPreferences?.getInt("month", 0)
        var year = sharedPreferences?.getInt("year", 0)
        var check = sharedPreferences?.getString("click", "")
        var day = sharedPreferences?.getString("day", "")?.toInt()
        if (month == date.monthValue && year == date.year) {
            if (check.equals("click")!!) {
                adapter.dayChosed(day!!, -1)
            }
            if (check.equals("double")) {
                adapter.dayChosed(-1, day!!)
            }
        }

        var layout = GridLayoutManager(context, 7, GridLayoutManager.VERTICAL, false)
        calendarRecyclerView.layoutManager = layout
        calendarRecyclerView.adapter = adapter
        var yearMonth = activity?.findViewById<TextView>(R.id.tvMonth)
        if (yearMonth != null) {
            yearMonth.text = date.month.toString() + " " + date.year
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
        if (dayOfWeek > 7) {
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
}