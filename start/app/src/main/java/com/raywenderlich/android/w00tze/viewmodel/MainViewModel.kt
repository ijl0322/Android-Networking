package com.raywenderlich.android.w00tze.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.net.Uri
import com.raywenderlich.android.w00tze.BuildConfig
import com.raywenderlich.android.w00tze.app.Injection
import com.raywenderlich.android.w00tze.model.AccessToken
import com.raywenderlich.android.w00tze.model.AuthenticationPrefs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by isabellee on 4/19/18.
 */
class MainViewModel(application: Application): AndroidViewModel(application){
	private val authApi = Injection.provideAuthApi()
	fun isAuthenticated() = AuthenticationPrefs.isAuthenticated()
	fun getAccesssToken(uri: Uri, callback: () -> Unit) {
		val accessCode = uri.getQueryParameter("code")
		authApi.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, accessCode).enqueue(object : Callback<AccessToken> {
			override fun onFailure(call: Call<AccessToken>?, t: Throwable?) {
				println("Error Getting Token")
			}

			override fun onResponse(call: Call<AccessToken>?, response: Response<AccessToken>?) {
				val accessToken = response?.body()?.accessToken
				val tokenType = response?.body()?.tokenType
				if (accessToken != null && tokenType != null) {
					AuthenticationPrefs.saveAuthToken(accessToken)
					AuthenticationPrefs.saveTokenType(tokenType)
					callback()
				}
			}
		})
	}

	fun logout() {
		AuthenticationPrefs.saveAuthToken("")
		AuthenticationPrefs.clearUsername()
	}
}