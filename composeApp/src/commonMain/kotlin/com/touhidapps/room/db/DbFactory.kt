@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.touhidapps.room.db

import androidx.room.RoomDatabaseConstructor

expect object DbFactory: RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}
