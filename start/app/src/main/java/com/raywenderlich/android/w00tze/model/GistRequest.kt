package com.raywenderlich.android.w00tze.model

/**
 * Created by isabellee on 4/19/18.
 */
class GistRequest(val description: String, val files: Map<String, GistFile>, val public: Boolean = true)