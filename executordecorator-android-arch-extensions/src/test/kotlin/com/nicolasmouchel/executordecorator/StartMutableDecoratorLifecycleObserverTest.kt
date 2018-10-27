package com.nicolasmouchel.executordecorator

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class StartMutableDecoratorLifecycleObserverTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private val lifecycle = mock(Lifecycle::class.java)
    private val owner = LifecycleOwner { lifecycle }
    private val decorated: Unit = Unit
    @Mock
    private lateinit var decorator: MutableDecorator<Unit>
    private lateinit var mutableDecoratorLifecycleObserver: StartMutableDecoratorLifecycleObserver<Unit>

    @Before
    fun setUp() {
        mutableDecoratorLifecycleObserver = StartMutableDecoratorLifecycleObserver(decorated, decorator)
    }

    @Test
    fun onStart_shouldSetDecorated() {
        mutableDecoratorLifecycleObserver.onStart(owner)

        then(decorator).should().mutate(decorated)
    }

    @Test
    fun onStop_shouldRemoveDecorated() {
        mutableDecoratorLifecycleObserver.onStop(owner)

        then(decorator).should().mutate(null)
    }

    @Test
    fun onStartAndMutate_shouldAddObserverOfTypeMutableDecoratorLifecycle() {
        decorator.onStart(LifecycleOwner { lifecycle }).mutate(decorated)
        then(lifecycle).should().addObserver(ArgumentMatchers.any(StartMutableDecoratorLifecycleObserver::class.java))
    }
}