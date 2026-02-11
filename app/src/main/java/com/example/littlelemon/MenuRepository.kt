package com.example.littlelemon

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuRepository(
    private val database: AppDatabase,
    private val networkDataSource: NetworkDataSource
) {
    fun getMenuItems(): Flow<List<MenuItem>> = flow {
        val items = database.menuDao().getAll()
        emit(items)
    }.flowOn(Dispatchers.IO)

    suspend fun refreshMenu(): Result<List<MenuItem>> {
        return try {
            val networkMenu = networkDataSource.fetchMenu()
            val menuItems = networkMenu.menu.map { it.toMenuItem() }

            database.menuDao().deleteAll()
            database.menuDao().insertAll(menuItems)

            Result.success(menuItems)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun isDatabaseEmpty(): Boolean {
        return database.menuDao().getAll().isEmpty()
    }
}