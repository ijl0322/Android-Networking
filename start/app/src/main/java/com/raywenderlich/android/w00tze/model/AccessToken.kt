package com.raywenderlich.android.w00tze.model

import com.google.gson.annotations.SerializedName

/**
 * Created by isabellee on 4/19/18.
 */
class AccessToken(
		@SerializedName("access_token") val accessToken: String,
		@SerializedName("token_type") val tokenType: String)