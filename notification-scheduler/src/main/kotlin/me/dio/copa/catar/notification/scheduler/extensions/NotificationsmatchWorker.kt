package me.dio.copa.catar.notification.scheduler.extensions

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import me.dio.copa.catar.domain.model.MatchDomain
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.Delayed

private const val NOTIFICATION_TITLE_KEY = "NOTIFICATION_TITLE_KEY"
private const val NOTIFICATION_CONTENT_KEY = "NOTIFICATION_CONTENT_KEY"

class NotificationsmatchWorker(private val contex: Context, workParams: WorkerParameters) :
    Worker(contex, workParams) {
    override fun doWork(): Result {
        val title = inputData.getString(NOTIFICATION_TITLE_KEY) ?: "Ops,required is title"
        val content =
            inputData.getString(NOTIFICATION_CONTENT_KEY) ?: "Ops,required is contetn"
        contex.showNotification(title, content)
        return Result.success()
    }

    companion object {
        fun start(contex: Context, match: MatchDomain) {
            val (id, _, _, team1, team2, date) = match
            val initialDelayed = Duration.between(LocalDateTime.now(), date).minusMinutes(3)
            val inputdata = workDataOf(
                NOTIFICATION_TITLE_KEY to "Logo mais tem jogão, não perca!",
                NOTIFICATION_CONTENT_KEY to "Hoje tem ${team1.flag} X ${team2.flag}, Com narração de Galvão Bueno!"

            )
            WorkManager.getInstance(contex)
                .enqueueUniqueWork(
                    id,
                    ExistingWorkPolicy.KEEP,
                    createdRequest(initialDelayed, inputdata)
                )
        }

        private fun createdRequest(initialDelayed: Duration, inputdata: Data) =
            OneTimeWorkRequestBuilder<NotificationsmatchWorker>()
                .setInitialDelay(initialDelayed)
                .setInputData(inputdata)
                .build()

         fun cancel(context: Context, match: MatchDomain) {
            WorkManager.getInstance(context)
                .cancelUniqueWork(match.id)
        }

    }

}