package com.example.finalproject1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CartDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "cartDB"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "cartItems"
        const val COLUMN_PRODUCT_ID = "productId"
        const val COLUMN_QUANTITY = "quantity"
        const val COLUMN_PRICE = "price"
        const val COLUMN_TOTAL_PRICE = "totalPrice"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_PRODUCT_ID INTEGER PRIMARY KEY,
                $COLUMN_QUANTITY INTEGER,
                $COLUMN_PRICE REAL,
                $COLUMN_TOTAL_PRICE REAL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun insertCartItem(cartItem: Cart): Long {
        val values = ContentValues().apply {
            put(COLUMN_PRODUCT_ID, cartItem.productId)
            put(COLUMN_QUANTITY, cartItem.quantity)
            put(COLUMN_PRICE, cartItem.price)
            put(COLUMN_TOTAL_PRICE, cartItem.totalPrice)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun getAllCartItems(): List<Cart> {
        val cartItems = mutableListOf<Cart>()
        val cursor: Cursor = readableDatabase.query(TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val productId = getLong(getColumnIndexOrThrow(COLUMN_PRODUCT_ID))
                val quantity = getInt(getColumnIndexOrThrow(COLUMN_QUANTITY))
                val price = getDouble(getColumnIndexOrThrow(COLUMN_PRICE))
                val totalPrice = getDouble(getColumnIndexOrThrow(COLUMN_TOTAL_PRICE))
                cartItems.add(Cart(productId, quantity, price, totalPrice))
            }
        }
        cursor.close()
        return cartItems
    }

    fun updateCartItem(cartItem: Cart): Int {
        val values = ContentValues().apply {
            put(COLUMN_QUANTITY, cartItem.quantity)
            put(COLUMN_PRICE, cartItem.price)
            put(COLUMN_TOTAL_PRICE, cartItem.totalPrice)
        }
        val selection = "$COLUMN_PRODUCT_ID = ?"
        val selectionArgs = arrayOf(cartItem.productId.toString())
        return writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)
    }

    fun deleteCartItem(productId: Long): Int {
        val selection = "$COLUMN_PRODUCT_ID = ?"
        val selectionArgs = arrayOf(productId.toString())
        return writableDatabase.delete(TABLE_NAME, selection, selectionArgs)
    }
}
