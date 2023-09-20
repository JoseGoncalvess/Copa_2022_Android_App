package me.dio.copa.catar.features

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.dio.copa.catar.R
import me.dio.copa.catar.domain.extensions.getDate
import me.dio.copa.catar.domain.model.MatchDomain
import me.dio.copa.catar.domain.model.TeamDomain
import me.dio.copa.catar.ui.theme.Shapes

typealias NotificationOnclik = (match: MatchDomain) -> Unit

@Composable
fun MainScreen(matches: List<MatchDomain>,onNotificationOnclik:NotificationOnclik) {

  Column(modifier = Modifier
      .fillMaxSize()
      .background(
          brush = Brush.verticalGradient(
              colors = listOf(
                  Color.Black,
                  Color.Black,
                  Color.Green
              )
          )
      )

  ) {
      TitleApp()
      Box(
          modifier = Modifier
              .fillMaxSize()
              .padding(4.dp)

      ) {

          LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {

              items(matches) { matchs ->
                  MatchInfo(match = matchs, onNotificationOnclik = onNotificationOnclik)

              }
          }
      }
  }


}

@Composable
fun TitleApp() {
   Row(modifier = Modifier.fillMaxWidth()
       ,verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Center
       ) {
       Text(text = "COPA DO MUNDO ", style =  MaterialTheme.typography.h4, color = Color.White  )
   }

}
@Composable
fun MatchInfo(match: MatchDomain, onNotificationOnclik: NotificationOnclik) {

    Column(modifier = Modifier.fillMaxWidth(),) {



        Card(shape = Shapes.large, modifier = Modifier.fillMaxWidth()) {
            Box {
                AsyncImage(
                    model = match.stadium.image ?: R.drawable.camp,
                    contentDescription = "Imagem dos estadio que sediaram os jogos",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(160.dp)
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Notification(match = match, onCliker = onNotificationOnclik)
                    Title(match = match)
                    Teams(match = match)

                }
            }
        }
    }




}


@Composable
fun Teams(match: MatchDomain) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        ItemTeam(team = match.team1, dir = "direita")
        Text(
            "X",
            Modifier.padding(start = 16.dp, end = 16.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.White)
        )
        ItemTeam(team = match.team2, dir = "esquerda")

    }
}

@Composable
fun ItemTeam(team: TeamDomain, dir: String) {
    if (dir == "direita") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = team.displayName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = team.flag,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.h3.copy(color = Color.White)
            )


        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = team.flag,
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.h3.copy(color = Color.White)
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = team.displayName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6.copy(color = Color.White)
            )


        }
    }

}

@Composable
fun Title(match: MatchDomain) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${match.date.getDate()} - ${match.name}",
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )

    }
}

@Composable
fun Notification(match: MatchDomain, onCliker: NotificationOnclik) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        val icon =
            if (match.notificationEnabled) R.drawable.ic_notifications_active else R.drawable.ic_notifications
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Icone de Notificação",
            modifier = Modifier.clickable { onCliker(match) })
    }
}