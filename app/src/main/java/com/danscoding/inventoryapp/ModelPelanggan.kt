package com.danscoding.inventoryapp

class ModelPelanggan {

    var key: String? = null
    var nama: String? = null
    var domisili: String? = null
    var gender: String? = null

    constructor() {}

    constructor(namaPelanggan: String?, domisiliPelanggan: String?, genderPelanggan: String?) {
        nama = namaPelanggan
        domisili = domisiliPelanggan
        gender = genderPelanggan
    }
}