package com.raywenderlich.android.w00tze.repository

import com.raywenderlich.android.w00tze.model.Gist
import com.raywenderlich.android.w00tze.model.Repo
import com.raywenderlich.android.w00tze.model.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

/**
 * Created by isabellee on 4/18/18.
 */
interface GithubApi {
	@GET("users/{user}/repos")
	fun getRepos(@Path("user") user:String): Call<List<Repo>>

	@GET("users/{user}/gists")
	fun getGists(@Path("user") user:String): Call<List<Gist>>

	@GET("users/{user}")
	fun getUser(@Path("user") user:String): Call<User>
}