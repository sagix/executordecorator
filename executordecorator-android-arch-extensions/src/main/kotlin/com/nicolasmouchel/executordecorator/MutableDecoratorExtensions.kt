@file:JvmName("MutableDecoratorUtils")

package com.nicolasmouchel.executordecorator

import android.arch.lifecycle.DefaultLifecycleObserver
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner

typealias MutableDecoratorLifecycleObserver<T> = (T, MutableDecorator<T>) -> LifecycleObserver
typealias MutableDecoratorLifecycleObserverFactory<T> = () -> MutableDecoratorLifecycleObserver<T>

/**
 * Will create the [LifecycleObserver] mutating the decorated value between <b>onCreate</b> and <b>onDestroy</b>
 */
fun <T> observeOnCreate(): MutableDecoratorLifecycleObserver<T> = ::CreateMutableDecoratorLifecycleObserver

/**
 * Will create the [LifecycleObserver] mutating the decorated value between <b>onStart</b> and <b>onStop</b>
 */
fun <T> observeOnStart(): MutableDecoratorLifecycleObserver<T> = ::StartMutableDecoratorLifecycleObserver

/**
 * Will create the [LifecycleObserver] mutating the decorated value between <b>onResume</b> and <b>onPause</b>
 */
fun <T> observeOnResume(): MutableDecoratorLifecycleObserver<T> = ::ResumeMutableDecoratorLifecycleObserver

/**
 * Returns a [MutableDecorator] that respect the lifecycle given
 * by mutating the decorated value between <b>onCreate</b> and <b>onDestroy</b>
 * @param owner the lifecycle to respect
 */
fun <T> MutableDecorator<T>.onCreate(owner: LifecycleOwner): MutableDecorator<T> =
        on(owner, ::observeOnCreate)

/**
 * Returns a [MutableDecorator] that respect the lifecycle given
 * by mutating the decorated value between <b>onStart</b> and <b>onStop</b>
 * @param owner the lifecycle to respect
 */
fun <T> MutableDecorator<T>.onStart(owner: LifecycleOwner): MutableDecorator<T> =
        on(owner, ::observeOnStart)

/**
 * Returns a [MutableDecorator] that respect the lifecycle given
 * by mutating the decorated value between <b>onResume</b> and <b>onPause</b>
 * @param owner the lifecycle to respect
 */
fun <T> MutableDecorator<T>.onResume(owner: LifecycleOwner): MutableDecorator<T> =
        on(owner, ::observeOnResume)

/**
 * Returns a [MutableDecorator] that respect the lifecycle given
 * by mutating the decorated value following the factory
 * @param owner the lifecycle to respect
 * @param factory will create the [LifecycleObserver] with the decorator and decorated
 * @sample onCreate
 * @sample onStart
 * @sample onResume
 */
fun <T> MutableDecorator<T>.on(
        owner: LifecycleOwner, factory: MutableDecoratorLifecycleObserverFactory<T>
): MutableDecorator<T> {
    return object : MutableDecorator<T> by this {
        override fun mutate(decorated: T) {
            owner.lifecycle.addObserver(factory().invoke(decorated, this@on))
        }
    }
}

internal class CreateMutableDecoratorLifecycleObserver<T>(
        private val decorated: T, private val decorator: MutableDecorator<T>
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        decorator.mutate(decorated)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        decorator.mutate(null)
    }
}

internal class StartMutableDecoratorLifecycleObserver<T>(
        private val decorated: T, private val decorator: MutableDecorator<T>
) : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        decorator.mutate(decorated)
    }

    override fun onStop(owner: LifecycleOwner) {
        decorator.mutate(null)
    }
}

internal class ResumeMutableDecoratorLifecycleObserver<T>(
        private val decorated: T, private val decorator: MutableDecorator<T>
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        decorator.mutate(decorated)
    }

    override fun onPause(owner: LifecycleOwner) {
        decorator.mutate(null)
    }
}