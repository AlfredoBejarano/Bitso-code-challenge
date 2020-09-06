package me.alfredobejarano.bitsocodechallenge.model

/**
 * Mapper
 */
interface Mapper<R, L> {
    fun map(item: R): L
}