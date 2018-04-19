package com.raywenderlich.android.w00tze.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.raywenderlich.android.w00tze.app.Injection
import com.raywenderlich.android.w00tze.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by isabellee on 4/18/18.
 */

object RemoteRepository : Repository {
	private const val TAG = "RemoteRepository"
	private const val LOGIN = "w00tze123"
	private val api = Injection.provideGithubApi()

	override fun getRepos(): LiveData<Either<List<Repo>>> {
		val liveData = MutableLiveData<Either<List<Repo>>>()

		api.getRepos(LOGIN).enqueue(object : Callback<List<Repo>> {
			override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
				liveData.value = Either.error(ApiError.REPOS, null)
			}

			override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
				if (response != null && response.isSuccessful) {
					liveData.value = Either.success(response.body())
				} else {
					liveData.value = Either.error(ApiError.REPOS, null)
				}
			}
		})
		return liveData
	}

	override fun getGists(): LiveData<Either<List<Gist>>> {
		val liveData = MutableLiveData<Either<List<Gist>>>()
		api.getGists(LOGIN).enqueue(object : Callback<List<Gist>>{
			override fun onResponse(call: Call<List<Gist>>?, response: Response<List<Gist>>?) {
				if (response != null && response.isSuccessful) {
					liveData.value = Either.success(response.body())
				} else {
					liveData.value = Either.error(ApiError.GISTS, null)
				}
			}

			override fun onFailure(call: Call<List<Gist>>?, t: Throwable?) {
				liveData.value = Either.error(ApiError.REPOS, null)
			}
		})
		return liveData
	}

	override fun getUser(): LiveData<Either<User>> {
		val liveData = MutableLiveData<Either<User>>()

		api.getUser(LOGIN).enqueue(object : Callback<User>{
			override fun onFailure(call: Call<User>?, t: Throwable?) {
				liveData.value = Either.error(ApiError.USER, null)
			}

			override fun onResponse(call: Call<User>?, response: Response<User>?) {
				if (response != null && response.isSuccessful) {
					liveData.value = Either.success(response.body())
				} else {
					liveData.value = Either.error(ApiError.USER, null)
				}
			}
		})
		return liveData
	}
}