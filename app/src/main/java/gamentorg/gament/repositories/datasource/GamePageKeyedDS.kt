package gamentorg.gament.repositories.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import gamentorg.gament.db.entities.Game
import gamentorg.gament.repositories.GameRepository
import gamentorg.gament.services.network.ApiService
import gamentorg.gament.services.network.models.MainGamesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamePageKeyedDS @Inject constructor(private val apiService: ApiService, private val repository: GameRepository): PageKeyedDataSource<Int, Game>() {

    private var totalPage: Int = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Game>) {

        apiService.fetchAllGames(1).enqueue(object : Callback<MainGamesResponse> {
            override fun onFailure(call: Call<MainGamesResponse>, t: Throwable) {
                Log.e("network error", t.message)
            }

            override fun onResponse(call: Call<MainGamesResponse>, response: Response<MainGamesResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.code() == 200 && response.body()!!.state) {
                        val games = response.body()!!.data.documents
                        repository.insertGames(games)
                        totalPage = response.body()!!.data.totalPage
                        callback.onResult(games, null, response.body()!!.data.currentPage++)
                    }
                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Game>) {
        if (params.key <= totalPage) {
            apiService.fetchAllGames(params.key).enqueue(object : Callback<MainGamesResponse>{
                override fun onFailure(call: Call<MainGamesResponse>, t: Throwable) {
                    Log.e("network error", t.message)
                }

                override fun onResponse(call: Call<MainGamesResponse>, response: Response<MainGamesResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.code() == 200 && response.body()!!.state) {
                            val games = response.body()!!.data.documents
                            repository.insertGames(games)
                            callback.onResult(games, response.body()!!.data.currentPage++)
                        }
                    }
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Game>) {}
}