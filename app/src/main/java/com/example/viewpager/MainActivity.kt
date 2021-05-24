package com.example.viewpager

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager.adapter.CalendarAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.YearMonth

class MainActivity : AppCompatActivity() {

    var startOfWeek: Int = 0
    var size = 2000
    lateinit var selectDate: LocalDate
    lateinit var viewPageAdapter: ViewPageAdapter
    var listFragment = MutableList<Fragment>(size) { index -> Fragment() }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectDate = LocalDate.now()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        
        listFragment.set(size / 2 - 1, CalendarFragment(selectDate.minusMonths(1), startOfWeek))
        listFragment.set(size / 2, CalendarFragment(selectDate, startOfWeek))
        listFragment.set(size / 2 + 1, CalendarFragment(selectDate.plusMonths(1), startOfWeek))

        viewPageAdapter = ViewPageAdapter(listFragment, supportFragmentManager, lifecycle)
        view_pager2.adapter = viewPageAdapter
        view_pager2.setCurrentItem(size / 2, false)

        view_pager2.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            var countLeft = 2
            var countRight = 2

            var jump = size / 2
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position < jump) {
                    viewPageAdapter.reloadFragment(jump)
                    jump = position
                    viewPageAdapter.setLeft(
                        CalendarFragment(
                            selectDate.minusMonths(countLeft.toLong()), startOfWeek
                        ), size / 2 - countLeft
                    )
                    countLeft++
                }

                if (position > jump) {
                    viewPageAdapter.reloadFragment(jump)
                    jump = position
                    viewPageAdapter.setRight(
                        CalendarFragment(
                            selectDate.plusMonths(countRight.toLong()), startOfWeek
                        ), size / 2 + countRight
                    )
                    countRight++
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_day, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mon -> {
                startOfWeek = 6
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.tue -> {
                startOfWeek = 5
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.wed -> {
                startOfWeek = 4
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.thu -> {
                startOfWeek = 3
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.fri -> {
                startOfWeek = 2
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.sat -> {
                startOfWeek = 1
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
            R.id.sun -> {
                startOfWeek = 0
                var listFragment1 = MutableList<Fragment>(size) { index -> Fragment() }
                listFragment1.set(
                    size / 2 - 1,
                    CalendarFragment(selectDate.minusMonths(1), startOfWeek)
                )
                listFragment1.set(size / 2, CalendarFragment(selectDate, startOfWeek))
                listFragment1.set(
                    size / 2 + 1,
                    CalendarFragment(selectDate.plusMonths(1), startOfWeek)
                )

                viewPageAdapter = ViewPageAdapter(listFragment1, supportFragmentManager, lifecycle)
                view_pager2.adapter = viewPageAdapter
                view_pager2.setCurrentItem(size / 2, false)
                viewPageAdapter.notifyDataSetChanged()
                changeDayStart(startOfWeek)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun changeDayStart(start: Int) {
        when (start) {
            0 -> {
                tvSun.text = "Sun"
                tvMon.text = "Mon"
                tvTue.text = "Tue"
                tvWed.text = "Wed"
                tvThu.text = "Thu"
                tvFri.text = "Fri"
                tvSat.text = "Sat"
            }
            1 -> {
                tvSun.text = "Sat"
                tvMon.text = "Sun"
                tvTue.text = "Mon"
                tvWed.text = "Tue"
                tvThu.text = "Wed"
                tvFri.text = "Thu"
                tvSat.text = "Fri"
            }
            2 -> {
                tvSun.text = "Fri"
                tvMon.text = "Sat"
                tvTue.text = "Sun"
                tvWed.text = "Mon"
                tvThu.text = "Tue"
                tvFri.text = "Wed"
                tvSat.text = "Thu"
            }
            3 -> {
                tvSun.text = "Thu"
                tvMon.text = "Fri"
                tvTue.text = "Sat"
                tvWed.text = "Sun"
                tvThu.text = "Mon"
                tvFri.text = "Tue"
                tvSat.text = "Wed"
            }
            4 -> {
                tvSun.text = "Wed"
                tvMon.text = "Thu"
                tvTue.text = "Fri"
                tvWed.text = "Sat"
                tvThu.text = "Sun"
                tvFri.text = "Mon"
                tvSat.text = "Tue"
            }
            5 -> {
                tvSun.text = "Tue"
                tvMon.text = "Wed"
                tvTue.text = "Thu"
                tvWed.text = "Fri"
                tvThu.text = "Sat"
                tvFri.text = "Sun"
                tvSat.text = "Mon"
            }
            6 -> {
                tvSun.text = "Mon"
                tvMon.text = "Tue"
                tvTue.text = "Wed"
                tvWed.text = "Thu"
                tvThu.text = "Fri"
                tvFri.text = "Sat"
                tvSat.text = "Sun"
            }
        }
    }
}