package com.danscoding.inventoryapp

import android.os.Parcel
import android.os.Parcelable

data class Penjualan(val namaPelanggan: String?, val tanggal: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaPelanggan)
        parcel.writeString(tanggal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Penjualan> {
        override fun createFromParcel(parcel: Parcel): Penjualan {
            return Penjualan(parcel)
        }

        override fun newArray(size: Int): Array<Penjualan?> {
            return arrayOfNulls(size)
        }
    }
}
