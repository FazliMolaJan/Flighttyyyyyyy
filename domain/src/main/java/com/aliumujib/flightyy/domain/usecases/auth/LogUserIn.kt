package com.aliumujib.flightyy.domain.usecases.auth


import com.aliumujib.flightyy.domain.executor.PostExecutionThread
import com.aliumujib.flightyy.domain.usecases.base.ObservableUseCase
import com.aliumujib.flightyy.domain.models.Airport
import com.aliumujib.flightyy.domain.models.schedule.Schedule
import com.aliumujib.flightyy.domain.repositories.auth.IAuthRepository
import com.aliumujib.flightyy.domain.repositories.schedules.ISchedulesRepository
import com.aliumujib.flightyy.domain.usecases.base.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class LogUserIn @Inject constructor(
    private val repository: IAuthRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<LogUserIn.Params>(postExecutionThread) {

    public override fun buildUseCaseCompletable(params: Params?): Completable {
        checkNotNull(params) { "Your params can't be null for this use case" }

        return repository.login(
            params.clientId,
            params.clientSecret)
    }

    data class Params constructor(val clientId: String, val clientSecret: String) {
        companion object {
            fun make(clientId: String, clientSecret: String): Params {
                return Params(clientId, clientSecret)
            }
        }
    }

}