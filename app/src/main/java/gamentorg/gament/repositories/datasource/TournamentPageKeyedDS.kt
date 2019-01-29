package gamentorg.gament.repositories.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import gamentorg.gament.db.entities.Tournament
import gamentorg.gament.repositories.TournamentRepository
import gamentorg.gament.services.network.ApiService
import gamentorg.gament.services.network.models.TournamentsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TournamentPageKeyedDS @Inject constructor(private val apiService: ApiService, private val repository: TournamentRepository): PageKeyedDataSource<Int, Tournament>() {

    private var totalPages: Int = 1
    private lateinit var game: String

    fun gameKey(key: String) {
        this.game = key
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Tournament>) {

        apiService.fetchAllTournaments(game, 1).enqueue(object : Callback<TournamentsResponse> {

            override fun onFailure(call: Call<TournamentsResponse>, t: Throwable) {
                Log.e("network error", t.message)
            }

            override fun onResponse(call: Call<TournamentsResponse>, response: Response<TournamentsResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    if (response.code() == 200) {
                        if (response.body()!!.state) {
                            val tournaments = response.body()!!.data.documents
                            repository.insertTournaments(tournaments)
                            totalPages = response.body()!!.data.totalPages
                            callback.onResult(tournaments, null, response.body()!!.data.currentPage++)
                        }
                    }

                }
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Tournament>) {
        if (params.key <= totalPages) {
            apiService.fetchAllTournaments(game, params.key + 1).enqueue(object : Callback<TournamentsResponse> {

                override fun onFailure(call: Call<TournamentsResponse>, t: Throwable) {
                    Log.e("network error", t.message)
                }

                override fun onResponse(call: Call<TournamentsResponse>, response: Response<TournamentsResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        if (response.code() == 200 && response.body()!!.state) {
                            val tournaments = response.body()!!.data.documents
                            repository.insertTournaments(tournaments)
                            callback.onResult(tournaments, response.body()!!.data.currentPage++)
                        }
                    }
                }
            })
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Tournament>) {}
}