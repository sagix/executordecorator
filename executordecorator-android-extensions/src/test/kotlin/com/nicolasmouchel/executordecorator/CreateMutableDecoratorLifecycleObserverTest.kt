package com.nicolasmouchel.executordecorator

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CreateMutableDecoratorLifecycleObserverTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private val lifecycle = mock(Lifecycle::class.java)
    private val owner = LifecycleOwner { lifecycle }
    private val decorated: Unit = Unit
    @Mock
    private lateinit var decorator: MutableDecorator<Unit>
    private lateinit var mutableDecoratorLifecycleObserver: CreateMutableDecoratorLifecycleObserver<Unit>

    @Before
    fun setUp() {
        mutableDecoratorLifecycleObserver = CreateMutableDecoratorLifecycleObserver(decorated, decorator)
    }

    @Test
    fun onCreate_shouldSetDecorated() {
        mutableDecoratorLifecycleObserver.onCreate(owner)

        then(decorator).should().mutate(decorated)
    }

    @Test
    fun onDestroy_shouldRemoveDecorated() {
        mutableDecoratorLifecycleObserver.onDestroy(owner)

        then(decorator).should().mutate(null)
    }

    @Test
    fun onCreateAndMutate_shouldAddObserverOfTypeMutableDecoratorLifecycle() {
        decorator.onCreate(owner).mutate(decorated)
        then(lifecycle).should().addObserver(ArgumentMatchers.any(CreateMutableDecoratorLifecycleObserver::class.java))
    }
}