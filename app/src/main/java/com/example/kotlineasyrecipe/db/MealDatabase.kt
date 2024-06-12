package com.example.kotlineasyrecipe.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlineasyrecipe.fragments.HomeFragment
import com.example.kotlineasyrecipe.models.Meal

// update version to notify database that I have changed things
@Database(entities = [Meal::class], version = 3, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase: RoomDatabase() {
    abstract fun mealDap(): MealDAO

    companion object {
        @Volatile
        var INSTANCE: MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, MealDatabase::class.java, "meal.db")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE as MealDatabase
        }
    }


}