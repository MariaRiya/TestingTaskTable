package ru.test.testingtasktable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import ru.test.testingtasktable.adapter.ItemData
import ru.test.testingtasktable.adapter.MainAdapter
import ru.test.testingtasktable.databinding.ActivityMainBinding
import androidx.activity.viewModels


class MainActivity : AppCompatActivity(), MainAdapter.OnInputClickListener {

    private lateinit var binding: ActivityMainBinding
    private var count = DEFAULT_COUNT

    private val viewModel by viewModels<MainViewModel>()

    companion object {
        const val DEFAULT_COUNT = 7
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpUI()
        setUpSubscriptions()
    }

    private fun setUpSubscriptions() {
        viewModel.screenState.observe(this) { state->
            when (state) {
                is ScreenState.ResetDataState -> {
                    binding.recycler.adapter = MainAdapter().apply {
                        listener = this@MainActivity
                    }
                    (binding.recycler.adapter as? MainAdapter)?.setData(state.list)
                }
            }
        }

        viewModel.pointsState.observe(this) {state ->
            when (state) {
                is CountState.SetCountState -> {
                    setCountToView(state.row, state.count.toString())
                }
                is CountState.RemoveCountState -> {
                    setCountToView(state.row, "")
                }
                is CountState.RemoveAllCounts -> {
                    for (i in 0..count-1) {
                        setCountToView(i, "")
                    }
                }
            }
        }

        viewModel.placesState.observe(this) { state ->
            when (state) {
                is PlacesState.SetPlacesState -> {
                    for (i in 0..state.listOfPlaces.size-1) {
                        setPlaceToView(i, (state.listOfPlaces[i].second + 1).toString())
                    }
                }
                is PlacesState.RemovePlacesState -> {
                    for (i in 0..count-1) {
                        setPlaceToView(i, "")
                    }
                }
            }
        }
    }

    private fun setCountToView(row: Int, text: String) {
        when (row) {
            0 -> {
                binding.count0.text = text
            }
            1 -> {
                binding.count1.text = text
            }
            2 -> {
                binding.count2.text = text
            }
            3 -> {
                binding.count3.text = text
            }
            4 -> {
                binding.count4.text = text
            }
            5 -> {
                binding.count5.text = text
            }
            6 -> {
                binding.count6.text = text
            }
        }
    }

    private fun setPlaceToView(row: Int, text: String) {
        when (row) {
            0 -> {
                binding.place0.text = text
            }
            1 -> {
                binding.place1.text = text
            }
            2 -> {
                binding.place2.text = text
            }
            3 -> {
                binding.place3.text = text
            }
            4 -> {
                binding.place4.text = text
            }
            5 -> {
                binding.place5.text = text
            }
            6 -> {
                binding.place6.text = text
            }
        }
    }

    private fun setUpUI() {

        binding.recycler.apply {
            layoutManager = GridLayoutManager(this@MainActivity, count)
        }

        viewModel.createListOfData(count)

        binding.clear.setOnClickListener {
            (viewModel.createListOfData(count, true))
        }
    }

    override fun onClick(item: ItemData) {
        viewModel.addNewPoints(item)
    }
}

sealed class ScreenState {
    data class ResetDataState(val list: ArrayList<ItemData>) : ScreenState()
}

sealed class CountState {
    data class SetCountState(val row: Int, val count: Int) : CountState()
    data class RemoveCountState(val row: Int) : CountState()
    object RemoveAllCounts : CountState()
}

sealed class PlacesState {
    data class SetPlacesState(val listOfPlaces: ArrayList<Pair<Int, Int>>) : PlacesState()
    object RemovePlacesState : PlacesState()
}