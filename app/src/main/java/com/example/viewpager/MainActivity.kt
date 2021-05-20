package com.example.viewpager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
import com.example.viewpager.adapter.CalendarAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : AppCompatActivity() {
    lateinit var calendarAdapter: CalendarAdapter
    lateinit var selectedDate: LocalDate
    lateinit var arrayLocaDate: ArrayList<LocalDate>
    lateinit var viewPage1Adapter:ViewPageAdapter
    lateinit var arrayText:ArrayList<String>
    var startOfWeek: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrayLocaDate = arrayListOf()
        selectedDate = LocalDate.now()

        arrayText = arrayListOf()
        arrayText.add("SUN")
        arrayText.add("MON")
        arrayText.add("TUE")
        arrayText.add("WED")
        arrayText.add("THU")
        arrayText.add("FRI")
        arrayText.add("SAT")

        var a = setMonthView(selectedDate,0)
        var b = setMonthView(selectedDate.minusMonths(1),0)
        var c = setMonthView(selectedDate.plusMonths(1),0)
        calendarAdapter = CalendarAdapter(a,this)
        var calendarAdapter1 = CalendarAdapter(b,this)
        var calendarAdapter2 = CalendarAdapter(c,this)
        var array: ArrayList<CalendarAdapter> = arrayListOf()
        array.add(calendarAdapter1)
        array.add(calendarAdapter)
        array.add(calendarAdapter2)

        arrayLocaDate.add(selectedDate.minusMonths(1))
        arrayLocaDate.add(selectedDate)
        arrayLocaDate.add(selectedDate.plusMonths(1))

        viewPage1Adapter = ViewPageAdapter(this,array,arrayLocaDate,startOfWeek,arrayText,view_pager2)
        view_pager2.adapter = viewPage1Adapter
        view_pager2.setCurrentItem(1,true)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView(day: LocalDate,start: Int): ArrayList<ItemCalendar> {
        var arrayList: ArrayList<ItemCalendar> = arrayListOf()
        var arrayListPrevious: ArrayList<ItemCalendar> = arrayListOf()
        var arrayListNext: ArrayList<ItemCalendar> = arrayListOf()


        val yearMonth = YearMonth.from(day)

        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = day.withDayOfMonth(1)

        var dayOfWeek = firstOfMonth.dayOfWeek.value+start
        if (dayOfWeek >7){
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_day,menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mon ->{
                startOfWeek = 6
                changeDayStart("MON",startOfWeek)
            }
            R.id.tue ->{
                startOfWeek = 5
                changeDayStart("TUE",startOfWeek)
            }
            R.id.wed ->{
                startOfWeek = 4
                changeDayStart("WED",startOfWeek)
            }
            R.id.thu ->{
                startOfWeek = 3
                changeDayStart("THU",startOfWeek)
            }
            R.id.fri ->{
                startOfWeek = 2
                changeDayStart("FRI",startOfWeek)
            }
            R.id.sat ->{
                startOfWeek = 1
                changeDayStart("SAT",startOfWeek)
            }
            R.id.sun ->{
                startOfWeek = 0
                changeDayStart("SUN",startOfWeek)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun changeDayStart(day:String, start: Int){
        var newArrayText: ArrayList<String> = arrayListOf()
        for(i in 0 until 7){
            if (arrayText[i].equals(day)){
                for (j in i..6){
                    newArrayText.add(arrayText[j])
                }
                for (k in i..6){
                    arrayText.removeAt(i)
                }
                arrayText.addAll(0,newArrayText)
            }
        }

        var a = setMonthView(selectedDate,start)
        var b = setMonthView(selectedDate.minusMonths(1),start)
        var c = setMonthView(selectedDate.plusMonths(1),start)
        var calendarAdapter = CalendarAdapter(a,this)
        var calendarAdapter1 = CalendarAdapter(b,this)
        var calendarAdapter2 = CalendarAdapter(c,this)
        var array: ArrayList<CalendarAdapter> = arrayListOf()
        array.add(calendarAdapter1)
        array.add(calendarAdapter)
        array.add(calendarAdapter2)
        var arrayDate: ArrayList<LocalDate> = arrayListOf()
        arrayDate.add(selectedDate.minusMonths(1))
        arrayDate.add(selectedDate)
        arrayDate.add(selectedDate.plusMonths(1))
        var viewPage1Adapter = ViewPageAdapter(this,array,arrayDate,start,arrayText,view_pager2)
        view_pager2.adapter = viewPage1Adapter
        view_pager2.setCurrentItem(1,true)
    }
}