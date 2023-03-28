package ru.test.testingtasktable.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.test.testingtasktable.R
import ru.test.testingtasktable.databinding.CardviewInputBinding

class ItemHolder(private val binding: CardviewInputBinding,
                 private val listener: MainAdapter.OnInputClickListener?
                 ): ViewHolder(binding.root) {

    fun bind(item: ItemData) {
        binding.apply {
            if (item.first == item.second) {
                root.setBackgroundColor(root.context.resources.getColor(R.color.black))
                editTextNumber.setBackgroundColor(root.context.resources.getColor(R.color.black))
                root.isEnabled = false
                editTextNumber.isEnabled = false

            } else {
                if (item.count != null) {
                    editTextNumber.setText(item.count.toString())
                }
                editTextNumber.addTextChangedListener(object : TextWatcher {
                    var data = ""

                    override fun beforeTextChanged(cs: CharSequence, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(cs: CharSequence, p1: Int, p2: Int, p3: Int) {
                        data = cs.toString()
                    }

                    override fun afterTextChanged(cs: Editable) {
                        if (data.isNotEmpty() && data.toInt() <= 5) {
                            editTextNumber.setTextColor(root.context.getColor(R.color.black))
                            item.count = data.toInt()
                        } else if (data.isEmpty()) {
                            editTextNumber.setTextColor(root.context.getColor(R.color.black))
                            item.count = null
                        } else {
                            editTextNumber.setTextColor(root.context.getColor(R.color.red))
                            item.count = data.toInt()
                            Toast.makeText(root.context, root.context.getText(R.string.error_message), Toast.LENGTH_SHORT).show()
                        }
                        listener?.onClick(item)
                    }
                })
            }
        }
    }
}