package de.moyapro.homecontroller.ui.settings

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider


object Label {
    const val DESCRIPTION = "Lable"
}

@Preview
@Composable
fun Label(@PreviewParameter(LabelProvider::class) labelText: String) {
    Text(text = labelText, modifier = Modifier.semantics {
        contentDescription = Label.DESCRIPTION
    })
}


class LabelProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = listOf("The Label").asSequence()
}
