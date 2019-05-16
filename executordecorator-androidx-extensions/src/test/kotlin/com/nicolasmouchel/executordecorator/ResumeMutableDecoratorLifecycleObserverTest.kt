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

class ResumeMutableDecoratorLifecycleObserverTest {

    @get:Rule
    val rule: MockitoRule = MockitoJUnit.rule()

    private val lifecycle = mock(Lifecycle::class.java)
    private val owner = LifecycleOwner { lifecycle }
    private val decorated: Unit = Unit
    @Mock
    private lateinit var decorator: MutableDecorator<Unit>
    private lateinit var mutableDecoratorLifecycleObserver: ResumeMutableDecoratorLifecycleObserver<Unit>

    @Before
    fun setUp() {
        mutableDecoratorLifecycleObserver = ResumeMutableDecoratorLifecycleObserver(decorated, decorator)
    }

    @Test
    fun onResume_shouldSetDecorated() {
        mutableDecoratorLifecycleObserver.onResume(owner)

        then(decorator).should().mutate(decorated)
    }

    @Test
    fun onPause_shouldRemoveDecorated() {
        mutableDecoratorLifecycleObserver.onPause(owner)

        then(decorator).should().mutate(null)
    }

    @Test
    fun onResumeAndMutate_shouldAddObserverOfTypeMutableDecoratorLifecycle() {
        decorator.onResume(LifecycleOwner { lifecycle }).mutate(decorated)
        then(lifecycle).should().addObserver(ArgumentMatchers.any(ResumeMutableDecoratorLifecycleObserver::class.java))
    }
}