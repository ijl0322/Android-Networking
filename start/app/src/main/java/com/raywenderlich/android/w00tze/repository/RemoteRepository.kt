package com.raywenderlich.android.w00tze.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import android.os.AsyncTask
import android.os.RemoteCallbackList
import android.util.Log
import com.raywenderlich.android.w00tze.app.Constants
import com.raywenderlich.android.w00tze.app.Injection
import com.raywenderlich.android.w00tze.app.isNullorBlankOrNullString
import com.raywenderlich.android.w00tze.model.Gist
import com.raywenderlich.android.w00tze.model.Repo
import com.raywenderlich.android.w00tze.model.User
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import java.io.IOException
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by isabellee on 4/18/18.
 */

object RemoteRepository : Repository {
	private const val TAG = "RemoteRepository"
	private const val LOGIN = "w00tze"
	private val api = Injection.provideGithubApi()

	override fun getRepos(): LiveData<List<Repo>> {
		val liveData = MutableLiveData<List<Repo>>()

		api.getRepos(LOGIN).enqueue(object : Callback<List<Repo>> {
			override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {

			}

			override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
				if (response != null) {
					liveData.value = emptyList()
				}
			}
		})

		return liveData
	}

	override fun getGists(): LiveData<List<Gist>> {
		val liveData = MutableLiveData<List<Gist>>()
		api.getGists(LOGIN).enqueue(object : Callback<List<Gist>>{
			override fun onResponse(call: Call<List<Gist>>?, response: Response<List<Gist>>?) {
				if (response != null) {
					liveData.value = emptyList()
				}
			}

			override fun onFailure(call: Call<List<Gist>>?, t: Throwable?) {

			}

		})
		return liveData
	}

	override fun getUser(): LiveData<User> {
		val liveData = MutableLiveData<User>()

		api.getUser(LOGIN).enqueue(object : Callback<User>{
			override fun onFailure(call: Call<User>?, t: Throwable?) {

			}

			override fun onResponse(call: Call<User>?, response: Response<User>?) {

			}

		})

		return liveData
	}
}