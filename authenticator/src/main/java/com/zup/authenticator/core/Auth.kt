package com.zup.authenticator.core

import android.app.Activity
import com.zup.authenticator.AuthApplication
import com.zup.authenticator.AuthApplicationCallback
import com.zup.authenticator.AuthDeclaration

internal class Auth(
    private val activity: Activity?,
    private val callbackError: AuthApplicationCallback?,
    private val callbackSuccess: AuthApplicationCallback?
) {
    init {
        if (activity == null) {
            throw AuthException.AuthInvalidException("Sem activity")
        }

        this.initCallAuth()
    }

    private fun initCallAuth() {
        // TODO: inicia a tela de validação do codigo
    }
}

internal fun startAuthApplication(appDeclaration: AuthDeclaration? = null): AuthApplication {
    val authApplication = AuthApplication()
    appDeclaration?.invoke(authApplication)
    Auth(
        activity = authApplication.getActivity(),
        callbackError = authApplication.getCallbackError(),
        callbackSuccess = authApplication.getCallbackSuccess()
    )
    return authApplication
}
