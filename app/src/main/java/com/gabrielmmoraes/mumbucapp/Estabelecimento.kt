package com.gabrielmmoraes.mumbucapp

data class Estabelecimento (
        val nomeFantasia: String,
        val tipoComercio: String,
        val telefone: String,
        val logradouro: String,
        val bairro: String,
        val CEP: String,
        val Lat: Float? = null,
        val Lng: Float? = null)