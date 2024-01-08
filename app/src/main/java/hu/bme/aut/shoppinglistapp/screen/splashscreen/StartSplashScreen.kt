package hu.bme.aut.shoppinglistapp.screen.splashscreen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import hu.bme.aut.todoapp.R

@Composable
fun SplashScreen(onNavigateToMain: () -> Unit) = Box(
    Modifier
        .fillMaxSize()
) {
    val scale = remember {
        Animatable(0.0f)
    }
    LaunchedEffect(key1 = Unit) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        // 3 second delay then navigate to main screen
        delay(3000)
        onNavigateToMain()
    }
    Image(
        painter = painterResource(id = R.drawable.splashicon),
        contentDescription = "Splash",
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize().padding(40.dp)
            .scale(scale.value)
    )
    Text(
        text = stringResource(R.string.AppNameSplash),
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(bottom = 60.dp)
    )
}