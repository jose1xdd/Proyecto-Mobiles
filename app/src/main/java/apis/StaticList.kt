package apis

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.stringResource
import com.ufps.proyectomobile.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Task(
    val title: String,
    val description: String,
    val createdAt: Date,
    val endsAt: Date,
    var finish: Boolean
)

