package com.aliumujib.flightyy.domain.usecases.auth


import com.aliumujib.flightyy.domain.repositories.auth.IAuthRepository
import com.aliumujib.flightyy.domain.usecases.base.SynchronousUseCase
import javax.inject.Inject

class CheckIfUserIsLoggedIn @Inject constructor(
    private val repository: IAuthRepository
) : SynchronousUseCase<Unit, Boolean>() {

    override fun execute(params: Unit?): Boolean {
        return repository.isUserLoggedIn()
    }

}