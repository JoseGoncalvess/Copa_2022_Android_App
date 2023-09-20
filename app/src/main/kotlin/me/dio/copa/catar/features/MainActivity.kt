package me.dio.copa.catar.features

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


import dagger.hilt.android.AndroidEntryPoint
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.extensions.observe
import me.dio.copa.catar.notification.scheduler.extensions.NotificationsmatchWorker
import me.dio.copa.catar.ui.theme.Copa2022Theme


@AndroidEntryPoint
class MainActivity : ComponentActivity(){
    private val viewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerActions()
        setContent {
            Copa2022Theme(darkTheme= isSystemInDarkTheme()) {
                val state by viewModel.state.collectAsState()
                MainScreen(matches = state.matchs, viewModel::toogleNotification)
            }
        }
    }

    private fun observerActions() {
        viewModel.action.observe(this){
            action ->
            when(action){
                is MainUiAction.DisableNotification -> {
                    NotificationsmatchWorker.cancel(applicationContext,action.match)
                }
                is MainUiAction.EnableNotification -> {
                    NotificationsmatchWorker.start(applicationContext,action.match)
                }
                is MainUiAction.MatchesNotfound -> TODO()
                MainUiAction.Unexpected -> TODO()
            }
        }
    }

}

