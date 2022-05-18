package com.boos.stock.util

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }
