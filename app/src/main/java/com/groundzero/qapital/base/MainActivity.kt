package com.groundzero.qapital.base

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.groundzero.qapital.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    MainActivityCallback {

    private val titleSubject = BehaviorSubject.create<String>()
    private val progressBarSubject = BehaviorSubject.create<Boolean>()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setNavigationItemSelectedListener(this)
        menu_button.setOnClickListener{ main_drawer.openDrawer(GravityCompat.START) }

        disposables.add(
            titleSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { toolbarTitle -> toolbar_title.text = toolbarTitle }
        )

        disposables.add(
            progressBarSubject.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isVisible -> progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE }
        )
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onBackPressed() {
        if (main_drawer.isDrawerOpen(GravityCompat.START)) {
            main_drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        main_drawer.closeDrawers()
        if (menuItem.itemId == R.id.somethingOne) {
            showToast(resources.getString(R.string.something_one_clicked))
        } else if (menuItem.itemId == R.id.somethingTwo) {
            showToast(resources.getString(R.string.something_two_clicked))
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun changeToolbarTitle(title: String) {
        titleSubject.onNext(title)
    }

    override fun changeProgressBarVisibility(isVisible: Boolean) {
        progressBarSubject.onNext(isVisible)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}