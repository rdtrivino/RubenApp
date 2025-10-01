package com.rubentrivino.rubenapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rubentrivino.rubenapp.model.TaskStore

class MainActivity : AppCompatActivity() {

    private val tasks = mutableListOf<String>()

    private lateinit var store: TaskStore
    private lateinit var rv: RecyclerView
    private lateinit var empty: View
    private lateinit var adapter: SimpleAdapter
    private var pendingEditPos: Int? = null

    private val addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val text = res.data?.getStringExtra("result_text")?.trim()
            if (!text.isNullOrEmpty()) {
                tasks.add(0, text)
                adapter.notifyItemInserted(0)
                rv.scrollToPosition(0)
                updateEmpty()
                store.save(tasks)
            }
        }
    }

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            val text = res.data?.getStringExtra("result_text")?.trim()
            pendingEditPos?.let { pos ->
                if (!text.isNullOrEmpty() && pos in tasks.indices) {
                    tasks[pos] = text
                    adapter.notifyItemChanged(pos)
                    store.save(tasks)
                }
            }
        }
        pendingEditPos = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        store = TaskStore(this)

        tasks.clear()
        tasks.addAll(store.load())

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        rv = findViewById(R.id.recyclerTasks)
        empty = findViewById(R.id.emptyState)
        val fab: FloatingActionButton = findViewById(R.id.fabAdd)

        adapter = SimpleAdapter(
            items = tasks,
            onShare = { share(it) },
            onEdit = { position, current ->
                pendingEditPos = position
                val i = Intent(this, DetailActivity::class.java)
                    .putExtra("current_text", current)
                editLauncher.launch(i)
            },
            onDelete = { position ->
                if (position !in tasks.indices) return@SimpleAdapter
                val removed = tasks.removeAt(position)
                adapter.notifyItemRemoved(position)
                updateEmpty()
                store.save(tasks)
                Snackbar.make(rv, "Tarea eliminada", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER") {
                        tasks.add(position, removed)
                        adapter.notifyItemInserted(position)
                        updateEmpty()
                        store.save(tasks)
                    }.show()
            }
        )

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        fab.setOnClickListener {
            val i = Intent(this, DetailActivity::class.java)
            addLauncher.launch(i)
        }

        val touchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.adapterPosition
                if (pos == RecyclerView.NO_POSITION) return

                val removed = tasks.removeAt(pos)
                adapter.notifyItemRemoved(pos)
                updateEmpty()
                store.save(tasks)
                Snackbar.make(rv, "Tarea eliminada", Snackbar.LENGTH_LONG)
                    .setAction("DESHACER") {
                        tasks.add(pos, removed)
                        adapter.notifyItemInserted(pos)
                        updateEmpty()
                        store.save(tasks)
                    }.show()
            }
        }
        ItemTouchHelper(touchHelper).attachToRecyclerView(rv)

        updateEmpty()
    }

    override fun onStop() {
        super.onStop()
        store.save(tasks)
    }

    private fun updateEmpty() {
        empty.isVisible = tasks.isEmpty()
        rv.isVisible = tasks.isNotEmpty()
    }

    private fun share(text: String) {
        val i = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(i, "Compartir con..."))
    }
}
