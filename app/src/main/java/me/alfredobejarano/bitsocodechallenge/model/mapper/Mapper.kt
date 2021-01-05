package me.alfredobejarano.bitsocodechallenge.model.mapper

/**
 * Mapper
 */
interface Mapper<R, L> {
    fun map(item: R): L
}