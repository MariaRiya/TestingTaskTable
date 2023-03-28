package ru.test.testingtasktable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.test.testingtasktable.adapter.ItemData

class MainViewModel : ViewModel() {

    private var _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> = _screenState

    private var _pointsState = MutableLiveData<CountState>()
    val pointsState: LiveData<CountState> = _pointsState

    private var _placesState = MutableLiveData<PlacesState>()
    val placesState: LiveData<PlacesState> = _placesState

    private var listOfItems = arrayListOf<ItemData>()

    private var countOfParticipants = MainActivity.DEFAULT_COUNT

    fun createListOfData(count: Int, immediately: Boolean = false) {
        if (immediately) {
            listOfItems.clear()
            _placesState.value = PlacesState.RemovePlacesState
            _pointsState.value = CountState.RemoveAllCounts
        }
        countOfParticipants = count
        if (listOfItems.isEmpty()) {
            for (i in 0..count-1) {
                for (j in 0..count-1) {
                    listOfItems.add(ItemData(i, j, null))
                }
            }
        }
        _screenState.value = ScreenState.ResetDataState(listOfItems)
    }

    fun addNewPoints(itemData: ItemData) {
        listOfItems.forEach {
            if (itemData.first == it.first && itemData.second == it.second) {
                it.count = itemData.count
                if (checkAllPoints(itemData.first)) {
                    checkAllFilled()
                } else if (_placesState.value is PlacesState.SetPlacesState){
                    _placesState.value = PlacesState.RemovePlacesState
                }
            }
        }
    }

    private fun checkAllPoints(row: Int) : Boolean {
        val arrayOfPoints = countPoints(row)
        if (arrayOfPoints.count() == countOfParticipants-1) {
            _pointsState.value = CountState.SetCountState(row, arrayOfPoints.sum())
            return true
        } else {
            _pointsState.value = CountState.RemoveCountState(row)
            return false
        }
    }

    private fun countPoints(row: Int): ArrayList<Int> {
        val startIndex = row * countOfParticipants
        val endIndex = startIndex + countOfParticipants
        val arrayOfPoints = arrayListOf<Int>()

        for (i in startIndex..endIndex-1) {
            val el = listOfItems[i]
            if (el.first != el.second) {
                if (el.count != null && el.count!! <= 5) {
                    arrayOfPoints.add(el.count!!)
                }
            }
        }
        return arrayOfPoints
    }

    private fun checkAllFilled() {
        val listOfPointsByParticipant = arrayListOf<Int>()
        for (i in 0..countOfParticipants-1) {
            val points = countPoints(i)
            if (points.size == countOfParticipants-1) {
                listOfPointsByParticipant.add(points.sum())
            } else {
                _placesState.value = PlacesState.RemovePlacesState
                return
            }
        }
        if (listOfPointsByParticipant.size == countOfParticipants) {
            setPlaces(listOfPointsByParticipant)
        }
    }

    private fun setPlaces(listOfPointsByParticipant: ArrayList<Int>) {
        val arrayOfTriples = arrayListOf<Triple<Int, Int, Int?>>()
        val result = arrayListOf<Pair<Int, Int>>()
        for (i in 0..listOfPointsByParticipant.size-1) {
            arrayOfTriples.add((Triple(i, listOfPointsByParticipant[i], null)))
        }
        listOfPointsByParticipant.sortDescending()
        arrayOfTriples.forEach {
            val place = listOfPointsByParticipant.indexOf(it.second)
            val pair = Pair(it.first, place)
            result.add(pair)
        }
        _placesState.value = PlacesState.SetPlacesState(result)
    }
}