package gamentorg.gament.repositories

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import gamentorg.gament.db.AppDatabase
import gamentorg.gament.db.dao.RuleDao
import gamentorg.gament.db.entities.Rule
import gamentorg.gament.services.network.ApiRequest
import gamentorg.gament.services.network.models.RuleResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RuleRepository @Inject constructor(appDatabase: AppDatabase, private val apiRequest: ApiRequest) {

    private val ruleDao: RuleDao = appDatabase.ruleDao()

    companion object {

        internal class InsertRule(val dao: RuleDao): AsyncTask<Rule, Void?, Void?>() {
            override fun doInBackground(vararg params: Rule?): Void? {
                dao.insertRule(params[0]!!)
                return null
            }
        }
    }

    fun insertRule(key: String) {

        apiRequest.fetchRule(key, object : ApiRequest.Callback<RuleResponse> {

            override fun onReceive(response: Response<RuleResponse>) {

                if (response.isSuccessful && response.body() != null) {
                    val rule = response.body()!!.data.document

                    InsertRule(ruleDao).execute(rule)
                }

            }
        })

    }

    fun getRuleByKey(key: String): LiveData<Rule> {
        return ruleDao.getRuleByKey(key)
    }
}