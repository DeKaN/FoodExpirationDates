package com.lorenzovainigli.foodexpirationdates.view.composable

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lorenzovainigli.foodexpirationdates.R
import com.lorenzovainigli.foodexpirationdates.model.repository.PreferencesRepository
import com.lorenzovainigli.foodexpirationdates.ui.theme.FoodExpirationDatesTheme
import com.lorenzovainigli.foodexpirationdates.view.preview.DefaultPreviews
import com.lorenzovainigli.foodexpirationdates.view.preview.LanguagePreviews
import java.text.SimpleDateFormat
import java.util.Calendar

const val DateFormatDialog = "DateFormatDialog"

@Composable
fun DateFormatDialog(
    isDialogOpen: Boolean = true,
    onDismissRequest: () -> Unit = {},
    onClickDate: (Context, String) -> Unit = { _, _ -> }
) {
    if (isDialogOpen) {
        Dialog(onDismissRequest = onDismissRequest) {
            Card(
                modifier = Modifier.testTag(DateFormatDialog),
                shape = RoundedCornerShape(10.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.choose_the_date_format),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        modifier = Modifier.padding(bottom = 4.dp),
                        text = stringResource(id = R.string.locale_formats),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    PreferencesRepository.getAvailLocaleDateFormats().forEach { item ->
                        DateFormatRow(
                            item = item,
                            onDismissRequest = onDismissRequest,
                            onClick = onClickDate
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                        text = stringResource(id = R.string.other_formats),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    PreferencesRepository.getAvailOtherDateFormats().forEach { item ->
                        DateFormatRow(
                            item = item,
                            onDismissRequest = onDismissRequest,
                            onClick = onClickDate
                        )
                    }
                }
            }
        }
    }
}

const val DateFormatRow = "DateFormatRow"

@Composable
fun DateFormatRow(
    item: String,
    onDismissRequest: () -> Unit,
    onClick: (Context, String) -> Unit
){
    val context = LocalContext.current
    val sdf = SimpleDateFormat(item, context.resources.configuration.locales[0])
    ClickableText(
        modifier = Modifier.padding(2.dp).testTag(DateFormatRow),
        text = AnnotatedString(sdf.format(Calendar.getInstance().time)),
        style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurface),
        onClick = {
            onClick(context, item)
            onDismissRequest()
        }
    )
}

@DefaultPreviews
@LanguagePreviews
@Composable
fun DateFormatDialogPreview(){
    FoodExpirationDatesTheme {
        Surface(
            color = MaterialTheme.colorScheme.surface
        ) {
            DateFormatDialog()
        }
    }
}