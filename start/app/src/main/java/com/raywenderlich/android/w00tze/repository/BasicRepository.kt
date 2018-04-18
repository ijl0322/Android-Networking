package com.raywenderlich.android.w00tze.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import com.raywenderlich.android.w00tze.app.Constants.fullUrlString
import com.raywenderlich.android.w00tze.model.Gist
import com.raywenderlich.android.w00tze.model.Repo
import com.raywenderlich.android.w00tze.model.User
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

/**
 * Created by isabellee on 4/18/18.
 */

object BasicRepository : Repository {
	private const val TAG = "BasicRepository"
	private const val LOGIN = "w00tze"


	override fun getRepos(): LiveData<List<Repo>> {
		val liveData = MutableLiveData<List<Repo>>()

		FetchReposAsyncTask({ repos ->
				liveData.value = repos
		}).execute()
		return liveData
	}

	override fun getGists(): LiveData<List<Gist>> {
		val liveData = MutableLiveData<List<Gist>>()
		val gists = mutableListOf<Gist>()
		FetchGistsAsyncTask({
			liveData.value = gists
		}).execute()
		return liveData
	}

	override fun getUser(): LiveData<User> {
		val liveData = MutableLiveData<User>()

		val user = User(
				1234L,
				"w00tze",
				"w00tze",
				"W00tzeWootze",
				"https://avatars0.githubusercontent.com/u/36771440?v=4")

		liveData.value = user

		return liveData
	}

	private fun fetchRepos(): List<Repo>? {
		try {
			val url = Uri.parse(fullUrlString("/users/${LOGIN}/repos")).toString()
			val jsonString = getURLAsString(url)

//			Log.i(TAG, "Repo data: $jsonString")
//
//			val repos = mutableListOf<Repo>()
//			for (i in 0 until 100) {
//				val repo = Repo("repo name")
//				repos.add(repo)
//			}

			return parseRepos(jsonString)
		} catch (e: IOException) {
			Log.e(TAG, "Error retrieving repos: ${e.localizedMessage}")
		} catch (e: JSONException) {
			Log.e(TAG, "Error retrieving repos: ${e.localizedMessage}")
		}
		return null
	}

	private fun fetchGist(): List<Gist>? {
		try {
			val url = Uri.parse(fullUrlString("/users/${LOGIN}/gists")).toString()
			val jsonString = getURLAsString(url)

			Log.i(TAG, "Gist data: $jsonString")

			val gists = mutableListOf<Gist>()

			for (i in 0 until 100) {
				val gist = Gist("2018-02-23T17:42:52Z", "w00t")
				gists.add(gist)
			}
			return gists
		} catch (e: IOException) {
			Log.e(TAG, "Error retrieving gists: ${e.localizedMessage}")
		}
		return null
	}

	private class FetchReposAsyncTask(val callback: ReposCallback) : AsyncTask<ReposCallback, Void, List<Repo>>() {
		override fun doInBackground(vararg params: ReposCallback?): List<Repo>? {
			return fetchRepos()
		}

		override fun onPostExecute(result: List<Repo>?) {
			super.onPostExecute(result)
			if (result != null) {
				callback(result)
			}
		}
	}

	private fun parseRepos(jsonString: String): List<Repo> {
		val repos = mutableListOf<Repo>()
		val reposArray = JSONArray(jsonString)
		for (i in 0 until reposArray.length()) {
			val repoObject = reposArray.getJSONObject(i)
			val repo = Repo(repoObject.getString("name"))
			repos.add(repo)
		}
		return repos
	}

	private class FetchGistsAsyncTask(val callback: GistsCallback) : AsyncTask<GistsCallback, Void, List<Gist>>() {

		override fun doInBackground(vararg params: GistsCallback?): List<Gist>? {
			return fetchGist()
		}

		override fun onPostExecute(result: List<Gist>?) {
			super.onPostExecute(result)
			if (result != null) {
				callback(result)
			}
		}
	}
}