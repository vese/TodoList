package com.example.todolist

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.example.todolist.db.DBHelper
import com.example.todolist.db.Todo
import com.example.todolist.db.TodoDbService
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: TodoDbService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = TodoDbService(DBHelper(this))

        updateTable()
    }

    fun openAddPopup(view: View) {
        openAddPopup(view, null)
    }

    private fun openAddPopup(view: View, todo: Todo?) {
        val rootView = findViewById<ConstraintLayout>(R.id.activity_main)

        val childView = layoutInflater.inflate(R.layout.view_add, null)

        val popupWindow = PopupWindow(
            view.context
        )
        popupWindow.contentView = childView
        popupWindow.contentView.measure(popupWindow.width, popupWindow.height)

        val editText = childView.findViewById<EditText>(R.id.name_edit)
        val buttonCancel = childView.findViewById<TextView>(R.id.button_cancel)
        val buttonSave = childView.findViewById<Button>(R.id.button_save)

        if (todo != null) {
            editText.text.append(todo.name)
        }
        editText.addTextChangedListener {
            buttonSave.isEnabled = editText.text.toString().isNotEmpty()
        }

        buttonCancel.setOnClickListener {
            popupWindow.dismiss()
        }

        buttonSave.setOnClickListener {
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)
            progressBar.visibility = VISIBLE
            if (todo == null) {
                dbHelper.insertTodo(editText.text.toString())
                updateTable()
                popupWindow.dismiss()
            } else {
                if (todo.name == editText.text.toString()) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.not_changed_msg),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    todo.name = editText.text.toString()
                    dbHelper.updateTodo(todo)
                    updateTable()
                    popupWindow.dismiss()
                }
            }
            progressBar.visibility = GONE
        }

        popupWindow.showAsDropDown(rootView, 120, 200, Gravity.START)
        popupWindow.isFocusable = true;
        popupWindow.update();
    }

    private fun openConfirmPopup(id: Int) {
        val rootView = findViewById<ConstraintLayout>(R.id.activity_main)

        val childView = layoutInflater.inflate(R.layout.view_confirm, null)

        val popupWindow = PopupWindow(
            rootView.context
        )
        popupWindow.contentView = childView
        popupWindow.contentView.measure(popupWindow.width, popupWindow.height)

        val buttonCancel = childView.findViewById<TextView>(R.id.button_no)
        val buttonSave = childView.findViewById<Button>(R.id.button_yes)

        buttonCancel.setOnClickListener {
            popupWindow.dismiss()
        }

        buttonSave.setOnClickListener {
            try {
                dbHelper.deleteTodo(id)
                updateTable()
                popupWindow.dismiss()
            } catch (e: Exception) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.can_not_save),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        popupWindow.showAsDropDown(rootView, 120, 200, Gravity.START)
        popupWindow.isFocusable = true;
        popupWindow.update();
    }

    private fun updateTable() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = VISIBLE

        val todos = dbHelper.getTodoList()

        val tableLayout = findViewById<TableLayout>(R.id.table_layout)

        tableLayout.removeAllViews()

        if (todos.size == 0) {
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val text = getString(R.string.todo_list_empty)
            textView.text = text
            tableRow.addView(textView)
            tableLayout.addView(tableRow)
        }

        for (i in 1..todos.size) {
            val tableRow = TableRow(this)
            val textView = TextView(this)
            val text = "$i. ${todos[i - 1].name}"
            textView.text = text
            tableRow.addView(textView)
            val editButton = Button(this)
            editButton.text = getString(R.string.edit_btn)
            editButton.setOnClickListener {
                openAddPopup(findViewById<ConstraintLayout>(R.id.activity_main), todos[i - 1])
            }
            tableRow.addView(editButton)
            val deleteButton = Button(this)
            deleteButton.text = getString(R.string.delete_btn)
            deleteButton.setOnClickListener {
                openConfirmPopup(todos[i - 1].id)
                updateTable()
            }
            tableRow.addView(deleteButton)
            tableLayout.addView(tableRow)
        }
        progressBar.visibility = GONE
    }
}