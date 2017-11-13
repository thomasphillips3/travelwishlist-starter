package com.raywenderlich.android.travelwishlist

import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit private var menu: Menu
    lateinit private var staggeredLayoutManager: StaggeredGridLayoutManager
    lateinit private var adapter: TravelListAdapter
    private var isListView: Boolean = false

    private val onItemClickListener = object :
            TravelListAdapter.OnItemClickListener {
        override fun onItemClick(view: View, position: Int) {
            val intent = DetailActivity.newIntent(this@MainActivity, position)
            val placeImage = view.findViewById<ImageView>(R.id.placeImage)
            val placeNameHolder = view.findViewById<LinearLayout>(R.id.placeNameHolder)
            val imagePair = Pair.create(placeImage as View, "tImage")
            val holderPair = Pair.create(placeNameHolder as View, "tNameHolder")
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, imagePair, holderPair)
            ActivityCompat.startActivity(this@MainActivity, intent, options.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isListView = true
        staggeredLayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        list.layoutManager = staggeredLayoutManager
        adapter = TravelListAdapter(this)
        list.adapter = adapter
        adapter.setOnItemClickListener(onItemClickListener)
    }

    private fun setUpActionBar() {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_toggle) {
            toggle()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun toggle() {
        if (isListView) {
            showGridView()
        } else {
            showListView()
        }
    }

    private fun showListView() {
        staggeredLayoutManager.spanCount = 1
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_grid)
        item.title = getString(R.string.show_as_grid)
        isListView = true
    }

    private fun showGridView() {
        staggeredLayoutManager.spanCount = 2
        val item = menu.findItem(R.id.action_toggle)
        item.setIcon(R.drawable.ic_action_list)
        item.title = getString(R.string.show_as_list)
        isListView = false
    }
}
