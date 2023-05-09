package components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun <T> ToggleCarousel(state: ToggleCarouselState<T>, description: String, height: Dp, setState: (ToggleCarouselState<T>) -> Unit) {
    Image(
        painter = painterResource(state.getSelectedImageLocation()),
        contentDescription = description,
        modifier = Modifier.height(height).clickable { setState(state.advance()) }
    )
}