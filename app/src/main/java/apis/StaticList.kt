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
private fun parseDate(dateString: String): Date {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return format.parse(dateString) ?: Date()
}

fun loadStaticList(context: Context): MutableList<Task> {
    val tasks = mutableListOf<Task>()

    for (i in 1..10) {
        val title = context.getString(getTitleResourceId(i))
        val description = context.getString(getDescriptionResourceId(i))
        val createdAt = parseDate(context.getString(getCreatedAtResourceId(i)))
        val endsAt = parseDate(context.getString(getEndsAtResourceId(i)))
        val finish = context.resources.getBoolean(getFinishResourceId(i))

        tasks.add(Task(title, description, createdAt, endsAt, finish))
    }

    return tasks.sortedBy { it.createdAt }.toMutableList()
}

fun getTitleResourceId(index: Int): Int {
    return when (index) {
        1 -> R.string.task1_title
        2 -> R.string.task2_title
        3 -> R.string.task3_title
        4 -> R.string.task4_title
        5 -> R.string.task5_title
        6 -> R.string.task6_title
        7 -> R.string.task7_title
        8 -> R.string.task8_title
        9 -> R.string.task9_title
        10 -> R.string.task10_title
        else -> throw IllegalArgumentException("No hay un recurso de título para el índice $index")
    }
}

fun getDescriptionResourceId(index: Int): Int {
    return when (index) {
        1 -> R.string.task1_description
        2 -> R.string.task2_description
        3 -> R.string.task3_description
        4 -> R.string.task4_description
        5 -> R.string.task5_description
        6 -> R.string.task6_description
        7 -> R.string.task7_description
        8 -> R.string.task8_description
        9 -> R.string.task9_description
        10 -> R.string.task10_description
        else -> throw IllegalArgumentException("No hay un recurso de descripción para el índice $index")
    }
}

fun getCreatedAtResourceId(index: Int): Int {
    return when (index) {
        1 -> R.string.task1_created_at
        2 -> R.string.task2_created_at
        3 -> R.string.task3_created_at
        4 -> R.string.task4_created_at
        5 -> R.string.task5_created_at
        6 -> R.string.task6_created_at
        7 -> R.string.task7_created_at
        8 -> R.string.task8_created_at
        9 -> R.string.task9_created_at
        10 -> R.string.task10_created_at
        else -> throw IllegalArgumentException("No hay un recurso de fecha de creación para el índice $index")
    }
}

fun getEndsAtResourceId(index: Int): Int {
    return when (index) {
        1 -> R.string.task1_ends_at
        2 -> R.string.task2_ends_at
        3 -> R.string.task3_ends_at
        4 -> R.string.task4_ends_at
        5 -> R.string.task5_ends_at
        6 -> R.string.task6_ends_at
        7 -> R.string.task7_ends_at
        8 -> R.string.task8_ends_at
        9 -> R.string.task9_ends_at
        10 -> R.string.task10_ends_at
        else -> throw IllegalArgumentException("No hay un recurso de fecha de finalización para el índice $index")
    }
}

fun getFinishResourceId(index: Int): Int {
    return when (index) {
        1 -> R.bool.task1_finish
        2 -> R.bool.task2_finish
        3 -> R.bool.task3_finish
        4 -> R.bool.task4_finish
        5 -> R.bool.task5_finish
        6 -> R.bool.task6_finish
        7 -> R.bool.task7_finish
        8 -> R.bool.task8_finish
        9 -> R.bool.task9_finish
        10 -> R.bool.task10_finish
        else -> throw IllegalArgumentException("No hay un recurso de finalización para el índice $index")
    }
}
