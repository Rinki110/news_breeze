package com.example.newsbreezer.view


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbreezer.data.network.ResultData
import com.example.newsbreezer.data.repository.MainRepository
import com.example.newsbreezer.model.BreakingNewsModel
import com.example.newsbreezer.utils.ApiException
import com.example.newsbreezer.utils.AppConstants
import com.example.newsbreezer.utils.NoInternetException
import kotlinx.coroutines.*


class HomeViewModel(
        private val repository: MainRepository,
) : ViewModel() {


    private val TAG = "MainViewModel"
    private lateinit var job: Job

    private var _newsResultData = MutableLiveData<ResultData<Any>>()
    val newsResultData: LiveData<ResultData<Any>>
        get() = _newsResultData

    private val savedArticleData = mutableListOf<BreakingNewsModel.Article>()
    val newsSavedData = MutableLiveData<MutableList<BreakingNewsModel.Article>>()

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized)
            job.cancel()
    }

    fun saveSelectedArticleDetails (articalDetails : BreakingNewsModel.Article){
        //todo create table for saved news using room and save it
        savedArticleData.add(articalDetails)
        newsSavedData.value = savedArticleData
    }

    fun removeSelectedArticleDetails (articalDetails : BreakingNewsModel.Article){
        savedArticleData.remove(articalDetails)
        newsSavedData.value = savedArticleData
    }

    fun getNewsDetails() {
        _newsResultData = MutableLiveData()
        _newsResultData.value = ResultData.Loading(AppConstants.API_LOADER_DEFAULT_MSG)
        job =  viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            try {
                val newsResult =  repository.getTopHeadLines()
                newsResult.articles?.let {
                    _newsResultData.value = ResultData.Success(it)
                    return@launch
                }
                _newsResultData.value = ResultData.Failure<String>(AppConstants.API_ERROR_DEFAULT_MSG)

            } catch (e: ApiException) {
                _newsResultData.value = ResultData.Failure<String>(e.message.toString())

            } catch (e: NoInternetException) {
                _newsResultData.value = ResultData.Failure<String>(e.message.toString())
            }
        }
    }

}







